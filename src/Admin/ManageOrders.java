package Admin;

import javax.swing.*;
import java.awt.*;

import Theme.DevSettings;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class ManageOrders {
    private String[][] orders;
    private String username;
    private JPanel orderContainerPanel;
    private JScrollPane scrollPane;

    public ManageOrders(String username, String[][] orders) {
        this.orders = orders;
        this.username = username;
    }

    static Theme.Colors themeColors = new Theme.Colors();

    private void updateOrderStatus(String[] order, String newStatus) {
        AdminDatabaseHandler dbHandler = new AdminDatabaseHandler();
        dbHandler.updateOrderStatus(username, order[0], newStatus);
        // Refresh the orders data and display
        orders = dbHandler.getUserOrders(username);
        updateOrderDisplay("All");
    }

    private JPanel createStatusButtonsPanel(String[] order) {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonsPanel.setBackground(Color.decode(themeColors.getColor("primary")));

        JButton toShipBtn = new JButton("To Ship");
        JButton onWayBtn = new JButton("On The Way");
        JButton receivedBtn = new JButton("Received");
        JButton cancelBtn = new JButton("Cancelled");

        JButton[] buttons = {toShipBtn, onWayBtn, receivedBtn, cancelBtn};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.PLAIN, 10));
            btn.setBackground(Color.decode(themeColors.getColor("secondary")));
            btn.setForeground(Color.decode(themeColors.getColor("text")));
            buttonsPanel.add(btn);
        }

        toShipBtn.addActionListener(e -> updateOrderStatus(order, "To Ship"));
        onWayBtn.addActionListener(e -> updateOrderStatus(order, "On the way"));
        receivedBtn.addActionListener(e -> updateOrderStatus(order, "Received"));
        cancelBtn.addActionListener(e -> updateOrderStatus(order, "Cancelled"));

        return buttonsPanel;
    }

    private JPanel createItemContainer(String[] order) {
        String orderId = order[0];
        String itemName = order[2];
        String description = order[3];
        double price = Double.parseDouble(order[4]);
        int quantity = Integer.parseInt(order[5]);
        String status = order[6];
        String deliveryDate = order[7];

        JPanel itemContainer = new JPanel();
        itemContainer.setBackground(Color.decode(themeColors.getColor("primary")));
        itemContainer.setPreferredSize(new Dimension(545, 120)); 
        itemContainer.setLayout(new BorderLayout());
        itemContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel orderIdLabel = new JLabel("Order #" + orderId);
        orderIdLabel.setFont(new Font("Arial", Font.BOLD, 12));
        orderIdLabel.setForeground(Color.decode(themeColors.getColor("text")));
        leftPanel.add(orderIdLabel);

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

        JLabel statusLabel = new JLabel("Status: " + status);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        statusLabel.setForeground(Color.decode(themeColors.getColor("text")));
        rightPanel.add(statusLabel);

        JLabel deliveryLabel = new JLabel("Delivery: " + deliveryDate);
        deliveryLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        deliveryLabel.setForeground(Color.decode(themeColors.getColor("text")));
        rightPanel.add(deliveryLabel);

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

        // Add status buttons panel
        JPanel statusButtonsPanel = createStatusButtonsPanel(order);
        itemContainer.add(statusButtonsPanel, BorderLayout.SOUTH);

        return itemContainer;
    }

    private void updateOrderDisplay(String filter) {
        orderContainerPanel.removeAll();
        int yPosition = 10;
        int totalHeight = 0;

        Map<String, java.util.List<String[]>> groupedOrders = new HashMap<>();
        for (String[] order : orders) {
            if (order.length < 8) continue;
            String groupId = order[1]; // Using groupOrderID instead of orderID
            groupedOrders.computeIfAbsent(groupId, k -> new ArrayList<>()).add(order);
        }

        for (java.util.List<String[]> orderGroup : groupedOrders.values()) {
            if (orderGroup.isEmpty()) continue;
            
            String status = orderGroup.get(0)[6]; // Status is at index 6
            if (!filter.equals("All") && !status.equals(filter)) continue;

            JPanel groupPanel = new JPanel();
            groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
            groupPanel.setBackground(Color.decode(themeColors.getColor("primary")));

            JLabel groupLabel = new JLabel("Group Order #" + orderGroup.get(0)[1], SwingConstants.CENTER); // Using groupOrderID
            groupLabel.setFont(new Font("Arial", Font.BOLD, 14));
            groupLabel.setForeground(Color.decode(themeColors.getColor("text")));
            groupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            groupPanel.add(groupLabel);

            double groupTotal = 0.0;

            for (String[] item : orderGroup) {
                try {
                    JPanel itemContainer = createItemContainer(item);
                    groupPanel.add(itemContainer);
                    
                    double price = Double.parseDouble(item[4]);
                    int quantity = Integer.parseInt(item[5]);
                    groupTotal += price * quantity;
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing order data: " + e.getMessage());
                    continue;
                }
            }

            JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            totalPanel.setBackground(Color.decode(themeColors.getColor("primary")));
            JLabel totalLabel = new JLabel(String.format("Order Total: ₱%.2f", groupTotal));
            totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            totalLabel.setForeground(Color.decode(themeColors.getColor("text")));
            totalPanel.add(totalLabel);
            groupPanel.add(totalPanel);

            groupPanel.setBounds(2, yPosition, 545, orderGroup.size() * 130 + 50); // Increased from 100 to 130
            orderContainerPanel.add(groupPanel);

            yPosition += (orderGroup.size() * 130) + 60; // Increased from 100 to 130
            totalHeight = yPosition;
        }

        orderContainerPanel.setPreferredSize(new Dimension(550, Math.max(totalHeight, 450)));
        orderContainerPanel.revalidate();
        orderContainerPanel.repaint();
    }

    public void showUsersOrders() {
        DevSettings devSettings = new DevSettings();
        Theme.Colors themeColors = new Theme.Colors();
        JFrame frame = new JFrame("Orders");
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

        JLabel title = new JLabel("Orders");
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);
        gbc.gridy = 1;

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        headerPanel.setPreferredSize(new Dimension(550, 36));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.add(headerPanel, gbc);

        // header components goes here
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        backButton.setForeground(Color.decode(themeColors.getColor("text")));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                AdminPanel adminPanel = new AdminPanel();
                adminPanel.showAdminPanel();
            }
        });
        headerPanel.add(backButton);

        JButton allOrders = new JButton("All");
        JButton toShip = new JButton("To Ship");
        JButton onTheWay = new JButton("On the way");
        JButton received = new JButton("Received");

        String buttonBG = themeColors.getColor("secondary");
        String buttonFG = themeColors.getColor("text");

        JButton[] buttons = { allOrders, toShip, onTheWay, received };
        for (JButton button : buttons) {
            button.setBackground(Color.decode(buttonBG));
            button.setForeground(Color.decode(buttonFG));
            headerPanel.add(button);
        }

        allOrders.addActionListener(e -> updateOrderDisplay("All"));
        toShip.addActionListener(e -> updateOrderDisplay("To Ship"));
        onTheWay.addActionListener(e -> updateOrderDisplay("On the way"));
        received.addActionListener(e -> updateOrderDisplay("Received"));

        frame.add(headerPanel, gbc);

        gbc.gridy = 2;

        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("User: " + username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        usernameLabel.setForeground(Color.decode(themeColors.getColor("text")));
        frame.add(usernameLabel, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 3;

        orderContainerPanel = new JPanel();
        orderContainerPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
        orderContainerPanel.setLayout(null);

        scrollPane = new JScrollPane(orderContainerPanel);
        scrollPane.setPreferredSize(new Dimension(550, 450));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        frame.add(scrollPane, gbc);

        // Initial display of all orders
        updateOrderDisplay("All");
    }
}
