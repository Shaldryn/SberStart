package examples_socket.integration_test;

import bank_api.config.ConfigurationManager;
import bank_api.core.BaseServer;
import bank_api.entity.Bill;
import bank_api.entity.Card;
import bank_api.service.BillService;
import bank_api.service.CardService;
import bank_api.util.ConnectionManager;
import bank_api.util.JsonService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetPostIT {

    private static BillService billService;
    private static CardService cardService;
    private final static Logger LOGGER = LoggerFactory.getLogger(GetPostIT.class);

    @BeforeAll
    public static void beforeOneTest() {
        billService = new BillService();
        cardService = new CardService();

        System.out.println("Start test");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/properties.json");

        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.getConnection();
        LOGGER.info("TEST: Connection DB for test");
        connectionManager.openWebInterfaceDB();
        connectionManager.createDB();

        try {
            BaseServer server = new BaseServer(ConfigurationManager.getInstance().getCurrentConfiguration());
            server.startServer();
        } catch (IOException e) {
            LOGGER.error("TEST: Error start server", e);
        }
    }

    @Before
    public void beforeEachTest() {

    }

    @After
    public void afterEachTest() {
        LOGGER.info("TEST: End");
    }

    @Test
    void getCardsByCustomerId_GET() throws IOException {
        String address = "http://localhost:8002/ShowCards?customerId=1";
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String input;
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((input = in.readLine()) != null) {
            sb.append(input);
        }
        in.close();
        String actual = sb.toString();
        List<Card> cardsActual = JsonService.parse(actual, List.class);

        String expected = cardService.getCardsByCustomerId(1L);
        List<Card> cardExpected = JsonService.parse(expected, List.class);

//        String expected = "[ [ {  \"id\" : 1,  \"billId\" : 1,  \"cardNumber\" : 4123456789012340} ] ]";
        LOGGER.info("actual: " + cardsActual);
        LOGGER.info("expected: " + cardExpected);
        Assertions.assertEquals(cardsActual, cardExpected);
    }

    @Test
    void checkBalanceByBillId_GET() throws IOException {
        String address = "http://localhost:8002/CheckBalance?billId=2";
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String input;
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((input = in.readLine()) != null) {
            sb.append(input);
        }
        in.close();

        String actual = sb.toString();
        Bill billActual = JsonService.parse(actual, Bill.class);

        String expected = billService.getBalanceById(null, 2L);
        Bill billExpected = JsonService.parse(expected, Bill.class);


//        String expected = "{  \"id\" : 2,  \"customerId\" : 2,  \"billNumber\" : 408178103520301011,  \"balance\" : 100.00}";
        LOGGER.info("actual: " + billActual);
        LOGGER.info("expected: " + billExpected);
        Assertions.assertEquals(billActual, billExpected);
    }

    @Test
    void checkBalanceByCardId_GET() throws IOException {
        String address = "http://localhost:8002/CheckBalance?cardId=4";
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String input;
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((input = in.readLine()) != null) {
            sb.append(input);
        }
        in.close();
        String actual = sb.toString();
        String expected = "{  \"id\" : 3,  \"customerId\" : 2,  \"billNumber\" : 408178103520301012,  \"balance\" : 350.00}";
        LOGGER.info("actual: " + actual);
        LOGGER.info("expected: " + expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createCardByBillId_POST() throws IOException {

        String address = "http://localhost:8002/CreateCard";
        URL url = new URL(address);
        String requestBody = "{\"billId\":2}";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(requestBody);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        String actual = response.toString();
        String expected = "{\"id\" : 7,\"billId\" : 2,\"cardNumber\" : 4123456789012346}";

        LOGGER.info("actual: " + actual);
        LOGGER.info("expected: " + expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void depositByBillId_POST() throws IOException {

        String address = "http://localhost:8002/Deposit";
        URL url = new URL(address);
        String requestBody = "{\"billId\":2, \"sum\":400}";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(requestBody);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        String actual = response.toString();
        String expected = "{\"id\" : 2,\"customerId\" : 2,\"billNumber\" : 408178103520301011,\"balance\" : 500.00}";

        LOGGER.info("actual: " + actual);
        LOGGER.info("expected: " + expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void depositByCardId_POST() throws IOException {

        String address = "http://localhost:8002/Deposit";
        URL url = new URL(address);
        String requestBody = "{\"cardId\":1, \"sum\":200}";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(requestBody);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        String actual = response.toString();
        String expected = "{\"id\" : 1,\"customerId\" : 1,\"billNumber\" : 408178103520301010,\"balance\" : 200.00}";

        LOGGER.info("actual: " + actual);
        LOGGER.info("expected: " + expected);

        Assertions.assertEquals(expected, actual);
    }
}
