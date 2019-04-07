package es.deusto.spq.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.UserDTO;

import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField tFEmail;
	private JTextField tFPassword;
	private final JPanel panel_3 = new JPanel();
	private RegisterWindow registerWindowFrame;
	private Logger log;
	private HotelManagementController controller;

	/**
	 * Create the application.
	 */
	public Login(HotelManagementController controller) {
		log = ClientLogger.getLogger();
		initialize();
		frame.setVisible(true);
		this.controller = controller;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 223, 231);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 3;
		gbc_panel_2.gridy = 1;
		frame.getContentPane().add(panel_2, gbc_panel_2);
		
		JLabel lblLogin = new JLabel("Login");
		panel_2.add(lblLogin);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 3;
		gbc_panel_1.gridy = 2;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		
		JLabel lblEmail = new JLabel("Email");
		panel_1.add(lblEmail);
		
		tFEmail = new JTextField();
		panel_1.add(tFEmail);
		tFEmail.setColumns(10);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 3;
		frame.getContentPane().add(panel, gbc_panel);
		
		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword);
		
		tFPassword = new JPasswordField();
		panel.add(tFPassword);
		tFPassword.setColumns(10);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridx = 3;
		gbc_panel_3.gridy = 4;
		frame.getContentPane().add(panel_3, gbc_panel_3);
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//This trigers when login in
				String email = tFEmail.getText();
				String password = tFPassword.getText();
				
				try {
					controller.logIn(email, password);
				} catch (RemoteException e) {
					log.info("Remote exception trying to create a UserDTO");

				}
				UserDTO loggedUser = controller.getLoggedUser();
				if(loggedUser == null)
					JOptionPane.showMessageDialog(frame, "Incorrect Password or UserID", "Login Error", JOptionPane.ERROR_MESSAGE);
				else {
					if(loggedUser.isGuest())
						;//TODO guest GUI
					else
						;//TODO admin GUI
				}
			}
		});
		panel_3.add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (registerWindowFrame == null) {
					registerWindowFrame = new RegisterWindow(controller);
				} else {
					registerWindowFrame.show();
				}
			}
		});
		panel_3.add(btnRegister);
	}

}
