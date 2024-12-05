package LoggedIn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Theme.DevSettings;

public class Payment {
    private String username;
    private String[][] cartItems;
    private double totalPrice;
    private double shippingPrice;
    private double finalPrice;

    public Payment(String username, String[][] cartItems, double totalPrice, double shippingPrice) {
        this.username = username;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.shippingPrice = shippingPrice;
        this.finalPrice = totalPrice + shippingPrice;
    }

    DevSettings devSettings = new DevSettings();
    Theme.Colors themeColors = new Theme.Colors();

    public void showPayment(){
        
    }

    


}
