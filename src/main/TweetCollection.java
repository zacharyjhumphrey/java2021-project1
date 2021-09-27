package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// TODO Test this class
public class TweetCollection implements Iterable<Tweet> {
	private HashMap<Long, Tweet> idMap;
	private HashMap<String, HashSet<Tweet>> authorMap;

	public TweetCollection() {
		idMap = new HashMap<Long, Tweet>();
		authorMap = new HashMap<String, HashSet<Tweet>>();
	}
	
	public TweetCollection(Set<Tweet> tweets) {
		super();
		for (Tweet tweet : tweets) {
			this.addTweet(tweet);
		}
	}
	
	public void addTweet(Tweet newTweet) {
		if (!authorMap.containsKey(newTweet.getAuthor())) {
			authorMap.put(newTweet.getAuthor(), new HashSet<Tweet>());
		}
		authorMap.get(newTweet.getAuthor()).add(newTweet);

		idMap.put(newTweet.getId(), newTweet);
	}

	public ArrayList<Tweet> getAllTweets() {
		return new ArrayList<Tweet>(idMap.values());
	}

	// TODO Test extensively
	public Tweet removeTweet(long id) {
		if (!this.containsTweet(id)) {
			return null;
		}
		Tweet tweetToBeRemoved = this.idMap.get(id);
		String authorName = tweetToBeRemoved.getAuthor();
		HashSet<Tweet> tweetsByAuthor = authorMap.get(authorName);

		tweetsByAuthor.remove(tweetToBeRemoved);
		idMap.remove(id);
		
		return tweetToBeRemoved;
	}

	public Tweet getTweetById(long id) {
		return (idMap.containsKey(id)) ? idMap.get(id) : null;
	}

	public int getTweetCount() {
		return this.idMap.size();
	}
	
	public Set<Long> getAllTweetIds() {
		return idMap.keySet();
	}

	public HashSet<Long> getTweetIdsByAuthor(String username) {
		HashSet<Tweet> tweets = authorMap.get(username);
		HashSet<Long> ids = new HashSet<Long>();
		
		for (Tweet tweet : tweets) {
			ids.add(tweet.getId());
		}
		
		return ids;
	}
	
	public ArrayList<Tweet> getTweetsByAuthor(String username) {
		return new ArrayList<Tweet>(this.authorMap.get(username));
	}

	// TODO Test this
	public void saveCollection(String name) {
		TweetFileUtil.writeCollectionToFile(this, name);
	}
	
	public boolean containsAuthor(String username) {
		return this.authorMap.containsKey(username);
	}
	
	public boolean containsTweet(long randomId) {
		return this.idMap.containsKey(randomId);
	}

	// TODO Make this if Doderer wants it
	public String toString() {
		return "";
	}

	// TODO Test these functions in suite
	public String getRandomAuthor() {
		ArrayList<String> auths = new ArrayList<String>(this.authorMap.keySet());
		return auths.get((int) (Math.random() * auths.size()));
	}

	public long getRandomId() {
		ArrayList<Long> ids = new ArrayList<Long>(this.idMap.keySet());
		return ids.get((int) (Math.random() * ids.size()));
	}
	
	public Tweet getRandomTweet() {
		return idMap.get(this.getRandomId());
	}
	
	@Override
	public Iterator<Tweet> iterator() {
		return idMap.values().iterator();
	}
	
	public boolean equals(TweetCollection coll) {
		return this.equals(coll.getAllTweetIds());
	}
	
	public boolean equals(Set<Long> ids) {
		return this.getAllTweetIds().equals(ids);
	}
}
