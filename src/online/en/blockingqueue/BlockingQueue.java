package online.en.blockingqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {

    List<T> contents;
    int capacity;

    private final Semaphore producerSemaphore = new Semaphore(100);
    private final Semaphore consumerSemaphore = new Semaphore(100);

    private final Semaphore sync = new Semaphore(1);

    public BlockingQueue(int capacity) {
        contents = new ArrayList<>();
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        producerSemaphore.acquire();
        sync.acquire();
        if(contents.size() == capacity) {
            sync.release();
            Thread.sleep(10);
            sync.acquire();
        }

        contents.add(item);

        sync.release();
        consumerSemaphore.release();
    }

    public T dequeue() throws InterruptedException {
        T item;
        consumerSemaphore.acquire();
        sync.acquire();
        while(contents.size() == 0) {
            sync.release();
            Thread.sleep(10);
            sync.acquire();
        }

        item = contents.remove(contents.size() - 1);

        sync.release();
        producerSemaphore.release();
        return item;
    }
}
