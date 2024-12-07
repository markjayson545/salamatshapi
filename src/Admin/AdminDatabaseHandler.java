package Admin;

import java.sql.*;

public class AdminDatabaseHandler {

    private static final String ADMIN_CREDENTIALS_DB = "jdbc:sqlite:src/Database/AdminCredentials.db";
    private static final String USER_ORDERS_DB = "jdbc:sqlite:src/Database/UserOrders.db";
    private static final String PRODUCTS_DB = "jdbc:sqlite:src/Database/Products.db";

    public void createDefaultAdmin() {
        try {
            Connection connection = DriverManager.getConnection(ADMIN_CREDENTIALS_DB);
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS AdminCredentials (username TEXT PRIMARY KEY, password TEXT)";
            String getDefaultAdmin = "SELECT * FROM AdminCredentials WHERE username = 'admin'";
            statement.execute(createTable);
            ResultSet resultSet = statement.executeQuery(getDefaultAdmin);
            if (!resultSet.next()) {
                System.out.println("Default admin credentials not found inserting: ");
                System.out.println("Username: admin\nPassword: admin");
                String insertAdminCred = "INSERT INTO AdminCredentials (username, password) VALUES ('admin', 'admin')";
                statement.execute(insertAdminCred);
            }
            statement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void createAdminAccount(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(ADMIN_CREDENTIALS_DB);
            Statement stmt = connection.createStatement();
            String checkUsername = "SELECT * FROM AdminCredentials WHERE username = '" + username + "'";
            ResultSet resultSet = stmt.executeQuery(checkUsername);
            if (resultSet.next()) {
                return;
            }
            String createTable = "CREATE TABLE IF NOT EXISTS AdminCredentials (username TEXT PRIMARY KEY, password TEXT)";
            String insertData = "INSERT INTO AdminCredentials (username, password) VALUES ('" + username + "', '"
                    + password + "')";
            stmt.execute(createTable);
            stmt.execute(insertData);
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addDefaultProducts() {
        String[][] productsList = {
                { "Product 1", "Description 1", "100" },
                { "Product 2", "Description 2", "200" },
                { "Product 3", "Description 3", "300" },
                { "Product 4", "Description 4", "400" },
                { "Product 5", "Description 5", "500" },
                { "Product 6", "Description 6", "600" },
                { "Product 7", "Description 7", "700" },
                { "Product 8", "Description 8", "800" },
                { "Product 9", "Description 9", "900" },
                { "Product 10", "Description 10", "1000" },
                { "Product 11", "Description 11", "1100" },
                { "Product 12", "Description 12", "1200" },
                { "Product 13", "Description 13", "1300" },
                { "Product 14", "Description 14", "1400" },
                { "Product 15", "Description 15", "1500" },
                { "Product 16", "Description 16", "1600" },
                { "Product 17", "Description 17", "1700" },
                { "Product 18", "Description 18", "1800" },
                { "Product 19", "Description 19", "1900" },
                { "Product 20", "Description 20", "2000" },
                { "Product 21", "Description 21", "2100" },
                { "Product 22", "Description 22", "2200" },
                { "Product 23", "Description 23", "2300" },
                { "Product 24", "Description 24", "2400" },
                { "Product 25", "Description 25", "2500" },
                { "Product 26", "Description 26", "2600" },
                { "Product 27", "Description 27", "2700" },
                { "Product 28", "Description 28", "2800" },
                { "Product 29", "Description 29", "2900" },
                { "Product 30", "Description 30", "3000" },
                { "Product 31", "Description 31", "3100" },
                { "Product 32", "Description 32", "3200" },
                { "Product 33", "Description 33", "3300" },
                { "Product 34", "Description 34", "3400" },
                { "Product 35", "Description 35", "3500" },
                { "Product 36", "Description 36", "3600" },
                { "Product 37", "Description 37", "3700" },
                { "Product 38", "Description 38", "3800" },
                { "Product 39", "Description 39", "3900" },
                { "Product 40", "Description 40", "4000" }
        };
        try {
            Connection connection = DriverManager.getConnection(PRODUCTS_DB);
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS Products (productName TEXT PRIMARY KEY, description TEXT, price TEXT)";
            statement.execute(createTable);
            for (String[] product : productsList) {
                String checkProduct = "SELECT * FROM Products WHERE productName = '" + product[0] + "'";
                ResultSet resultSet = statement.executeQuery(checkProduct);
                if (!resultSet.next()) {
                    String insertData = "INSERT INTO Products (productName, description, price) VALUES ('" + product[0]
                            + "', '"
                            + product[1] + "', '" + product[2] + "')";
                    statement.execute(insertData);
                }
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addProduct(String productName, String description, double price) {
        try {
            Connection connection = DriverManager.getConnection(PRODUCTS_DB);
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS Products (productName TEXT PRIMARY KEY, description TEXT, price TEXT)";
            String checkProduct = "SELECT * FROM Products WHERE productName = '" + productName + "'";
            statement.execute(createTable);
            ResultSet resultSet = statement.executeQuery(checkProduct);
            String parsePrice = Double.toString(price);
            if (!resultSet.next()) {
                String insertData = "INSERT INTO Products (productName, description, price) VALUES ('" + productName
                        + "', '"
                        + description + "', '" + parsePrice + "')";
                statement.execute(insertData);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateProduct(String currentProductname, String newProductName, String description, String price) {
        try {
            Connection connection = DriverManager.getConnection(PRODUCTS_DB);
            Statement statement = connection.createStatement();
            String checkProduct = "SELECT * FROM Products WHERE productName = '" + newProductName
                    + "' AND productName != '" + currentProductname + "'";
            ResultSet resultSet = statement.executeQuery(checkProduct);
            if (!resultSet.next()) {
                String updateData = "UPDATE Products SET productName = '" + newProductName + "', description = '"
                        + description
                        + "', price = '" + price + "' WHERE productName = '" + currentProductname + "'";
                statement.execute(updateData);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String[][] getProducts() {
        String[][] productsList = null;
        try {
            Connection connection = DriverManager.getConnection(PRODUCTS_DB);
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS Products (productName TEXT PRIMARY KEY, description TEXT, price TEXT)";
            statement.execute(createTable);
            String selectData = "SELECT * FROM Products";
            ResultSet resultSet = statement.executeQuery(selectData);
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }
            productsList = new String[rowCount][3];
            resultSet = statement.executeQuery(selectData);
            int i = 0;
            while (resultSet.next()) {
                productsList[i][0] = resultSet.getString("productName");
                productsList[i][1] = resultSet.getString("description");
                productsList[i][2] = resultSet.getString("price");
                i++;
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return productsList;
    }

    public void deleteProduct(String productName) {
        try {
            Connection connection = DriverManager.getConnection(PRODUCTS_DB);
            Statement statement = connection.createStatement();
            String deleteData = "DELETE FROM Products WHERE productName = '" + productName + "'";
            statement.execute(deleteData);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String[][] getUserOrders() {
        String[][] userOrders = null;
        try {
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return userOrders;
    }
}
