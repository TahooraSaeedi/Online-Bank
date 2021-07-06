package Server;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private final int port = 16999;

    public Server(String[] args) {
        try {
            server = new ServerSocket(port);
            Information.receiveInformation();
            //خالی کردن فایل
//            Information.sendInformation();
            Thread FX = new Thread(new ExitButton(args));
            FX.start();
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
        new Server(args);
    }

}