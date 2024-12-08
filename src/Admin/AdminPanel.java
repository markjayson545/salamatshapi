package Admin;

import javax.swing.*;

import LoggedIn.Homepage;
import MainActivity.Main;
import Theme.DevSettings;

import java.awt.*;
import java.awt.event.*;

public class AdminPanel extends AdminDatabaseHandler {
    static DevSettings devSettings = new DevSettings();
    static Theme.Colors themeColors = new Theme.Colors();
    static ChooseUsers chooseUsers = new ChooseUsers();

    public void showAdminPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (devSettings.getSetting("centered")) {
            frame.setLocationRelativeTo(null);
        }
        frame.setResizable(devSettings.getSetting("resizable"));
        frame.setAlwaysOnTop(devSettings.getSetting("alwaysOnTop"));
        frame.setVisible(devSettings.getSetting("visible"));
        frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Admin Panel");
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
        headerPanel.setPreferredSize(new Dimension(400, 400));
        headerPanel.setLayout(new GridBagLayout());

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);

        // Menu Buttons
        JButton backButton = new JButton("Logout");
        backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        backButton.setForeground(Color.decode(themeColors.getColor("text")));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main main = new Main();
                main.showHomepage();
            }
        });

        frame.add(backButton, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 0, 0);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
        menuPanel.setPreferredSize(new Dimension(450, 100));
        menuPanel.setLayout(new GridLayout(1, 3, 20, 0));

        JButton viewProducts = new JButton("View Products");
        JButton viewUsers = new JButton("View Users");

        JButton[] buttons = { viewProducts, viewUsers };
        for (JButton button : buttons) {
            button.setBackground(Color.decode(themeColors.getColor("secondary")));
            button.setForeground(Color.decode(themeColors.getColor("text")));
            button.setBorder(BorderFactory.createLineBorder(Color.decode(themeColors.getColor("subHeader")), 10));
            button.setFont(new Font("Arial", Font.BOLD, 14));
            menuPanel.add(button);
        }

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 20, 10, 20);

        viewProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Homepage homepage = new Homepage(null, true);
                homepage.showHomepage();
            }
        });

        viewUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                chooseUsers.showChooseUser();
            }
        });

        frame.add(menuPanel, gbc);

    }
}
