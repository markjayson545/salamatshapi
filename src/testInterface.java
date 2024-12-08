import Admin.AdminPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class testInterface {

    static Theme.Colors themeColors = new Theme.Colors();
    static Theme.DevSettings devSettings = new Theme.DevSettings();

    private static void showFlowLayoutDemo() {
        JFrame frame = new JFrame("FlowLayout Demo");
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 300);
        
        frame.add(new JButton("Button 1"));
        frame.add(new JButton("Button 2"));
        frame.add(new JButton("Button 3"));
        frame.add(new JButton("Long Button 4"));
        frame.add(new JButton("5"));
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showBorderLayoutDemo() {
        JFrame frame = new JFrame("BorderLayout Demo");
        frame.setLayout(new BorderLayout(10, 10));
        frame.setSize(400, 300);
        
        frame.add(new JButton("NORTH"), BorderLayout.NORTH);
        frame.add(new JButton("SOUTH"), BorderLayout.SOUTH);
        frame.add(new JButton("EAST"), BorderLayout.EAST);
        frame.add(new JButton("WEST"), BorderLayout.WEST);
        frame.add(new JButton("CENTER"), BorderLayout.CENTER);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showGridLayoutDemo() {
        JFrame frame = new JFrame("GridLayout Demo");
        frame.setLayout(new GridLayout(3, 2, 10, 10));
        frame.setSize(400, 300);
        
        for (int i = 1; i <= 6; i++) {
            frame.add(new JButton("Button " + i));
        }
        AdminPanel adminPanel = new AdminPanel();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Flow Layout Demo
        JFrame flowFrame = new JFrame("FlowLayout Demo");
        flowFrame.setLayout(new FlowLayout());
        flowFrame.setSize(400, 300);
        flowFrame.setLocation(50, 50);
        
        flowFrame.add(new JButton("Button 1"));
        flowFrame.add(new JButton("Button 2"));
        flowFrame.add(new JButton("Button 3"));
        flowFrame.add(new JButton("Long Button 4"));
        flowFrame.add(new JButton("5"));
        
        flowFrame.setVisible(true);

        // Border Layout Demo
        JFrame borderFrame = new JFrame("BorderLayout Demo");
        borderFrame.setLayout(new BorderLayout(10, 10));
        borderFrame.setSize(400, 300);
        borderFrame.setLocation(500, 50);
        
        borderFrame.add(new JButton("NORTH"), BorderLayout.NORTH);
        borderFrame.add(new JButton("SOUTH"), BorderLayout.SOUTH);
        borderFrame.add(new JButton("EAST"), BorderLayout.EAST);
        borderFrame.add(new JButton("WEST"), BorderLayout.WEST);
        borderFrame.add(new JButton("CENTER"), BorderLayout.CENTER);
        
        borderFrame.setVisible(true);

        // Grid Layout Demo
        JFrame gridFrame = new JFrame("GridLayout Demo");
        gridFrame.setLayout(new GridLayout(3, 2, 10, 10));
        gridFrame.setSize(400, 300);
        gridFrame.setLocation(950, 50);
        
        for (int i = 1; i <= 6; i++) {
            gridFrame.add(new JButton("Button " + i));
        }
        
        gridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridFrame.setVisible(true);

        // Box Layout Demo
        JFrame boxFrame = new JFrame("BoxLayout Demo");
        Box verticalBox = Box.createVerticalBox();
        boxFrame.add(verticalBox);
        boxFrame.setSize(400, 300);
        boxFrame.setLocation(50, 400);
        
        verticalBox.add(new JButton("Top"));
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(new JButton("Middle"));
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(new JButton("Bottom"));
        
        boxFrame.setVisible(true);

        // Card Layout Demo
        JFrame cardFrame = new JFrame("CardLayout Demo");
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardFrame.add(cardPanel);
        cardFrame.setSize(400, 300);
        cardFrame.setLocation(500, 400);

        JPanel card1 = new JPanel();
        card1.add(new JButton("Card 1 Content"));
        JPanel card2 = new JPanel();
        card2.add(new JButton("Card 2 Content"));
        
        cardPanel.add(card1, "Card1");
        cardPanel.add(card2, "Card2");
        JButton switchButton = new JButton("Switch Cards");
        cardFrame.add(switchButton, BorderLayout.SOUTH);
        switchButton.addActionListener(e -> cardLayout.next(cardPanel));
        
        cardFrame.setVisible(true);

        // GridBag Layout Demo
        JFrame gridBagFrame = new JFrame("GridBagLayout Demo");
        gridBagFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gridBagFrame.setSize(400, 300);
        gridBagFrame.setLocation(950, 400);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gridBagFrame.add(new JButton("Button 1"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gridBagFrame.add(new JButton("Button 2"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gridBagFrame.add(new JButton("Wide Button 3"), gbc);
        
        gridBagFrame.setVisible(true);

        // Group Layout Demo
        JFrame groupFrame = new JFrame("GroupLayout Demo");
        JPanel groupPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(groupPanel);
        groupPanel.setLayout(groupLayout);
        groupFrame.add(groupPanel);
        groupFrame.setSize(400, 300);
        groupFrame.setLocation(50, 750);

        JButton b1 = new JButton("B1");
        JButton b2 = new JButton("B2");
        JButton b3 = new JButton("B3");

        groupLayout.setHorizontalGroup(
            groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(b1)
                    .addComponent(b2))
                .addComponent(b3)
        );

        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup()
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(b1)
                    .addComponent(b2))
                .addComponent(b3)
        );
        
        groupFrame.setVisible(true);
    }
}
