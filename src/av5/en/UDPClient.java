package av5.en;

import java.io.IOException;
import java.net.*;

public class UDPClient extends Thread {

    private DatagramSocket datagramSocket;

    public UDPClient() throws SocketException {
        datagramSocket = new DatagramSocket();
    }

    @Override
    public void run() {
        String messageToSendToServer = "Hello from client";
        byte[] bytes = messageToSendToServer.getBytes();
        DatagramPacket packet1 = new DatagramPacket(bytes, bytes.length);
        try {
            InetAddress address = InetAddress.getByName("localhost");
            packet1.setAddress(address);
            packet1.setPort(9876);
            datagramSocket.send(packet1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException {
        UDPClient client1 = new UDPClient();
        client1.start();
    }
}
