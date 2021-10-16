package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Main {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;

	public static void main(String[] args) {

		new Main();

	}

	Main() {
		JFrame frame = new JFrame("tweets.vc");
		frame.setLayout(new BorderLayout());

		MainPanel mainPanel = new MainPanel();
		frame.add(mainPanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);

		frame.addWindowListener(new WindowListener() {
		    public void windowClosing(WindowEvent e) {
		    	System.out.println("I am poor");
		        mainPanel.getTweets().save();
		        System.exit(0);
		    }

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
