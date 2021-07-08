package bank_api.core.controller;

import bank_api.dto.CardDTO;
import bank_api.service.BillService;
import bank_api.util.JsonService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class DepositHandler implements HttpHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String response = "";
        String data;
        int responseCode = 200;

        if (method.equals("POST")) {
            BillService billService = new BillService();
            CardDTO cardDTO = new CardDTO();
            try {
                cardDTO = JsonService.parse(exchange.getRequestBody(), CardDTO.class);
            } catch (IOException e) {
                LOGGER.error("Error parsing request body", e);
            }

            data = billService.depositById(cardDTO.getId(), cardDTO.getBillId(), cardDTO.getSum());

            if (data == null) {
                response = "Invalid id number";
                responseCode = 400;
            } else {
                response = data;
            }
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        writeResponse(response, responseCode, exchange);
        exchange.close();
    }

    private void writeResponse(String response, int responseCode, HttpExchange exchange) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(exchange.getResponseBody())) {
            outputStreamWriter.write(response);
            exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
        } catch (IOException e) {
            LOGGER.error("Error write response body", e);
        }
    }
}
