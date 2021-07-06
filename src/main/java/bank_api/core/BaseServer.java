package bank_api.core;

import bank_api.config.Configuration;
import bank_api.config.ConfigurationManager;
import bank_api.core.handlers.AddCardHandler;
import bank_api.core.handlers.CheckBalanceHandler;
import bank_api.core.handlers.DepositHandler;
import bank_api.core.handlers.ShowCardsHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BaseServer {

    public void startServer() throws IOException {

        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        HttpServer server = HttpServer.create(new InetSocketAddress(conf.getPort()), 0);
//        server.createContext("/addCard", new AddCardHandler());
        server.createContext("/getCards", new ShowCardsHandler());
//        server.createContext("/deposit", new DepositHandler());
//        server.createContext("/checkBalance", new CheckBalanceHandler());
        server.start();
    }

}
