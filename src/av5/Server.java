package av5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Server extends Thread {
    private Semaphore maxConnections = new Semaphore(100);
    private ServerSocket serverSocket;
    private Map<Integer, Candidate> candidates;

    private Object object = new Object();

    public Server (int port) throws IOException {
        serverSocket = new ServerSocket(port);
        candidates = new HashMap<>();
        initCandidates();
        initCandidatesFile();
    }

    private void initCandidates() {
        candidates.put(0, new Candidate("Kandidat 0", 0));
        candidates.put(1, new Candidate("Kandidat 1", 0));
        candidates.put(2, new Candidate("Kandidat 2", 0));
        candidates.put(3, new Candidate("Kandidat 3", 0));
    }

    /*
    Candidate Index
    Candidate Name
     */
    private void initCandidatesFile() throws IOException {
        DataOutputStream outputStreamVotes = new DataOutputStream(new FileOutputStream("votes.txt"));
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("candidates.txt"));
        for(int i = 0; i < 4; i++) {
            outputStreamVotes.writeInt(0);
            outputStream.writeInt(i);
            outputStream.writeUTF("Candidate" + i);
        }
        outputStream.close();
        outputStreamVotes.close();
    }

    public void vote(int i) {

        if(i >=0 && i <=3) {
            synchronized (object) {
                Candidate candidate = candidates.get(i);
                candidate.votes++;
            }
        }
    }

    public synchronized void voteToFile(int i) throws IOException {
        if(i >=0 && i <=3) {
            DataInputStream inputStreamVotes = new DataInputStream(new FileInputStream("votes.txt"));
            List<Integer> votes = new ArrayList<>();

            for(int index = 0; index < 4; index++) {
                int i1= inputStreamVotes.readInt();
                votes.add(i1);
            }
            Integer currentNumberOfVotes = votes.get(i);
            votes.set(i, currentNumberOfVotes + 1);
            inputStreamVotes.close();

            DataOutputStream outputStreamVotes = new DataOutputStream(new FileOutputStream("votes.txt"));
            for(int index = 0; index < 4; index++) {
                outputStreamVotes.writeInt(votes.get(index));
            }
            outputStreamVotes.flush();

            outputStreamVotes.close();
        }
    }

    public String getCandidates() {
        StringBuilder builder = new StringBuilder();
        for(Integer i: candidates.keySet()) {
            builder.append(i).append("-").append(candidates.get(i).name).append("\n");
        }
        return builder.toString();
    }

    public String getCandidatesFromFile() throws IOException {
        DataInputStream inputStream = new DataInputStream(new FileInputStream("candidates.txt"));
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            int i1 = inputStream.readInt();
            String name = inputStream.readUTF();
            builder.append(i1).append("-").append(name).append("\n");
        }
        return builder.toString();
    }

    public String getResults() {
        StringBuilder builder = new StringBuilder();
        for(Integer i: candidates.keySet()) {
            Candidate candidate = candidates.get(i);
            builder.append(candidate.name)
                    .append(":")
                    .append(candidate.votes)
                    .append(" Votes")
                    .append("\n");
        }
        return builder.toString();
    }

    public synchronized String getResultsFromFile() throws IOException {
        StringBuilder builder = new StringBuilder();
        DataInputStream inputStream = new DataInputStream(new FileInputStream("votes.txt"));
        for(int i = 0; i < 4; i++) {
            Integer votes = inputStream.readInt();
            builder.append(i).append("-").append(votes).append("\n");
        }
        inputStream.close();
        return builder.toString();
    }

    public void closeConnection() {
        maxConnections.release();
    }

    @Override
    public void run() {
        while(true) {
            try {
                maxConnections.acquire();
                Socket accept = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(accept.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(accept.getOutputStream());
                ServerWorker worker = new ServerWorker(this, inputStream, outputStream);
                worker.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
