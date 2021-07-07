package bank_api.dao;

import bank_api.entity.Card;

import java.util.List;

public interface CardDAO extends DAO<Card>{

    List<Card> getAllCardsByCustomerId(Long id);

    Long saveCardByBillId(Long id);

    Card getCardByNumber(Long number);

}
