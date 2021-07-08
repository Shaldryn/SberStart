package bank_api.service;

import bank_api.core.controller.ShowCardsHandler;
import bank_api.dao.BillDAOImp;
import bank_api.entity.Bill;
import bank_api.util.JsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BillService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);
    private final BillDAOImp billDAO;

    public BillService() {
        this.billDAO = new BillDAOImp();
    }

    public String getBalanceById(Long id, Long billId) {
        Bill bill = null;
        if (id == null) {
            if (billDAO.getById(billId).isPresent()) {
                bill = billDAO.getById(billId).get();
            }
        } else {
            if (billDAO.getByCardId(id).isPresent()) {
                bill = billDAO.getByCardId(id).get();
            }
        }

        if (bill == null) {
            return null;
        }

        String stringJson = null;
        try {
            stringJson = JsonService.stringifyPretty(bill);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error process to json", e);
        }
        return stringJson;
    }

    public String depositById(Long id, Long billId, BigDecimal sum) {
        Bill bill = null;
        if (id == null) {
            if (billDAO.getById(billId).isPresent()) {
                bill = billDAO.getById(billId).get();
            }
        } else {
            if (billDAO.getByCardId(id).isPresent()) {
                bill = billDAO.getByCardId(id).get();
            }
        }

        if (bill == null) {
            return null;
        }

        bill.setBalance(bill.getBalance().add(sum));
        billDAO.update(bill);
        String stringJson = null;
        try {
            stringJson = JsonService.stringifyPretty(bill);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error process to json", e);
        }
        return stringJson;
    }

}
