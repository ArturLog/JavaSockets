package org.lab3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CustomerService implements Runnable{
    private Socket clientSocket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    public CustomerService(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        os = new ObjectOutputStream(clientSocket.getOutputStream());
        is = new ObjectInputStream(clientSocket.getInputStream());
    }
    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " - going to serve the customer");
        try {
            os.writeObject("ready");
            os.flush();
            Object message;
            message = is.readObject();
            System.out.println("Ready for " + message + " messages");
            int n = (int)message;
            message = null;
            os.writeObject("ready for messages");
            os.flush();
            for(int i = 0; i<n; i++)
            {
                message = is.readObject();
                System.out.println(message.toString());
                os.writeObject("next");
            }
            os.writeObject("finished");
            os.flush();
            System.out.println("Thread " + Thread.currentThread().getId() + " - ends serve the customer");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
