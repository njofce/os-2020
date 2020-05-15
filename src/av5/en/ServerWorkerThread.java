package av5.en;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerWorkerThread extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ServerWorkerThread(DataInputStream inputStream, DataOutputStream outputStream) {
        this.dataInputStream = inputStream;
        this.dataOutputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            String messageFromClient = dataInputStream.readUTF();
            System.out.println("Revceived the message from client: " + messageFromClient);
            dataOutputStream.writeUTF("Hello, I am the server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
