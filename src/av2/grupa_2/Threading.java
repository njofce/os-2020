package av2.grupa_2;

class Buffer {
    public String name = "buffer1";
    public int count = 0;
    public Buffer() {}

    void unsafeIncrement() {
        synchronized (this) {
            count++;
        }
        System.out.println("accessed by thread: " + Thread.currentThread().getName());
    }

}

public class Threading {

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 20; i++) {
            Buffer b = new Buffer();
            Thread parallelExecutor1 = new ParallelExecutor("p1", b);
            Thread parallelExecutor2 = new ParallelExecutor("p2", b);

            parallelExecutor1.start();
            parallelExecutor2.start();

            parallelExecutor1.join();
            parallelExecutor2.join();

            System.out.println("Total: " + b.count);
        }
    }
}
