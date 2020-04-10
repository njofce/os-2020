package av5;

import java.io.IOException;

public class NetworkingExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "localhost";
        int port = 9876;
        Server server = new Server(port);
        Client client1 = new Client("Client 1", host, port);
        Client client2 = new Client("Client 2", host, port);
        Client client3 = new Client("Client 3", host, port);

        server.start();
        Thread.sleep(1000);

        client1.start();
        client2.start();
        client3.start();

        // Zosto ne zavrsuva programata bez join
    }

}
