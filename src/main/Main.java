package main;

public class Main {
	public static void main(String[] args) {
		TweetCollection coll = TweetFileUtil.getTweetsFromFile("src/data/test.txt");
		
		System.out.println("Predicting the polarity of these tweets has led to the following results: " + PolarityPredictor.predictMany(coll));
		System.out.println("For example, here is a single tweet's prediction: " + PolarityPredictor.predictOne(coll.getRandomTweet()));
		
		coll.save();
	}

}

