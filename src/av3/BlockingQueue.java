package av3;

import java.util.ArrayList;
import java.util.List;

// A -> B -> C
// [A, B, C, D, _, _, _, _]
public class BlockingQueue<T> {

    List<T> contents;
    int capacity;

    public BlockingQueue(int capacity) {
        contents = new ArrayList<>();
        this.capacity = capacity;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (contents.size() == capacity) {
            wait();
        }

        contents.add(item);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        T item;
        while (contents.size() == 0) {
            wait();   // 1 Consumers
        }

        item = contents.remove(contents.size() - 1); // 0

        notifyAll();
        return item;
    }

}
