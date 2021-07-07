package examples_socket.gubernik.bank;

import examples_socket.gubernik.bank.server.BaseServer;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) {
        try {
            new BaseServer().startServer();
        } catch (IOException e) {
            System.out.println("Ошибка старта сервера");
        }
    }
}
