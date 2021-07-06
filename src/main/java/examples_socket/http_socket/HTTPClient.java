package examples_socket.http_socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HTTPClient {

    // первый аргумент - имя файла, содержащего HTTP запрос
    // предполагается, что запрос не будет больше 64 килобайт
    // второй - имя файла, куда будет слит ответ сервера
    public static void main(String[] args) {

        //Scanner sc = new Scanner(System.in);
        String header = "GET http://www.devresource.org HTTP/1.1\n" +
                "Host: localhost:3128\n" +
                "User-Agent: HTTPClient\n" +
                "\n" +
                "\n";
//        while (sc.hasNext()){
//            header += sc.nextLine();
//        }

        try {
            byte[] buf = new byte[64 * 1024];
            int r;

            //читаем файл с запросом в переменную header
//            FileInputStream fis = new FileInputStream(args[0]);
//            r = fis.read(buf);
//            String header = new String(buf, 0, r);
//            fis.close();

            // выделяем из строки запроса хост, порт и URL ресурса
            // для выделения используется специальнонаписанная ф-ия extract
            String host = extract(header, "Host:", "\n");

            if (host == null) {
                System.out.println("invalid request:\n" + header);
                return;
            }

            //находим порт сервера, по умолчанию он - 80
            int indexPort = host.indexOf(":", 0);
            int port;
            if (indexPort < 0) {
                port = 80;
            } else {
                port = Integer.parseInt(host.substring(indexPort + 1));
                host = host.substring(0, indexPort);
            }
            //открываем сокет до сервера
            Socket s = new Socket(host, port);
            //пишем туда HTTP request
            s.getOutputStream().write(header.getBytes(StandardCharsets.UTF_8));
            //получаем поток данных от сервера
            InputStream is = s.getInputStream();
            //открываем для записи файл, куда будет слит лог
//            FileOutputStream fos = new FileOutputStream(args[1]);
            String out = "";
            //читаем ответ сервера, одновременно сливая его в открытый файл
            r = 1;
            while (r > 0) {
                r = is.read(buf);
                if (r > 0) {
                    //fos.write(buf, 0, r);
                    out = new String(buf, 0, r);
                }
            }
            System.out.println(out);
            //fos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // "вырезает" из строки str часть, находящуюся между строками start и end
    // если строки end нет, то берётся строка после start
    // если кусок не найден, возвращается null
    // для поиска берётся строка до "\n\n" или "\r\n\r\n", если таковые присутствуют
    protected static String extract(String str, String start, String end) {
        int s = str.indexOf("\n\n", 0), e;
        if (s < 0) {
            s = str.indexOf("\r\n\r\n", 0);
        }
        if (s > 0) {
            str = str.substring(0, s);
        }
        s = str.indexOf(start, 0) + start.length();
        if (s < start.length()) {
            return null;
        }
        e = str.indexOf(end, s);
        if (e < 0) {
            e = str.length();
        }
        return (str.substring(s, e)).trim();
    }
}
