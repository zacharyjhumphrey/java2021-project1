package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Polarity;
import main.PolarityPredictor;
import main.Tweet;
import main.TweetCollection;
import main.TweetFileUtil;

public class MainPanel extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 5275890363047819126L;
	private TweetCollection tweets;
	private JPanel moreFunctions;
	private JLabel appHeader;
	private SearchTweetsPanel searchPanel;
	public FilterState filter;
	private JButton moreFunctionsButton;
	private int moreFunctionsSliderValue;

	private JPanel filterPanel;

	private final int WIDTH = 800, HEIGHT = 500;

	private JButton getMoreFunctionsButton() {
		return moreFunctionsButton;
	}

	public TweetCollection getTweets() {
		return this.tweets;
	}

	public SearchTweetsPanel getSearchPanel() {
		return this.searchPanel;
	}

	public int getMoreFunctionsSliderValue() {
		return this.moreFunctionsSliderValue;
	}

	public void setMoreFunctionsSliderValue(int moreFunctionsSliderValue) {
		this.moreFunctionsSliderValue = moreFunctionsSliderValue;
	}

	// -----------------------------------------------------------------
	// Sets up the panel, including the timer for the animation.
	// -----------------------------------------------------------------
	public MainPanel() {
		super(new BorderLayout());

		tweets = TweetFileUtil.getTweetsFromFile("src/data/YourTweetCollection.txt");
		filter = new FilterState();
		moreFunctionsSliderValue = 1;

		appHeader = new JLabel("Tweet Vibe Check");
		filterPanel = this.createFilterPanel();
		searchPanel = this.createSearchPanel();

		moreFunctionsButton = this.createAdditionalTweetFunctionalityButton();
		moreFunctions = this.createAdditionalTweetFunctionalityPanel();

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(new Color(29, 161, 242));

		this.positionPanels();
	}

	/**
	 * Creates a search panel for the user to search and see results
	 * 
	 * @return SearchTweetsPanel - search panel
	 */
	private SearchTweetsPanel createSearchPanel() {
		SearchTweetsPanel panel = new SearchTweetsPanel(tweets, filter);
		panel.setPreferredSize(new Dimension(600, 500));
		return panel;
	}

	/**
	 * Positions panels on the panel
	 */
	private void positionPanels() {
		add(appHeader, BorderLayout.NORTH);
		add(filterPanel, BorderLayout.WEST);
		add(searchPanel, BorderLayout.EAST);
		add(moreFunctions, BorderLayout.SOUTH);
	}

	/**
	 * Creates a button that has multiple functionalities Depending on the slider
	 * value, the button 0 -> delete 1 -> add 2 -> predict
	 * 
	 * @return JButton - button with multiple functions
	 */
	private JButton createAdditionalTweetFunctionalityButton() {
		JButton button = new JButton("ADD TWEET");
		MainPanel thisPanel = this;

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int moreFunctionsValue = thisPanel.getMoreFunctionsSliderValue();
				if (moreFunctionsValue == 0) {
					thisPanel.openDeleteTweetDialog();
				} else if (moreFunctionsValue == 1) {
					thisPanel.openAddTweetDialog();
				} else {
					thisPanel.openPredictTweetDialog();
				}
			}
		});

		return button;
	}

	/**
	 * Opens a dialog where the user can enter ids and delete tweets Tweet ids must:
	 * not be blank, not be comprised of spaces, be found in the tweet collection
	 * and be capable of being parsed into a long Authors must: not be blank AND not
	 * be comprised of spaces Text must: not be blank AND not be comprised of spaces
	 * Polarity must: be 0, 2 or 4
	 */
	private void openAddTweetDialog() {
		MainPanel thisPanel = this;
		JDialog dialog = new JDialog();
		JTextField idField = new JTextField();
		JTextField authorField = new JTextField();
		JTextField textField = new JTextField();
		JTextField polarityField = new JTextField();
		JButton submitButton = new JButton();

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String author = authorField.getText();
				String idText = idField.getText();
				String text = textField.getText();
				String polarityText = polarityField.getText();
				long id;
				int polarity;

				if (author.strip().equals("") || idText.strip().equals("") || text.strip().equals("")
						|| polarityText.strip().equals("")) {
					thisPanel.createTextDialog("Fields must have values");
					return;
				}

				try {
					id = Long.parseLong(idText);
				} catch (Exception err) {
					thisPanel.createTextDialog("ID is incorrect");
					return;
				}

				try {
					polarity = Integer.parseInt(polarityText);
				} catch (Exception err) {
					thisPanel.createTextDialog("Polarity is incorrect");
					return;
				}

				if (!(polarity == 0 || polarity == 2 || polarity == 4)) {
					thisPanel.createTextDialog("Polarity must be 0, 2 or 4");
					return;
				}

				if (thisPanel.getTweets().containsTweet(id)) {
					thisPanel.createTextDialog("ID already contained in collection");
					return;
				}

				Tweet newTweet = new Tweet();
				newTweet.setAuthor(authorField.getText());
				newTweet.setId(id);
				newTweet.setPolarity(Polarity.valueOf(polarity));
				newTweet.setText(textField.getText());
				thisPanel.getTweets().addTweet(newTweet);
				thisPanel.createTextDialog("Tweet has been added");
				thisPanel.getSearchPanel().search();
			}
		});

		dialog.setLayout(new GridLayout(0, 1));
		dialog.add(new JLabel("ID"));
		dialog.add(idField);
		dialog.add(new JLabel("Author"));
		dialog.add(authorField);
		dialog.add(new JLabel("Text"));
		dialog.add(textField);
		dialog.add(new JLabel("Polarity"));
		dialog.add(polarityField);
		dialog.add(submitButton);

		dialog.setSize(500, 500);
		dialog.setVisible(true);
	}

	/**
	 * Creates a simple text dialog
	 * 
	 * @param text - text to be shown
	 */
	private void createTextDialog(String text) {
		JDialog dialog = new JDialog();
		dialog.add(new JLabel(text));
		dialog.setSize(200, 200);
		dialog.setVisible(true);

	}

	/**
	 * Opens a dialog where the user can enter ids and delete tweets Tweet ids must:
	 * not be blank, not be comprised of spaces, be found in the tweet collection
	 * and be capable of being parsed into a long
	 */
	private void openDeleteTweetDialog() {
		MainPanel thisPanel = this;
		JDialog dialog = new JDialog();
		JTextField idField = new JTextField();
		JButton submitButton = new JButton("Delete");

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String idText = idField.getText();
				long id;

				if (idText.strip().equals("")) {
					thisPanel.createTextDialog("Fields must have values");
					return;
				}

				try {
					id = Long.parseLong(idText);
				} catch (Exception err) {
					thisPanel.createTextDialog("ID is incorrect");
					return;
				}

				if (!thisPanel.getTweets().containsTweet(id)) {
					thisPanel.createTextDialog("Tweet cannot be found");
					return;
				}

				thisPanel.getTweets().removeTweet(id);
				thisPanel.createTextDialog("Tweet has been removed");
				thisPanel.getSearchPanel().search();
			}
		});

		dialog.setLayout(new GridLayout(0, 1));
		dialog.add(new JLabel("ID"));
		dialog.add(idField);
		dialog.add(submitButton);

		dialog.setSize(500, 500);
		dialog.setVisible(true);
	}

	/**
	 * Opens a dialog where the user can enter a tweet id for the program to predict
	 * the polarity of the tweet Tweet ids must: not be blank, not be comprised of
	 * spaces, be found in the tweet collection and be capable of being parsed into
	 * a long
	 */
	private void openPredictTweetDialog() {
		MainPanel thisPanel = this;
		JDialog dialog = new JDialog();
		JTextField idField = new JTextField();
		JButton submitButton = new JButton("Predict");

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String idText = idField.getText();
				long id;

				if (idText.strip().equals("")) {
					thisPanel.createTextDialog("Fields must have values");
					return;
				}

				try {
					id = Long.parseLong(idText);
				} catch (Exception err) {
					thisPanel.createTextDialog("ID is incorrect");
					return;
				}

				Tweet tweetToPredict;

				try {
					tweetToPredict = thisPanel.getTweets().getTweetById(id);
				} catch (Exception err) {
					thisPanel.createTextDialog("Tweet not found");
					return;
				}

				Polarity polarity = PolarityPredictor.predictOne(tweetToPredict);
				thisPanel.createTextDialog("Predicted polarity: " + polarity);
			}
		});

		dialog.setLayout(new GridLayout(0, 1));
		dialog.add(new JLabel("ID"));
		dialog.add(idField);
		dialog.add(submitButton);

		dialog.setSize(500, 500);
		dialog.setVisible(true);
	}

	/**
	 * Creates a panel that switches from predict -> add -> delete The slider inside
	 * of the panel switches between these modes
	 * 
	 * @return JPanel - functionality panel
	 */
	private JPanel createAdditionalTweetFunctionalityPanel() {
		JPanel panel = new JPanel();

		JSlider slider = this.createFunctionalitySlider();

		panel.add(slider);
		panel.add(this.moreFunctionsButton);

		return panel;
	}

	/**
	 * Slider that switches from predict -> add -> delete When the value is changed,
	 * the label of the button in the class is also changed
	 * 
	 * @return JSlider - functionality slider
	 */
	private JSlider createFunctionalitySlider() {
		MainPanel thisPanel = this;
		JSlider slider = new JSlider(0, 2);
		slider.setMajorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (slider.getValue() == 0) {
					thisPanel.getMoreFunctionsButton().setText("DELETE TWEET");
				} else if (slider.getValue() == 1) {
					thisPanel.getMoreFunctionsButton().setText("ADD TWEET");
				} else {
					thisPanel.getMoreFunctionsButton().setText("PREDICT TWEET");
				}
				thisPanel.setMoreFunctionsSliderValue(slider.getValue());
			}
		});

		return slider;
	}

	/**
	 * Creates a filter panel on the left side of the screen that allows the user to
	 * filter the results of a tweet collection search
	 * 
	 * Radio buttons: Text filter Author filter
	 * 
	 * Chechbox buttons: Negative polarity Neutral polarity Positive polarity
	 * 
	 * @return
	 */
	private JPanel createFilterPanel() {
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new GridLayout(0, 1));
		ButtonGroup searchFieldRadioGroup = new ButtonGroup();
		JRadioButton findByAuthorButton = this.createAuthorFilterButton();
		JRadioButton findByTweetText = this.createTextFilterButton();
		JCheckBox unhappyPolarity = this.createNegativeFilterButton();
		JCheckBox neutralPolarity = this.createNeutralFilterButton();
		JCheckBox happyPolarity = this.createPositiveFilterButton();

		filterPanel.setPreferredSize(new Dimension(WIDTH / 4, HEIGHT));
		filterPanel.add(new JLabel("Find by"));
		filterPanel.add(findByAuthorButton);
		filterPanel.add(findByTweetText);

		searchFieldRadioGroup.add(findByAuthorButton);
		searchFieldRadioGroup.add(findByTweetText);

		filterPanel.add(new JLabel("Filter Polarities"));
		filterPanel.add(unhappyPolarity);
		filterPanel.add(neutralPolarity);
		filterPanel.add(happyPolarity);

		return filterPanel;
	}

	/**
	 * Creates an author filter button If this button is turned on, text filter must
	 * be turned off
	 * 
	 * @return JRadioButton - author filter button
	 */
	private JRadioButton createAuthorFilterButton() {
		JRadioButton button = new JRadioButton("Author");
		FilterState filter = this.filter;

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter.setAuthor(button.isSelected());
				filter.setText(false);
			}
		});

		return button;
	}

	/**
	 * Creates an text filter button If this button is turned on, author filter must
	 * be turned off
	 * 
	 * @return JRadioButton - text filter button
	 */
	private JRadioButton createTextFilterButton() {
		JRadioButton button = new JRadioButton("Text");
		FilterState filter = this.filter;

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter.setText(button.isSelected());
				filter.setAuthor(false);
			}
		});

		return button;
	}

	/**
	 * Creates a checkbox that - when turned on - finds all tweets in a search with
	 * a negative polarity By default, this value is turned on
	 * 
	 * @return JCheckBox - negative filter checkbox
	 */
	private JCheckBox createNegativeFilterButton() {
		JCheckBox button = new JCheckBox("Negative", true);
		FilterState filter = this.filter;

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter.setNegativePolarity(button.isSelected());
			}
		});

		return button;
	}

	/**
	 * Creates a checkbox that - when turned on - finds all tweets in a search with
	 * a neutral polarity By default, this value is turned on
	 * 
	 * @return JCheckBox - neutral filter checkbox
	 */
	private JCheckBox createNeutralFilterButton() {
		JCheckBox button = new JCheckBox("Neutral", true);
		FilterState filter = this.filter;

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter.setNeutralPolarity(button.isSelected());
			}
		});

		return button;
	}

	/**
	 * Creates a checkbox that - when turned on - finds all tweets in a search with
	 * a positive polarity By default, this value is turned on
	 * 
	 * @return JCheckBox - positive filter checkbox
	 */
	private JCheckBox createPositiveFilterButton() {
		JCheckBox button = new JCheckBox("Positive", true);
		FilterState filter = this.filter;

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter.setPositivePolarity(button.isSelected());
			}
		});

		return button;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
