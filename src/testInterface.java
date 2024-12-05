import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class testInterface {

    static Theme.Colors themeColors = new Theme.Colors();
    static Theme.DevSettings devSettings = new Theme.DevSettings();
    
        public static void main(String[] args) {
            JFrame frame = new JFrame("Payment");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(650, 700);
            frame.setVisible(devSettings.getSetting("visible"));
            frame.setAlwaysOnTop(devSettings.getSetting("alwaysOnTop"));
            frame.setLocationRelativeTo(null);
            frame.setResizable(devSettings.getSetting("resizable"));
            frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));
            frame.setVisible(true);
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            JLabel title = new JLabel("Payment");
            title.setForeground(Color.decode(themeColors.getColor("header")));
            title.setFont(new Font("Arial", Font.BOLD, 30));

            frame.add(title, gbc);

            gbc.gridy = 1;

            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
            headerPanel.setPreferredSize(new Dimension(550, 36));
            headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JButton backButton = new JButton("Back");
            backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            backButton.setForeground(Color.decode(themeColors.getColor("text")));
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            headerPanel.add(backButton);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
            mainPanel.setPreferredSize(new Dimension(550, 500));
            mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            frame.add(headerPanel, gbc);

            gbc.gridy = 2;
            frame.add(mainPanel, gbc);

            // TODO: Add payment form here
        
    }

}
