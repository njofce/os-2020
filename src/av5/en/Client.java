package av5.en;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client extends Thread {
    private String name;
    private Socket socket;

    public Client(String name, String host, int port) throws IOException {
        this.name = name;
        socket = new Socket(host, port);
    }

    @Override
    public void run() {
        // Communicate with the server
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            outputStream.writeUTF("Hello, my name is " + name);
            String serverGreet = inputStream.readUTF();
            System.out.println("Received greet from server:" + serverGreet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client1 = new Client("john", "localhost", 9876);
        Client client2 = new Client("jim", "localhost", 9876);
        Client client3 = new Client("jake", "localhost", 9876);

        client1.start();
        client2.start();
        client3.start();
    }
}
