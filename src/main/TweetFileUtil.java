package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// TODO Move external jars into their own folders
// TODO Doc me
public abstract class TweetFileUtil {
	private static final String OUTPUT_DIR = "src/data/";

	public static TweetCollection getTweetsFromFile(String pathToInput) {
		TweetCollection coll = new TweetCollection();
		HashSet<Long> dups = new HashSet<Long>();

		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(pathToInput));
			while ((line = br.readLine()) != null) {
				Tweet parsedTweet = TweetFileUtil.parseTweet(line);

				if (coll.containsTweet(parsedTweet.getId())) {
					dups.add(parsedTweet.getId());
					continue;
				}

				coll.addTweet(parsedTweet);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Long dupId : dups) {
			coll.removeTweet(dupId);
		}

		return coll;
	}

	public static Tweet getTweetById(String pathToInput, long id) {
		String line = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(pathToInput));
			while ((line = br.readLine()) != null) {
				Tweet parsedTweet = TweetFileUtil.parseTweet(line);
				
				if (parsedTweet.getId() == id) {
					br.close();
					return parsedTweet;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static HashSet<Long> getAllIds(String pathToFile) {
		HashSet<Long> idSet = new HashSet<Long>();
		HashSet<Long> hasDupsInFile = new HashSet<Long>();
		String line = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(pathToFile));
			
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				Long id = Long.parseLong(data[1]);
				
				if (idSet.contains(id)) {
					hasDupsInFile.add(id);
					continue;
				}
				
				idSet.add(id);
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Long dup : hasDupsInFile) {
			idSet.remove(dup);
		}
		
		return idSet;
	}

	public static void writeCollectionToFile(TweetCollection coll) {
		TweetFileUtil.writeCollectionToFile(coll, OUTPUT_DIR + coll.getName() + ".txt");
	}

	public static void writeCollectionToFile(TweetCollection coll, String dir) {
		ArrayList<Tweet> tweets = coll.getAllTweets();
		
		try {
			FileWriter fw = new FileWriter(dir);

			for (Tweet tweet : tweets) {
				fw.write(tweet.toString());
			}

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Tweet parseTweet(String input) {
		String[] data = input.split(",");
		Polarity polarity = Polarity.valueOf(data[0].charAt(0));
		long id = Long.parseLong(data[1]);
		String authorName = data[2];
		String text = data[3];

		Tweet newTweet = new Tweet();
		newTweet.setPolarity(polarity);
		newTweet.setId(id);
		newTweet.setAuthor(authorName);
		newTweet.setText(text);

		return newTweet;
	}

	public static ArrayList<Tweet> getTweetsByAuthor(String pathToInput, String author) {
		HashSet<Long> dups = new HashSet<Long>();
		HashMap<Long, Tweet> authorTweets = new HashMap<Long, Tweet>();

		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(pathToInput));
			while ((line = br.readLine()) != null) {
				Tweet parsedTweet = TweetFileUtil.parseTweet(line);
				
				if (!parsedTweet.getAuthor().equals(author)) {
					continue;
				}
				
				if (authorTweets.containsKey(parsedTweet.getId())) {
					dups.add(parsedTweet.getId());
					continue;
				}
				
				authorTweets.put(parsedTweet.getId(), parsedTweet);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Long dupId : dups) {
			authorTweets.remove(dupId);
		}

		return new ArrayList<Tweet>(authorTweets.values());
	}
	
	public static HashSet<Long> getTweetIdsByAuthor(String pathToInput, String author) {
		HashSet<Long> ids = new HashSet<>();
		ArrayList<Tweet> idsFromFile = TweetFileUtil.getTweetsByAuthor(pathToInput, author);
		
		for (Tweet tweet : idsFromFile) {
			ids.add(tweet.getId());
		}
		
		return ids;
	}
}
