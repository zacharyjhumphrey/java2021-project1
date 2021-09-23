package main;

import java.util.ArrayList;
import java.util.HashMap;

public class TweetCollection {
	private HashMap<Integer, Tweet> idMap;
	private HashMap<String, ArrayList<Tweet>> authorMap;
	private ArrayList<Tweet> tweets;

	public void addTweet(Tweet newTweet) {
		tweets.add(newTweet);

		if (authorMap.containsKey(newTweet.getAuthor())) {
			authorMap.get(newTweet.getAuthor()).add(newTweet);
		} else {
			authorMap.put(newTweet.getAuthor(), new ArrayList<Tweet>());
			authorMap.get(newTweet.getAuthor()).add(newTweet);
		}

		idMap.put(newTweet.getId(), newTweet);
	}
}
