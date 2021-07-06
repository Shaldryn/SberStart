package examples_socket.tcp_ip;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SampleClient extends Thread {
    public static void main(String[] args) {
        String out = "";
        try {
            //Открываем сокет и коннектимся к localhost:3128
            //получаем сокет клиента
            Socket s = new Socket("localhost", 3128);

            //берем поток вывода и выводим туда первый аргумент
            //заданный при вызове, адрес открытого сокета и его порт
            out += "\n" + s.getInetAddress().getHostAddress()
                    + ":" + s.getLocalPort();
            s.getOutputStream().write(out.getBytes(StandardCharsets.UTF_8));

            //читаем ответ
            byte[] buf = new byte[64 * 1024];
            int r = s.getInputStream().read(buf);
            String data = new String(buf, 0, r);

            //выводим ответ в консоль
            System.out.println(data);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
