package av5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    String name;
    private Socket socket;

    public Client(String name, String host, int port) throws IOException {
        this.name = name;
        this.socket = new Socket(host, port);
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            String candidatesList = inputStream.readUTF();
            System.out.println(candidatesList);
            System.out.println(name + ": Izberete kandidat");

            Scanner scanner = new Scanner(System.in);
            int readVote = scanner.nextInt();
            outputStream.writeInt(readVote);
            System.out.println("Client " + name +" sent vote: " + readVote);

            System.out.println("Client " +name + " waiting for results....");
            String results = inputStream.readUTF();
            System.out.println(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
