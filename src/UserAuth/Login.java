package UserAuth;

import javax.swing.*;
import MainActivity.Main;
import Theme.Components;
import Theme.DevSettings;
import UserFiles.UserFileHandler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import LoggedIn.Homepage;

public class Login {

    public void showLogin() {
        Theme.Colors themeColors = new Theme.Colors();
        String primaryColor = themeColors.getColor("primary");
        String headerColor = themeColors.getColor("header");

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

        JLabel userNameLabel = components.createTextFieldLabel("Username");
        JTextField userName = components.createTextField(20);

        JLabel passwordLabel = components.createTextFieldLabel("Password");
        JPasswordField passwordField = components.createPasswordField(20);
        JButton button = components.createButton("Login");

        // Allow user to press enter to login
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });

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
                    UserFileHandler userFileHandler = new UserFileHandler();
                    userFileHandler.createUserFile(username);
                    frame.dispose();
                    Homepage homepage = new Homepage(username);
                    homepage.showHomepage();
                } else {
                    JOptionPane.showMessageDialog(frame, "Login failed"+ "\n" + "Please check your username and password");
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
