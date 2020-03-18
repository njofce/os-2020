package av2.grupa_2;

public class ParallelExecutor extends Thread {

    private String name;
    private Buffer b1;

    public ParallelExecutor(String name, Buffer b) {
        this.name = name;
        this.b1 = b;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            b1.unsafeIncrement();
        }
    }
}
