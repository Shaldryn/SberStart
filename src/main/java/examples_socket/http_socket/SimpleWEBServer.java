package examples_socket.http_socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SimpleWEBServer extends Thread {
    Socket s;

    public SimpleWEBServer(Socket s) {
        this.s = s;

        //запускаем новый вычислительный поток
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run() {
        try {
            // из сокета клиента берём поток входящих данных
            InputStream is = s.getInputStream();
            // и оттуда же - поток данных от сервера к клиенту
            OutputStream os = s.getOutputStream();

            // буффер данных в 64 килобайта
            byte buf[] = new byte[64 * 1024];
            // читаем 64кб от клиента, результат - кол-во реально принятых данных
            int r = is.read(buf);

            // создаём строку, содержащую полученную от клиента информацию
            String request = new String(buf, 0, r);

            // получаем путь
            String path = getPath(request);

            // если из запроса не удалось выделить путь, то
            // возвращаем "400 Bad Request"
            if (path == null) {
                // первая строка ответа
                String response = "HTTP/1.1 400 Bad Request\n";

                // дата в GMT
                DateFormat df = DateFormat.getTimeInstance();
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                response = response + "Date: " + df.format(new Date()) + "\n";

                // остальные заголовки
                response = response
                        + "Connection: close\n"
                        + "Server: SimpleWEBServer\n"
                        + "Pragma: no-cache\n\n";

                // выводим данные:
                os.write(response.getBytes());

                // завершаем соединение
                s.close();

                // выход
                return;
            }

            // создаём ответ

            // первая строка ответа
            String response = "HTTP/1.1 200 OK\n";

            // дата создания в GMT
            DateFormat df = DateFormat.getTimeInstance();
            df.setTimeZone(TimeZone.getTimeZone("GMT"));

            // остальные заголовки
            response = response
                    + "Connection: close\n"
                    + "Server: SimpleWEBServer\n\n";

            // и сообщение
            response = response + "OK";

            // выводим заголовок:
            os.write(response.getBytes());

            // завершаем соединение
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        } // вывод исключений
    }

    // "вырезает" из HTTP заголовка URI ресурса и конвертирует его в filepath
    // URI берётся только для GET и POST запросов, иначе возвращается null
    protected String getPath(String header) {
        // ищем URI, указанный в HTTP запросе
        // URI ищется только для методов POST и GET, иначе возвращается null
        String URI = extract(header, "GET ", " "), path;
        if (URI == null) URI = extract(header, "POST ", " ");
        if (URI == null) return null;

        // если URI записан вместе с именем протокола
        // то удаляем протокол и имя хоста
        path = URI.toLowerCase();
        if (path.indexOf("http://", 0) == 0) {
            URI = URI.substring(7);
            URI = URI.substring(URI.indexOf("/", 0));
        } else if (path.indexOf("/", 0) == 0)
            URI = URI.substring(1); // если URI начинается с символа /, удаляем его

        // отсекаем из URI часть запроса, идущего после символов ? и #
        int i = URI.indexOf("?");
        if (i > 0) URI = URI.substring(0, i);
        i = URI.indexOf("#");
        if (i > 0) URI = URI.substring(0, i);

        return URI;
    }

    // "вырезает" из строки str часть, находящуюся между строками start и end
    // если строки end нет, то берётся строка после start
    // если кусок не найден, возвращается null
    // для поиска берётся строка до "\n\n" или "\r\n\r\n", если таковые присутствуют
    protected String extract(String str, String start, String end) {
        int s = str.indexOf("\n\n", 0), e;
        if (s < 0) s = str.indexOf("\r\n\r\n", 0);
        if (s > 0) str = str.substring(0, s);
        s = str.indexOf(start, 0) + start.length();
        if (s < start.length()) return null;
        e = str.indexOf(end, s);
        if (e < 0) e = str.length();
        return (str.substring(s, e)).trim();
    }

    public static void main(String[] args) {
        try {
            //сокет сервера
            ServerSocket server = new ServerSocket(3128, 0, InetAddress.getByName("localhost"));
            System.out.println("Server is started");

            //слушаем порт
            while (true) {
                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток
                new SimpleWEBServer(server.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
