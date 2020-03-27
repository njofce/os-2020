package online.en.blockingqueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Producer extends Thread{

    private String name;
    private BlockingQueue<String> sharedQueue;

    public Producer(String name, BlockingQueue sharedQueue) {
        this.name = name;
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println("The producer " + name + "is producing " + i);
            try {
                sharedQueue.enqueue("Product " + i + " produced by " + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread{

    private String name;
    private BlockingQueue<String> sharedQueue;

    public Consumer(String name, BlockingQueue sharedQueue) {
        this.name = name;
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            String element = null;
            try {
                element = sharedQueue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i + "-" + name + " is getting the element " + element);
        }
    }
}


public class BlockingQueueSimulation {

    public static void main(String[] args) {
        BlockingQueue<String> sharedQueue = new BlockingQueue<>(10);
        List<Thread> threads = new ArrayList<>();

        for(int i=0; i <10; i++) {
            Producer producer = new Producer("Producer " + i, sharedQueue);
            threads.add(producer);

            Consumer consumer = new Consumer("Consumer " + i, sharedQueue);
            threads.add(consumer);
        }

        threads.forEach(t -> t.start());
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("The simulation is over");
    }
}
