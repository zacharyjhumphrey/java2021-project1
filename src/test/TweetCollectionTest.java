package test;

import java.util.ArrayList;
import java.util.HashSet;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;

import main.Tweet;
import main.TweetCollection;
import main.TweetFileUtil;

// TODO Doc me
public class TweetCollectionTest {
	private TestDataGenerator testFile;
	
	@BeforeSuite
	public void beforeSuite() {
		testFile = new TestDataGenerator();
	}

	@Test
	public void testAddTweet() {
		TweetCollection coll = this.getTestTweets();
		int previousLength = coll.getTweetCount();
		Tweet toBeAdded = testFile.generateNewTweet();
		coll.addTweet(toBeAdded);
		assertTrue(coll.containsTweet(toBeAdded.getId()));
		assertEquals(coll.getTweetCount(), previousLength+1);
	}

	// TODO Create a method to find a tweet that is not contained in the class
	@Test
	public void testRemoveTweet() {
		TweetCollection coll = this.getTestTweets();
		long tweetToBeRemovedId = coll.getRandomId();
		long idNotContainedInCollection = 432783;
		int previousLength = coll.getTweetCount();
		coll.removeTweet(tweetToBeRemovedId);
		assertFalse(coll.containsTweet(tweetToBeRemovedId));
		assertEquals(coll.getTweetCount(), previousLength-1);
		assertEquals(coll.removeTweet(idNotContainedInCollection), null);
	}

	@Test
	public void testGetTweetById() {
		TweetCollection coll = this.getTestTweets();
		long tweetToBeRetrievedId = coll.getRandomId();
		Tweet tweetFromFile = TweetFileUtil.getTweetById(this.testFile.getPath(), tweetToBeRetrievedId);
		Tweet tweetFromCollection = null;
		try {
			tweetFromCollection = coll.getTweetById(tweetToBeRetrievedId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// May need to add an assertNotNull here
		assertTrue(tweetFromFile.equals(tweetFromCollection));
	}

	// TODO Factor out casting
	@Test
	public void testGetAllTweetIds() {
		TweetCollection coll = this.getTestTweets();
		HashSet<Long> idsFromFile = TweetFileUtil.getAllIds(this.testFile.getPath());
		HashSet<Long> idsFromCollection = new HashSet<Long>(coll.getAllTweetIds());
		assertTrue(idsFromCollection.equals(idsFromFile));
	}

	@Test
	public void testGetTweetsByUser() {	
		TweetCollection coll = this.getTestTweets();
		String randomAuthor = coll.getRandomAuthor();
		ArrayList<Tweet> tweetsByAuthor = coll.getTweetsByAuthor(randomAuthor);
		for (Tweet tweet : tweetsByAuthor) {
			assertTrue(tweet.getAuthor().equals(randomAuthor));
		}
	}

	/**
	 * 
	 * This test grabs a random tweet id from the collection
	 * then it replaces the tweet in the collection with a 
	 * new tweet. 
	 * The old tweet's information should be replaced by the 
	 * new tweet's information.
	 */
	@Test
	public void testDuplicateTweetFunctionality() {
		TweetCollection coll = this.getTestTweets();
		Long alreadyExistingTweetId = coll.getRandomId();
		Tweet previousTweet = null;
		try {
			previousTweet = coll.getTweetById(alreadyExistingTweetId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		Tweet tweetToReplace = this.testFile.generateNewTweet();
		Tweet newTweet = null;
		tweetToReplace.setId(alreadyExistingTweetId);
		coll.addTweet(tweetToReplace);

		try {
			newTweet = coll.getTweetById(alreadyExistingTweetId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(coll.containsTweet(alreadyExistingTweetId));
		assertEquals(previousTweet.getId(), tweetToReplace.getId());
		assertNotEquals(previousTweet.getText(), newTweet.getText());
	}
	
	@Test
	public void testRandomFunctionsFromTweetCollection() {
		TweetCollection coll = this.getTestTweets();
		String randomAuthor = coll.getRandomAuthor();
		assertTrue(coll.containsAuthor(randomAuthor));
		long randomId = coll.getRandomId();
		assertTrue(coll.containsTweet(randomId));
	}
	
	private TweetCollection getTestTweets() {
		return TweetFileUtil.getTweetsFromFile(this.testFile.getPath());
	}
}
