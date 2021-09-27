package main;

public class PolarityPredictor {
	
	public static double predictMany(TweetCollection coll) {
		int correct = 0;
		
		for (Tweet tweet : coll) {
			Polarity prediction = PolarityPredictor.predictOne(tweet);
			
			if (tweet.getPolarity() == prediction) {
				correct++;
			}
		}
		
		return (correct / coll.getTweetCount());
	}
	
	public static Polarity predictOne(Tweet tweet) {
		return Polarity.INVALID;
	}
}
