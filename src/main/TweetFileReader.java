package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class TweetFileReader {
	public static TweetCollection getTweetsFromFile(String pathToInput) {
		TweetCollection coll = new TweetCollection();
		
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(pathToInput));
			while ((line = br.readLine()) != null)
			{
				Tweet parsedTweet = TweetFileReader.parseTweet(line);
				coll.addTweet(parsedTweet);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return coll;
	}

	public static Tweet parseTweet(String input) {		
		String[] data = input.split(",");
		Polarity polarity = Polarity.valueOf(data[0].charAt(0));
		int id = Integer.parseInt(data[1]);
		String authorName = data[2];
		String text = data[3];
		
		Tweet newTweet = new Tweet();
		newTweet.setPolarity(polarity);
		newTweet.setId(id);
		newTweet.setAuthor(authorName);
		newTweet.setText(text);
		
		return newTweet;
	}
}
