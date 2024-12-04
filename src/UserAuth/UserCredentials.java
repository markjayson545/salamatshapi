package UserAuth;
import java.sql.*;

public class UserCredentials {

    public boolean registerUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserAuth/userCredentials.db");
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS userCredentials (username TEXT PRIMARY KEY, password TEXT)";
            statement.execute(createTable);
            String insertData = "INSERT INTO userCredentials (username, password) VALUES ('" + username + "', '" + password + "')";
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
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/UserAuth/userCredentials.db");
            Statement statement = connection.createStatement();
            String selectData = "SELECT * FROM userCredentials WHERE username = '" + username + "' AND password = '" + password + "'";
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
