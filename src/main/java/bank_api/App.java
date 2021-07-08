package bank_api;

import bank_api.core.BaseServer;
import bank_api.util.ConnectionManager;

import java.io.IOException;

import bank_api.config.Configuration;
import bank_api.config.ConfigurationManager;
import examples_socket.http_server.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/properties.json");

        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.getConnection();
        LOGGER.info("Connection DB");
        connectionManager.openWebInterfaceDB();
        connectionManager.createDB();

        try {
            BaseServer server = new BaseServer(ConfigurationManager.getInstance().getCurrentConfiguration());
            server.startServer();
        } catch (IOException e) {
            LOGGER.error("Error start server", e);
        }

    }
}
