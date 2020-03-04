package av2.grupa_1;

public class PrintThread extends Thread {
    private String id;

    public PrintThread(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++) {
            System.out.println("Running thread " + this.id + ". Index = " + i);
        }
    }
}
