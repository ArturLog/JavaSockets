package org.lab3;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket serverSocket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private ServerSocket server;
    private Socket socket;
    public static void main( String[] args )
    {
        Server server = new Server();
        server.start(8080);
    }

    private void start(int port)
    {
        try {
            server = new ServerSocket(port);
            System.out.println("Server start succesed !");
            try{
                while(true)
                {
                    socket = server.accept();
                    System.out.println("Someone connected ! Create new thread");
                    Thread thread = new Thread(new CustomerService(socket));
                    thread.start();
                }
            } catch (IOException e) {
                System.err.println("Sever failed");
                closeConnection();
            }
        } catch (IOException ex) {
            System.err.println("Failed to start server");
        }
    }

    private void closeConnection()
    {
        try{
            is.close();
            os.close();
            server.close();
            System.out.println("Server close correct");
        } catch (IOException e) {
            System.err.println("Failed to close server");
        }
    }
}
