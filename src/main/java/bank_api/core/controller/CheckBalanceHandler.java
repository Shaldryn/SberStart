package bank_api.core.controller;

import bank_api.core.http.HttpParser;
import bank_api.core.http.HttpParsingException;
import bank_api.service.BillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
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
            HashMap<String, String> params = null;
            try {
                params = HttpParser.parseRequest(exchange);
            } catch (HttpParsingException e) {
                LOGGER.error("Error parsing http request", e);
            }

            if (URI.contains("billId")) {
                paramName = params.entrySet().
                        stream().
                        filter(e -> e.getKey().equals("billId")).
                        map(Map.Entry::getKey).
                        findFirst().get();
            } else {
                paramName = params.entrySet().
                        stream().
                        filter(e -> e.getKey().equals("cardId")).
                        map(Map.Entry::getKey).
                        findFirst().get();
            }

            switch (paramName) {
                case "billId":
                    Long billId = Long.parseLong(params.entrySet().
                            stream().
                            filter(e -> e.getKey().equals("billId")).
                            map(Map.Entry::getValue).
                            findFirst().get());
                    data = billService.getBalanceByBillId(billId);
                    if (data == null) {
                        response = "Invalid bill id";
                        responseCode = 404;
                    } else {
                        response = data;
                    }
                    break;
                case "cardId":
                    Long cardId = Long.parseLong(params.entrySet().
                            stream().
                            filter(e -> e.getKey().equals("cardId")).
                            map(Map.Entry::getValue).
                            findFirst().get());
                    data = billService.getBalanceByCardId(cardId);
                    if (data == null) {
                        response = "Invalid card id";
                        responseCode = 404;
                    } else {
                        response = data;
                    }
                    break;
            }
            exchange.sendResponseHeaders(responseCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        }
    }
}

