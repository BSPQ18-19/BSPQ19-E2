package es.deusto.spq.client.gui.views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import es.deusto.spq.client.logger.ClientLogger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import es.deusto.spq.client.Client;
import es.deusto.spq.client.controller.HotelManagementController;

public class CreateHotel extends JPanel {

	private JLabel idLabel, nameLabel, locationLabel, seasonStartLabel, seasonEndingLabel;
	private TextField idTextField, nameTextField, locationTextField;
	private TextField seasonStartTextField, seasonEndingTextField;
	private JButton	logout, confirm;
	private JButton	createHotel, viewHotel, editHotel, deleteHotel;
	private JPanel upperButtons, centerPanel;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private Logger log;
	private ClientWindow clientWindow;
	
	public CreateHotel(int screenWidth, int screenHeight, ClientWindow clientWindow) {

		this.clientWindow = clientWindow;

		log = ClientLogger.getLogger();
		
		this.setLayout(new BorderLayout());
		
		idLabel = new JLabel("ID");
		idLabel.setFont(new Font(idLabel.getName(), Font.PLAIN, 25));
		
		nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font(nameLabel.getName(), Font.PLAIN, 25));
		
		locationLabel = new JLabel("Location");
		locationLabel.setFont(new Font(locationLabel.getName(), Font.PLAIN, 25));
		
		seasonStartLabel = new JLabel("Season start (YYYY-MM-DD hh:mm:ss)");
		seasonStartLabel.setFont(new Font(seasonStartLabel.getName(), Font.PLAIN, 25));
		
		seasonEndingLabel = new JLabel("Season ending (YYYY-MM-DD hh:mm:ss)");
		seasonEndingLabel.setFont(new Font(seasonEndingLabel.getName(), Font.PLAIN, 25));
		
		idTextField = new TextField("", 20);
		idTextField.setFont(new Font(idTextField.getName(), Font.PLAIN, 25));
		
		nameTextField = new TextField("", 20);
		nameTextField.setFont(new Font(nameTextField.getName(), Font.PLAIN, 25));
		
		locationTextField = new TextField("", 20);
		locationTextField.setFont(new Font(locationTextField.getName(), Font.PLAIN, 25));
		
		seasonStartTextField = new TextField("", 20);
		seasonStartTextField.setFont(new Font(seasonStartTextField.getName(), Font.PLAIN, 25));
		
		seasonEndingTextField = new TextField("", 20);
		seasonEndingTextField.setFont(new Font(seasonEndingTextField.getName(), Font.PLAIN, 25));
		
		
		this.controller = clientWindow.getController();
		
		this.setLayout(new BorderLayout());
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		createHotel = new JButton("New hotel");
		createHotel.setSize(100, 30);
		createHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindow.changeScreen(ScreenType.CREATE_HOTEL_ADMIN);
				confirm.setEnabled(true);
				
			}
		});
		
		viewHotel = new JButton("View hotels");
		viewHotel.setSize(100, 30);
		viewHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindow.changeScreen(ScreenType.VIEW_HOTEL_ADMIN);
			}
		});
		
		editHotel = new JButton("Edit hotel");
		editHotel.setSize(100, 30);
		editHotel.setEnabled(false);
		
		deleteHotel = new JButton("Delete hotel");
		deleteHotel.setSize(100, 30);
		deleteHotel.setEnabled(false);
		
		confirm = new JButton("Confirm");
		confirm.setSize(100, 30);
		confirm.setBackground(Color.GREEN);
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(idTextField.getText().equals("")
						|| nameTextField.getText().equals("")
						|| locationTextField.getText().equals("")
						|| seasonStartTextField.getText().equals("")
						|| seasonEndingTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please fill everything.", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					controller.createHotel(idTextField.getText(), nameTextField.getText(), locationTextField.getText(),
							Timestamp.valueOf(seasonStartTextField.getText()),
							Timestamp.valueOf(seasonEndingTextField.getText()));
					JOptionPane.showMessageDialog(null, "Hotel created", "Done", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
				
		logout = new JButton("Log out");
		logout.setSize(100, 30);
		logout.setBackground(Color.white);

		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindow.dispose();
			}
		});
		
		upperButtons = new JPanel();
		upperButtons.setBackground(Color.LIGHT_GRAY);
		upperButtons.add(createHotel);
		upperButtons.add(viewHotel);
		upperButtons.add(editHotel);
		upperButtons.add(deleteHotel);
		upperButtons.add(confirm);
		upperButtons.add(logout);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(12, 1));
				
		centerPanel.add(idLabel);
		centerPanel.add(idTextField);
		centerPanel.add(nameLabel);
		centerPanel.add(nameTextField);
		centerPanel.add(locationLabel);
		centerPanel.add(locationTextField);
		centerPanel.add(seasonStartLabel);
		centerPanel.add(seasonStartTextField);
		centerPanel.add(seasonEndingLabel);			
		centerPanel.add(seasonEndingTextField);
		
		this.add(upperButtons, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}	
}
