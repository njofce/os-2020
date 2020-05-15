package av5.en;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;

    public Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public void listen() throws IOException {
        while (true) {
            System.out.println("Waiting for connections");
            Socket accept = server.accept();
            System.out.println("Accepted connection");
            DataInputStream inputStream = new DataInputStream(accept.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(accept.getOutputStream());
            ServerWorkerThread serverWorkerThread = new ServerWorkerThread(inputStream, outputStream);
            serverWorkerThread.start();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(9876);
        server.listen();
    }
}
