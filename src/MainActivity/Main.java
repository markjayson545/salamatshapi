package MainActivity;

import javax.swing.*;

import UserAuth.Login;
import UserAuth.Register;
import java.awt.*;
import java.awt.event.*;;

public class Main {

    public void showHomepage() {
        Theme.Colors themeColors = new Theme.Colors();
        String primaryColor = themeColors.getColor("primary");
        String secondaryColor = themeColors.getColor("secondary");
        String headerColor = themeColors.getColor("header");
        String subHeaderColor = themeColors.getColor("subHeader");
        String textColor = themeColors.getColor("text");
        JFrame frame = new JFrame("Home");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        frame.getContentPane().setBackground(Color.decode(primaryColor));

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Welcome to Shapi");
        title.setForeground(Color.decode(headerColor));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);
        gbc.gridy = 1;

        JLabel description = new JLabel("Please login or register to continue");
        description.setForeground(Color.decode(subHeaderColor));
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        frame.add(description, gbc);
        gbc.gridy = 2;

        gbc.insets = new Insets(10, 0, 10, 0);

        // Row for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode(primaryColor));
        buttonPanel.setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.decode(secondaryColor));
        loginButton.setForeground(Color.decode(textColor));
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.decode(secondaryColor));
        registerButton.setForeground(Color.decode(textColor));
        buttonPanel.add(registerButton);

        gbc.gridy = 2;
        frame.add(buttonPanel, gbc);

        // onClick event for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            frame.dispose();
            Login login = new Login();
            login.showLogin();
            }
        });

        // onClick event for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            frame.dispose();
            Register register = new Register();
            register.showRegister();
            }
        });

    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.showHomepage();
    }
}