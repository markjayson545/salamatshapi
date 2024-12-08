package LoggedIn;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Admin.AdminDatabaseHandler;
import Admin.AdminPanel;
import Admin.EditProduct;
import MainActivity.Main;
import Theme.Colors;
import Theme.DevSettings;
import UserFiles.UserFileHandler;

public class Homepage extends UserFileHandler {

    private String username;
    private boolean isAdmin;

    public Homepage(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    private int totalCartItems = 0;
    JButton cartButton = new JButton("View Cart" + " (" + totalCartItems + ")");

    private void getTotalCartItems() {
        if (!isAdmin) {
            int quantity = 0;
            String[][] cartItems = getCartItems(username);
            for (int i = 0; i < cartItems.length; i++) {
                quantity++;
            }
            cartButton.setText("View Cart" + " (" + quantity + ")");
            totalCartItems = quantity;
        }
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

        // User Mode
        if (!isAdmin) {
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
                    addItemToCart(username, productName, description, quantity[0], price);
                    JOptionPane.showMessageDialog(null, "Added to cart");
                    getTotalCartItems();
                }
            });
        }
        // Admin Mode
        else {
            JPanel adminPanel = new JPanel();
            adminPanel.setBackground(Color.decode(themeColors.getColor("primary")));
            adminPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            JButton editButton = new JButton("Edit");
            editButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            editButton.setForeground(Color.decode(themeColors.getColor("text")));
            adminPanel.add(editButton);

            EditProduct editProduct = new EditProduct();
            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editProduct.showEditor(productName, price, description);
                }
            });

            JButton removeButton = new JButton("Remove");
            removeButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            removeButton.setForeground(Color.decode(themeColors.getColor("text")));
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    AdminDatabaseHandler adminDatabaseHandler = new AdminDatabaseHandler();
                    adminDatabaseHandler.deleteProduct(productName);
                    JOptionPane.showMessageDialog(null, "Product removed");
                    productContainer.setVisible(false);
                }
            });

            adminPanel.add(removeButton);
            productContainer.add(adminPanel, gbc);
        }

        return productContainer;
    }

    public void showHomepage() {
        Colors themeColors = new Colors();
        DevSettings devSettings = new DevSettings();
        JFrame frame = new JFrame("Homepage");

        if (!isAdmin) {
            getTotalCartItems();
        }

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

        if (isAdmin) {
            frame.setTitle("View Products");
        }

        // Top Bar
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode(themeColors.getColor("primary")));
        panel.setPreferredSize(new Dimension(800, 36));
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        if (!isAdmin) {
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
        }

        if (isAdmin) {
            JButton addProductButton = new JButton("Add Product");
            addProductButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            addProductButton.setForeground(Color.decode(themeColors.getColor("text")));
            addProductButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    EditProduct editProduct = new EditProduct();
                    editProduct.showEditor(null, null, null);
                }
            });

            JButton backButton = new JButton("Back");
            backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            backButton.setForeground(Color.decode(themeColors.getColor("text")));
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    AdminPanel adminPanel = new AdminPanel();
                    adminPanel.showAdminPanel();
                }
            });
            panel.add(backButton, BorderLayout.EAST);
            panel.add(addProductButton, BorderLayout.WEST);
        } else {
            int orderNumbers = getTotalGroupedOrders(username);
            JButton viewOrdersButton = new JButton("View Orders" + " (" + orderNumbers + ")");
            viewOrdersButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            viewOrdersButton.setForeground(Color.decode(themeColors.getColor("text")));
            viewOrdersButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    String[][] userOrder = getOrders(username);
                    Orders orders = new Orders(username, userOrder);
                    orders.showOrders();
                }
            });
            panel.add(viewOrdersButton, BorderLayout.WEST);

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
        }
        frame.add(panel, gbc);

        gbc.gridy = 2;

        // Main Content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        AdminDatabaseHandler adminDatabaseHandler = new AdminDatabaseHandler();
        String[][] productsList = adminDatabaseHandler.getProducts();

        // Calculate required rows and height
        int itemsPerRow = 5;
        int rows = (int) Math.ceil(productsList.length / (double) itemsPerRow);
        int baseHeight = 480;
        int rowHeight = 170; // Height of each product panel + spacing
        int calculatedHeight = Math.max(baseHeight, rows * rowHeight);

        mainPanel.setPreferredSize(new Dimension(800, calculatedHeight));

        for (String[] product : productsList) {
            mainPanel.add(createProductPanel(product[0], product[1], product[2]));
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(780, Math.min(calculatedHeight, 480)));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane, gbc);

        frame.setVisible(true);
    }

}
