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


    public void showAdminPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(500, 650);
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
        menuPanel.setPreferredSize(new Dimension(400, 400));
        menuPanel.setLayout(new GridLayout(3, 2, 0, 0));  // 3 rows, 2 columns, 10px spacing

        // Main Buttons
        JButton addProduct = new JButton("Add Product");
        addProduct.setBackground(Color.decode(themeColors.getColor("secondary")));
        addProduct.setForeground(Color.decode(themeColors.getColor("text")));
        addProduct.setBorder(BorderFactory.createLineBorder(Color.decode(themeColors.getColor("subHeader")), 10));
        addProduct.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(addProduct);

        JButton viewProducts = new JButton("View Products");
        viewProducts.setBackground(Color.decode(themeColors.getColor("secondary")));
        viewProducts.setForeground(Color.decode(themeColors.getColor("text")));
        viewProducts.setBorder(BorderFactory.createLineBorder(Color.decode(themeColors.getColor("subHeader")), 10));
        viewProducts.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(viewProducts);

        JButton viewOrders = new JButton("View Orders");
        viewOrders.setBackground(Color.decode(themeColors.getColor("secondary")));
        viewOrders.setForeground(Color.decode(themeColors.getColor("text")));
        viewOrders.setBorder(BorderFactory.createLineBorder(Color.decode(themeColors.getColor("subHeader")), 10));
        viewOrders.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(viewOrders);

        JButton viewUsers = new JButton("View Users");
        viewUsers.setBackground(Color.decode(themeColors.getColor("secondary")));
        viewUsers.setForeground(Color.decode(themeColors.getColor("text")));
        viewUsers.setBorder(BorderFactory.createLineBorder(Color.decode(themeColors.getColor("subHeader")), 10));
        viewUsers.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(viewUsers);

        JButton addAdmin = new JButton("Add Admin");
        addAdmin.setBackground(Color.decode(themeColors.getColor("secondary")));
        addAdmin.setForeground(Color.decode(themeColors.getColor("text")));
        addAdmin.setBorder(BorderFactory.createLineBorder(Color.decode(themeColors.getColor("subHeader")), 10));
        addAdmin.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(addAdmin);

        JButton viewAdmins = new JButton("View Admins");
        viewAdmins.setBackground(Color.decode(themeColors.getColor("secondary")));
        viewAdmins.setForeground(Color.decode(themeColors.getColor("text")));
        viewAdmins.setBorder(BorderFactory.createLineBorder(Color.decode(themeColors.getColor("subHeader")), 10));
        viewAdmins.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(viewAdmins);

        addProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                EditProduct editProduct = new EditProduct();
                editProduct.showEditor(null, null, null);
            }
        }); 

        viewProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Homepage homepage = new Homepage(null, true);
                homepage.showHomepage();
            }
        });
        
        frame.add(menuPanel, gbc);

    }
}
