package av5.en;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Thread {

    private DatagramSocket datagramSocket;

    public UDPServer(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        int size = 64;
        byte dataBuff[] = new byte[size];
        DatagramPacket received = new DatagramPacket(dataBuff, size);

        while (true) {
            try {
                datagramSocket.receive(received);
                String receivedMessage = new String(received.getData());
                System.out.println("Received message: " + receivedMessage);
                String messageFromServer = "This is a message from the server!";
                byte[] bytesToSend = messageFromServer.getBytes();
                DatagramPacket datagramToSend = new DatagramPacket(bytesToSend, bytesToSend.length);
                datagramToSend.setAddress(received.getAddress());
                datagramToSend.setPort(received.getPort());
                datagramSocket.send(datagramToSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        UDPServer udpServer = new UDPServer(9876);
        udpServer.start();
    }
}
