package UserAuth;

import java.awt.*;

import javax.swing.*;

import Theme.Components;

public class RegistrationMessages {

    int windowWidth = 350;
    int windowHeight = 200;

    Components components = new Components();

    public void showRegistrationSuccessful() {
        Theme.Colors themeColors = new Theme.Colors();
        JFrame frame = new JFrame("Registration Successful");
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel description = new JLabel("Whoo! You have successfully registered!");
        description.setForeground(Color.decode(themeColors.getColor("text")));
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        frame.add(description, gbc);
        gbc.gridy = 1;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        buttonPanel.setLayout(new FlowLayout());

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        closeButton.setForeground(Color.decode(themeColors.getColor("text")));
        closeButton.addActionListener(e -> frame.dispose());
        buttonPanel.add(closeButton);

        JButton loginButton = components.createButton("Login");
        loginButton.addActionListener(e -> {
            frame.dispose();
            Login login = new Login();
            login.showLogin();
        });
        buttonPanel.add(loginButton);

        frame.add(buttonPanel, gbc);

    }

    public void showPasswordDoNotMatch() {
        Theme.Colors themeColors = new Theme.Colors();
        JFrame frame = new JFrame("Password Do Not Match");
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel description = new JLabel("Password do not match. Please try again.");
        description.setForeground(Color.decode(themeColors.getColor("text")));
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        frame.add(description, gbc);
        gbc.gridy = 1;

        JButton closeButton = components.createButton("Close");
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton, gbc);

    }

    public void showRegistrationFailed() {
        Theme.Colors themeColors = new Theme.Colors();
        JFrame frame = new JFrame("Registration Failed");
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel description = new JLabel("Registration failed. Please try again.");
        description.setForeground(Color.decode(themeColors.getColor("text")));
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        frame.add(description, gbc);
        gbc.gridy = 1;

        JButton closeButton = components.createButton("Close");
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton, gbc);

    }

    public void showUsernameAlreadyExists(String username) {
        Theme.Colors themeColors = new Theme.Colors();
        JFrame frame = new JFrame("Username Already Exists");
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        frame.getContentPane().setBackground(Color.decode(themeColors.getColor("primary")));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel description = new JLabel("Username already exists for \"" + username + "\".");
        description.setForeground(Color.decode(themeColors.getColor("text")));
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        frame.add(description, gbc);
        gbc.gridy = 1;

        JButton closeButton = components.createButton("Close");
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton, gbc);
    }

}
