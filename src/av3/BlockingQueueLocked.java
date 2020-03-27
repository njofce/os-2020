package av3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueLocked<T> {

    private Lock mutex = new ReentrantLock();

    List<T> contents;
    int capacity;

    public BlockingQueueLocked(int capacity) {
        contents = new ArrayList<>();
        this.capacity = capacity;
    }

    public void enqueue(T item) {
        while (true) {
            mutex.lock();
            if(contents.size() < capacity) {
                contents.add(item);
                mutex.unlock();
                break;
            }
            mutex.unlock();
        }
    }

    public T dequeue() {
        T item = null;

        while (true) {
            mutex.lock();
            if(contents.size() > 0) {
                item = contents.remove(contents.size() - 1);
                mutex.unlock();
                break;
            }
            mutex.unlock();
        }

        return item;
    }
}
