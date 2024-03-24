package ro.tuc.Presentation.View;

import ro.tuc.BusinessLogic.ClientBLL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ClientInsertView extends JFrame {
    private JLabel idL = new JLabel("ID:    ");
    private JLabel numeL = new JLabel("Nume: ");
    private JLabel prenumeL = new JLabel("Prenume: ");
    private JLabel adresaL = new JLabel("Adresa: ");
    private JLabel emailL = new JLabel("Email: ");
    private JLabel telefonL = new JLabel("Telefon: ");

    private JTextField idT = new JTextField(3);
    private JTextField numeT = new JTextField(10);
    private JTextField prenumeT = new JTextField(15);
    private JTextField adresaT = new JTextField(15);
    private JTextField emailT = new JTextField(15);
    private JTextField telefonT = new JTextField(10);
    private JButton updateButton = new JButton("Adauga");

    private Color bg_color = new Color(255, 234, 210);
    private Color purple1 = new Color(219, 223, 234);
    private Color purple2 = new Color(172, 177, 214);
    private Color purple3 = new Color(81, 50, 82);
    private ClientBLL clientBLL = new ClientBLL();
    public JFrame frame = new JFrame("Simulator magazin");

    public ClientInsertView(ClientView clientView) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocation(650, 300);

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();
        JPanel p6 = new JPanel();
        JPanel p7 = new JPanel();

        p1.add(idL);
        p1.add(idT);
        p1.setBackground(purple2);
        p1.setLayout(new FlowLayout());

        p2.add(numeL);
        p2.add(numeT);
        p2.setBackground(purple2);
        p2.setLayout(new FlowLayout());

        p3.add(prenumeL);
        p3.add(prenumeT);
        p3.setBackground(purple2);
        p3.setLayout(new FlowLayout());

        p4.add(adresaL);
        p4.add(adresaT);
        p4.setBackground(purple2);
        p4.setLayout(new FlowLayout());

        p5.add(emailL);
        p5.add(emailT);
        p5.setBackground(purple2);
        p5.setLayout(new FlowLayout());

        p6.add(telefonL);
        p6.add(telefonT);
        p6.setBackground(purple2);
        p6.setLayout(new FlowLayout());

        p7.add(updateButton);
        p7.setBackground(purple2);
        p7.setLayout(new FlowLayout());
        updateButton.setBackground(bg_color);

        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p5);
        p.add(p6);
        p.add(p7);

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(purple2);

        frame.setContentPane(p);
        frame.setVisible(true);
    }

    public JTextField getIdT() {
        return idT;
    }

    public JTextField getNumeT() {
        return numeT;
    }

    public JTextField getPrenumeT() {
        return prenumeT;
    }

    public JTextField getAdresaT() {
        return adresaT;
    }

    public JTextField getEmailT() {
        return emailT;
    }

    public JTextField getTelefonT() {
        return telefonT;
    }

    public String getId() {
        return idT.getText();
    }

    public String getNume() {
        return numeT.getText();
    }

    public String getPrenume() {
        return prenumeT.getText();
    }

    public String getAdresa() {
        return adresaT.getText();
    }

    public String getEmail() {
        return emailT.getText();
    }

    public String getTelefon() {
        return telefonT.getText();
    }

    public void updateListener(ActionListener listenUpdateButton) {
        updateButton.addActionListener(listenUpdateButton);
    }
}
