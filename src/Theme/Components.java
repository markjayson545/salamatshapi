package Theme;

import javax.swing.*;
import java.awt.*;

public class Components {
    public JButton createButton(String text) {
        Colors themeColors = new Colors();
        JButton button = new JButton(text);
        button.setBackground(Color.decode(themeColors.getColor("secondary")));
        button.setForeground(Color.decode(themeColors.getColor("text")));
        return button;
    }
}
