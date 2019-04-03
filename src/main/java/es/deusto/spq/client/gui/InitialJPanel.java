package es.deusto.spq.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class InitialJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel;
	private JLabel infoLabel;
	private JLabel authorLabel;
	private JButton logInButton;
	
	public InitialJPanel(int screenWidth, int screenHeight) {
		
		this.setLayout(null);
		
		titleLabel = new JLabel("Hotel Management", SwingConstants.CENTER);
		titleLabel.setSize(screenWidth / 2, screenHeight / 2);
		titleLabel.setLocation((int) (screenWidth / 2 - titleLabel.getWidth()/ 2), (int) (screenHeight / 4 - titleLabel.getHeight() / 2));
		SPQG2Util.fixJLabelFontSize(titleLabel);
				
		infoLabel = new JLabel("Welcome. Please sign in.", SwingConstants.CENTER);
		infoLabel.setSize((int) (screenWidth / 3.3), screenHeight / 6);
		infoLabel.setLocation(screenWidth / 2 - infoLabel.getWidth() / 2, 
				(int) (titleLabel.getLocation().getY() + titleLabel.getFont().getSize() + screenHeight / 6));
		SPQG2Util.fixJLabelFontSize(infoLabel);
		
		authorLabel = new JLabel("SPQ Group 3", SwingConstants.CENTER);
		authorLabel.setSize(screenWidth / 8, screenHeight / 15);
		authorLabel.setLocation((int) (screenWidth / 1.25 - authorLabel.getWidth() / 2),
				(int) (screenHeight / 1.25 - authorLabel.getHeight() / 2));	
		SPQG2Util.fixJLabelFontSize(authorLabel);
		
		logInButton = new JButton("Log in");		
		logInButton.setSize(screenWidth / 5, screenHeight / 7);
		logInButton.setLocation(screenWidth / 2 - logInButton.getWidth() / 2, 
				(int) (infoLabel.getLocation().getY() + infoLabel.getFont().getSize() + screenHeight / 8));
		SPQG2Util.fixJButtonFontSize(logInButton);
		logInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientWindow.getClientWindow(null).changeScreen(ScreenType.LOG_IN);
			}
		});
		
		this.add(titleLabel);
		this.add(infoLabel);
		this.add(authorLabel);
		this.add(logInButton);
	}
	
	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		testFrame.add(new InitialJPanel(800, 600));
		testFrame.setVisible(true);
	}

}
