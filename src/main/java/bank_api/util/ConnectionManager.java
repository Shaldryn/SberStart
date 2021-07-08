package bank_api.util;

import bank_api.config.Configuration;
import bank_api.config.ConfigurationManager;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectionManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    private final Configuration conf = getConfiguration();
    private Connection connection;

    public Connection getConnection() {
        try {
            Class.forName(conf.getJdbcDriverClassName());
            connection = DriverManager.getConnection(conf.getJdbcUrl(), conf.getJdbcUser(), conf.getJdbcPass());
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Connection ERROR", e);
        }
        return connection;
    }

    public void createDB() {
        InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream("data_init.sql");
        StringBuilder stringBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (in, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            in.close();
        } catch (IOException e) {
            LOGGER.error("Error read file", e);
        }

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(stringBuilder.toString());
            LOGGER.info("Create and initialize DB");
        } catch (SQLException e) {
            LOGGER.error("Error execute statement", e);
        }
    }

    public void openWebInterfaceDB() {
        Connection finalDbConnection = connection;
        Runnable runServer = () -> {
            try {
                Server.startWebServer(finalDbConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        Thread threadServer = new Thread(runServer);
        threadServer.start();
        LOGGER.info("Start web interface H2");
    }

    private Configuration getConfiguration() {
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/properties.json");
        return ConfigurationManager.getInstance().getCurrentConfiguration();
    }

}
