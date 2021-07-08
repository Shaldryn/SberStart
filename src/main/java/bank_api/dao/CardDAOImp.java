package bank_api.dao;

import bank_api.entity.Card;
import bank_api.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardDAOImp extends ConnectionManager implements CardDAO {

    @Override
    public List<Card> getAllCardsByCustomerId(Long id) {
        String sqlQuery = "SELECT card.id, card.bill_id, card.card_number\n" +
                "FROM cards card\n" +
                "INNER JOIN bills bill ON card.bill_id = bill.id where bill.customer_id = ?";

        List<Card> cards = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setLong(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getLong("id"));
                card.setBillId(resultSet.getLong("bill_id"));
                card.setCardNumber(resultSet.getLong("card_number"));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Optional<Card> getById(long id) {
        String sqlQuery = "SELECT * FROM cards WHERE id = ?";

        Card card = new Card();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                card.setId(resultSet.getLong("id"));
                card.setBillId(resultSet.getLong("bill_id"));
                card.setCardNumber(resultSet.getLong("card_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(card);
    }

    @Override
    public List<Card> getAll() {
        String sqlQuery = "SELECT * FROM cards";

        List<Card> cards = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getLong("id"));
                card.setBillId(resultSet.getLong("bill_id"));
                card.setCardNumber(resultSet.getLong("card_number"));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Card getCardByNumber(Long number) {
        String sqlQuery = "SELECT * FROM cards WHERE card_number = ?";

        Card card = new Card();
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, number);

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                card.setId(resultSet.getLong("id"));
                card.setBillId(resultSet.getLong("bill_id"));
                card.setCardNumber(number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public void save(Card card) {
        String sqlQuery = "INSERT INTO cards (bill_id, card_number) VALUES(?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, card.getBillId());
            prepareStatement.setLong(2, card.getCardNumber());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long saveCardByBillId(Long id) {
        String sqlQuery = "INSERT INTO cards (bill_id) VALUES(?)";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery, new String[]{"card_number"})) {

            prepareStatement.setLong(1, id);

            prepareStatement.executeUpdate();
            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Card card) {
        String sqlQuery = "UPDATE cards SET bill_id=?, card_number=? WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, card.getBillId());
            prepareStatement.setLong(2, card.getCardNumber());
            prepareStatement.setLong(3, card.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sqlQuery = "DELETE FROM cards WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {

            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
