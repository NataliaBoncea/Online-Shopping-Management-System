package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.Model.Client;
import ro.tuc.Presentation.View.ClientView;
import ro.tuc.Presentation.View.OrderView;
import ro.tuc.Presentation.View.ProductView;
import ro.tuc.Presentation.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class Controller {
    private View view;
    public Controller(View view){
        this.view = view;
        this.view.clientListener(new ClientListener());
        this.view.productListener(new ProductListener());
        this.view.OrdersListener(new OrderListener());
    }

    class ClientListener implements ActionListener {
        private ClientView clientView;
        private ClientController controller;

        public void actionPerformed(ActionEvent e) {
            clientView = new ClientView();
            controller = new ClientController(clientView);
        }
    }

    class OrderListener implements ActionListener {
        private ro.tuc.Presentation.View.OrderView OrderView;
        private OrderController controller;

        public void actionPerformed(ActionEvent e) {
            OrderView = new OrderView();
            controller = new OrderController(OrderView);
        }
    }

    class ProductListener implements ActionListener {
        private ProductView productView;
        private ProductController controller;

        public void actionPerformed(ActionEvent e) {
            productView = new ProductView();
            controller = new ProductController(productView);
        }
    }
}
