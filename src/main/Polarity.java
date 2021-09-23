package main;

public enum Polarity {
	NEGATIVE(0), NEUTRAL(2), POSITIVE(4);

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
		return Polarity.valueOf((int) value);
	}
	
	// TODO Find a prettier way to write this
	public static Polarity valueOf(int value) {
		Polarity[] polarities = Polarity.values();
		int current = 0;
		
		while (polarities[current].value != value) {
			current++;
		}

		return polarities[current];
	}
}
