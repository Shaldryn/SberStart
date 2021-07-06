package examples_socket.tcp_ip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SampleServer extends Thread {
    Socket s;
    int num;

    public SampleServer(int num, Socket s) {
        this.num = num;
        this.s = s;

        //запускаем новый поток
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run() {
        try {
            //из сокета клиента берем поток входящих данных
            InputStream is = s.getInputStream();
            //и оттуда же - поток данных от сервера к клиенту
            OutputStream os = s.getOutputStream();
            //буффер данных в 64 килобайта
            byte[] buf = new byte[64 * 1024];
            //читаем 64Кб от клиента, результат - кол-во реально принятых данных
            int r = is.read(buf);
            //создаем строку полученную от клиента информацию
            String data = new String(buf, 0, r);
            //добавляем данные об адресе сокета
            data = "" + num + ": " + "\n" + data;
            //вывод данных
            os.write(data.getBytes(StandardCharsets.UTF_8));
            //завершение соединения
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            //счетчик подключений
            int i = 0;

            //сокет сервера
            ServerSocket server = new ServerSocket(3128, 0, InetAddress.getByName("localhost"));
            System.out.println("Server is started");

            //слушаем порт
            while (true) {
                //ждем новое подключение, после чего запускаем обработку клиента
                //в новый вычислительный поток и увеличиваем счетчик подключений
                new SampleServer(i, server.accept());
                i++;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
