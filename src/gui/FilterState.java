package gui;

public class FilterState {
	private boolean author = false;
	private boolean text = false;
	private boolean negativePolarity = true;
	private boolean neutralPolarity = true;
	private boolean positivePolarity = true;

	public FilterState() {}
	
	public boolean isAuthor() {
		return author;
	}

	public void setAuthor(boolean author) {
		this.author = author;
	}

	public boolean isText() {
		return text;
	}

	public void setText(boolean text) {
		this.text = text;
	}

	public boolean isNegativePolarity() {
		return negativePolarity;
	}

	public void setNegativePolarity(boolean negativePolarity) {
		this.negativePolarity = negativePolarity;
	}

	public boolean isNeutralPolarity() {
		return neutralPolarity;
	}

	public void setNeutralPolarity(boolean neutralPolarity) {
		this.neutralPolarity = neutralPolarity;
	}

	public boolean isPositivePolarity() {
		return positivePolarity;
	}

	public void setPositivePolarity(boolean positivePolarity) {
		this.positivePolarity = positivePolarity;
	}
}
