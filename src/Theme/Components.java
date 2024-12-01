package Theme;

import javax.swing.*;
import java.awt.*;

public class Components {
    Colors themeColors = new Colors();

    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode(themeColors.getColor("secondary")));
        button.setForeground(Color.decode(themeColors.getColor("text")));
        return button;
    }

    public JLabel createTextFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.decode(themeColors.getColor("text")));
        return label;
    }

    public JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }

    public JPasswordField createPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setPreferredSize(new Dimension(200, 30));
        return passwordField;
    }


}
