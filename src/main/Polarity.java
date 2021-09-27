package main;

public enum Polarity {
	NEGATIVE(0), NEUTRAL(2), POSITIVE(4), INVALID(-1);

	private int value;

	Polarity(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static Polarity valueOf(char value) {
		return Polarity.valueOf(Character.getNumericValue(value));
	}

	public static Polarity valueOf(int value) {
		for (Polarity current : Polarity.values()) {
			if (current.value == value) {
				return current;
			}
		}

		return Polarity.INVALID;
	}

	public static Polarity[] validValues() {
		return new Polarity[] { Polarity.NEGATIVE, Polarity.NEUTRAL, Polarity.POSITIVE };
	}
}
