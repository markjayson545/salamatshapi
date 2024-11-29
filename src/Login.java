import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Login {
    
    public void showLogin() {
        JFrame frame = new JFrame("Login");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JLabel label = new JLabel("Login");
        JTextField userName = new JTextField(20);
        userName.setPreferredSize(new java.awt.Dimension(200, 30));
        JTextField passwordField = new JTextField(20);
        passwordField.setPreferredSize(new java.awt.Dimension(200, 30));
        JButton button = new JButton("Login");

        frame.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;

        frame.add(label, gbc);
        gbc.gridy = 1;
        frame.add(userName, gbc);
        gbc.gridy = 2;
        frame.add(passwordField, gbc);
        gbc.gridy = 3;
        frame.add(button, gbc);
    }
}
