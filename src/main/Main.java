package main;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World!");

		TweetCollection coll = TweetFileReader.getTweetsFromFile("src/data/training.txt");
	}

}

