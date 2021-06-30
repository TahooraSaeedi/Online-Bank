package Server;

import java.util.ArrayList;
import java.io.*;

public class Information {
    private static File file = new File("Information.dat");
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Account> accounts = new ArrayList<Account>();

    public static void receiveInformation() {
        try (ObjectInputStream x = new ObjectInputStream(new FileInputStream(file))) {
            users = (ArrayList<User>) x.readObject();
            accounts = (ArrayList<Account>) x.readObject();
        } catch (IOException | ClassNotFoundException ignored) {

        }
    }

    public static void sendInformation() {
        try (ObjectOutputStream x = new ObjectOutputStream(new FileOutputStream(file))) {
            x.writeObject(users);
            x.writeObject(accounts);
        } catch (IOException ignored) {

        }
    }

}
