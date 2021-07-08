package bank_api.core.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CloseHandler implements HttpHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
