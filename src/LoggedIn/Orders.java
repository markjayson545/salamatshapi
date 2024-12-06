package LoggedIn;

import javax.swing.*;
import java.awt.*;

import Theme.DevSettings;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Orders {
    private String username;
    private String[][] orders;
    private JPanel orderContainerPanel;
    private JScrollPane scrollPane;

    public Orders(String username, String[][] orders) {
        this.username = username;
        this.orders = orders;
    }

    static Theme.Colors themeColors = new Theme.Colors();

    private JPanel createItemContainer(String orderId, String itemName, String description, double price, int quantity,
            String status, String deliveryDate) {

        JPanel itemContainer = new JPanel();
        itemContainer.setBackground(Color.decode(themeColors.getColor("primary")));
        itemContainer.setPreferredSize(new Dimension(545, 75));
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

        return itemContainer;
    }

    private void updateOrderDisplay(String filter) {
        orderContainerPanel.removeAll();
        int yPosition = 10;
        int totalHeight = 0;

        Map<String, java.util.List<String[]>> groupedOrders = new HashMap<>();
        for (String[] order : orders) {
            if (order.length < 8) continue;  // Skip invalid orders
            String groupId = order[7];
            groupedOrders.computeIfAbsent(groupId, k -> new ArrayList<>()).add(order);
        }

        for (java.util.List<String[]> orderGroup : groupedOrders.values()) {
            if (orderGroup.isEmpty()) continue;
            
            String status = orderGroup.get(0)[5];
            if (!filter.equals("All") && !status.equals(filter)) continue;

            // Create a container for each order group
            JPanel groupPanel = new JPanel();
            groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
            groupPanel.setBackground(Color.decode(themeColors.getColor("primary")));

            // Add group header
            JLabel groupLabel = new JLabel("Order #" + orderGroup.get(0)[7]);
            groupLabel.setFont(new Font("Arial", Font.BOLD, 14));
            groupLabel.setForeground(Color.decode(themeColors.getColor("text")));
            groupPanel.add(groupLabel);

            // Add items
            for (String[] item : orderGroup) {
                try {
                    JPanel itemContainer = createItemContainer(
                        item[0], item[1], item[2], 
                        Double.parseDouble(item[3]), 
                        Integer.parseInt(item[4]), 
                        item[5], item[6]
                    );
                    groupPanel.add(itemContainer);
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            groupPanel.setBounds(2, yPosition, 545, orderGroup.size() * 85 + 30);
            orderContainerPanel.add(groupPanel);

            yPosition += (orderGroup.size() * 85) + 40;
            totalHeight = yPosition;
        }

        orderContainerPanel.setPreferredSize(new Dimension(550, Math.max(totalHeight, 450)));
        orderContainerPanel.revalidate();
        orderContainerPanel.repaint();
    }

    public void showOrders() {
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
                Homepage homepage = new Homepage(username);
                homepage.showHomepage();
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
