package synchronization_exercises;

import java.util.HashSet;

public abstract class ProblemExecution {

	public static void start(HashSet<Thread> threads, AbstractState state)
			throws Exception {

		for (Thread t : threads) {
			t.start();
		}

		for (Thread t : threads) {
			t.join(1000);
		}

		// check for deadlock
		for (Thread t : threads) {
			if (t.isAlive()) {
				t.interrupt();
				if (t instanceof TemplateThread) {
					TemplateThread tt = (TemplateThread) t;
					tt.setException(new PointsException(25, "DEADLOCK"));
				}
			}
		}

		// print the status
		state.printStatus();
	}

}
