package test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import main.Polarity;
import main.Tweet;

/*
 * TODO Since this is being used exclusively for testing, creating random values would not 
 * be a bad idea, but maybe fetching random tweets off the web would be a better idea
 * I may also be able to pull in random values from the tweet info
 */
public class TestDataGenerator {
	private ArrayList<String> authors;
	private String pathToFile;
	private static final long TOTAL_TWEETS = 1;
	
	// TODO These can be worked in later
	private static final long NUMBER_OF_DUPLICATES = 3;
	private static final long NUMBER_OF_AUTHORS = 8;
	
	public TestDataGenerator() {
		this.authors = TestDataGenerator.generateAuthors();
		this.pathToFile = this.generateTestFile();
	}
	
	public String getPath() {
		return pathToFile;
	}
	
	public String getRandomAuthor() {
		return this.authors.get((int) Math.random() * this.authors.size());
	}

	private String generateTestFile() {
		// TODO Timestamp me
		String path = "src/data/test-for-today.txt";

		try {
			FileWriter fw = new FileWriter(path);

			for (long i = 0; i < TestDataGenerator.TOTAL_TWEETS; i++) {
				Tweet newTweet = this.generateNewTweet();
				fw.write(newTweet.toString());
			}

			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
	
	public Tweet generateNewTweet() {
		Tweet newTweet = new Tweet();
		
		newTweet.setId(TestDataGenerator.generateRandomId());
		newTweet.setAuthor(this.getRandomAuthor());
		newTweet.setText(TestDataGenerator.generateRandomText());
		newTweet.setPolarity(TestDataGenerator.generateRandomPolarity());
		
		return newTweet;
	}
	
	private static ArrayList<String> generateAuthors() {
		ArrayList<String> auths = new ArrayList<>();
		for (int i = 0; i < TestDataGenerator.NUMBER_OF_AUTHORS; i++) {
			auths.add(TestDataGenerator.generateRandomAuthor());
		}
		return auths;
	}

	private static long generateRandomId() {
		return (long) (Math.random() * Long.MAX_VALUE) + 1;
	}

	private static String generateRandomAuthor() {
		return TestDataGenerator.generateRandomString((int) (Math.random() * 20) + 1);
	}

	private static String generateRandomText() {
		return TestDataGenerator.generateRandomString((int) (Math.random() * 120) + 40);
	}

	// TODO Fix exports problem
	private static Polarity generateRandomPolarity() {
		return Polarity.validValues()[(int) (Math.random() * 3)];
	}

	private static String generateRandomString(int len) {
		// TODO Add special characters
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(len);

		for (int i = 0; i < len; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}
}
