package ro.tuc.Presentation.View;

import ro.tuc.BusinessLogic.ProductBLL;
import ro.tuc.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * @Author Boncea Natalia
 * @Since May 18, 2023
 */
public class ProductView extends JFrame {
    private JLabel title = new JLabel("Simulator magazin", SwingConstants.CENTER);

    private JLabel search = new JLabel("Cautare dupa: ");
    private String searchList[] = {"ID", "denumire", "cantitate", "pret minim", "pret maxim", "afiseaza tot"};
    private JComboBox<String> searchOptions = new JComboBox<>(searchList);
    private JTextField searchText = new JTextField(20);
    private JButton searchButton = new JButton("Cauta");
    private JButton insertButton = new JButton("Produs nou");
    private JButton deleteButton = new JButton("Sterge produs");
    private JButton updateButton = new JButton("Modifica produs");
    private String columnNames[] = {"ID", "DENUMIRE", "CANTITATE", "PRET"};
    public DefaultTableModel model  = new DefaultTableModel();
    private JTable productTable = new JTable(model);
    JPanel p2 = new JPanel();
    ProductTableGenerator reflectionProduct = new ProductTableGenerator();

    private Color bg_color = new Color(255, 234, 210);
    private Color purple1 = new Color(219, 223, 234);
    private Color purple2 = new Color(172, 177, 214);
    private Color purple3 = new Color(81, 50, 82);

    public ProductView(){
        JFrame frame = new JFrame ("Simulator magazin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 300);
        frame.setLocation(400, 275);

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();

        JPanel p3= new JPanel();
        JPanel p_title = new JPanel();

        Font f1  = new Font(Font.SERIF, Font.BOLD,  20);
        Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  20);

        title.setFont(f1);
        title.setForeground(purple1);
        p_title.add(title);
        p_title.setBackground(purple3);
        p_title.setAlignmentY(Component.CENTER_ALIGNMENT);
        p_title.setPreferredSize(new Dimension(800, 50));

        p.add(p_title);

        p1.add(search);
        p1.add(searchOptions);
        p1.add(searchText);
        p1.add(searchButton);
        searchButton.setBackground(purple2);
        p1.setBackground(bg_color);
        p1.setLayout(new FlowLayout());

        ProductBLL productBLL = new ProductBLL();
        ArrayList<Product> productsList = new ArrayList<>(productBLL.findAllProducts());
        reflectionProduct.setTable(productsList, model);
        productTable.setPreferredScrollableViewportSize(new Dimension(700, 100));
        productTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        p2.add(new JScrollPane(productTable));
        p2.setBackground(bg_color);
        p2.setLayout(new FlowLayout());

        p3.add(insertButton);
        insertButton.setBackground(purple2);
        p3.add(deleteButton);
        deleteButton.setBackground(purple2);
        p3.add(updateButton);
        updateButton.setBackground(purple2);
        p3.setBackground(bg_color);
        p3.setLayout(new FlowLayout());

        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(bg_color);

        frame.setContentPane(p);
        frame.setVisible(true);
    }

    public String getSearch(){
        return searchText.getText();
    }
    public void searchListener(ActionListener listenSearchButton){
        searchButton.addActionListener(listenSearchButton);
    }
    public void insertListener(ActionListener listenInsertButton){
        insertButton.addActionListener(listenInsertButton);
    }
    public void deleteListener(ActionListener listenDeleteButton){
        deleteButton.addActionListener(listenDeleteButton);
    }
    public void updateListener(ActionListener listenUpdateButton){
        updateButton.addActionListener(listenUpdateButton);
    }

    public int getSelectedId(){
        int row = productTable.getSelectedRow();
        return Integer.parseInt((String)productTable.getValueAt(row, 0));
    }
    public int getSearchOption(){
        return searchOptions.getSelectedIndex();
    }
}
