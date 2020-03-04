package av2.grupa_1;

import av2.grupa_1.PrintThread;

public class Threading {

    public static void main(String[] args) {
        System.out.println("Hello from main thread");

        PrintThread printThread1 = new PrintThread("1");
        PrintThread printThread2 = new PrintThread("2");

        printThread1.start();
        printThread2.start();

    }

}
