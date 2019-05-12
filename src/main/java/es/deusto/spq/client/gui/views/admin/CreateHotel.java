package es.deusto.spq.client.gui.views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import es.deusto.spq.client.logger.ClientLogger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import es.deusto.spq.client.Client;
import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.gui.views.auth.RegisterAdminView;

/** Panel for creating hotels as an admin
 * @author gonzalo
 *
 */
public class CreateHotel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel idLabel, nameLabel, locationLabel, seasonStartLabel, seasonEndingLabel;
	private TextField idTextField, nameTextField, locationTextField;
	private TextField seasonStartTextField, seasonEndingTextField;
	private JButton	confirm;
	private JButton	createHotel, viewHotel, editHotel, deleteHotel;
	private JButton registerAdmin;
	private JPanel upperButtons, centerPanel, bottomPanel;
	private int screenWidth, screenHeight;
	private Logger log;
	private ClientWindowAdmin clientWindowAdmin;
	
	public CreateHotel(int screenWidth, int screenHeight, ClientWindowAdmin clientWindowAdmin) {
		log = ClientLogger.getLogger();
		
		this.setLayout(new BorderLayout());
		
		this.clientWindowAdmin = clientWindowAdmin;
		idLabel = new JLabel("ID");
		idLabel.setFont(new Font(idLabel.getName(), Font.PLAIN, 25));
		
		nameLabel = new JLabel(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.label.name"));
		nameLabel.setFont(new Font(nameLabel.getName(), Font.PLAIN, 25));
		
		locationLabel = new JLabel(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.label.location"));
		locationLabel.setFont(new Font(locationLabel.getName(), Font.PLAIN, 25));
		
		seasonStartLabel = new JLabel(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.label.seasonStart"));
		seasonStartLabel.setFont(new Font(seasonStartLabel.getName(), Font.PLAIN, 25));
		
		seasonEndingLabel = new JLabel(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.label.seasonEnding"));
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
		
		this.setLayout(new BorderLayout());
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		registerAdmin = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.button.register"));
		registerAdmin.setSize(100, 30);
		registerAdmin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowAdmin.getAdminView().getViewManager().openView(ViewFactory.buildView(ViewType.REGISTER_ADMINISTRATOR, clientWindowAdmin.getAdminView().getViewManager()));
			}
		});

		createHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.button.create"));
		createHotel.setSize(100, 30);
		createHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowAdmin.changeScreen(ScreenTypeAdmin.CREATE_HOTEL_ADMIN);
				confirm.setEnabled(true);
				
			}
		});
		
		viewHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.button.view"));
		viewHotel.setSize(100, 30);
		viewHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowAdmin.changeScreen(ScreenTypeAdmin.VIEW_HOTEL_ADMIN);		
			}
		});
		
		editHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.button.edit"));
		editHotel.setSize(100, 30);
		editHotel.setEnabled(false);
		
		deleteHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.button.delete"));
		deleteHotel.setSize(100, 30);
		deleteHotel.setEnabled(false);
		
		confirm = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("create.button.confirm"));
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
					clientWindowAdmin.getController().createHotel(idTextField.getText(), nameTextField.getText(), locationTextField.getText(),
							seasonStartTextField.getText(), seasonEndingTextField.getText());
					JOptionPane.showMessageDialog(null, "Hotel created", "Done", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		upperButtons = new JPanel();
		upperButtons.setBackground(Color.LIGHT_GRAY);
		upperButtons.add(createHotel);
		upperButtons.add(viewHotel);
		upperButtons.add(editHotel);
		upperButtons.add(deleteHotel);
		upperButtons.add(confirm);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.LIGHT_GRAY);
		bottomPanel.add(registerAdmin);
		
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
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.add(centerPanel, BorderLayout.CENTER);
	}	
}
