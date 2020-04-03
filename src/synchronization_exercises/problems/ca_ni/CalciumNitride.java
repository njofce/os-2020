package synchronization_exercises.problems.ca_ni;

import synchronization_exercises.ProblemExecution;
import synchronization_exercises.TemplateThread;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Ca3N2
// Atoms gathering | -> Molecule
public class CalciumNitride {

    private static Semaphore caHere = new Semaphore(3);
    private static Semaphore nHere = new Semaphore(2);
    private static Semaphore canBond = new Semaphore(0);

    private static Semaphore canExit = new Semaphore(0);
    private static int totalAtoms = 0;

    private static Lock lock = new ReentrantLock();

    public static void init() {

    }

    public static class Calcium extends TemplateThread {

        public Calcium(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            caHere.acquire();
            lock.lock();
            totalAtoms++;
            if(totalAtoms == 5) {
                // notify other atoms that are waiting to bond
                canBond.release(5);
                lock.unlock();
            }
            else {
                lock.unlock();
            }

            canBond.acquire();
            state.bond();

            lock.lock();
            totalAtoms--;
            if(totalAtoms == 0) {
                state.validate();
                canExit.release(5);
                lock.unlock();
            } else {
                lock.unlock();
            }

            canExit.acquire();
            caHere.release();
        }

    }

    public static class Nitrogen extends TemplateThread {

        public Nitrogen(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            nHere.acquire();
            lock.lock();
            totalAtoms++;
            if(totalAtoms == 5) {
                // notify other atoms that are waiting to bond
                canBond.release(5);
                lock.unlock();
            }
            else {
                lock.unlock();
            }

            canBond.acquire();
            state.bond();

            lock.lock();
            totalAtoms--;
            if(totalAtoms == 0) {
                state.validate();
                canExit.release(5);
                lock.unlock();
            } else {
                lock.unlock();
            }

            canExit.acquire();
            nHere.release();
        }

    }

    static CalciumNitrideState state = new CalciumNitrideState();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    public static void run() {
        try {
            Scanner s = new Scanner(System.in);
            int numRuns = 1;
            int numIterations = 100;
            s.close();

            HashSet<Thread> threads = new HashSet<Thread>();

            for (int i = 0; i < numIterations; i++) {
                Nitrogen n = new Nitrogen(numRuns);
                threads.add(n);
                Calcium ca = new Calcium(numRuns);
                threads.add(ca);
                ca = new Calcium(numRuns);
                threads.add(ca);
                n = new Nitrogen(numRuns);
                threads.add(n);
                ca = new Calcium(numRuns);
                threads.add(ca);
            }

            init();

            ProblemExecution.start(threads, state);
            System.out.println(new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
