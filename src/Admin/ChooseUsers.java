package Admin;

import javax.swing.*;
import javax.swing.event.*;

import Theme.DevSettings;

import java.awt.*;
import java.awt.event.*;

public class ChooseUsers extends AdminDatabaseHandler {
    Theme.Colors themeColors = new Theme.Colors();
    DevSettings devSettings = new DevSettings();
    AdminPanel adminPanel = new AdminPanel();
    private JPanel usersContainer;

    public void showChooseUser() {
        JFrame frame = new JFrame("Choose User");
        frame.setSize(600, 650);
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

        JLabel title = new JLabel("Choose User");
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        headerPanel.setPreferredSize(new Dimension(500, 50));
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();

        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        headerGbc.weightx = 0.2;
        headerGbc.anchor = GridBagConstraints.WEST;
        headerGbc.insets = new Insets(0, 10, 0, 0);
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        backButton.setForeground(Color.decode(themeColors.getColor("text")));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                adminPanel.showAdminPanel();
            }
        });
        headerPanel.add(backButton, headerGbc);

        headerGbc.gridx = 1;
        headerGbc.weightx = 1.0;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.decode(themeColors.getColor("primary")));

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setForeground(Color.decode(themeColors.getColor("text")));
        searchPanel.add(searchLabel);

        JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                filterUsers(searchField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                filterUsers(searchField.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                filterUsers(searchField.getText());
            }
        });
        searchPanel.add(searchField);

        headerPanel.add(searchPanel, headerGbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);

        frame.add(headerPanel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        usersContainer = new JPanel(); // Remove the JPanel declaration since it's now a field
        usersContainer.setBackground(Color.decode(themeColors.getColor("subHeader")));
        usersContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

        String[] users = getUsernames();
        for (String user : users) {
            JButton userButton = new JButton(user);
            userButton.setPreferredSize(new Dimension(180, 40)); // Fixed button size
            userButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            userButton.setForeground(Color.decode(themeColors.getColor("text")));
            userButton.setFont(new Font("Arial", Font.BOLD, 20));
            userButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    String[][] orders = getUserOrders(user);
                    ManageOrders manageOrders = new ManageOrders(user, orders);
                    manageOrders.showUsersOrders();
                }
            });
            usersContainer.add(userButton);
        }

        JScrollPane scrollPane = new JScrollPane(usersContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel scrollWrapper = new JPanel(new BorderLayout());
        scrollWrapper.setPreferredSize(new Dimension(400, 400));
        scrollWrapper.add(scrollPane, BorderLayout.CENTER);
        scrollWrapper.setBackground(Color.decode(themeColors.getColor("subHeader")));

        frame.add(scrollWrapper, gbc);

    }

    public void showAdmin() {
        String[] usernames = getAdminUsernames();
        JFrame frame = new JFrame("Administrators");
        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (devSettings.getSetting("centered")) {
            frame.setLocationRelativeTo(null);
        }
        frame.setResizable(devSettings.getSetting("resizable"));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Administrators");
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        headerPanel.setPreferredSize(new Dimension(500, 50));
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        headerGbc.weightx = 0.2;
        headerGbc.anchor = GridBagConstraints.WEST;
        headerGbc.insets = new Insets(0, 10, 0, 0);
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        backButton.setForeground(Color.decode(themeColors.getColor("text")));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                adminPanel.showAdminPanel();
            }
        });
        headerPanel.add(backButton, headerGbc);

        headerGbc.gridx = 1;
        headerGbc.weightx = 1.0;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.decode(themeColors.getColor("primary")));

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setForeground(Color.decode(themeColors.getColor("text")));
        searchPanel.add(searchLabel);

        JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                filterUsers(searchField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                filterUsers(searchField.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                filterUsers(searchField.getText());
            }
        });
        searchPanel.add(searchField);

        headerPanel.add(searchPanel, headerGbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);

        frame.add(headerPanel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        usersContainer = new JPanel();
        usersContainer.setBackground(Color.decode(themeColors.getColor("subHeader")));
        usersContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

        for (String username : usernames) {
            JButton userButton = new JButton(username);
            userButton.setPreferredSize(new Dimension(180, 40)); // Fixed button size
            userButton.setBackground(Color.decode(themeColors.getColor("secondary")));
            userButton.setForeground(Color.decode(themeColors.getColor("text")));
            userButton.setFont(new Font("Arial", Font.BOLD, 20));
            userButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    String[][] orders = getUserOrders(username);
                    ManageOrders manageOrders = new ManageOrders(username, orders);
                    manageOrders.showUsersOrders();
                }
            });
            usersContainer.add(userButton);
        }

        JScrollPane scrollPane = new JScrollPane(usersContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel scrollWrapper = new JPanel(new BorderLayout());
        scrollWrapper.setPreferredSize(new Dimension(400, 400));
        scrollWrapper.add(scrollPane, BorderLayout.CENTER);
        scrollWrapper.setBackground(Color.decode(themeColors.getColor("subHeader")));

        frame.add(scrollWrapper, gbc);

        frame.setVisible(true);
    }

    private void filterUsers(String searchText) {
        String query = searchText.toLowerCase().trim();
        for (Component c : usersContainer.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                btn.setVisible(btn.getText().toLowerCase().contains(query));
            }
        }
        usersContainer.revalidate();
        usersContainer.repaint();
    }

}
