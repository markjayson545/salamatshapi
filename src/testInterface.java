import Admin.AdminPanel;

public class testInterface {

    static Theme.Colors themeColors = new Theme.Colors();
    static Theme.DevSettings devSettings = new Theme.DevSettings();

    public static void main(String[] args) {
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.showAddProduct();
    }

}
