package UserFiles;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;;

public class UserFileHandler {
    public void createUserFile(String username) {
        System.out.println("Creating user file for: " + username);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS " + username + " (username TEXT, password TEXT)";
            String createCartTable = "CREATE TABLE IF NOT EXISTS " + username + "cart"
                    + " (itemName TEXT, description TEXT, price TEXT, amount INTEGER)";
            String createOrderTable = "CREATE TABLE IF NOT EXISTS " + username + "orders"
                    + " (orderID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "groupOrderID INTEGER, "
                    + "itemName TEXT, "
                    + "description TEXT, "
                    + "price TEXT, "
                    + "amount INTEGER, "
                    + "status TEXT, "
                    + "estimatedDeliveryDate TEXT)";
            statement.execute(createTable);
            statement.execute(createCartTable);
            statement.execute(createOrderTable);
            statement.close();
            connection.close();
            System.out.println("User file created for: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemToCart(String username, String itemName, String description, int quantity, String price) {
        System.out.println("Adding item to cart for user: " + username);
        System.out.println("Item details - Name: " + itemName + ", Description: " + description + ", Quantity: " + quantity + ", Price: " + price);
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
                System.out.println("Updated item amount in cart for user: " + username + ". New amount: " + (currentAmount + quantity));
            } else {
                String createTable = "CREATE TABLE IF NOT EXISTS " + username + "cart"
                        + " (itemName TEXT, description TEXT, price TEXT, amount INTEGER)";
                statement.execute(createTable);
                String insertData = "INSERT INTO " + username + "cart"
                        + " (itemName, description, price, amount) VALUES ('" + itemName + "', '" + description + "', '"
                        + price + "', " + quantity + ")";
                statement.execute(insertData);
                System.out.println("Inserted new item into cart for user: " + username);
            }
            
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrementItemAmount(String username, String itemName) {
        System.out.println("Decrementing item amount in cart for user: " + username);
        System.out.println("Item name: " + itemName);
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
                System.out.println("Decremented item amount in cart for user: " + username + ". New amount: " + (currentAmount - 1));
            } else {
                String deleteData = "DELETE FROM " + username + "cart" + " WHERE itemName = '" + itemName + "'";
                statement.execute(deleteData);
                System.out.println("Deleted item from cart for user: " + username);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[][] getCartItems(String username) {
        System.out.println("Getting cart items for user: " + username);
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
            System.out.println("Retrieved cart items for user: " + username);
            return items.toArray(new String[items.size()][4]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }

    public double getTotalPrice(String username) {
        System.out.println("Calculating total price for user: " + username);
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
            System.out.println("Total price calculated for user: " + username + ". Total price: " + totalPrice);
            return totalPrice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteItem(String username, String itemName) {
        System.out.println("Deleting item from cart for user: " + username);
        System.out.println("Item name: " + itemName);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String deleteData = "DELETE FROM " + username + "cart" + " WHERE itemName = '" + itemName + "'";
            statement.execute(deleteData);
            statement.close();
            connection.close();
            System.out.println("Item deleted from cart for user: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCart(String username) {
        System.out.println("Clearing cart for user: " + username);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String deleteData = "DELETE FROM " + username + "cart";
            statement.execute(deleteData);
            statement.close();
            connection.close();
            System.out.println("Cart cleared for user: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrder(String username, String[][] items) {
        System.out.println("Adding order for user: " + username);
        System.out.println("Order items: " + Arrays.deepToString(items));
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS " + username + "orders"
                    + " (orderID INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, description TEXT, price TEXT, amount INTEGER, status TEXT, estimatedDeliveryDate TEXT)";
            statement.execute(createTable);
            
            // Get the next group order ID
            String maxGroupIdQuery = "SELECT COALESCE(MAX(groupOrderID), 0) as maxGroup FROM " + username + "orders";
            ResultSet rs = statement.executeQuery(maxGroupIdQuery);
            int groupOrderID = rs.next() ? rs.getInt("maxGroup") + 1 : 1;
            
            String status = "To Ship";
            String estimatedDeliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000));
            for (String[] item : items) {
                PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO " + username + "orders (groupOrderID, itemName, description, price, amount, status, estimatedDeliveryDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                pstmt.setInt(1, groupOrderID);
                pstmt.setString(2, item[0]);
                pstmt.setString(3, item[1]);
                pstmt.setString(4, item[2]);
                pstmt.setInt(5, Integer.parseInt(item[3]));
                pstmt.setString(6, status);
                pstmt.setString(7, estimatedDeliveryDate);
                pstmt.executeUpdate();
                pstmt.close();
            }
            
            statement.close();
            connection.close();
            System.out.println("Order added for user: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalGroupedOrders(String username) {
        System.out.println("Getting total grouped orders for user: " + username);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String selectData = "SELECT COUNT(DISTINCT groupOrderID) as totalGroupedOrders FROM " + username + "orders";
            ResultSet resultSet = statement.executeQuery(selectData);
            int totalGroupedOrders = resultSet.next() ? resultSet.getInt("totalGroupedOrders") : 0;
            statement.close();
            connection.close();
            System.out.println("Total grouped orders retrieved for user: " + username + ". Total grouped orders: " + totalGroupedOrders);
            return totalGroupedOrders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateOrderStatus(String username) {
        System.out.println("Updating order status for user: " + username);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            Statement statement = connection.createStatement();
            String selectData = "SELECT * FROM " + username + "orders";
            ResultSet resultSet = statement.executeQuery(selectData);
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            
            while (resultSet.next()) {
                String estimatedDeliveryDate = resultSet.getString("estimatedDeliveryDate");
                String status;
                if (estimatedDeliveryDate.equals(currentDate)) {
                    status = "On the way";
                } else if (estimatedDeliveryDate.compareTo(currentDate) < 0) {
                    status = "Received";
                } else {
                    continue;
                }
                String updateData = "UPDATE " + username + "orders SET status = '" + status + "' WHERE orderID = " + resultSet.getInt("orderID");
                statement.execute(updateData);
                System.out.println("Order status updated for user: " + username + ". Order ID: " + resultSet.getInt("orderID") + ", New status: " + status);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[][] getOrders(String username) {
        System.out.println("Getting orders for user: " + username);
        String[][] orders;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserFiles/Users.db");
            String selectData = "SELECT * FROM " + username + "orders ORDER BY groupOrderID DESC, orderID ASC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectData);
            List<String[]> orderList = new ArrayList<>();
            while (resultSet.next()) {
                String[] order = new String[8]; // Added one more field for groupOrderID
                order[0] = String.valueOf(resultSet.getInt("orderID"));
                order[1] = resultSet.getString("itemName");
                order[2] = resultSet.getString("description");
                order[3] = resultSet.getString("price");
                order[4] = String.valueOf(resultSet.getInt("amount"));
                order[5] = resultSet.getString("status");
                order[6] = resultSet.getString("estimatedDeliveryDate");
                order[7] = String.valueOf(resultSet.getInt("groupOrderID"));
                orderList.add(order);
            }
            orders = orderList.toArray(new String[orderList.size()][8]);
            statement.close();
            connection.close();
            System.out.println("Orders retrieved for user: " + username);
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

}
