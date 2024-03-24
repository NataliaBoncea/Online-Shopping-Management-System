package ro.tuc.Presentation.View;

import ro.tuc.BusinessLogic.OrderBLL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class OrderUpdateView {
    private JLabel cantitateL = new JLabel("Cantitate: ");
    private JTextField cantitateT = new JTextField(15);
    private JButton updateButton = new JButton("Modifica");

    private Color bg_color = new Color(255, 234, 210);
    private Color purple1 = new Color(219, 223, 234);
    private Color purple2 = new Color(172, 177, 214);
    private Color purple3 = new Color(81, 50, 82);

    public JFrame frame = new JFrame("Simulator magazin");

    public OrderUpdateView(OrderView orderView) {
        OrderBLL orderBLL = new OrderBLL();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocation(650, 330);

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        p1.add(cantitateL);
        p1.add(cantitateT);
        p1.setBackground(purple2);
        p1.setLayout(new FlowLayout());
        cantitateT.setText(Integer.toString(orderBLL.findOrderById(orderView.getSelectedId(orderView.cartTable)).getCantitate()));

        p2.add(updateButton);
        p2.setBackground(purple2);
        p2.setLayout(new FlowLayout());
        updateButton.setBackground(bg_color);

        p.add(p1);
        p.add(p2);

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(purple2);

        frame.setContentPane(p);
        frame.setVisible(true);
    }

    public String getCantitate() {
        return cantitateT.getText();
    }

    public void updateListener(ActionListener listenUpdateButton) {
        updateButton.addActionListener(listenUpdateButton);
    }
}
