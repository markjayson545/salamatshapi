package UserAuth;

import java.io.*;

public class UserCredentials {

    public boolean registerUser(String username, String password) {
        try {
            File file = new File("src/UserAuth/userCredentials.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String currentUsername = null;

            RegistrationMessages registrationMessages = new RegistrationMessages();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    currentUsername = line.substring(10);
                    if (currentUsername.equals(username)) {
                        bufferedReader.close();
                        return false;
                    }
                }
            }
            bufferedReader.close();
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            // Writing username and password with clear separation
            bufferedWriter.write("Username: " + username);
            bufferedWriter.newLine();
            bufferedWriter.write("Password: " + password);
            bufferedWriter.newLine();
            bufferedWriter.write("------------------------");
            bufferedWriter.newLine();
            
            bufferedWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return false;
    }

    public boolean loginUser(String username, String password) {
        try {
            File file = new File("src/UserAuth/userCredentials.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String currentUsername = null;
            String currentPassword = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    currentUsername = line.substring(10);
                } else if (line.startsWith("Password: ")) {
                    currentPassword = line.substring(10);
                    if (currentUsername.equals(username) && currentPassword.equals(password)) {
                        bufferedReader.close();
                        return true;
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
