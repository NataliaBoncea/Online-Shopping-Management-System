package ro.tuc.DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.tuc.Connection.ConnectionFactory;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    private String createInsertQuery(ArrayList<String> fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" ( ");
        for(int i=0;i< fields.size()-1;i++){
            sb.append(fields.get(i)+ ", ");
        }
        sb.append(fields.get(fields.size()-1));
        sb.append(" ) ");
        sb.append(" VALUES ( ");
        for(int i=0;i< fields.size()-1;i++){
            sb.append("?, ");
        }
        sb.append("? )");
        return sb.toString();
    }

    public String createUpdateQuery(String condField, ArrayList<String> setFields){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for(int i=0;i< setFields.size()-1;i++){
            sb.append(setFields.get(i)+ " = ?, ");
        }
        sb.append(setFields.get(setFields.size()-1)+ " = ?");
        sb.append(" WHERE " + condField + " =?");
        return sb.toString();
    }
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * <p>Se extrag toate elementele dintr-un tabel
     * </p>
     * @return lista de obiecte extrase
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * <p>Se extrag toate elementele in functie de un id dat
     * </p>
     * @param id id-ul dupa care se face filtrarea
     * @return obiectul T cu id = id
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | SecurityException | IllegalArgumentException | IllegalAccessException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * <p>Se insereaza obiectul t in tabel
     * </p>
     * @param t obiectul de inserat
     * @return id-ul obiectului inserat
     */
    public int insert(T t) {
        Connection dbConnection = ConnectionFactory.getConnection();
        ArrayList<String> fileds = new ArrayList<>();
        PreparedStatement insertStatement = null;
        for (Field field : type.getDeclaredFields()) {
            fileds.add(field.getName());
        }
        String query = createInsertQuery(fileds);
        int parameterIndex = 1;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (Field field : type.getDeclaredFields()) {
                String getterMethodName = "get" + field.getName().substring(0, 1).toUpperCase() +
                        field.getName().substring(1);
                Method getterMethod = t.getClass().getMethod(getterMethodName);
                insertStatement.setObject(parameterIndex++, getterMethod.invoke(t));
            }
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * <p>Se actualizeaza obiectul cu id-ul lui t din tabel cu datele noului obiect t
     * </p>
     * @param t obiectul nou
     * @return obiectul nou
     */
    public T update(T t) {
        Connection dbConnection = ConnectionFactory.getConnection();
        ArrayList<String> fields = new ArrayList<>();
        PreparedStatement updateStatement = null;
        for (Field field : type.getDeclaredFields()) {
            fields.add(field.getName());
        }
        String query = createUpdateQuery("id",fields);
        int parameterIndex = 1;
        int updatedId = -1;
        try {
            updateStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for (Field field : type.getDeclaredFields()) {
                String getterMethodName = "get" + field.getName().substring(0, 1).toUpperCase() +
                        field.getName().substring(1);
                Method getterMethod = t.getClass().getMethod(getterMethodName);
                updateStatement.setObject(parameterIndex++, getterMethod.invoke(t));
            }
            String getterMethodName = "get" + type.getDeclaredFields()[0].getName().substring(0, 1).toUpperCase() +
                    type.getDeclaredFields()[0].getName().substring(1);
            Method getterMethod = t.getClass().getMethod(getterMethodName);
            updateStatement.setObject(parameterIndex, getterMethod.invoke(t));
            updateStatement.executeUpdate();

        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return t;
    }

    /**
     * <p>Se sterge din tabel elementul cu id-ul dat ca parametru
     * </p>
     * @param id id obiectului care trebuie sters
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * <p>Se extrag toate elementele in functie un atribut si un parametru de tip int
     * </p>
     * @param field  atributul obiectului
     * @param cond parametrul de tip int dupa care se face filtrarea
     * @return lista de obiecte care au atributul field = cond
     */
    public List<T> findByInt(String field, int cond) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, cond);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByInt " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * <p>Se extrag toate elementele in functie un atribut si un parametru de tip String
     * </p>
     * @param field  atributul obiectului
     * @param cond parametrul de tip String dupa care se face filtrarea
     * @return lista de obiecte care au atributul field = cond
     */
    public List<T> findByString(String field, String cond) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, cond);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByString " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}

