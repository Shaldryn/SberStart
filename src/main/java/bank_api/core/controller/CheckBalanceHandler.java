package bank_api.core.controller;

import bank_api.core.http.HttpParser;
import bank_api.core.http.HttpParsingException;
import bank_api.dto.CardDTO;
import bank_api.service.BillService;
import bank_api.util.JsonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CheckBalanceHandler implements HttpHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String URI = exchange.getRequestURI().toString();
        String response = "";
        String paramName;
        String data;
        int responseCode = 200;

        if (method.equals("GET")) {
            BillService billService = new BillService();
            HashMap<String, String> params = new HashMap<>();
            CardDTO cardDTO;
            try {
                params = HttpParser.parseRequest(exchange);
            } catch (HttpParsingException e) {
                LOGGER.error("Error parsing http request", e);
            }
            cardDTO = JsonService.parse(JsonService.stringify(params), CardDTO.class);

            data = billService.getBalanceById(cardDTO.getId(), cardDTO.getBillId());

            if (data == null) {
                response = "Invalid id number";
                responseCode = 400;
            } else {
                response = data;
            }

            writeResponse(response, responseCode, exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
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

