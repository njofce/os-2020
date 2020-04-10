package av5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerWorker extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Server server;

    public ServerWorker(Server server, DataInputStream inputStream, DataOutputStream outputStream) {
        this.server = server;
        this.dataInputStream = inputStream;
        this.dataOutputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            dataOutputStream.writeUTF(server.getCandidatesFromFile());
            System.out.println("Worker: sent list to client");
            int candidateNumber = dataInputStream.readInt();
            System.out.println("Received vote for: " + candidateNumber);
            server.voteToFile(candidateNumber);
            dataOutputStream.writeUTF(server.getResultsFromFile());
            server.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
