package ro.tuc.Presentation.View;

import javax.swing.*;
import java.awt.*;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ErrorBox {
    private static Color bg_color = new Color(255, 234, 210);
    private static Color purple2 = new Color(172, 177, 214);
    private static Color purple3 = new Color(81, 50, 82);
    public static void incorrectValueError(JTextField tf, String mesaj, Component parentComponent){
        tf.setBackground(Color.RED);
        UIManager.put("OptionPane.background", purple3);
        UIManager.put("Panel.background", purple3);
        UIManager.put("Button.background", purple2);
        String instructions = mesaj;
        JTextArea area = new JTextArea(instructions);
        area.setFont(new Font("Tahoma", Font.PLAIN, 14));
        area.setBackground(purple3);
        area.setForeground(bg_color);
        JOptionPane.showMessageDialog(parentComponent, area, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }
    public static void unselectedOption(String mesaj, Component parentComponent){
        UIManager.put("OptionPane.background", purple3);
        UIManager.put("Panel.background", purple3);
        UIManager.put("Button.background", purple2);
        String instructions = mesaj;
        JTextArea area = new JTextArea(instructions);
        area.setFont(new Font("Tahoma", Font.PLAIN, 14));
        area.setBackground(purple3);
        area.setForeground(bg_color);
        JOptionPane.showMessageDialog(parentComponent, area, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }
    public static void correctValue(JTextField tf, String mesaj, Component parentComponent){
        tf.setBackground(Color.WHITE);
    }
}
