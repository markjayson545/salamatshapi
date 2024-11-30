package MainActivity;

import javax.swing.*;

import Theme.Components;
import Theme.DevSettings;
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

        DevSettings devSettings = new DevSettings();

        Components components = new Components();

        

        JFrame frame = new JFrame("Home");

        // JButton tempButton = components.createButton("Login");
        // tempButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         System.out.println("Login button clicked");
        //     }
        // });
        // frame.add(tempButton);

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

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Welcome to " + devSettings.getAppName());
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

        JButton loginButton = components.createButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login login = new Login();
                login.showLogin();
            }
        });
        buttonPanel.add(loginButton);

        JButton registerButton = components.createButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Register register = new Register();
                register.showRegister();
            }
        });
        buttonPanel.add(registerButton);

        gbc.gridy = 2;
        frame.add(buttonPanel, gbc);
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.showHomepage();
    }
}