package org.lab3;
import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private static Socket client;
    private static ObjectOutputStream os;
    private static ObjectInputStream is;
    static final int N = 4;

    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        Client client = new Client();
        client.connectTo("localhost", 8080);
        Object message;
        String msg;
        if((message = (String) is.readObject()) != null) {
            System.out.println(message);
            message = client.sendMessage(N);
            for(int i = 0;i<N;i++)
            {
                msg = scan.nextLine();
                message = client.sendMessage(new Message(i, msg));
                if(!Objects.equals((String) message, "next")) break;
            }
            System.out.println((String)is.readObject());
        }
        closeConnection();
    }

    private void connectTo(String host, int port)
    {
        try{
            client = new Socket(host, port);
            os = new ObjectOutputStream(client.getOutputStream());
            is = new ObjectInputStream(client.getInputStream());
            System.out.println("Connected !");

        } catch (IOException ex) {
            System.err.println("Failed to connect");
        }
    }

    private String sendMessage(Object message)
    {
        try{
            os.writeObject(message);
            os.flush();
            return (String) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to send message");
            return "Error";
        }
    }
    private static void closeConnection() throws IOException {
        try{
            is.close();
            os.close();
            client.close();
            System.out.println("Client close correct");
        } catch (IOException e) {
            System.err.println("Failed to close client");
        }
    }
}
