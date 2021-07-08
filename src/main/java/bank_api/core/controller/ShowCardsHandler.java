package bank_api.core.controller;

import bank_api.core.http.HttpParser;
import bank_api.core.http.HttpParsingException;
import bank_api.service.CardService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ShowCardsHandler implements HttpHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String URI = exchange.getRequestURI().toString();
        String response;
        int responseCode = 200;

        if (method.equals("GET")) {
            CardService cardService = new CardService();
            HashMap<String, String> params = null;
            try {
                params = HttpParser.parseRequest(exchange);
            } catch (HttpParsingException e) {
                LOGGER.error("Error parsing http request", e);
            }

            if (URI.contains("customerId")) {
                Long customerId = Long.parseLong(params.entrySet().
                        stream().
                        filter(e -> e.getKey().equals("customerId")).
                        map(Map.Entry::getValue).
                        findFirst().get());
                String data = cardService.getCardsByCustomerId(customerId);
                if (data == null) {
                    response = "No cards";
                    responseCode = 400;
                } else {
                    response = data;
                }
                writeResponse(response, responseCode, exchange);
            }
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
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
