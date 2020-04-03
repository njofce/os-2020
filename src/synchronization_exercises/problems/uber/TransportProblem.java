package synchronization_exercises.problems.uber;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransportProblem {

    public static void main(String[] args) {
        TransportProblemTest u = new TransportProblemTest();
        try {
            u.simulate();
        } catch (InterruptedException e) {
            System.out.println("NOT OK");
        }
    }

}

class TransportProblemTest {

    private static int counterA = 0;
    private static int counterB = 0;

    private static Lock lock = new ReentrantLock();

    private static Semaphore fanACanEnter = new Semaphore(0);
    private static Semaphore fanBCanEnter = new Semaphore(0);

    private static Semaphore isSeated = new Semaphore(0);
    private static Semaphore canExit = new Semaphore(0);

    private static class Taxi {

        void seatFanA() throws InterruptedException {
            // TODO: 4/3/20
            boolean isLast = false;
            lock.lock();
            counterA++;
            personHere();
            if(counterA == 2 && counterB >= 2) {
                // notify waiting threads to get into the taxi
                isLast = true;
                counterA -= 2;
                counterB -= 2;
                fanACanEnter.release();
                fanBCanEnter.release(2);
            }else if(counterA == 4) {
                // notify waiting threads to get into the taxi
                isLast = true;
                counterA -=4;
                fanACanEnter.release(3);
            }
            else {
                lock.unlock();
                fanACanEnter.acquire();
            }

            seated();

            if(isLast) {
                isSeated.acquire(3);
                drive();
                canExit.release(4);
                lock.unlock();
            }
            else {
                isSeated.release();
            }
            canExit.acquire();
            exit();
        }

        void seatFanB() throws InterruptedException {
            // TODO: 4/3/20
            boolean isLast = false;
            lock.lock();
            counterB++;
            personHere();
            if(counterA >= 2 && counterB == 2) {
                // notify waiting threads to get into the taxi
                isLast = true;
                counterA -= 2;
                counterB -= 2;
                fanBCanEnter.release();
                fanACanEnter.release(2);
            }else if(counterB == 4) {
                // notify waiting threads to get into the taxi
                isLast = true;
                counterB -=4;
                fanBCanEnter.release(3);
            }
            else {
                lock.unlock();
                fanBCanEnter.acquire();
            }

            seated();

            if(isLast) {
                isSeated.acquire(3);
                drive();
                canExit.release(4);
                lock.unlock();
            }
            else {
                isSeated.release();
            }
            canExit.acquire();
            exit();
        }

        void personHere() {
            System.out.println("====HERE: " + Thread.currentThread().getName());
            System.out.flush();
        }

        void seated() {
            System.out.println("====SEATED: " + Thread.currentThread().getName());
            System.out.flush();
        }

        void drive() {
            System.out.println("====DRIVE: " + Thread.currentThread().getName());
        }

        void exit() {
            System.out.println("====EXITING: " + Thread.currentThread().getName());
        }

    }

    private class FanA extends Thread {
        private Taxi taxi;

        public FanA(Taxi taxi) {
            this.taxi = taxi;
        }

        @Override
        public void run() {
            try {
                taxi.seatFanA();
            }catch (InterruptedException e) {
                System.out.println("===ERROR====");
            }

        }
    }

    private class FanB extends Thread {
        private Taxi taxi;

        public FanB(Taxi taxi) {
            this.taxi = taxi;
        }

        @Override
        public void run() {
            try {
                taxi.seatFanB();
            }catch (InterruptedException e) {
                System.out.println("===ERROR====");
            }
        }
    }

    void simulate() throws InterruptedException {
        Taxi taxi = new Taxi();

        HashSet<Thread> threads = new HashSet<>();

        for(int i = 0; i < 14; i++) {
            FanA fanA = new FanA(taxi);
            fanA.setName("FanA_" + i);
            threads.add(fanA);
        }

        for(int i = 0; i < 10; i++) {
            FanB fanB = new FanB(taxi);
            fanB.setName("FanB_" + i);
            threads.add(fanB);
        }

//        Thread.sleep(100);

        for(Thread t: threads) {
            t.start();
        }
        for(Thread t: threads) {
            t.join(1000);
        }

        boolean hasDeadlock = false;
        for(Thread t: threads) {
            if(t.isAlive()) {
                t.interrupt();
                System.out.println("Deadlock");
                hasDeadlock = true;
            }
        }

        if(!hasDeadlock)
            System.out.println("Synced successfully");
    }

}
