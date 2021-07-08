package bank_api.core;

import bank_api.config.Configuration;
import bank_api.config.ConfigurationManager;
import bank_api.core.controller.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BaseServer {

    private HttpServer server;

    public BaseServer(Configuration conf) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(conf.getPort()), 0);
    }

    public void startServer() throws IOException {

//        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
//
//        HttpServer server = HttpServer.create(new InetSocketAddress(conf.getPort()), 0);
//        server.createContext("/exit", new CloseHandler());
        server.createContext("/CreateCard", new CreateCardHandler());
        server.createContext("/ShowCards", new ShowCardsHandler());
        server.createContext("/Deposit", new DepositHandler());
        server.createContext("/CheckBalance", new CheckBalanceHandler());
        server.start();
    }
}
