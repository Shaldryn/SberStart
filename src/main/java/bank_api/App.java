package bank_api;

import bank_api.core.BaseServer;
import bank_api.util.ConnectionManager;

import java.io.IOException;

import bank_api.config.Configuration;
import bank_api.config.ConfigurationManager;

public class App {

    public static void main(String[] args) {

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/properties.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.getConnection();
        connectionManager.openWebInterfaceDB();
        connectionManager.createDB();

        try {
            new BaseServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
