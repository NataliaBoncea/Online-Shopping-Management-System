package ro.tuc.Presentation.View;

import ro.tuc.BusinessLogic.*;
import ro.tuc.BusinessLogic.OrderBLL;
import ro.tuc.Model.Client;
import ro.tuc.Model.Orders;
import ro.tuc.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class OrderView extends JFrame {
    public JLabel title = new JLabel("Comanda noua", SwingConstants.CENTER);
    public JLabel titleClient = new JLabel("Clienti", SwingConstants.CENTER);
    public JLabel titleProduct = new JLabel("Produse", SwingConstants.CENTER);
    public String columnNamesClient[] = {"ID", "NUME", "PRENUME", "ADRESA", "EMAIL", "NR. TELEFON"};
    public DefaultTableModel modelClient  = new DefaultTableModel();
    public JTable clientTable = new JTable(modelClient);
    public String columnNamesProduct[] = {"ID", "DENUMIRE", "CANTITATE", "PRET"};
    public DefaultTableModel modelProduct  = new DefaultTableModel();
    public JTable productTable = new JTable(modelProduct);

    public JLabel searchClient = new JLabel("Cautare dupa: ");
    public String searchListClient[] = {"ID", "nume", "prenume", "adresa", "afiseaza tot"};
    public JComboBox<String> searchOptionsClient = new JComboBox<>(searchListClient);
    public JTextField searchTextClient = new JTextField(20);
    public JButton searchButtonClient = new JButton("Cauta");
    public JButton addButtonClient = new JButton("Adauga");

    public JLabel searchProduct = new JLabel("Cautare dupa: ");
    public String searchListProduct[] = {"ID", "denumire", "cantitate", "pret minim", "pret maxim", "afiseaza tot"};
    public JComboBox<String> searchOptionsProduct = new JComboBox<>(searchListProduct);
    public JTextField searchTextProduct = new JTextField(20);
    public JButton searchButtonProduct = new JButton("Cauta");
    public JLabel quantityLabelProduct = new JLabel("Cantitate: ");
    public JTextField quantityTextProduct = new JTextField(5);
    public JButton addButtonProduct = new JButton("Adauga");

    public JLabel client = new JLabel("Destinatar: ");
    public JLabel address = new JLabel("Adresa livrarii: ");
    public JLabel cart = new JLabel("Cosul de cumparaturi: ");
    public String columnNamesCart[] = {"ID", "DENUMIRE", "CANTITATE", "PRET"};
    public DefaultTableModel modelCart  = new DefaultTableModel();
    public JTable cartTable = new JTable(modelCart);
    public JScrollPane scrollPane = new JScrollPane(cartTable);
    public JButton modifyOrdersButton = new JButton("Modifica");
    public JButton deleteOrdersButton = new JButton("Sterge");
    public JLabel total_price = new JLabel("Pret total: ");
    public JButton finishOrdersButton = new JButton("Finalizare comanda");
    public JButton historyButton = new JButton("Istoric");

    public Color bg_color = new Color(255, 234, 210);
    public Color purple1 = new Color(219, 223, 234);
    public Color purple2 = new Color(172, 177, 214);
    public Color purple3 = new Color(81, 50, 82);
    ClientTableGenerator reflectionClient = new ClientTableGenerator();
    ProductTableGenerator reflectionProduct = new ProductTableGenerator();
    OrderTableGenerator reflectionOrder = new OrderTableGenerator();

    public OrderView(){
        JFrame frame = new JFrame ("Simulator magazin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1200, 500);
        frame.setLocation(200, 100);

        JPanel p = new JPanel();
        JPanel pTables = new JPanel();
        JPanel pClient = new JPanel();
        JPanel pClientTitle = new JPanel();
        JPanel pClient1 = new JPanel();
        JPanel pClient2 = new JPanel();
        JPanel pClient3 = new JPanel();
        JPanel pOrders = new JPanel();
        JPanel pOrders1 = new JPanel();
        JPanel pOrders2 = new JPanel();
        JPanel pOrders3 = new JPanel();
        JPanel pProduct = new JPanel();
        JPanel pProductTitle = new JPanel();
        JPanel pProduct1 = new JPanel();
        JPanel pProduct2 = new JPanel();
        JPanel pProduct3 = new JPanel();
        JPanel pTitle = new JPanel();

        Font f1  = new Font(Font.SERIF, Font.BOLD,  20);
        Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  15);

        title.setFont(f1);
        title.setForeground(purple1);
        pTitle.add(title);
        pTitle.setBackground(purple3);
        pTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        pTitle.setPreferredSize(new Dimension(1200, 35));

        titleClient.setFont(f1);
        titleClient.setForeground(purple1);
        pClientTitle.add(titleClient);
        pClientTitle.setBackground(purple3);
        pClientTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        pClient1.add(searchClient);
        pClient1.add(searchOptionsClient);
        pClient1.add(searchTextClient);
        pClient1.add(searchButtonClient);
        searchButtonClient.setBackground(purple3);
        searchButtonClient.setForeground(Color.WHITE);
        pClient1.setBackground(purple2);
        pClient1.setLayout(new FlowLayout());

        ClientBLL clientBLL = new ClientBLL();
        ArrayList<Client> clientList = new ArrayList<>(clientBLL.findAllClients());
        reflectionClient.setTable(clientList, modelClient);
        clientTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        pClient2.add(new JScrollPane(clientTable));
        pClient2.setBackground(purple2);
        pClient2.setLayout(new FlowLayout());

        pClient3.add(addButtonClient);
        addButtonClient.setBackground(purple3);
        addButtonClient.setForeground(Color.WHITE);
        pClient3.setBackground(purple2);

        pClient.add(pClientTitle);
        pClient.add(pClient1);
        pClient.add(pClient2);
        pClient.add(pClient3);
        pClient.setLayout(new BoxLayout(pClient, BoxLayout.Y_AXIS));
        pTables.add(pClient);

        titleProduct.setFont(f1);
        titleProduct.setForeground(purple1);
        pProductTitle.add(titleProduct);
        pProductTitle.setBackground(purple3);
        pProductTitle.setAlignmentY(Component.CENTER_ALIGNMENT);

        pProduct1.add(searchProduct);
        pProduct1.add(searchOptionsProduct);
        pProduct1.add(searchTextProduct);
        pProduct1.add(searchButtonProduct);
        searchButtonProduct.setBackground(purple3);
        searchButtonProduct.setForeground(Color.WHITE);
        pProduct1.setBackground(purple2);
        pProduct1.setLayout(new FlowLayout());

        ProductBLL productBLL = new ProductBLL();
        ArrayList<Product> productsList = new ArrayList<>(productBLL.findAllProducts());
        reflectionProduct.setTable(productsList, modelProduct);
        productTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        pProduct2.add(new JScrollPane(productTable));
        pProduct2.setBackground(purple2);
        pProduct2.setLayout(new FlowLayout());

        pProduct3.add(quantityLabelProduct);
        pProduct3.add(quantityTextProduct);
        pProduct3.add(addButtonProduct);
        addButtonProduct.setBackground(purple3);
        addButtonProduct.setForeground(Color.WHITE);
        pProduct3.setBackground(purple2);
        pProduct3.setLayout(new FlowLayout());

        pProduct.add(pProductTitle);
        pProduct.add(pProduct1);
        pProduct.add(pProduct2);
        pProduct.add(pProduct3);
        pProduct.setLayout(new BoxLayout(pProduct, BoxLayout.Y_AXIS));
        pTables.add(pProduct);
        pTables.setLayout(new BoxLayout(pTables, BoxLayout.Y_AXIS));
        pTables.setPreferredSize(new Dimension(600, 500));
        pOrders.setPreferredSize(new Dimension(600, 500));

        p.add(pTables);

        JPanel pOrders11 = new JPanel();
        JPanel pOrders12 = new JPanel();
        JPanel pOrders13 = new JPanel();
        JPanel pHistory = new JPanel();
        pOrders11.add(client);
        pOrders3.setPreferredSize(new Dimension(600, 30));
        pOrders2.setPreferredSize(new Dimension(600, 300));
        pHistory.setPreferredSize(new Dimension(100, 20));

        client.setFont(f2);
        pOrders11.setLayout(new FlowLayout(FlowLayout.LEFT));
        pOrders11.setBackground(bg_color);
        pOrders12.add(address);
        address.setFont(f2);
        pOrders12.setLayout(new FlowLayout(FlowLayout.LEFT));
        pOrders12.setBackground(bg_color);

        pHistory.add(historyButton);
        pHistory.setLayout(new FlowLayout(FlowLayout.RIGHT));
        historyButton.setBackground(purple3);
        historyButton.setForeground(Color.WHITE);
        pHistory.setBackground(bg_color);

        pOrders13.add(pOrders11);
        pOrders13.add(pOrders12);
        pOrders13.setLayout(new BoxLayout(pOrders13, BoxLayout.Y_AXIS));
        pOrders13.setBackground(purple1);
        pOrders1.add(pOrders13);
        pOrders1.add(pHistory);
        pOrders1.setLayout(new BoxLayout(pOrders1, BoxLayout.X_AXIS));
        pOrders1.setBackground(purple1);

        JPanel pOrders21 = new JPanel();
        JPanel pOrders22 = new JPanel();
        pOrders21.add(cart);
        pOrders21.setPreferredSize(new Dimension(600, 50));
        pOrders21.setBackground(bg_color);
        cart.setFont(f2);
        OrderBLL ordersBLL = new OrderBLL();
        ArrayList<Orders> ordersList = new ArrayList<>();
        setCartTable(ordersList);
        //reflectionOrder.setTable(ordersList, modelCart);
        pOrders22.add(scrollPane);
        pOrders22.setBackground(bg_color);
        cartTable.setPreferredScrollableViewportSize(new Dimension(500, 230));
        pOrders2.add(pOrders21);
        pOrders2.add(pOrders22);
        //cartTable.setFillsViewportHeight(true);

        pOrders2.setBackground(bg_color);
        pOrders2.setLayout(new BoxLayout(pOrders2, BoxLayout.Y_AXIS));

        JPanel pOrders31 = new JPanel();
        JPanel pOrders32 = new JPanel();
        JPanel pOrders33 = new JPanel();
        pOrders31.setLayout(new FlowLayout(FlowLayout.LEFT));
        pOrders31.add(total_price);
        total_price.setFont(f2);
        pOrders31.setBackground(bg_color);
        pOrders3.add(pOrders31);

        pOrders32.setLayout(new FlowLayout(FlowLayout.CENTER));
        pOrders32.setBackground(bg_color);
        pOrders32.add(deleteOrdersButton);
        deleteOrdersButton.setBackground(purple2);
        pOrders32.add(modifyOrdersButton);
        modifyOrdersButton.setBackground(purple2);
        pOrders3.add(pOrders32);

        pOrders33.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pOrders33.setBackground(bg_color);
        pOrders33.add(finishOrdersButton);
        finishOrdersButton.setBackground(purple2);
        pOrders3.add(pOrders33);
        pOrders3.setBackground(bg_color);
        pOrders3.setLayout(new BoxLayout(pOrders3, BoxLayout.X_AXIS));

        pOrders.add(pTitle);
        pOrders.add(pOrders1);
        pOrders.add(pOrders2);
        pOrders.add(pOrders3);

        pOrders.setBackground(bg_color);
        pOrders.setLayout(new BoxLayout(pOrders, BoxLayout.Y_AXIS));

        p.add(pOrders);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBackground(bg_color);

        frame.setContentPane(p);
        frame.setVisible(true);
    }

    public void setCartTable(ArrayList<Orders> ordersList){
        modelCart.setColumnIdentifiers(columnNamesCart);
        modelCart.setRowCount(0);
        cartTable.getTableHeader().setBackground(purple2);
        ProductBLL productBLL = new ProductBLL();
        for(Orders o: ordersList){
            String fields[] = {Integer.toString(o.getId()) , productBLL.findProductById(o.getId_produs()).getDenumire(), Integer.toString(o.getCantitate()), Double.toString(o.getPret_total())};
            modelCart.addRow(fields);
        }
    }

    public int getSelectedId(JTable table){
        int row = table.getSelectedRow();
        return Integer.parseInt((String)table.getValueAt(row, 0));
    }
    public int getSearchOption(JComboBox searchOptions){
        return searchOptions.getSelectedIndex();
    }
    public String getTextField(JTextField textField){
        return textField.getText();
    }
    public void searchListenerClient(ActionListener listenSearchButton){
        searchButtonClient.addActionListener(listenSearchButton);
    }
    public void searchListenerProduct(ActionListener listenSearchButton){
        searchButtonProduct.addActionListener(listenSearchButton);
    }
    public void addProduct(ActionListener listenAddButton){
        addButtonProduct.addActionListener(listenAddButton);
    }
    public void addClient(ActionListener listenAddButton){
        addButtonClient.addActionListener(listenAddButton);
    }
    public void deleteOrder(ActionListener listenDeleteButton){
        deleteOrdersButton.addActionListener(listenDeleteButton);
    }
    public void updateOrder(ActionListener listenUpdateButton){
        modifyOrdersButton.addActionListener(listenUpdateButton);
    }
    public void finishOrder(ActionListener listenFinishButton){
        finishOrdersButton.addActionListener(listenFinishButton);
    }
    public void displayHistory(ActionListener listenHistoryButton){
        historyButton.addActionListener(listenHistoryButton);
    }
    public void setText(JLabel label, String text){
        label.setText(text);
    }

    public void incorrectValueError(JTextField tf){
        tf.setBackground(Color.RED);
        UIManager.put("OptionPane.background", purple3);
        UIManager.put("Panel.background", purple3);
        UIManager.put("Button.background", purple2);
        String instructions = """
                Oups! Se pare ca nu ati introdus corect o valoare.
                Asigurati-va ca ati introdus valoarea corect.""";
        JTextArea area = new JTextArea(instructions);
        area.setFont(new Font("Tahoma", Font.PLAIN, 14));
        area.setBackground(purple3);
        area.setForeground(bg_color);
        JOptionPane.showMessageDialog(this, area, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }
}
