package UserAuth;

import javax.swing.*;
import MainActivity.Main;
import Theme.Components;
import Theme.DevSettings;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Login {

    public void showLogin() {
        Theme.Colors themeColors = new Theme.Colors();
        String primaryColor = themeColors.getColor("primary");
        String secondaryColor = themeColors.getColor("secondary");
        String headerColor = themeColors.getColor("header");
        String textColor = themeColors.getColor("text");

        DevSettings devSettings = new DevSettings();
        Components components = new Components();

        JFrame frame = new JFrame("Login");
        frame.setSize(
                devSettings.getDimension("width"),
                devSettings.getDimension("height"));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (devSettings.getSetting("centered")) {
            frame.setLocationRelativeTo(null);
        }
        frame.setResizable(devSettings.getSetting("resizable"));
        frame.setAlwaysOnTop(devSettings.getSetting("alwaysOnTop"));
        frame.setVisible(devSettings.getSetting("visible"));

        frame.getContentPane().setBackground(Color.decode(primaryColor));

        JLabel label = new JLabel("Login");
        label.setForeground(Color.decode(headerColor));
        label.setFont(new Font("Arial", Font.BOLD, 30));

        JButton backButton = components.createButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                frame.dispose();
                Main main = new Main();
                main.showHomepage();
            }
        });

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setForeground(Color.decode(textColor));
        JTextField userName = new JTextField(20);
        userName.setPreferredSize(new Dimension(200, 30));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.decode(textColor));
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));

        JButton button = components.createButton("Login");

        // Check if username and password fields are not empty
        // before enabling the login button
        button.setEnabled(false);
        DocumentListener documentListener = new DocumentListener() {
            public void check() {
                button.setEnabled(!userName.getText().isEmpty() && passwordField.getPassword().length > 0);
            }

            public void changedUpdate(DocumentEvent e) {
                check();
            }

            public void removeUpdate(DocumentEvent e) {
                check();
            }

            public void insertUpdate(DocumentEvent e) {
                check();
            }
        };
        userName.getDocument().addDocumentListener(documentListener);
        passwordField.getDocument().addDocumentListener(documentListener);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String username = userName.getText();
                String password = String.valueOf(passwordField.getPassword());
                UserCredentials userCredentials = new UserCredentials();
                boolean loginStatus = userCredentials.loginUser(username, password);
                if (loginStatus) {
                    JOptionPane.showMessageDialog(frame, "Login successful");
                } else {
                    JOptionPane.showMessageDialog(frame, "Login failed");
                }
            }
        });

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        // Align to the left
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(backButton, gbc);
        gbc.gridy = 1;

        // Align to the center
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(label, gbc);
        gbc.gridy = 2;

        // Align to the left
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(userNameLabel, gbc);
        gbc.gridy = 3;

        frame.add(userName, gbc);
        gbc.gridy = 4;

        frame.add(passwordLabel, gbc);
        gbc.gridy = 5;

        frame.add(passwordField, gbc);
        gbc.gridy = 6;

        gbc.insets = new Insets(20, 0, 20, 0);

        // Align to the center
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(button, gbc);
    }

}
