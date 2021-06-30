package Server;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientManager implements Runnable {
    Server server;
    Socket client;
    DataInputStream reader;
    PrintWriter writer;

    public ClientManager(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            reader = new DataInputStream(client.getInputStream());
            writer = new PrintWriter(client.getOutputStream(), true);
            char command = reader.readLine().charAt(0);
            System.out.println(command);
            switch (command) {
                case '1': {
                    String info = reader.readLine();
                    System.out.println(info);
                    StringTokenizer x = new StringTokenizer(info, "*");
                    Entrance.signUp(x.nextToken(), x.nextToken(), x.nextToken(), x.nextToken(), x.nextToken());
                    writer.println("You are signed up successfully!");
                    break;
                }
                case '2': {
                    String info = reader.readLine();
                    System.out.println(info);
                    StringTokenizer x = new StringTokenizer(info, "*");
                    try {
                        Entrance.logIn(x.nextToken(), x.nextToken());
                        writer.println("You are signed up successfully!");
                    } catch (Exception e) {
                        writer.println(e.getMessage());
                    }
                }
                case '3': {
                    String nationalId = reader.readLine();
                    for (User user : Information.users) {
                        if (user.getNationalId().compareTo(nationalId) == 0) {
                            writer.println(user.getName() + "*" + user.getNationalId() + "*" + user.getPassword() + "*" + user.getPhoneNumber() + "*" + user.getEmail());
                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
