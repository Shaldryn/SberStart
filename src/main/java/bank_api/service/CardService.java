package bank_api.service;

import bank_api.config.Configuration;
import bank_api.config.ConfigurationManager;
import bank_api.core.controller.ShowCardsHandler;
import bank_api.dao.CardDAOImp;
import bank_api.entity.Card;
import bank_api.util.JsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);
    private CardDAOImp cardDAO;

    public CardService() {
        this.cardDAO = new CardDAOImp();
    }

    public String getCardsByCustomerId(Long id) {
        List<Card> cards = cardDAO.getAllCardsByCustomerId(id);
        if (cards.isEmpty()) {
            return null;
        }
        String stringJson = "";
        try {
            stringJson = JsonService.stringifyPretty(Collections.singletonList(cards));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error process to json", e);
        }
        return stringJson;
    }

    public String createCardByBillId(Long id) {
        Long resultNewNumberCard = cardDAO.saveCardByBillId(id);
        if (resultNewNumberCard == null) {
            return null;
        }
        Card card;
        card = getCardByCardNumber(resultNewNumberCard);
        String stringJson = "";
        try {
            stringJson = JsonService.stringifyPretty(card);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error process to json", e);
        }
        return stringJson;
    }

    private Card getCardByCardNumber(Long cardNumber) {
        return cardDAO.getCardByNumber(cardNumber);
    }

}
