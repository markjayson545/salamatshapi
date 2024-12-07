package UserAuth;

import java.sql.*;

public class UserCredentials {

    private static final String USER_CREDENTIALS_DB = "jdbc:sqlite:src/Database/UserCredentials.db";
    private static final String ADMIN_CREDENTIALS_DB = "jdbc:sqlite:src/Database/AdminCredentials.db";

    public boolean registerUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(USER_CREDENTIALS_DB);
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS UserCredentials (username TEXT PRIMARY KEY, password TEXT)";
            statement.execute(createTable);
            String insertData = "INSERT INTO userCredentials (username, password) VALUES ('" + username + "', '"
                    + password + "')";
            statement.execute(insertData);
            statement.close();
            connection.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(USER_CREDENTIALS_DB);
            Statement statement = connection.createStatement();
            String selectData = "SELECT * FROM UserCredentials WHERE username = '" + username + "' AND password = '"
                    + password + "'";
            ResultSet resultSet = statement.executeQuery(selectData);
            if (resultSet.next()) {
                statement.close();
                connection.close();
                return true;
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAdminCred(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(ADMIN_CREDENTIALS_DB);
            Statement statement = connection.createStatement();
            String selectData = "SELECT * FROM AdminCredentials WHERE username = '" + username + "' AND password = '"
                    + password + "'";
            ResultSet resultSet = statement.executeQuery(selectData);
            if (resultSet.next()) {
                statement.close();
                connection.close();
                return true;
            }
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
