package LoggedIn;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import MainActivity.Main;
import Theme.Colors;
import Theme.DevSettings;
import UserFiles.UserFileHandler;

public class Homepage {

    private String username;

    public Homepage(String username) {
        this.username = username;
    }

    private int totalCartItems = 0;
    JButton cartButton = new JButton("View Cart" + " (" + totalCartItems + ")");
    private void getTotalCartItems(){
        UserFileHandler userFileHandler = new UserFileHandler();
        int quantity = 0;
        String[][] cartItems = userFileHandler.getCartItems(username);
        for (int i = 0; i < cartItems.length; i++) {
            quantity++;
        }
        cartButton.setText("View Cart" + " (" + quantity + ")");
        totalCartItems = quantity;
    }

    private JPanel createProductPanel(String productName, String description, String price) {

        Theme.Colors themeColors = new Theme.Colors();
        JPanel productContainer = new JPanel();
        
        productContainer.setBackground(Color.decode(themeColors.getColor("primary")));
        productContainer.setPreferredSize(new Dimension(150, 150));
        productContainer.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel productLabel = new JLabel(productName);
        productLabel.setFont(new Font("Arial", Font.BOLD, 20));
        productLabel.setForeground(Color.decode(themeColors.getColor("text")));
        productContainer.add(productLabel, gbc);

        gbc.gridy = 1;

        JLabel productDescription = new JLabel(description);
        productDescription.setFont(new Font("Arial", Font.PLAIN, 15));
        productDescription.setForeground(Color.decode(themeColors.getColor("text")));
        productContainer.add(productDescription, gbc);

        gbc.gridy = 2;

        JLabel productPrice = new JLabel("Price: ₱" + price);
        productPrice.setFont(new Font("Arial", Font.PLAIN, 10));
        productPrice.setForeground(Color.decode(themeColors.getColor("text")));
        productContainer.add(productPrice, gbc);

        gbc.gridy = 3;

        JPanel panel = new JPanel();
        panel.setBackground(Color.decode(themeColors.getColor("primary")));
        panel.setPreferredSize(new Dimension(150, 30));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton decrementButton = new JButton("-");
        decrementButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        decrementButton.setForeground(Color.decode(themeColors.getColor("text")));
        panel.add(decrementButton, BorderLayout.WEST);

        final int[] quantity = { 1 };

        JLabel quantityLabel = new JLabel(String.valueOf(quantity[0]));
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityLabel.setForeground(Color.decode(themeColors.getColor("text")));
        panel.add(quantityLabel, BorderLayout.CENTER);

        JButton incrementButton = new JButton("+");
        incrementButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        incrementButton.setForeground(Color.decode(themeColors.getColor("text")));
        panel.add(incrementButton, BorderLayout.EAST);

        decrementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (quantity[0] > 1) {
                    quantity[0]--;
                    quantityLabel.setText(String.valueOf(quantity[0]));
                    getTotalCartItems();
                }
            }
        });

        incrementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (quantity[0] < 10) {
                    quantity[0]++;
                    quantityLabel.setText(String.valueOf(quantity[0]));
                    getTotalCartItems();
                }
            }
        });

        productContainer.add(panel, gbc);

        gbc.insets = new Insets(5, 0, 0, 0);

        gbc.gridy = 4;

        JButton productButton = new JButton("Add to Cart");
        productButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        productButton.setForeground(Color.decode(themeColors.getColor("text")));
        productContainer.add(productButton, gbc);
        productButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserFileHandler userFileHandler = new UserFileHandler();
                userFileHandler.addItemToCart(username, productName, description, quantity[0], price);
                getTotalCartItems();
            }
        });
        return productContainer;
    }

    public void showHomepage() {
        Colors themeColors = new Colors();
        DevSettings devSettings = new DevSettings();
        JFrame frame = new JFrame("Homepage");

        getTotalCartItems();

        frame.setSize(850, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(devSettings.getSetting("visible"));
        frame.setAlwaysOnTop(devSettings.getSetting("alwaysOnTop"));
        frame.setResizable(devSettings.getSetting("resizable"));
        if (devSettings.getSetting("centered")) {
            frame.setLocationRelativeTo(null);
        }
        frame.setVisible(devSettings.getSetting("visible"));

        frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));

        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;

        // Title
        JLabel title = new JLabel();
        title.setText(devSettings.getAppName());
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title, gbc);

        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridy = 1;

        // Top Bar
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode(themeColors.getColor("primary")));
        panel.setPreferredSize(new Dimension(800, 36));
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        cartButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        cartButton.setForeground(Color.decode(themeColors.getColor("text")));
        cartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Cart cart = new Cart(username);
                cart.showCart();
            }
        });
        panel.add(cartButton, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        logoutButton.setForeground(Color.decode(themeColors.getColor("text")));
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main main = new Main();
                main.showHomepage();
            }
        });
        panel.add(logoutButton, BorderLayout.EAST);

        frame.add(panel, gbc);

        gbc.gridy = 2;

        // Main Content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
        mainPanel.setPreferredSize(new Dimension(780, 470));
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        String[][] productsList = {
                { "Product 1", "Description 1", "100" },
                { "Product 2", "Description 2", "200" },
                { "Product 3", "Description 3", "300" },
                { "Product 4", "Description 4", "400" },
                { "Product 5", "Description 5", "500" },
                { "Product 6", "Description 6", "600" },
                { "Product 7", "Description 7", "700" },
                { "Product 8", "Description 8", "800" },
                { "Product 9", "Description 9", "900" },
                { "Product 10", "Description 10", "1000" },
                { "Product 11", "Description 11", "1100" },
                { "Product 12", "Description 12", "1200" },
                { "Product 13", "Description 13", "1300" },
                { "Product 14", "Description 14", "1400" },
                { "Product 15", "Description 15", "1500" },
        };

        for (String[] product : productsList) {
            mainPanel.add(createProductPanel(product[0], product[1], product[2]));
        }

        frame.add(mainPanel, gbc);

        frame.setVisible(true);
    }

}
