package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.OrderBLL;
import ro.tuc.BusinessLogic.ProductBLL;
import ro.tuc.Model.Orders;
import ro.tuc.Model.Product;
import ro.tuc.Presentation.View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class OrderUpdateController {
    private OrderView orderView;
    private OrderUpdateView orderUpdateView;

    public OrderUpdateController(OrderView orderView, OrderUpdateView orderUpdateView){
        this.orderView = orderView;
        this.orderUpdateView = orderUpdateView;
        this.orderUpdateView.updateListener(new OrderUpdateController.OrderUpdateListener(orderView, orderUpdateView));
    }

    class OrderUpdateListener implements ActionListener {
        private OrderView orderView;
        private OrderUpdateView orderUpdateView;
        private OrderBLL orderBLL;
        private ProductBLL productBLL;
        ProductTableGenerator reflectionProduct = new ProductTableGenerator();
        public OrderUpdateListener(OrderView orderView, OrderUpdateView orderUpdateView){
            this.orderView = orderView;
            this.orderUpdateView = orderUpdateView;
            productBLL = new ProductBLL();
            orderBLL = new OrderBLL();
        }
        public void actionPerformed(ActionEvent e) {
            Orders newOrder = orderBLL.findOrderById(orderView.getSelectedId(orderView.cartTable));
            newOrder.setCantitate(Integer.parseInt(orderUpdateView.getCantitate()));
            orderBLL.updateOrder(newOrder);
            ArrayList<Orders> ordersList = (ArrayList<Orders>) orderBLL.findOrdersByInt("id_client", newOrder.getId_client());
            orderView.setCartTable(ordersList);
            ArrayList<Product> productList = (ArrayList<Product>) productBLL.findAllProducts();
            reflectionProduct.setTable(productList, orderView.modelProduct);
            orderUpdateView.frame.dispose();
        }
    }
}
