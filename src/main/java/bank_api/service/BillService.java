package bank_api.service;

import bank_api.core.controller.ShowCardsHandler;
import bank_api.dao.BillDAOImp;
import bank_api.entity.Bill;
import bank_api.util.JsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCardsHandler.class);
    private BillDAOImp billDAO;

    public BillService() {
        this.billDAO = new BillDAOImp();
    }

    public String getBalanceByBillId(Long id) {
        Bill bill;
        if (billDAO.getById(id).isPresent()) {
            bill = billDAO.getById(id).get();
        } else {
            return null;
        }
        String stringJson = "";
        try {
            stringJson = JsonService.stringifyPretty(bill);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error process to json", e);
        }
        return stringJson;
    }

    public String getBalanceByCardId(Long id) {
        Bill bill;
        if (billDAO.getByCardId(id).isPresent()) {
            bill = billDAO.getByCardId(id).get();
        } else {
            return null;
        }
        String stringJson = "";
        try {
            stringJson = JsonService.stringifyPretty(bill);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error process to json", e);
        }
        return stringJson;
    }

}
