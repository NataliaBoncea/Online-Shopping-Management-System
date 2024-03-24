package ro.tuc.DataAccess;

import com.mysql.cj.log.NullLogger;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import ro.tuc.BusinessLogic.ProductBLL;
import ro.tuc.Connection.ConnectionFactory;
import ro.tuc.Model.Bill;
import ro.tuc.Model.Orders;
import ro.tuc.Model.Product;

import java.beans.PropertyDescriptor;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class BillDAO{
    protected static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO log_table (id,nume,adresa,telefon,produs,cantitate,pret)"
            + " VALUES (?,?,?,?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM log_table";
    private final static String countStatementString = "SELECT COUNT(*) FROM log_table";

    public ArrayList<Bill> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<Bill> list = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findStatementString);
            rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String adresa = rs.getString("adresa");
                String telefon = rs.getString("telefon");
                String produs = rs.getString("produs");
                int cantitate = rs.getInt("cantitate");
                double pret = rs.getDouble("pret");
                list.add(new Bill(id, nume, adresa, telefon, produs, cantitate, pret, 0, null));
            }

            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public static int insert(Bill bill) {
        Connection dbConnection = ConnectionFactory.getConnection();
        ProductBLL productBLL = new ProductBLL();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, bill.id());
            insertStatement.setString(2, bill.numeClient());
            insertStatement.setString(3, bill.adresa());
            insertStatement.setString(4, bill.telefon());
            insertStatement.setString(5, bill.produs());
            insertStatement.setInt(6, bill.cantitate());
            insertStatement.setDouble(7, bill.pret());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static int count() {
        int rowCount = 0;
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
            LOGGER.log(Level.WARNING, "BillDAO:count " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }
}
