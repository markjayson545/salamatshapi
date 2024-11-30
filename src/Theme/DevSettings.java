package Theme;

public class DevSettings {
    String appName = "Shapi";

    int width = 500;
    int height = 650;

    boolean isAlwaysOnTop = true;
    boolean isResizable = false;
    boolean isVisible = true;
    boolean isCentered = true;

    public boolean getSetting(String setting) {
        if (setting.equals("alwaysOnTop")) {
            return isAlwaysOnTop;
        } else if (setting.equals("resizable")) {
            return isResizable;
        } else if (setting.equals("visible")) {
            return isVisible;
        } else if (setting.equals("centered")) {
            return isCentered;
        }
        return false;
    }

    public int getDimension(String dimension) {
        if (dimension.equals("width")) {
            return width;
        } else if (dimension.equals("height")) {
            return height;
        }
        return 0;
    }

    public String getAppName() {
        return appName;
    }

}
