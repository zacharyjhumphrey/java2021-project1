package test;

import org.testng.TestNG;

public class Main {
	/**
	 * Without the testng extension, you can't see the output of the tests, 
	 * but if you run this file, you should see that 7/7 tests are passing
	 * in the console
	 * @param args
	 */
	public static void main(String[] args) {
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { TweetCollectionTest.class });
		testng.run();
	}
}
