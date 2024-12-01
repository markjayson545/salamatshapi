package UserAuth;

import javax.swing.*;
import javax.swing.event.*;

import MainActivity.Main;
import Theme.Components;
import Theme.DevSettings;

import java.awt.*;
import java.awt.event.*;

public class Register {

    private boolean validateRegister(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }

        if (!password.equals(confirmPassword)) {
            return false;
        }
        UserCredentials userCredentials = new UserCredentials();
        boolean registerStatus = userCredentials.registerUser(username, password);
        if (!registerStatus) {
            return false;
        }
        return true;
    }

    public void showRegister() {

        JFrame frame = new JFrame("Register");
        Theme.Colors themeColors = new Theme.Colors();
        String primaryColor = themeColors.getColor("primary");
        DevSettings devSettings = new DevSettings();
        Components components = new Components();

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

        // Declare components
        JLabel label = new JLabel("Register");
        label.setForeground(Color.decode(themeColors.getColor("header")));
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

        JLabel confirmPasswordLabel = components.createTextFieldLabel("Confirm Password");
        JPasswordField confirmPasswordField = components.createPasswordField(20);
        JButton registerButton = components.createButton("Register");

        // Allow user to press enter to register
        confirmPasswordField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    registerButton.doClick();
                }
            }
        });
        
        // Check if the fields are filled
        // Disable the button if the fields are empty
        registerButton.setEnabled(false);
        DocumentListener documentListener = new DocumentListener() {
            public void check() {
                registerButton.setEnabled(userName.getText().length() > 0 && passwordField.getPassword().length > 0 &&
                        confirmPasswordField.getPassword().length > 0);
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
        confirmPasswordField.getDocument().addDocumentListener(documentListener);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String usernameInput = userName.getText();
                String passwordInput = new String(passwordField.getPassword());
                String confirmPasswordInput = new String(confirmPasswordField.getPassword());
                RegistrationMessages registrationMessages = new RegistrationMessages();
                if (validateRegister(usernameInput, passwordInput, confirmPasswordInput)) {
                    registrationMessages.showRegistrationSuccessful();
                } else if (!passwordInput.equals(confirmPasswordInput)) {
                    registrationMessages.showPasswordDoNotMatch();
                } else {
                    registrationMessages.showUsernameAlreadyExists(usernameInput);
                }
            }
        });
        // Build the layout
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.WEST;
        frame.add(backButton, gbc);
        gbc.gridy = 1;

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

        frame.add(confirmPasswordLabel, gbc);
        gbc.gridy = 7;

        frame.add(confirmPasswordField, gbc);
        gbc.gridy = 8;

        gbc.insets = new Insets(10, 0, 10, 0);
        // Align to the center
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(registerButton, gbc);
    }
}
