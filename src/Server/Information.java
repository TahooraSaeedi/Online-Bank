package Server;

import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.io.*;

public abstract class Information {
    private static final File file = new File("src/Server/Information");
    public static String adminPassword = "1111";
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Account> accounts = new ArrayList<Account>();

    public static void receiveInformation() {
        try (ObjectInputStream x = new ObjectInputStream(new FileInputStream(file))) {
            users = (ArrayList<User>) x.readObject();
            accounts = (ArrayList<Account>) x.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (User user : Information.users) {
            user.setLock(new ReentrantLock(true));
        }
        for (Account account : Information.accounts) {
            account.setLock(new ReentrantLock(true));
        }
    }

    public static void sendInformation() {
        try (ObjectOutputStream x = new ObjectOutputStream(new FileOutputStream(file))) {
            x.writeObject(users);
            x.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}