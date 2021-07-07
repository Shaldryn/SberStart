package bank_api.core.controller;

import bank_api.dto.CardDTO;
import bank_api.service.CardService;
import bank_api.util.JsonService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

public class CreateCardHandler implements HttpHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String response;
        int responseCode = 200;

        if (method.equals("POST")) {
            CardService cardService = new CardService();
            CardDTO cardDTO = new CardDTO();
            try {
                cardDTO = JsonService.parse(exchange.getRequestBody(), CardDTO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String data = cardService.createCardByBillId(cardDTO.getId());

            if (data == null) {
                response = "Invalid bill id";
            } else {
                response = data;
            }

            if (response.equals("Invalid bill id")) {
                responseCode = 404;
            }
            exchange.sendResponseHeaders(responseCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        }
    }
}

