package main;

//import org.testng.annotations.Test;

public class Main {

	// TODO JavaDoc everything
	public static void main(String[] args) {
		TweetCollection coll = TweetFileUtil.getTweetsFromFile("src/data/test.txt");
		TweetFileUtil.writeCollectionToFile(coll);
	}

}

