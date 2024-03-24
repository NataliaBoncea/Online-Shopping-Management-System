package ro.tuc.Presentation.View;

import ro.tuc.BusinessLogic.BillBLL;
import ro.tuc.Model.Bill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class OrderHistoryView {
    private JLabel title = new JLabel("Istoric", SwingConstants.CENTER);

    private String columnNames[] = {"ID", "NUME", "ADRESA", "TELEFON", "PRODUS", "CANTITATE", "PRET"};
    private DefaultTableModel model  = new DefaultTableModel();
    private JTable logTable = new JTable(model);

    private Color bg_color = new Color(255, 234, 210);
    private Color purple1 = new Color(219, 223, 234);
    private Color purple2 = new Color(172, 177, 214);
    private Color purple3 = new Color(81, 50, 82);

    public OrderHistoryView(){
        JFrame frame = new JFrame ("Simulator magazin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 300);
        frame.setLocation(400, 275);

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p_title = new JPanel();

        Font f1  = new Font(Font.SERIF, Font.BOLD,  20);
        Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  20);

        title.setFont(f1);
        title.setForeground(purple1);
        p_title.add(title);
        p_title.setBackground(purple3);
        p_title.setAlignmentY(Component.CENTER_ALIGNMENT);

        p.add(p_title);

        BillBLL billBLL = new BillBLL();
        ArrayList<Bill> billList = new ArrayList<>(billBLL.findAllBills());
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        logTable.getTableHeader().setBackground(purple2);
        for(Bill b: billList){
            String fields[] = {Integer.toString(b.id()), b.numeClient(), b.adresa(), b.telefon(), b.produs(), Integer.toString(b.cantitate()), Double.toString(b.pret())};
            model.addRow(fields);
        }
        logTable.setPreferredScrollableViewportSize(new Dimension(700, 150));
        p1.add(new JScrollPane(logTable));
        p1.setBackground(bg_color);
        p1.setLayout(new FlowLayout());

        p.add(p1);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(bg_color);

        frame.setContentPane(p);
        frame.setVisible(true);
    }
}
