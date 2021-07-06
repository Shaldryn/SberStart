package bank_api.dao;

import bank_api.entity.Bill;
import bank_api.entity.Card;
import bank_api.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillDAOImp extends ConnectionManager implements BillDAO {
    @Override
    public Optional<Bill> getById(long id) {
        String sqlQuery = "SELECT * FROM bills WHERE id = ?";

        Bill bill = new Bill();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                bill.setId(resultSet.getLong("id"));
                bill.setCustomerId(resultSet.getLong("customer_id"));
                bill.setBillNumber(resultSet.getBigDecimal("bill_number"));
                bill.setBalance(resultSet.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(bill);
    }

    @Override
    public List<Bill> getAll() {
        String sqlQuery = "SELECT * FROM bills";

        List<Bill> bills = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getLong("id"));
                bill.setCustomerId(resultSet.getLong("customer_id"));
                bill.setBillNumber(resultSet.getBigDecimal("bill_number"));
                bill.setBalance(resultSet.getBigDecimal("balance"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public void save(Bill bill) {
        String sqlQuery = "INSERT INTO bills (customer_id, bill_number, balance) VALUES(?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, bill.getCustomerId());
            prepareStatement.setBigDecimal(2, bill.getBillNumber());
            prepareStatement.setBigDecimal(3, bill.getBalance());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bill bill) {
        String sqlQuery = "UPDATE bills SET customer_id=?, bill_number=?, balance=? WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, bill.getCustomerId());
            prepareStatement.setBigDecimal(2, bill.getBillNumber());
            prepareStatement.setBigDecimal(3, bill.getBalance());
            prepareStatement.setLong(4, bill.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sqlQuery = "DELETE FROM bills WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
