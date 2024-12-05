package UserFiles;

import java.util.*;
import java.sql.*;;

public class UserFileHandler {
    public void createUserFile(String username) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS " + username + " (username TEXT, password TEXT)";
            String createCartTable = "CREATE TABLE IF NOT EXISTS " + username + "cart"
                    + " (itemName TEXT, description TEXT, price TEXT, amount INTEGER)";
            String createOrderTable = "CREATE TABLE IF NOT EXISTS " + username + "orders"
                    + " (orderID INTEGER PRIMARY KEY AUTOINCREMENT, items TEXT, totalPrice TEXT)";
            statement.execute(createTable);
            statement.execute(createCartTable);
            statement.execute(createOrderTable);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemToCart(String username, String itemName, String description, int quantity, String price) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            
            String selectData = "SELECT amount FROM " + username + "cart" + " WHERE itemName = '" + itemName + "'";
            ResultSet resultSet = statement.executeQuery(selectData);
            
            if (resultSet.next()) {
                int currentAmount = resultSet.getInt("amount");
                String updateData = "UPDATE " + username + "cart" + " SET amount = " + (currentAmount + quantity) 
                        + " WHERE itemName = '" + itemName + "'";
                statement.execute(updateData);
            } else {
                String createTable = "CREATE TABLE IF NOT EXISTS " + username + "cart"
                        + " (itemName TEXT, description TEXT, price TEXT, amount INTEGER)";
                statement.execute(createTable);
                String insertData = "INSERT INTO " + username + "cart"
                        + " (itemName, description, price, amount) VALUES ('" + itemName + "', '" + description + "', '"
                        + price + "', " + quantity + ")";
                statement.execute(insertData);
            }
            
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrementItemAmount(String username, String itemName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String selectData = "SELECT * FROM " + username + "cart" + " WHERE itemName = '" + itemName + "'";
            ResultSet resultSet = statement.executeQuery(selectData);
            resultSet.next();
            int currentAmount = resultSet.getInt("amount");
            if (currentAmount > 1) {
                String updateData = "UPDATE " + username + "cart" + " SET amount = " + (currentAmount - 1)
                        + " WHERE itemName = '" + itemName + "'";
                statement.execute(updateData);
            } else {
                String deleteData = "DELETE FROM " + username + "cart" + " WHERE itemName = '" + itemName + "'";
                statement.execute(deleteData);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[][] getCartItems(String username) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS " + username + "cart"
                    + " (itemName TEXT, description TEXT, price TEXT, amount INTEGER)";
            statement.execute(createTable);
            String selectData = "SELECT * FROM " + username + "cart";
            ResultSet resultSet = statement.executeQuery(selectData);
            List<String[]> items = new ArrayList<>();
            while (resultSet.next()) {
                String[] currentItem = new String[4];
                currentItem[0] = resultSet.getString("itemName");
                currentItem[1] = resultSet.getString("description");
                currentItem[2] = resultSet.getString("price");
                currentItem[3] = String.valueOf(resultSet.getInt("amount"));
                items.add(currentItem);
            }
            statement.close();
            connection.close();
            return items.toArray(new String[items.size()][4]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }

    public double getTotalPrice(String username) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS " + username + "cart"
                    + " (itemName TEXT, description TEXT, price TEXT, amount INTEGER)";
            statement.execute(createTable);
            String selectData = "SELECT * FROM " + username + "cart";
            ResultSet resultSet = statement.executeQuery(selectData);
            double totalPrice = 0;
            while (resultSet.next()) {
                totalPrice += resultSet.getDouble("price") * resultSet.getInt("amount");
            }
            statement.close();
            connection.close();
            return totalPrice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteItem(String username, String itemName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String deleteData = "DELETE FROM " + username + "cart" + " WHERE itemName = '" + itemName + "'";
            statement.execute(deleteData);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCart(String username) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String deleteData = "DELETE FROM " + username + "cart";
            statement.execute(deleteData);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrder(String username, String[][] items) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS " + username + "orders"
                    + " (orderID INTEGER PRIMARY KEY AUTOINCREMENT, items TEXT, totalPrice TEXT)";
            statement.execute(createTable);
            for (String[] item : items) {
                String insertData = "INSERT INTO " + username + "orders"
                        + " (items, totalPrice) VALUES ('" + Arrays.toString(item) + "', '"
                        + getTotalPrice(username) + "')";
                statement.execute(insertData);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[][] getOrders(String username) {
        String[][] orders;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            String selectData = "SELECT * FROM" + username + "order";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectData);
            List<String[]> orderList = new ArrayList<>();
            while (resultSet.next()) {
                String[] order = new String[3];
                order[0] = String.valueOf(resultSet.getInt("orderID"));
                order[1] = resultSet.getString("items");
                order[2] = resultSet.getString("totalPrice");
                orderList.add(order);
            }
            orders = orderList.toArray(new String[orderList.size()][3]);
            statement.close();
            connection.close();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

}
