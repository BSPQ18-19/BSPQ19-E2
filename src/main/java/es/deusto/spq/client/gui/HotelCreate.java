package es.deusto.spq.client.gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HotelCreate extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel, locationLabel, servicesLabel, seasonStartLabel, seasonEndingLabel, roomLabel;
	private JTextArea nameTextArea, locationTextArea, servicesTextArea, roomTextArea;
	private JTextArea seasonStartTextArea, seasonEndingTextArea;
	
	public HotelCreate() {
		
		this.setLayout(new GridLayout(12, 1));
		
		nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font(nameLabel.getName(), Font.PLAIN, 25));
		
		locationLabel = new JLabel("Location");
		locationLabel.setFont(new Font(locationLabel.getName(), Font.PLAIN, 25));
		
		servicesLabel = new JLabel("Services");
		servicesLabel.setFont(new Font(servicesLabel.getName(), Font.PLAIN, 25));
		
		seasonStartLabel = new JLabel("Season start (YYYY-MM-DD)");
		seasonStartLabel.setFont(new Font(seasonStartLabel.getName(), Font.PLAIN, 25));
		
		seasonEndingLabel = new JLabel("Season ending (YYYY-MM-DD)");
		seasonEndingLabel.setFont(new Font(seasonEndingLabel.getName(), Font.PLAIN, 25));

		roomLabel = new JLabel("Season ending (YYYY-MM-DD)");
		roomLabel.setFont(new Font(roomLabel.getName(), Font.PLAIN, 25));
		
		nameTextArea = new JTextArea(1, 20);
		nameTextArea.setFont(new Font(nameTextArea.getName(), Font.PLAIN, 25));
		
		locationTextArea = new JTextArea(1, 20);
		locationTextArea.setFont(new Font(locationTextArea.getName(), Font.PLAIN, 25));
		
		servicesTextArea = new JTextArea(1, 20);
		servicesTextArea.setFont(new Font(servicesTextArea.getName(), Font.PLAIN, 25));
		
		seasonStartTextArea = new JTextArea(1, 20);
		seasonStartTextArea.setFont(new Font(seasonStartTextArea.getName(), Font.PLAIN, 25));
		
		seasonEndingTextArea = new JTextArea(1, 20);
		seasonEndingTextArea.setFont(new Font(seasonEndingTextArea.getName(), Font.PLAIN, 25));
		
		roomTextArea = new JTextArea(1, 20);
		roomTextArea.setFont(new Font(roomTextArea.getName(), Font.PLAIN, 25));
		
		this.add(nameLabel);
		this.add(nameTextArea);
		this.add(locationLabel);
		this.add(locationTextArea);
		this.add(servicesLabel);
		this.add(servicesTextArea);
		this.add(seasonStartLabel);
		this.add(seasonStartTextArea);
		this.add(seasonEndingLabel);			
		this.add(seasonEndingTextArea);
		this.add(roomLabel);			
		this.add(roomTextArea);
	}

	public String getNameTextArea() {
		return nameTextArea.getText();
	}

	public String getLocationTextArea() {
		return locationTextArea.getText();
	}

	public String getServicesTextArea() {
		return servicesTextArea.getText();
	}

	public String getRoomTextArea() {
		return roomTextArea.getText();
	}

	public String getSeasonStartTextArea() {
		return seasonStartTextArea.getText();
	}

	public String getSeasonEndingTextArea() {
		return seasonEndingTextArea.getText();
	}
	
	
}
