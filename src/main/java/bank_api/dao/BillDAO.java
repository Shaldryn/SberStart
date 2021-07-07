package bank_api.dao;

import bank_api.entity.Bill;

import java.util.Optional;

public interface BillDAO extends DAO<Bill> {

    Optional<Bill> getByCardId(Long id);

}
