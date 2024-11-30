package Theme;

public class Colors {
    String primaryColor = "#8686AC";
    String secondaryColor = "#0F0E47";
    String textColor = "#FFFFFF";

    String headerColor = "#FFFFFF";
    String subHeaderColor = "#F0F0F0";

    public String getColor(String color) {
        switch (color) {
            case "primary":
                return primaryColor;
            case "secondary":
                return secondaryColor;
            case "header":
                return headerColor;
            case "subHeader":
                return subHeaderColor;
            case "text":
                return textColor;
            default:
                return null;
        }
    }

}
