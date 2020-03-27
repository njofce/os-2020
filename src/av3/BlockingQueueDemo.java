package av3;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

class Producer extends Thread{

    private String name;
    private BlockingQueueSemaphore<String> sharedQueue;

    public Producer(String name, BlockingQueueSemaphore sharedQueue) {
        this.name = name;
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for(int i = 0; i< 5; i++) {
            System.out.println("Producer " + i + " is trying to produce");
            try {
                sharedQueue.enqueue("Product " + i + " from " + this.name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread{

    private String name;
    private BlockingQueueSemaphore<String> sharedQueue;

    public Consumer(String name, BlockingQueueSemaphore sharedQueue) {
        this.name = name;
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for(int i = 0; i< 5; i++) {
            System.out.println("Consumer " + i + " is trying to consume");
            String element = null;
            try {
                element = sharedQueue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(element + " was consumed from Consumer " + name);
        }
    }
}

public class BlockingQueueDemo {

    // Monitor, Lock, Semaphore
    public static void main(String[] args) {
        BlockingQueueSemaphore blockingQueue = new BlockingQueueSemaphore<String>(10);

        HashSet<Thread> consumers = new HashSet<>();
        HashSet<Thread> producers = new HashSet<>();

        for(int i=0; i<10; i++) {
            Consumer consumer = new Consumer("Consumer " + i, blockingQueue);
            consumers.add(consumer);

            Producer producer = new Producer("Producer " + i, blockingQueue);
            producers.add(producer);
        }

        consumers.forEach(c -> c.start());
        producers.forEach(c -> c.start());

        consumers.forEach(c -> {
            try {
                c.join(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producers.forEach(c -> {
            try {
                c.join(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Validation for deadlock

        System.out.println("Successfully executed");
    }
}
