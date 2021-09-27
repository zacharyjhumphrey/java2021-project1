package main;

public class Tweet {
	private String text;
	private long id;
	private Polarity polarity;
	private String author;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Polarity getPolarity() {
		return polarity;
	}

	public void setPolarity(Polarity polarity) {
		this.polarity = polarity;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String toString() {
		return polarity.getValue() + "," + Long.toString(id) + "," + author + "," + text + "\n";
	}

	public boolean equals(Tweet tweet) {
		return (
			this.id == tweet.getId() && 
			this.author.equals(tweet.getAuthor()) && 
			this.polarity == tweet.getPolarity() && 
			this.text.equals(tweet.getText())
		);
	}
	
}
