package Server;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private final int port = 8080;

    public Server() {
        try {
            server = new ServerSocket(port);
            Information.receiveInformation();
            while (true) {
                Socket client = server.accept();
                Thread thread = new Thread(new ClientManager(this, client));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Information.sendInformation();
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}
