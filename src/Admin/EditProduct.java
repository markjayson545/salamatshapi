package Admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Theme.DevSettings;

public class EditProduct extends AdminDatabaseHandler {

    DevSettings devSettings = new DevSettings();
    Theme.Colors themeColors = new Theme.Colors();

    public void showEditor(String productNameInit, String productPriceInit, String productDescriptionInit) {
        JFrame frame = new JFrame("Add Product");
        frame.setSize(500, 550);
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

        JLabel title = new JLabel("Add Product");
        title.setForeground(Color.decode(themeColors.getColor("header")));
        title.setFont(new Font("Arial", Font.BOLD, 30));

        frame.add(title, gbc);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        headerPanel.setPreferredSize(new Dimension(400, 35));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        backButton.setForeground(Color.decode(themeColors.getColor("text")));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                AdminPanel adminPanel = new AdminPanel();
                adminPanel.showAdminPanel();
            }
        });

        headerPanel.add(backButton);

        gbc.gridy = 1;
        frame.add(headerPanel, gbc);
        gbc.insets = new Insets(10, 0, 0, 0);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.decode(themeColors.getColor("subHeader")));
        formPanel.setPreferredSize(new Dimension(400, 300));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.insets = new Insets(5, 5, 5, 5);

        JLabel productName = new JLabel("Product Name: ");
        productName.setForeground(Color.decode(themeColors.getColor("secondary")));
        productName.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField productNameField = new JTextField();
        productNameField.setPreferredSize(new Dimension(300, 30));

        JLabel productPrice = new JLabel("Product Price: ");
        productPrice.setForeground(Color.decode(themeColors.getColor("secondary")));
        productPrice.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField productPriceField = new JTextField();
        productPriceField.setPreferredSize(new Dimension(300, 30));

        JLabel productDescription = new JLabel("Product Description: ");
        productDescription.setForeground(Color.decode(themeColors.getColor("secondary")));
        productDescription.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField productDescriptionField = new JTextField();
        productDescriptionField.setPreferredSize(new Dimension(300, 30));

        productNameField.setText(productNameInit);
        productDescriptionField.setText(productDescriptionInit);
        productPriceField.setText(productPriceInit);

        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(productName, formGbc);
        formGbc.gridy = 1;
        formPanel.add(productNameField, formGbc);
        formGbc.gridy = 2;
        formPanel.add(productDescription, formGbc);
        formGbc.gridy = 3;
        formPanel.add(productDescriptionField, formGbc);
        formGbc.gridy = 4;
        formPanel.add(productPrice, formGbc);
        formGbc.gridy = 5;
        formPanel.add(productPriceField, formGbc);

        gbc.gridy = 2;
        frame.add(formPanel, gbc);

        // Footer here

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.decode(themeColors.getColor("primary")));
        footerPanel.setPreferredSize(new Dimension(400, 35));
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.decode(themeColors.getColor("secondary")));
        submitButton.setForeground(Color.decode(themeColors.getColor("text")));

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (productNameInit != null && productDescriptionInit != null && productPriceInit != null) {
                    updateProduct(productNameInit, productNameField.getText(), productDescriptionField.getText(), productPriceField.getText());
                    frame.dispose();
                } else {
                    addProduct(productNameField.getText(), productDescriptionField.getText(),
                            Double.parseDouble(productPriceField.getText()));
                    frame.dispose();
                    AdminPanel adminPanel = new AdminPanel();
                    adminPanel.showAdminPanel();
                }
            }
        });

        footerPanel.add(submitButton);

        gbc.gridy = 3;
        frame.add(footerPanel, gbc);
    }

}
