package bank_api.core.handlers;

import bank_api.dao.CardDAOImp;
import bank_api.entity.Customer;
import bank_api.util.ConnectionManager;
import bank_api.util.JsonService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ShowCardsHandler implements HttpHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
