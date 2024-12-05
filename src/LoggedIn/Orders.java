package LoggedIn;

import javax.swing.*;
import java.awt.*;

import Theme.DevSettings;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Orders {
    private String username;
    private String[][] orders;
    public Orders(String username, String[][] orders){
        username = this.username;
        orders = this.orders;
    }

    private JPanel createItemContainer(String itemName, String description, double price, int quantity){
        Theme.Colors themeColors = new Theme.Colors();
        
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


    public void showOrders(){
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
                Cart cart = new Cart(username);
                cart.showCart();
            }
        });
        headerPanel.add(backButton);

        JButton toPay =  new JButton("To Ship");
        JButton toReceive = new JButton("To Receive");
        JButton toRefund = new JButton("To Refund");

        String buttonBG = themeColors.getColor("secondary");
        String buttonFG = themeColors.getColor("text");

        toPay.setBackground(Color.decode(buttonBG));
        toReceive.setBackground(Color.decode(buttonBG));
        toRefund.setBackground(Color.decode(buttonBG));

        toPay.setForeground(Color.decode(buttonFG));
        toReceive.setForeground(Color.decode(buttonFG));
        toRefund.setForeground(Color.decode(buttonFG));

        headerPanel.add(toPay);
        headerPanel.add(toReceive);
        headerPanel.add(toRefund);

        frame.add(headerPanel, gbc);

        gbc.gridy = 2;

        JPanel orderContainerPanel = new JPanel();
        orderContainerPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
        orderContainerPanel.setLayout(null);

        int yPosition = 10;
        int totalHeight = 0;

        for (String[] order : orders){
            String itemName = order[0];
            String description = order[1];
            double price = Double.parseDouble(order[2]);
            int quantity = Integer.parseInt(order[3]);

            JPanel itemContainer = createItemContainer(itemName, description, price, quantity);
            itemContainer.setBounds(2, yPosition, 545, 75);
            orderContainerPanel.add(itemContainer);

            yPosition += 85;
            totalHeight = yPosition;
        }

        orderContainerPanel.setPreferredSize(new Dimension(550, totalHeight));

        JScrollPane scrollPane = new JScrollPane(orderContainerPanel);
        scrollPane.setPreferredSize(new Dimension(550, 450));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        frame.add(scrollPane, gbc);
        
    }
}
