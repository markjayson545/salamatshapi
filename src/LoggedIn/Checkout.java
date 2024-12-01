package LoggedIn;

import Theme.DevSettings;
import UserFiles.UserFileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Checkout {

    String username;
    String[][] cartItems;

    public Checkout(String username, String[][] cartItems) {
        this.username = username;
        this.cartItems = cartItems;
    }

    UserFileHandler userFileHandler = new UserFileHandler();
    Theme.Colors themeColors = new Theme.Colors();
    DevSettings devSettings = new DevSettings();

    private double totalPrice = userFileHandler.getTotalPrice(username);
    JLabel totalLabel = new JLabel("Total: ₱" + userFileHandler.getTotalPrice(username));
    private void updateTotalPrice() {
        totalPrice = userFileHandler.getTotalPrice(username);
        totalLabel.setText("Total: ₱" + totalPrice);
    }

    private JPanel createItemContainer(String itemName, String description, double price, int quantity){
        JPanel itemContainer = new JPanel();
        itemContainer.setBackground(Color.decode(themeColors.getColor("primary")));
        itemContainer.setPreferredSize(new Dimension(545, 75));
        itemContainer.setLayout(new BorderLayout());
        itemContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel itemNameLabel = new JLabel(itemName);
        itemNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        itemNameLabel.setForeground(Color.decode(themeColors.getColor("text")));
        leftPanel.add(itemNameLabel);

        JLabel itemDescription = new JLabel(description);
        itemDescription.setFont(new Font("Arial", Font.PLAIN, 15));
        itemDescription.setForeground(Color.decode(themeColors.getColor("text")));
        leftPanel.add(itemDescription);

        itemContainer.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel itemPrice = new JLabel("Price: ₱" + price + " x " + quantity, SwingConstants.RIGHT);
        itemPrice.setFont(new Font("Arial", Font.PLAIN, 10));
        itemPrice.setForeground(Color.decode(themeColors.getColor("text")));
        rightPanel.add(itemPrice);

        JLabel itemTotalPrice = new JLabel("Total Price: ₱" + (price * quantity), SwingConstants.RIGHT);
        itemTotalPrice.setFont(new Font("Arial", Font.PLAIN, 10));
        itemTotalPrice.setForeground(Color.decode(themeColors.getColor("text")));
        rightPanel.add(itemTotalPrice);

        itemContainer.add(rightPanel, BorderLayout.EAST);

        return itemContainer;
    }

    public void showCheckout(){
        JFrame frame = new JFrame("Checkout");

        updateTotalPrice();

        frame.setSize(650, 700);
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

        JLabel title = new JLabel("Checkout");
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);
        gbc.gridy = 1;

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        headerPanel.setPreferredSize(new Dimension(550, 36));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.add(headerPanel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        backButton.setForeground(Color.decode(themeColors.getColor("text")));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Cart cart = new Cart(username);
                cart.showCart();
            }
        });
        headerPanel.add(backButton);

        frame.add(headerPanel, gbc);

        gbc.gridy = 2;

        JPanel checkoutItemsContainer = new JPanel();
        checkoutItemsContainer.setBackground(Color.decode(themeColors.getColor("subHeader")));
        checkoutItemsContainer.setPreferredSize(new Dimension(550, 450));
        checkoutItemsContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

        // CheckOut Items
        for (String[] cartItem : cartItems) {
            String itemName = cartItem[0];
            String description = cartItem[1];
            double price = Double.parseDouble(cartItem[2]);
            int quantity = Integer.parseInt(cartItem[3]);

            JPanel itemContainer = createItemContainer(itemName, description, price, quantity);
            checkoutItemsContainer.add(itemContainer);
        }
        frame.add(checkoutItemsContainer, gbc);

        gbc.gridy = 3;

        // Footer Here
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        footerPanel.setPreferredSize(new Dimension(550, 36));
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        totalLabel.setForeground(Color.decode(themeColors.getColor("text")));
        footerPanel.add(totalLabel);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        checkoutButton.setForeground(Color.decode(themeColors.getColor("text")));
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checkout
            }
        });
        footerPanel.add(checkoutButton);

        frame.add(footerPanel, gbc);


    }

}