package LoggedIn;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Theme.DevSettings;
import UserFiles.UserFileHandler;

public class Cart extends UserFileHandler {
    private String username;

    public Cart(String username) {
        this.username = username;
    }

    private double totalPrice = getTotalPrice(username);
    private JLabel totalLabel = new JLabel("Total: ₱" + getTotalPrice(username));
    private JButton checkoutButton = new JButton("Checkout");

    private void updateTotalPrice() {
        totalPrice = getTotalPrice(username);
        totalLabel.setText("Total: ₱" + totalPrice);
        checkoutButton.setEnabled(totalPrice > 0);
    }

    private JPanel createCartItemPanel(String productName, String description, int quantity, String price) {
        JPanel productContainer = new JPanel();
        Theme.Colors themeColors = new Theme.Colors();
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
        int[] getQuantity = { quantity };

        JButton decrementButton = new JButton("-");
        decrementButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        decrementButton.setForeground(Color.decode(themeColors.getColor("text")));

        panel.add(decrementButton);

        JLabel quantityLabel = new JLabel("1");
        quantityLabel.setText(String.valueOf(quantity));
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityLabel.setForeground(Color.decode(themeColors.getColor("text")));
        panel.add(quantityLabel);

        JButton incrementButton = new JButton("+");
        incrementButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        incrementButton.setForeground(Color.decode(themeColors.getColor("text")));

        panel.add(incrementButton);

        productContainer.add(panel, gbc);

        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridy = 4;

        JButton removeButton = new JButton("Remove");
        removeButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        removeButton.setForeground(Color.decode(themeColors.getColor("text")));
        productContainer.add(removeButton, gbc);

        // On Click Functions
        decrementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getQuantity[0] > 1) {
                    getQuantity[0]--;
                    quantityLabel.setText(String.valueOf(getQuantity[0]));
                    decrementItemAmount(username, productName);
                    updateTotalPrice();
                }

            }
        });

        incrementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getQuantity[0] >= 1) {
                    getQuantity[0]++;
                    quantityLabel.setText(String.valueOf(getQuantity[0]));
                    addItemToCart(username, productName, description, 1, price);
                    updateTotalPrice();
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteItem(username, productName);
                productContainer.setVisible(false);
                updateTotalPrice();
            }
        });

        return productContainer;
    }

    public void showCart() {
        Theme.Colors themeColors = new Theme.Colors();
        DevSettings devSettings = new DevSettings();
        JFrame frame = new JFrame("Cart");

        updateTotalPrice();

        frame.setSize(850, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(devSettings.getSetting("visible"));
        if (devSettings.getSetting("centered")) {
            frame.setLocationRelativeTo(null);
        }
        frame.setResizable(devSettings.getSetting("resizable"));
        frame.setAlwaysOnTop(devSettings.getSetting("alwaysOnTop"));
        frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Cart");
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);
        gbc.gridy = 1;

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        headerPanel.setPreferredSize(new Dimension(800, 36));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        backButton.setForeground(Color.decode(themeColors.getColor("text")));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Homepage homepage = new Homepage(username, false);
                homepage.showHomepage();
            }
        });
        headerPanel.add(backButton, BorderLayout.CENTER);

        frame.add(headerPanel, gbc);

        JPanel cartContainer = new JPanel();
        cartContainer.setBackground(Color.decode(themeColors.getColor("subHeader")));
        cartContainer.setPreferredSize(new Dimension(780, 470));
        cartContainer.setLayout(new FlowLayout(FlowLayout.LEFT));

        String[][] cartItems = getCartItems(username);

        for (String item[] : cartItems) {
            cartContainer.add(createCartItemPanel(item[0], item[1], Integer.parseInt(item[3]), item[2]));
        }

        gbc.gridy = 2;

        frame.add(cartContainer, gbc);

        gbc.gridy = 3;

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        footerPanel.setPreferredSize(new Dimension(780, 36));
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        totalLabel.setForeground(Color.decode(themeColors.getColor("text")));
        footerPanel.add(totalLabel, BorderLayout.CENTER);

        checkoutButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        checkoutButton.setForeground(Color.decode(themeColors.getColor("text")));
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Checkout checkout = new Checkout(username, cartItems);
                checkout.showCheckout();
            }
        });

        footerPanel.add(checkoutButton, BorderLayout.CENTER);

        frame.add(footerPanel, gbc);

    }
}
