package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is used to store and handle multiple tweets
 * @author ZacharyHumphrey
 *
 */
public class TweetCollection implements Iterable<Tweet> {
	private static final String DEFAULT_NAME = "YourTweetCollection";
	private String name;
	// TODO Turn this into a TreeSet bc I want the position to stay updated with the id
	private HashMap<Long, Tweet> idMap;
	private HashMap<String, HashSet<Tweet>> authorMap;

	public TweetCollection() {
		this.name = TweetCollection.DEFAULT_NAME;
		idMap = new HashMap<Long, Tweet>();
		authorMap = new HashMap<String, HashSet<Tweet>>();
	}
	
	public TweetCollection(Set<Tweet> tweets) {
		super();
		for (Tweet tweet : tweets) {
			this.addTweet(tweet);
		}
	}
	
	/**
	 * Returns the name of the collection
	 * This name is used to name the save file
	 * @return name of this collection
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the collection
	 * This name is used to name the save file
	 * @param name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Adds tweet to the class's authorMap and idMap
	 * @param newTweet - tweet to be added to the collection
	 */
	public void addTweet(Tweet newTweet) {
		if (!authorMap.containsKey(newTweet.getAuthor())) {
			authorMap.put(newTweet.getAuthor(), new HashSet<Tweet>());
		}
		authorMap.get(newTweet.getAuthor()).add(newTweet);

		idMap.put(newTweet.getId(), newTweet);
	}

	/**
	 * Retuns all tweets in the collection
	 * @return ArrayList of all tweets
	 */
	public ArrayList<Tweet> getAllTweets() {
		return new ArrayList<Tweet>(idMap.values());
	}

	public String[] getAllTweetsAsString() {
		ArrayList<Tweet> allTweets = this.getAllTweets();
		String[] tweetsAsString = new String[allTweets.size()];
		
		for (int i = 0; i < allTweets.size(); i++) {
			tweetsAsString[i] = allTweets.get(i).toString();
		}
		
		return tweetsAsString;
	}
	
	public String getAllTweetsAsOneString() {
		return String.join("\n", this.getAllTweetsAsString());
	}
	
	/**
	 * TODO Test whether or not the tweet is being removed from authMap
	 * TODO Create test making sure that author with 0 tweets is being removed from the collection
	 * TODO Throw exception instead of returning null
	 * Removes the tweet from the authorMap and idMap of the collection
	 * @param id - id of the tweet to be removed
	 * @return tweet that has been removed or null if the tweet is not in the collection
	 */
	public Tweet removeTweet(long id) {
		if (!this.containsTweet(id)) {
			return null;
		}
		Tweet tweetToBeRemoved = this.idMap.get(id);
		String authorName = tweetToBeRemoved.getAuthor();
		HashSet<Tweet> tweetsByAuthor = authorMap.get(authorName);

		tweetsByAuthor.remove(tweetToBeRemoved);
		idMap.remove(id);
		
		if (tweetsByAuthor.size() == 0) {
			this.authorMap.remove(authorName);
		}
		
		return tweetToBeRemoved;
	}

	/**
	 * TODO May want to refactor how this is used for readability when I use the function
	 * Gets the desired tweet with a given id
	 * @param id - id of tweet to be fetched
	 * @return tweet with given id or null if the collection does not contain the tweet
	 * @throws Exception 
	 */
	public Tweet getTweetById(long id) throws Exception {
		if (!this.containsTweet(id)) {
			throw new Exception("Tweet of id: " + id + ".");
		}
		return idMap.get(id);
	}

	/**
	 * Gets the number of tweets in the collection
	 * @return number of tweets in the collection
	 */
	public int getTweetCount() {
		return this.idMap.size();
	}
	
	/**
	 * Gets all ids of the tweets in the collection
	 * @return ids of all tweets in the collection
	 */
	public Set<Long> getAllTweetIds() {
		return idMap.keySet();
	}
	
	/**
	 * @param authorName - name of author's tweets
	 * @return list of tweets written by the author
	 */
	public ArrayList<Tweet> getTweetsByAuthor(String authorName) {
		return new ArrayList<Tweet>(this.authorMap.get(authorName));
	}

	/**
	 * TODO Ask Doderer if I can support the next version with an AWS database
	 * Saves the collection to a .txt file
	 */
	public void save() {
		TweetFileUtil.writeCollectionToFile(this);
	}
	
	/**
	 * Returns whether or not the collection contains at least one tweet with 
	 * the specified author
	 * @param username - author to search
	 * @return whether or not the author is in the collection
	 */
	public boolean containsAuthor(String username) {
		return this.authorMap.containsKey(username);
	}
	
	/**
	 * Returns whether or not the tweet is stored in the collection
	 * @param randomId
	 * @return
	 */
	public boolean containsTweet(long randomId) {
		return this.idMap.containsKey(randomId);
	}

	/**
     * TODO Make this more advanced if Doderer wants more from it
	 * If there are more than 20 tweets, returns the first and last
	 * five of the tweets in the idMap (unsorted)
	 * else it returns an empty string
	 * @return string representation of the collection
	 */
	public String toString() {
		StringBuilder collAsString = new StringBuilder("");
		ArrayList<Tweet> tweets = this.getAllTweets();
		if (tweets.size() <= 20) {
			return collAsString.toString();
		}
		
		for (int i = 0; i < 5; i++) {
			collAsString.append(tweets.get(i));
		}
		
		for (int i = tweets.size() - 5; i < tweets.size(); i++) {
			collAsString.append(tweets.get(i));
		}
		
		return collAsString.toString();
	}

	/**
	 * Returns a random tweets found in the collection
	 * @return random tweet
	 */
	public String getRandomAuthor() {
		ArrayList<String> auths = new ArrayList<String>(this.authorMap.keySet());
		return auths.get((int) (Math.random() * auths.size()));
	}

	/**
	 * Returns a random tweet id found in the collection
	 * @return ranodm id from the collection
	 */
	public long getRandomId() {
		ArrayList<Long> ids = new ArrayList<Long>(this.idMap.keySet());
		return ids.get((int) (Math.random() * ids.size()));
	}
	
	/**
	 * Returns a random tweets found in the collection
	 * @return random tweet
	 */
	public Tweet getRandomTweet() {
		return idMap.get(this.getRandomId());
	}
	
	public static ArrayList<Tweet> findTweetsWhoseAuthorContainsString(ArrayList<Tweet> tweets, String str) {
		return new ArrayList<Tweet>(tweets.stream().filter(t -> t.getAuthor().contains(str)).collect(Collectors.toList()));
	}
	
	public static ArrayList<Tweet> findTweetsWhoseTextContainsString(ArrayList<Tweet> tweets, String str) {
		return new ArrayList<Tweet>(tweets.stream().filter(t -> t.getText().contains(str)).collect(Collectors.toList()));
	}
	
	public static ArrayList<Tweet> findTweetsWhosePolarityMatches(ArrayList<Tweet> tweets, Polarity pol) {
		return new ArrayList<Tweet>(tweets.stream().filter(t -> (t.getPolarity() == pol)).collect(Collectors.toList()));
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
