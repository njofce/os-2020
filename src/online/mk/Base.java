package online.mk;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ThreadExecutor extends Thread {

    private String name;
    private Incrementor incrementor;

    public ThreadExecutor(String name, Incrementor incrementor) {
        this.name = name;
        this.incrementor = incrementor;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++) {
            incrementor.safeLockedIncrement();
        }
    }
}

class Incrementor {

    private int count = 0;
    private Lock lock = new ReentrantLock();

    private Semaphore semaphore = new Semaphore(1);

    void unsafeIncrement() {
        count++;
    }

    void safeIncrementWithSemaphore() throws InterruptedException {
        semaphore.acquire();
            count++;
        semaphore.release();
    }

    void safeSynchronizedIncrement() {
        synchronized (this) {
            count++;
        }
    }

    void safeLockedIncrement() {
        lock.lock();
            count++;
        lock.unlock();
    }

    int getCount() {
        return count;
    }

}

public class Base {

    public static void threadWithRunnable() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from runnable");
            }
        });

        t1.start();
    }

    public static void main(String[] args) throws InterruptedException {
//        threadWithRunnable();

        Incrementor incrementor = new Incrementor();

        Thread executor1 = new ThreadExecutor("Thread 1", incrementor);
        Thread executor2 = new ThreadExecutor("Thread 2", incrementor);

        executor1.start();
        executor2.start();

        executor1.join(1000);
        executor2.join(1000);

        if(executor1.isAlive() || executor2.isAlive()) {
            System.out.println("Deadlock");
            executor1.interrupt();
            executor2.interrupt();
        }

        int count = incrementor.getCount();
        System.out.println("Result is: " + count);
    }

}
