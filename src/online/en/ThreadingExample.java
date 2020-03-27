package online.en;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {

    private Lock lock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(0);
    private int count = 0;

    // (Main             TA1)
    // read <- count(2)
    // inc val
    // write -> count
    // Sync Mechanisms: Monitor (synchronized, wait, notify, notifyAll), Lock, Semaphore
    public void increment() throws InterruptedException {
        semaphore.acquire();
            count++;
        semaphore.release();
    }

    public void decrement() throws InterruptedException {
        semaphore.acquire();
            count--;
        semaphore.release();
    }

    public int getCount() {
        return this.count;
    }

}

public class ThreadingExample {

    public static final Object monitor = new Object();
    public static final Lock lock = new ReentrantLock();
    public static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        ThreadA threadA1 = new ThreadA(counter, "Thread 1");
        ThreadA threadA2 = new ThreadA(counter, "Thread 2");
        threadA1.start();
        threadA2.start();

        semaphore.acquire();
            threadA1.publicCount++;
        semaphore.release();

        threadA1.join(1000);
        threadA2.join(1000);

        if(threadA1.isAlive() || threadA2.isAlive()) {
            threadA1.interrupt();
            threadA2.interrupt();
        }

        System.out.println(counter.getCount());
    }

}

class B {

}

class A extends B implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello from runnable A");
    }
}

class ThreadA extends Thread {

    public int publicCount = 0;

    private Counter counter;
    private String name;

    public ThreadA(Counter counter, String name) {
        this.counter = counter;
        this.name = name;
    }

    private void performPublicIncrement() throws InterruptedException {
        ThreadingExample.semaphore.acquire();
        publicCount++;
        ThreadingExample.semaphore.release();
    }

    public void run() {
        try {
            performPublicIncrement();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0; i< 20; i++) {
            System.out.println(name +" is executing!");
            try {
                counter.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}
