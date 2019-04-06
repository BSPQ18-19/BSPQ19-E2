package es.deusto.spq.client.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HotelCreate extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel idLabel, nameLabel, locationLabel, servicesLabel, seasonStartLabel, seasonEndingLabel, roomLabel;
	private TextField idTextField, nameTextField, locationTextField, servicesTextField, roomTextField;
	private TextField seasonStartTextField, seasonEndingTextField;
	
	public HotelCreate() {
		
		this.setLayout(new GridLayout(12, 1));
		
		idLabel = new JLabel("ID");
		idLabel.setFont(new Font(idLabel.getName(), Font.PLAIN, 25));
		
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

//		roomLabel = new JLabel("Season ending (YYYY-MM-DD)");
//		roomLabel.setFont(new Font(roomLabel.getName(), Font.PLAIN, 25));
		
		idTextField = new TextField("", 20);
		idTextField.setFont(new Font(idTextField.getName(), Font.PLAIN, 25));
		
		nameTextField = new TextField("", 20);
		nameTextField.setFont(new Font(nameTextField.getName(), Font.PLAIN, 25));
		
		locationTextField = new TextField("", 20);
		locationTextField.setFont(new Font(locationTextField.getName(), Font.PLAIN, 25));
		
		servicesTextField = new TextField("", 20);
		servicesTextField.setFont(new Font(servicesTextField.getName(), Font.PLAIN, 25));
		
		seasonStartTextField = new TextField("", 20);
		seasonStartTextField.setFont(new Font(seasonStartTextField.getName(), Font.PLAIN, 25));
		
		seasonEndingTextField = new TextField("", 20);
		seasonEndingTextField.setFont(new Font(seasonEndingTextField.getName(), Font.PLAIN, 25));
//		
//		roomTextArea = new TextField(20);
//		roomTextArea.setFont(new Font(roomTextArea.getName(), Font.PLAIN, 25));
		
		this.add(idLabel);
		this.add(idTextField);
		this.add(nameLabel);
		this.add(nameTextField);
		this.add(locationLabel);
		this.add(locationTextField);
		this.add(servicesLabel);
		this.add(servicesTextField);
		this.add(seasonStartLabel);
		this.add(seasonStartTextField);
		this.add(seasonEndingLabel);			
		this.add(seasonEndingTextField);
//		this.add(roomLabel);			
//		this.add(roomTextArea);
	}

	
	public String getIdTextFieldText() {
		return idTextField.getText();
	}

	public String getNameTextFieldText() {
		return nameTextField.getText();
	}

	public String getLocationTextFieldText() {
		return locationTextField.getText();
	}

	public String getServicesTextFieldText() {
		return servicesTextField.getText();
	}

//	public String getRoomTextField() {
//		return roomTextArea.getText();
//	}

	public String getSeasonStartTextFieldText() {
		return seasonStartTextField.getText();
	}

	public String getSeasonEndingTextFieldText() {
		return seasonEndingTextField.getText();
	}


	public TextField getIdTextField() {
		return idTextField;
	}


	public TextField getNameTextField() {
		return nameTextField;
	}


	public TextField getLocationTextField() {
		return locationTextField;
	}
	
	public TextField getServicesTextField() {
		return servicesTextField;
	}


	public TextField getSeasonStartTextField() {
		return seasonStartTextField;
	}


	public TextField getSeasonEndingTextField() {
		return seasonEndingTextField;
	}
	
	
	
}
