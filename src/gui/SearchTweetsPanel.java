package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import main.Polarity;
import main.Tweet;
import main.TweetCollection;

public class SearchTweetsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public TweetCollection tweetCollection;
	private JPanel searchBar;
	private JTextField searchBarField;
	private JTextArea results;
	private FilterState filter;
	private JScrollPane resultsScroll;

	public SearchTweetsPanel(TweetCollection tweets, FilterState filter) {
		this.tweetCollection = tweets;
		this.filter = filter;
		this.searchBar = this.createSearchBarPanel();
		this.results = this.createResultsElement();
		this.resultsScroll = this.createResultsScroll();
	}

	/**
	 * Takes all of the tweets in the collection and searches them 
	 * based on this panel's filter
	 */
	public void search() {
		ArrayList<Tweet> allTweets = tweetCollection.getAllTweets();
		ArrayList<Tweet> negativeTweets = new ArrayList<Tweet>();
		ArrayList<Tweet> neutralTweets = new ArrayList<Tweet>();
		ArrayList<Tweet> positiveTweets = new ArrayList<Tweet>();

		if (filter.isAuthor()) {
			allTweets = TweetCollection.findTweetsWhoseAuthorContainsString(allTweets, this.searchBarField.getText());
		}

		if (filter.isText()) {
			allTweets = TweetCollection.findTweetsWhoseTextContainsString(allTweets, this.searchBarField.getText());
		}

		if (filter.isNegativePolarity()) {
			negativeTweets = TweetCollection.findTweetsWhosePolarityMatches(allTweets, Polarity.NEGATIVE);
		}

		if (filter.isNeutralPolarity()) {
			neutralTweets = TweetCollection.findTweetsWhosePolarityMatches(allTweets, Polarity.NEUTRAL);
		}

		if (filter.isPositivePolarity()) {
			positiveTweets = TweetCollection.findTweetsWhosePolarityMatches(allTweets, Polarity.POSITIVE);
		}

		if (filter.isNegativePolarity() || filter.isNeutralPolarity() || filter.isPositivePolarity()) {
			allTweets = new ArrayList<Tweet>(Stream.of(negativeTweets, neutralTweets, positiveTweets)
					.flatMap(Collection::stream).collect(Collectors.toList()));
		}

		String[] toReturn = new String[allTweets.size()];

		for (int i = 0; i < allTweets.size(); i++) {
			toReturn[i] = allTweets.get(i).toString();
		}

		this.updateResults(toReturn);
	}

	private JPanel createSearchBarPanel() {
		JPanel panel = new JPanel();
		this.searchBarField = new JTextField(30);
		JButton searchBtn = this.createSearchButton();

		panel.add(this.searchBarField);
		panel.add(searchBtn);

		this.add(panel);
		return panel;
	}

	/**
	 * Creates a search button and attaches an event that calls search() when it is clicked
	 * @return JButton - search button
	 */
	private JButton createSearchButton() {
		JButton button = new JButton("SEARCH");
		SearchTweetsPanel thisPanel = this;

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thisPanel.search();
			}
		});

		return button;
	}

	/**
	 * Creates a textarea that holds all of the search result tweets
	 * The textarea cannot be edited
	 * @return JTextArea - textarea
	 */
	private JTextArea createResultsElement() {
		JTextArea tweetTextArea = new JTextArea(
				this.joinAllTweetsIntoOneString(tweetCollection.getAllTweetsAsString()));
		tweetTextArea.setEditable(false);
		return tweetTextArea;
	}

	/**
	 * Creates a scroll pane for the search results to reside in
	 * @return
	 */
	private JScrollPane createResultsScroll() {
		JScrollPane scroll = new JScrollPane(this.results);
		scroll.setPreferredSize(new Dimension(400, 400));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll);
		return scroll;
	}

	private void updateResults(String[] tweets) {
		this.updateResults(this.joinAllTweetsIntoOneString(tweets));
	}

	private void updateResults(String tweets) {
		this.results.setText(tweets);
	}

	private String joinAllTweetsIntoOneString(String[] tweets) {
		return String.join("\n", tweets);
	}
}
