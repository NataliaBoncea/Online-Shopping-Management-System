package ro.tuc.Presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class View  extends JFrame {
    private JLabel title = new JLabel("Simulator magazin", SwingConstants.CENTER);

    private JButton clientButton = new JButton("Clienti");
    private JButton productButton = new JButton("Produse");
    private JButton OrdersButton = new JButton("Pune comanda");

    private Color bg_color = new Color(255, 234, 210);
    private Color purple1 = new Color(219, 223, 234);
    private Color purple2 = new Color(172, 177, 214);
    private Color purple3 = new Color(81, 50, 82);
    public View(){

        JFrame frame = new JFrame ("Simulator magazin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        frame.setLocation(650, 300);

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3= new JPanel();
        JPanel p_title = new JPanel();

        Font f1  = new Font(Font.SERIF, Font.BOLD,  20);
        Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  20);

        title.setFont(f1);
        title.setForeground(purple1);
        p_title.add(title);
        p_title.setBackground(purple3);
        p_title.setAlignmentY(Component.CENTER_ALIGNMENT);
        p_title.setPreferredSize(new Dimension(700, 25));

        p.add(p_title);

        p1.add(clientButton);
        clientButton.setPreferredSize(new Dimension(150, 30));
        clientButton.setBackground(purple2);
        p1.setBackground(bg_color);
        p1.setLayout(new FlowLayout());

        p2.add(productButton);
        productButton.setPreferredSize(new Dimension(150, 30));
        productButton.setBackground(purple2);
        p2.setBackground(bg_color);
        p2.setLayout(new FlowLayout());

        p3.add(OrdersButton);
        OrdersButton.setPreferredSize(new Dimension(150, 30));
        OrdersButton.setBackground(purple2);
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

    public void clientListener(ActionListener listenClientButton){
        clientButton.addActionListener(listenClientButton);
    }
    public void productListener(ActionListener listenProductButton){
        productButton.addActionListener(listenProductButton);
    }
    public void OrdersListener(ActionListener listenOrdersButton){
        OrdersButton.addActionListener(listenOrdersButton);
    }
}
