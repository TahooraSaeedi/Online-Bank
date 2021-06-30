package Server;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class Server {
    ServerSocket server;
    int port = 8080;

    public Server() {
        try {
            server = new ServerSocket(port);
            while (true) {
                Socket client = server.accept();
                Thread thread = new Thread(new ClientManager(this, client));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}