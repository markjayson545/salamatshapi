package LoggedIn;

import javax.swing.JFrame;

import Theme.DevSettings;

public class Orders {
    private String username;
    private String[][] orders;
    public Orders(String username, String[][] orders){
        username = this.username;
        orders = this.orders;
    }
    public void showOrders(){
        DevSettings devSettings = new DevSettings();
        JFrame frame = new JFrame("Orders");
        frame.setSize(650, 700);
    
        

    }
}
