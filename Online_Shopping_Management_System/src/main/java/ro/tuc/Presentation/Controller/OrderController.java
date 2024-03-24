package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.BillBLL;
import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.BusinessLogic.OrderBLL;
import ro.tuc.BusinessLogic.ProductBLL;
import ro.tuc.Model.Bill;
import ro.tuc.Model.Client;
import ro.tuc.Model.Orders;
import ro.tuc.Model.Product;
import ro.tuc.Presentation.View.*;
import ro.tuc.Presentation.View.OrderView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class OrderController {
    private OrderView orderView;
    private int idClient;
    private FileWriter logFile;
    ClientTableGenerator reflectionClient = new ClientTableGenerator();
    ProductTableGenerator reflectionProduct = new ProductTableGenerator();
    OrderTableGenerator reflectionOrder = new OrderTableGenerator();

    public OrderController(OrderView orderView){
        this.orderView = orderView;
        this.orderView.searchListenerClient(new OrderController.SearchListenerClient(orderView));
        this.orderView.searchListenerProduct(new OrderController.SearchListenerProduct(orderView));
        this.orderView.addClient(new OrderController.AddClient(orderView));
        this.orderView.addProduct(new OrderController.AddProduct(orderView));
        this.orderView.deleteOrder(new OrderController.DeleteOrder(orderView));
        this.orderView.updateOrder(new OrderController.UpdateOrder(orderView));
        this.orderView.finishOrder(new OrderController.FinishOrder(orderView));
        this.orderView.displayHistory(new OrderController.DisplayHistory());
    }

    class AddClient implements ActionListener {
        private OrderView orderView;
        private OrderBLL orderBLL;
        public AddClient(OrderView orderView){
            this.orderView = orderView;
            orderBLL = new OrderBLL();
        }

        public void actionPerformed(ActionEvent e) {
            ClientBLL clientBLL = new ClientBLL();

            try{
                idClient = orderView.getSelectedId(orderView.clientTable);
            }catch(ArrayIndexOutOfBoundsException ex){
                ErrorBox.unselectedOption("Clientul nu a fost selectat!", orderView);
                throw new ArrayIndexOutOfBoundsException("Clientul nu a fost selectat!");
            }
            orderView.setText(orderView.client, "Destinatar: "+clientBLL.findClientById(idClient).getNume()+ " " + clientBLL.findClientById(idClient).getPrenume());
            orderView.setText(orderView.address, "Adresa livrarii: "+clientBLL.findClientById(idClient).getAdresa());
        }
    }

    class AddProduct implements ActionListener {
        private OrderView orderView;
        private OrderBLL orderBLL;
        public AddProduct(OrderView orderView){
            this.orderView = orderView;
            orderBLL = new OrderBLL();
        }

        public void actionPerformed(ActionEvent e) {
            ProductBLL productBLL = new ProductBLL();
            int idProduct;
            try{
                idProduct = orderView.getSelectedId(orderView.productTable);
            }catch(ArrayIndexOutOfBoundsException ex){
                ErrorBox.unselectedOption("Produsul nu a fost selectat!", orderView);
                throw new ArrayIndexOutOfBoundsException("Produsul nu a fost selectat!");
            }
            System.out.println(Integer.parseInt(orderView.getTextField(orderView.quantityTextProduct)));
            Orders newOrders = new Orders(orderBLL.count()+1, idClient, idProduct, Integer.parseInt(orderView.getTextField(orderView.quantityTextProduct)),
                    Integer.parseInt(orderView.getTextField(orderView.quantityTextProduct))*productBLL.findProductById(idProduct).getPret());
            System.out.println(newOrders);
            try {
                orderBLL.insertOrder(newOrders);
            }catch (IllegalArgumentException ex){
                ErrorBox.incorrectValueError(orderView.quantityTextProduct, ex.getMessage(), orderView);
            }
            ErrorBox.correctValue(orderView.quantityTextProduct, null, orderView);
            ArrayList<Orders> orderList = (ArrayList<Orders>) orderBLL.findOrdersByInt("id_client", idClient);
            orderView.setCartTable(orderList);
            orderView.setText(orderView.total_price, "Pret total: "  + orderBLL.priceSum(idClient) + " lei");
            ArrayList<Product> productsList = (ArrayList<Product>) productBLL.findAllProducts();
            reflectionProduct.setTable(productsList, orderView.modelProduct);
        }
    }

    class DeleteOrder implements ActionListener {
        private OrderView orderView;
        private OrderBLL orderBLL;
        public DeleteOrder(OrderView orderView){
            this.orderView = orderView;
            orderBLL = new OrderBLL();
        }

        public void actionPerformed(ActionEvent e) {
            ProductBLL productBLL = new ProductBLL();
            int idOrder;
            try{
                idOrder = orderView.getSelectedId(orderView.cartTable);
            }catch(ArrayIndexOutOfBoundsException ex){
                ErrorBox.unselectedOption("Produsul din cos nu a fost selectat!", orderView);
                throw new ArrayIndexOutOfBoundsException("Produsul din cos nu a fost selectat!");
            }

            orderBLL.deleteOrder(idOrder);
            ArrayList<Orders> orderList = (ArrayList<Orders>) orderBLL.findOrdersByInt("id_client", idClient);
            orderView.setCartTable(orderList);
            orderView.setText(orderView.total_price, "Pret total: "  + orderBLL.priceSum(idClient) + " lei");
            ArrayList<Product> productsList = (ArrayList<Product>) productBLL.findAllProducts();
            reflectionProduct.setTable(productsList, orderView.modelProduct);
        }
    }

    class FinishOrder implements ActionListener {
        private OrderView orderView;
        private OrderBLL orderBLL;
        private Bill bill;
        public FinishOrder(OrderView orderView){
            this.orderView = orderView;
            orderBLL = new OrderBLL();
        }

        public void actionPerformed(ActionEvent e) {
            ClientBLL clientBLL = new ClientBLL();
            ProductBLL productBLL = new ProductBLL();
            BillBLL billBLL = new BillBLL();
            ArrayList<Orders> orderList = (ArrayList<Orders>) orderBLL.findOrdersByInt("id_client", idClient);
            orderView.setText(orderView.total_price, "Pret total: "  + orderBLL.priceSum(idClient) + " lei");
            try {
                logFile = new FileWriter("Bill.txt");
            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ArrayList<Bill> billList = new ArrayList<>();
            for(Orders o: orderList){
                bill = new Bill(BillBLL.count()+1, clientBLL.findClientById(idClient).getNume()+" "+clientBLL.findClientById(idClient).getPrenume(),
                        clientBLL.findClientById(idClient).getAdresa(), clientBLL.findClientById(idClient).getNr_telefon(),
                        productBLL.findProductById(o.getId_produs()).getDenumire(), o.getCantitate(), o.getPret_total(), orderBLL.priceSum(idClient), logFile);
                billList.add(bill);
                billBLL.insertBill(bill);
            }
            System.out.println(billBLL.findAllBills());
            bill.writeBill(billList);
            System.out.println("Factura realizata cu succes!");
            orderBLL.deleteAllOrders();
        }
    }

    class UpdateOrder implements ActionListener {
        private OrderView orderView;
        private OrderUpdateView orderUpdateView;
        private OrderUpdateController orderUpdateController;
        public UpdateOrder(OrderView orderView){
            this.orderView = orderView;
        }

        public void actionPerformed(ActionEvent e) {
            orderUpdateView = new OrderUpdateView(orderView);
            orderUpdateController = new OrderUpdateController(orderView, orderUpdateView);
        }
    }

    class DisplayHistory implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            OrderHistoryView orderHistoryView = new OrderHistoryView();
        }
    }

    class SearchListenerClient implements ActionListener {
        private OrderView orderView;
        private ClientBLL clientBLL;
        private String searchList[] = {"id", "nume", "prenume", "adresa", "afiseaza tot"};
        public SearchListenerClient(OrderView orderView){
            this.orderView = orderView;
            clientBLL = new ClientBLL();
        }
        public void actionPerformed(ActionEvent e) {
            int index = orderView.getSearchOption(orderView.searchOptionsClient);
            ArrayList<Client> clientList;
            if(index == 4){
                clientList = (ArrayList<Client>) clientBLL.findAllClients();
            }
            else if(index == 0){
                clientList = new ArrayList<>();
                clientList.add(clientBLL.findClientById(Integer.parseInt(orderView.getTextField(orderView.searchTextClient))));
            }
            else{
                clientList = (ArrayList<Client>) clientBLL.findClientsByString(searchList[index], orderView.getTextField(orderView.searchTextClient));
            }
            reflectionClient.setTable(clientList, orderView.modelClient);
        }
    }

    class SearchListenerProduct implements ActionListener {
        private OrderView orderView;
        private ProductBLL productBLL;
        private OrderBLL orderBLL;
        private String searchList[] = {"ID", "denumire", "cantitate", "pret minim", "pret maxim", "afiseaza tot"};
        public SearchListenerProduct(OrderView orderView){
            this.orderView = orderView;
            productBLL = new ProductBLL();
            orderBLL = new OrderBLL();
        }
        public void actionPerformed(ActionEvent e) {
            int index = orderView.getSearchOption(orderView.searchOptionsProduct);
            ArrayList<Product> productList;
            switch(index){
                case 0:
                    productList = new ArrayList<>();
                    productList.add(productBLL.findProductById(Integer.parseInt(orderView.getTextField(orderView.searchTextProduct))));
                    break;
                case 1: productList = (ArrayList<Product>) productBLL.findProductsByString(searchList[index], orderView.getTextField(orderView.searchTextProduct));
                    break;
                case 2: productList = (ArrayList<Product>) productBLL.findProductsByInt(searchList[index], Integer.parseInt(orderView.getTextField(orderView.searchTextProduct)));
                    break;
                case 3: productList = (ArrayList<Product>) productBLL.findProductsByMinPrice(Integer.parseInt(orderView.getTextField(orderView.searchTextProduct)));
                    break;
                case 4: productList = (ArrayList<Product>) productBLL.findProductsByMaxPrice(Integer.parseInt(orderView.getTextField(orderView.searchTextProduct)));
                    break;
                case 5: productList = (ArrayList<Product>) productBLL.findAllProducts();
                    break;
                default: productList = new ArrayList<>();
            }
            reflectionProduct.setTable(productList, orderView.modelProduct);
        }
    }
}
