package ro.tuc.BusinessLogic;

import ro.tuc.BusinessLogic.Validators.*;
import ro.tuc.DataAccess.ClientDAO;
import ro.tuc.DataAccess.OrderDAO;
import ro.tuc.DataAccess.ProductDAO;
import ro.tuc.Model.Client;
import ro.tuc.Model.Orders;
import ro.tuc.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class OrderBLL {
    private List<Validator<Orders>> validators;
    public OrderBLL(){
        validators = new ArrayList<Validator<Orders>>();
        validators.add(new OrderQuantityValidator());
    }
    public List<Orders> findAllOrders() {
        OrderDAO orderDAO = new OrderDAO();
        List<Orders> orderList = orderDAO.findAll();
        return orderList;
    }
    public Orders findOrderById(int id) {
        OrderDAO orderDAO = new OrderDAO();
        Orders order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("Comanda cu id =" + id + " nu a fost gasita!");
        }
        return order;
    }

    public List<Orders> findOrdersByInt(String field, int cond) {
        OrderDAO orderDAO = new OrderDAO();
        List<Orders> ordersList = orderDAO.findByInt(field, cond);
        return ordersList;
    }

    public int insertOrder(Orders order) throws IllegalArgumentException{
        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(order.getId_produs());

        for (Validator<Orders> v : validators) {
            v.validate(order);
        }

        product.setCantitate(product.getCantitate() - order.getCantitate());
        productDAO.update(product);
        return orderDAO.insert(order);
    }

    public Orders updateOrder(Orders order) {
        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(order.getId_produs());

        for (Validator<Orders> v : validators) {
            v.validate(order);
        }
        if(order.getCantitate() != orderDAO.findById(order.getId()).getCantitate()){
            product.setCantitate(product.getCantitate() + (orderDAO.findById(order.getId()).getCantitate() - order.getCantitate()));
            productDAO.update(product);
        }
        return orderDAO.update(order);
    }

    public void deleteOrder(int id){
        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(orderDAO.findById(id).getId_produs());
        product.setCantitate(product.getCantitate() + orderDAO.findById(id).getCantitate());
        productDAO.update(product);
        orderDAO.delete(id);

    }

    public int count(){
        OrderDAO orderDAO = new OrderDAO();
        return orderDAO.count();
    }

    public double priceSum(int idClient){
        OrderDAO orderDAO = new OrderDAO();
        return orderDAO.priceSum(idClient);
    }

    public void deleteAllOrders(){
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.deleteAllOrders();

    }
}
