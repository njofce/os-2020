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
        b1.increment();
//        for (int i = 0; i < 20; i++) {
//            System.out.println(name + ":" + i);
//            b1.increment();
//        }
    }
}
