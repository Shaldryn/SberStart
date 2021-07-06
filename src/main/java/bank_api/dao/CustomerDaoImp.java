package bank_api.dao;

import bank_api.entity.Customer;
import bank_api.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImp extends ConnectionManager implements CustomerDao {

    @Override
    public Optional<Customer> getById(long id) {

        String sqlQuery = "SELECT id, name, login, password FROM customers WHERE id = ?";

        Customer customer = new Customer();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setLogin(resultSet.getString("login"));
                customer.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> getAll() {

        String sqlQuery = "SELECT id, name, login, password FROM customers";

        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setLogin(resultSet.getString("login"));
                customer.setPassword(resultSet.getString("password"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void save(Customer customer) {
        String sqlQuery = "INSERT INTO customers (name, login, password) VALUES(?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setString(1, customer.getName());
            prepareStatement.setString(2, customer.getLogin());
            prepareStatement.setString(3,customer.getPassword());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        String sqlQuery = "UPDATE customers SET name=?, login=?, password=? WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setString(1, customer.getName());
            prepareStatement.setString(2, customer.getLogin());
            prepareStatement.setString(3, customer.getPassword());
            prepareStatement.setLong(4, customer.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sqlQuery = "DELETE FROM customers WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
