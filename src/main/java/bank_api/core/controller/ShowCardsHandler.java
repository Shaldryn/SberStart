package bank_api.core.controller;

import bank_api.core.http.HttpParser;
import bank_api.core.http.HttpParsingException;
import bank_api.service.CardService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
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
                } else {
                    response = data;
                }

                if (response.equals("No cards")) {
                    responseCode = 204;
                }
                exchange.sendResponseHeaders(responseCode, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.flush();
                os.close();
            }
        }
    }
}
