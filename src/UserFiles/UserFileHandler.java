package UserFiles;

import java.io.*;
import java.util.*;

public class UserFileHandler {
    public void createUserFile(String username) {
        try {
            File file = new File("src/UserFiles/" + username + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemToCart(String username, String itemName, String description, int quantity, String price) {
        try {
            String filePath = "src/UserFiles/" + username + ".txt";
            File file = new File(filePath);

            // Check if item already exists and increment amount
            String[][] existingItems = getCartItems(username);
            for (String[] item : existingItems) {
                if (item[0].equals(itemName)) {
                    int amount = Integer.parseInt(item[3]) + quantity;
                    clearCart(username);
                    rewriteItemsWithNewAmount(username, existingItems, itemName, amount);
                    return;
                }
            }

            // If item doesn't exist, add new item with amount 1
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("ItemName: " + itemName);
            bufferedWriter.newLine();
            bufferedWriter.write("Description: " + description);
            bufferedWriter.newLine();
            bufferedWriter.write("Price: " + price);
            bufferedWriter.newLine();
            bufferedWriter.write("Amount: 1");
            bufferedWriter.newLine();
            bufferedWriter.write("------------------------");
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrementItemAmount(String username, String itemName) {
        try {
            String[][] existingItems = getCartItems(username);
            for (String[] item : existingItems) {
                if (item[0].equals(itemName)) {
                    int currentAmount = Integer.parseInt(item[3]);
                    if (currentAmount > 1) {
                        clearCart(username);
                        rewriteItemsWithNewAmount(username, existingItems, itemName, currentAmount - 1);
                    } else {
                        deleteItem(username, itemName);
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rewriteItemsWithNewAmount(String username, String[][] items, String itemName, int newAmount) {
        try {
            FileWriter fileWriter = new FileWriter("src/UserFiles/" + username + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String[] item : items) {
                bufferedWriter.write("ItemName: " + item[0]);
                bufferedWriter.newLine();
                bufferedWriter.write("Description: " + item[1]);
                bufferedWriter.newLine();
                bufferedWriter.write("Price: " + item[2]);
                bufferedWriter.newLine();
                bufferedWriter.write("Amount: " + (item[0].equals(itemName) ? newAmount : item[3]));
                bufferedWriter.newLine();
                bufferedWriter.write("------------------------");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[][] getCartItems(String username) {
        try {
            File file = new File("src/UserFiles/" + username + ".txt");
            if (!file.exists()) {
                return new String[0][0];
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            List<String[]> items = new ArrayList<>();
            String[] currentItem = new String[4]; // Changed to 4 to include amount

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ItemName: ")) {
                    currentItem = new String[4];
                    currentItem[0] = line.substring(10);
                } else if (line.startsWith("Description: ")) {
                    currentItem[1] = line.substring(13);
                } else if (line.startsWith("Price: ")) {
                    currentItem[2] = line.substring(7);
                } else if (line.startsWith("Amount: ")) {
                    currentItem[3] = line.substring(8);
                    items.add(currentItem);
                }
            }
            reader.close();

            return items.toArray(new String[items.size()][4]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }

    public double getTotalPrice(String username) {
        double totalPrice = 0;
        String[][] items = getCartItems(username);
        for (String[] item : items) {
            totalPrice += Double.parseDouble(item[2]) * Integer.parseInt(item[3]);
        }
        return totalPrice;
    }

    public void deleteItem(String username, String itemName) {
        try {
            String[][] items = getCartItems(username);
            List<String[]> remainingItems = new ArrayList<>();
            
            for (String[] item : items) {
                if (!item[0].equals(itemName)) {
                    remainingItems.add(item);
                }
            }
            
            clearCart(username);
            
            if (!remainingItems.isEmpty()) {
                FileWriter fileWriter = new FileWriter("src/UserFiles/" + username + ".txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                
                for (String[] item : remainingItems) {
                    bufferedWriter.write("ItemName: " + item[0]);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Description: " + item[1]);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Price: " + item[2]);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Amount: " + item[3]);
                    bufferedWriter.newLine();
                    bufferedWriter.write("------------------------");
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCart(String username) {
        try {
            File file = new File("src/UserFiles/" + username + ".txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
