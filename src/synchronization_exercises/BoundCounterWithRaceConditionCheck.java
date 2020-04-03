package synchronization_exercises;

/**
 * This is helper class for incrementing and decrementing a integer variable,
 * with range validation. It also checks for race condition occurrence if needed
 * 
 * @author ristes
 * 
 */
public class BoundCounterWithRaceConditionCheck {

	private static final int RACE_CONDITION_POINTS = 25;
	private static final String RACE_CONDITION_MESSAGE = "Race condition occured";

	private int value;
	private Integer maxAllowed;
	private Integer minAllowed;
	private int maxErrorPoints;
	private int minErrorPoints;
	private String maxErrorMessage;
	private String minErrorMessage;

	private int max;

	/**
	 * 
	 * @param value
	 */
	public BoundCounterWithRaceConditionCheck(int value) {
		super();
		this.value = value;
		this.max = value;
	}

	/**
	 * 
	 * @param value
	 *            initial value
	 * @param maxAllowed
	 *            upper bound of the value
	 * @param maxErrorPoints
	 *            how many points are lost with the max value constraint
	 *            violation
	 * @param maxErrorMessage
	 *            message shown when the upper bound constrain is violated
	 * @param minAllowed
	 *            lower bound of the value
	 * @param minErrorPoints
	 *            how many points are lost with the min value constraint
	 *            violation
	 * @param minErrorMessage
	 *            message shown when the lower bound constrain is violated
	 */
	public BoundCounterWithRaceConditionCheck(int value, Integer maxAllowed, int maxErrorPoints,
			String maxErrorMessage, Integer minAllowed, int minErrorPoints,
			String minErrorMessage) {
		super();
		this.value = value;
		this.max = value;
		this.maxAllowed = maxAllowed;
		this.minAllowed = minAllowed;
		this.maxErrorPoints = maxErrorPoints;
		this.minErrorPoints = minErrorPoints;
		this.maxErrorMessage = maxErrorMessage;
		this.minErrorMessage = minErrorMessage;
	}

	/**
	 * 
	 * @return the maximum value of the integer variable that occurred at some
	 *         point of the execution
	 */
	public int getMax() {
		return max;
	}

	/**
	 * 
	 * @return the current value
	 */
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Testing for race condition. NOTE: there are no guarantees that the race
	 * condition will be detected
	 * 
	 * @return
	 */
	public PointsException checkRaceCondition() {
		return checkRaceCondition(3, RACE_CONDITION_MESSAGE);
	}

	/**
	 * Testing for race condition. NOTE: there are no guarantees that the race
	 * condition will be detected, but higher the time argument is, the
	 * probability for race condition occurrence is higher
	 * 
	 * @return
	 */
	public PointsException checkRaceCondition(int time, String message) {
		int val;

		synchronized (this) {
			val = value;
		}
		Switcher.forceSwitch(time);
		if (val != value) {
			PointsException e = new PointsException(RACE_CONDITION_POINTS,
					message);
			return e;
		}
		return null;

	}

	public PointsException incrementWithMax() {
		return incrementWithMax(true);
	}

	public PointsException incrementWithMax(boolean checkRaceCondition) {
		if (checkRaceCondition) {
			PointsException raceCondition = checkRaceCondition();
			if (raceCondition != null) {
				return raceCondition;
			}
		}
		synchronized (this) {
			value++;

			if (value > max) {
				max = value;
			}
			if (maxAllowed != null) {
				if (value > maxAllowed) {
					PointsException e = new PointsException(maxErrorPoints,
							maxErrorMessage);
					return e;
				}
			}
		}

		return null;
	}

	public PointsException decrementWithMin() {
		return decrementWithMin(true);
	}

	public PointsException decrementWithMin(boolean checkRaceCondition) {
		if (checkRaceCondition) {
			PointsException raceCondition = checkRaceCondition();
			if (raceCondition != null) {
				return raceCondition;
			}
		}

		synchronized (this) {
			value--;
			if (minAllowed != null) {
				if (value < minAllowed) {
					PointsException e = new PointsException(minErrorPoints,
							minErrorMessage);
					return e;
				}
			}
		}
		return null;
	}
}