import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;

public class Main {

    public void showHomepage() {
        JFrame frame = new JFrame("Home");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton loginButton = new JButton("Login");
        frame.add(loginButton, gbc);
        gbc.gridy = 1;

        // onClick event for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login login = new Login();
                login.showLogin();
            }
        });

        JButton registerButton = new JButton("Register");
        frame.add(registerButton, gbc);

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