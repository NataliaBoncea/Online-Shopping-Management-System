package ro.tuc.Presentation.View;

import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.BusinessLogic.ProductBLL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ProductUpdateView extends JFrame {
    private JLabel idL = new JLabel("ID:    ");
    private JLabel denumireL = new JLabel("Denumire: ");
    private JLabel cantitateL = new JLabel("Cantitate: ");
    private JLabel pretL = new JLabel("Pret: ");

    private JTextField idT = new JTextField(3);
    private JTextField denumireT = new JTextField(10);
    private JTextField cantitateT = new JTextField(15);
    private JTextField pretT = new JTextField(15);
    private JButton updateButton = new JButton("Modifica");

    private Color bg_color = new Color(255, 234, 210);
    private Color purple1 = new Color(219, 223, 234);
    private Color purple2 = new Color(172, 177, 214);
    private Color purple3 = new Color(81, 50, 82);
    private ProductBLL productBLL = new ProductBLL();
    public JFrame frame = new JFrame("Simulator magazin");

    public ProductUpdateView(ProductView productView) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocation(650, 300);

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p7 = new JPanel();

        p1.add(idL);
        p1.add(idT);
        p1.setBackground(purple2);
        p1.setLayout(new FlowLayout());
        idT.setText(Integer.toString(productBLL.findProductById(productView.getSelectedId()).getId()));

        p2.add(denumireL);
        p2.add(denumireT);
        p2.setBackground(purple2);
        p2.setLayout(new FlowLayout());
        denumireT.setText(productBLL.findProductById(productView.getSelectedId()).getDenumire());

        p3.add(cantitateL);
        p3.add(cantitateT);
        p3.setBackground(purple2);
        p3.setLayout(new FlowLayout());
        cantitateT.setText(Integer.toString(productBLL.findProductById(productView.getSelectedId()).getCantitate()));

        p4.add(pretL);
        p4.add(pretT);
        p4.setBackground(purple2);
        p4.setLayout(new FlowLayout());
        pretT.setText(Double.toString(productBLL.findProductById(productView.getSelectedId()).getPret()));

        p7.add(updateButton);
        p7.setBackground(purple2);
        p7.setLayout(new FlowLayout());
        updateButton.setBackground(bg_color);

        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p7);

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(purple2);

        frame.setContentPane(p);
        frame.setVisible(true);
    }

    public String getId() {
        return idT.getText();
    }

    public String getDenumire() {
        return denumireT.getText();
    }

    public String getCantitate() {
        return cantitateT.getText();
    }

    public String getPret() {
        return pretT.getText();
    }

    public void updateListener(ActionListener listenUpdateButton) {
        updateButton.addActionListener(listenUpdateButton);
    }
}
