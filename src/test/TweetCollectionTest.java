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

public class TweetCollectionTest {
	private TestFile testFile;
	
	@BeforeSuite
	public void beforeSuite() {
		testFile = new TestFile();
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
		Tweet tweetFromCollection = coll.getTweetById(tweetToBeRetrievedId);
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

	@Test
	public void testDuplicateTweetFunctionality() {
		TweetCollection coll = this.getTestTweets();
		Long alreadyExistingTweetId = coll.getRandomId();
		Tweet previousTweet = coll.getTweetById(alreadyExistingTweetId);
		Tweet tweetToReplace = this.testFile.generateNewTweet();
		tweetToReplace.setId(alreadyExistingTweetId);
		coll.addTweet(tweetToReplace);
		assertTrue(coll.containsTweet(alreadyExistingTweetId));
		assertEquals(previousTweet.getId(), tweetToReplace.getId());
		assertNotEquals(previousTweet.getText(), coll.getTweetById(alreadyExistingTweetId).getText());
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
