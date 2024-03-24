package ro.tuc.DataAccess;

import ro.tuc.Connection.ConnectionFactory;
import ro.tuc.Model.Client;
import ro.tuc.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ProductDAO extends AbstractDAO<Product>{
    private final static String findByMinPriceStatementString = "SELECT * FROM product where pret >= ?";
    private final static String findByMaxPriceStatementString = "SELECT * FROM product where pret <= ?";

    public List<Product> findProductsByMinPrice(int cond){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findByMinPriceStatementString);
            statement.setDouble(1, cond);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public List<Product> findProductsByMaxPrice(int cond){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findByMaxPriceStatementString);
            statement.setObject(1, cond);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
