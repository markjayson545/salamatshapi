package UserAuth;

import javax.swing.*;

import MainActivity.Main;

import java.awt.*;
import java.awt.event.ActionListener;

public class Register {

    private boolean validateRegister(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }

        if (!password.equals(confirmPassword)) {
            return false;
        }
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.registerUser(username, password);
        return true;
    }

    public void showRegister() {

        JFrame frame = new JFrame("Register");
        Theme.Colors themeColors = new Theme.Colors();
        String primaryColor = themeColors.getColor("primary");
        String secondaryColor = themeColors.getColor("secondary");
        String textColor = themeColors.getColor("text");

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.decode(primaryColor));

        // Declare components
        JLabel label = new JLabel("Register");

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode(secondaryColor));
        backButton.setForeground(Color.decode(textColor));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                frame.dispose();
                Main main = new Main();
                main.showHomepage();
            }
        });

        JLabel userNameLabel = new JLabel("Username");
        JTextField userName = new JTextField(20);
        userName.setPreferredSize(new Dimension(200, 30));

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField(20);

        passwordField.setPreferredSize(new Dimension(200, 30));

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setPreferredSize(new Dimension(200, 30));

        JButton button = new JButton("Register");
        button.setBackground(Color.decode(secondaryColor));
        button.setForeground(Color.decode(textColor));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String usernameInput = userName.getText();
                String passwordInput = new String(passwordField.getPassword());
                String confirmPasswordInput = new String(confirmPasswordField.getPassword());
                if (validateRegister(usernameInput, passwordInput, confirmPasswordInput)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful");
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed");
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
        frame.add(button, gbc);
    }
}
