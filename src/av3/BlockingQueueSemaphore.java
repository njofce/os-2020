package av3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BlockingQueueSemaphore<T> {

    List<T> contents;
    int capacity;

    private Semaphore producerSemaphore = new Semaphore(100);
    private Semaphore consumerSemaphore = new Semaphore(100);

    private Semaphore pcCoordinator = new Semaphore(1);

    public BlockingQueueSemaphore(int capacity) {
        contents = new ArrayList<>();
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        producerSemaphore.acquire();

        pcCoordinator.acquire();
        while(contents.size() == capacity) {
            pcCoordinator.release();
            Thread.sleep(100);
            pcCoordinator.acquire();
        }
        contents.add(item);

        pcCoordinator.release();
        consumerSemaphore.release();
    }

    public T dequeue() throws InterruptedException {
        T item = null;
        consumerSemaphore.acquire();

        pcCoordinator.acquire();
        while(contents.size() == 0) {
            pcCoordinator.release();

            Thread.sleep(100);

            pcCoordinator.acquire();
        }

        item = contents.remove(contents.size() - 1);
        pcCoordinator.release();
        producerSemaphore.release();
        return item;
    }

}
