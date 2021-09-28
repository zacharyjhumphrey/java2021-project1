package main;

public class PolarityPredictor {
	
	public static double predictMany(TweetCollection coll) {
		double correct = 0;
		
		for (Tweet tweet : coll) {
			Polarity prediction = PolarityPredictor.predictOne(tweet);
			Polarity tweetPolarity = tweet.getPolarity();
			
			if (tweetPolarity == prediction) {
				correct++;
			}
		}
		
		return (correct / coll.getTweetCount());
	}
	
	public static Polarity predictOne(Tweet tweet) {
		return Polarity.validValues()[(int) (Math.random() * 3)];
	}
}
