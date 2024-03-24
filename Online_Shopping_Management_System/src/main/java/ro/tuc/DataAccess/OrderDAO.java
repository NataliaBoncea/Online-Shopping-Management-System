package ro.tuc.DataAccess;

import ro.tuc.Connection.ConnectionFactory;
import ro.tuc.Model.Client;
import ro.tuc.Model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class OrderDAO extends AbstractDAO<Orders>{
    private final static String countStatementString = "SELECT COUNT(*) FROM orders";
    private final static String priceSumStatementString = "SELECT SUM(pret_total) FROM orders WHERE id_client = ?";
    private final static String deleteAllStatementString = "DELETE FROM orders WHERE id_client > 0";
    private int rowCount;
    private double priceSum;
    public int count() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(countStatementString);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

            return rowCount;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:count " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    public double priceSum(int idClient) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(priceSumStatementString);
            statement.setDouble(1, idClient);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                priceSum = resultSet.getDouble(1);
            }

            return priceSum;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:priceSum " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    public void deleteAllOrders() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(deleteAllStatementString);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
