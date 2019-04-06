package es.deusto.spq.client.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class AdminRegisterGUI {

	private JFrame frame;
	private JTextField tFName;
	private JTextField tFPassword;
	private JTextField tFAddress;
	private JTextField tFEmail;
	private JTextField tFPhone;
	
	HotelManagementController hm;
	
	/**
	 * Create the application.
	 */
	public AdminRegisterGUI(HotelManagementController controller) {
		hm = controller;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 530, 294);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblRegister = new JLabel("Admin Register");
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_lblRegister = new GridBagConstraints();
		gbc_lblRegister.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegister.gridx = 0;
		gbc_lblRegister.gridy = 0;
		frame.getContentPane().add(lblRegister, gbc_lblRegister);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 2;
		gbc_panel_4.gridy = 0;
		frame.getContentPane().add(panel_4, gbc_panel_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		frame.getContentPane().add(panel_2, gbc_panel_2);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(0, 3, 47, 14);
		panel_2.add(lblName);
		
		tFName = new JTextField();
		tFName.setBounds(57, 0, 150, 20);
		panel_2.add(tFName);
		tFName.setColumns(10);
		
		JLabel lblNameError = new JLabel("Name must be at least 3 letters");
		lblNameError.setBounds(10, 22, 180, 14);
		lblNameError.setForeground(Color.RED);
		panel_2.add(lblNameError);
		lblNameError.setVisible(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(0, 3, 44, 14);
		panel.add(lblEmail);
		
		tFEmail = new JTextField();
		tFEmail.setBounds(61, 0, 156, 20);
		panel.add(tFEmail);
		tFEmail.setColumns(10);
		
		JLabel lblEmailError = new JLabel("Enter email");
		lblEmailError.setBounds(10, 22, 134, 14);
		lblEmailError.setForeground(Color.RED);
		panel.add(lblEmailError);
		lblEmailError.setVisible(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(0, 3, 66, 14);
		panel_1.add(lblPassword);
		
		tFPassword = new JPasswordField();
		tFPassword.setBounds(60, 0, 151, 20);
		panel_1.add(tFPassword);
		tFPassword.setColumns(10);
		
		JLabel lblPasswordError = new JLabel("Password must be at least 5 digits");
		lblPasswordError.setBounds(10, 28, 201, 14);
		lblPasswordError.setForeground(Color.RED);
		panel_1.add(lblPasswordError);
		lblPasswordError.setVisible(false);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 2;
		gbc_panel_3.gridy = 2;
		frame.getContentPane().add(panel_3, gbc_panel_3);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(0, 3, 46, 14);
		panel_3.add(lblPhone);
		
		tFPhone = new JTextField();
		tFPhone.setBounds(66, 0, 151, 20);
		panel_3.add(tFPhone);
		tFPhone.setColumns(10);
		
		JLabel lblPhoneError = new JLabel("Phone have to be at least 9 digits");
		lblPhoneError.setBounds(10, 22, 194, 14);
		lblPhoneError.setForeground(Color.RED);
		panel_3.add(lblPhoneError);
		lblPhoneError.setVisible(false);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.gridwidth = 2;
		gbc_panel_5.insets = new Insets(0, 0, 0, 5);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 3;
		frame.getContentPane().add(panel_5, gbc_panel_5);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(0, 3, 59, 14);
		panel_5.add(lblAddress);
		
		tFAddress = new JTextField();
		tFAddress.setBounds(69, 0, 145, 20);
		panel_5.add(tFAddress);
		tFAddress.setColumns(10);
		
		JLabel lblAddressError = new JLabel("Enter address");
		lblAddressError.setBounds(10, 28, 86, 14);
		lblAddressError.setForeground(Color.RED);
		panel_5.add(lblAddressError);
		lblAddressError.setVisible(false);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean correct = true;
				lblNameError.setVisible(false);
				lblEmailError.setVisible(false);
				lblPasswordError.setVisible(false);
				lblPhoneError.setVisible(false);
				lblAddressError.setVisible(false);
				
				String name = tFName.getText();
				if(name.length() < 3) {
					correct = false;
					lblNameError.setVisible(true);
				}
				String email = tFEmail.getText();
				if(email.isEmpty()) {
					correct = false;
					lblEmailError.setVisible(true);
				}
				String password = tFPassword.getText();
				if(password.length() < 5) {
					correct = false;
					lblPasswordError.setVisible(true);
				}
				String phone = tFPhone.getText();
				if(phone.length() < 5) {
					correct = false;
					lblPhoneError.setVisible(true);
				}
				String address = tFAddress.getText();
				if(address.length() < 5) {
					correct = false;
					lblAddressError.setVisible(true);
				}
				if(correct) {
					//TODO Call the register method  and close window
				}
			}
		});
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegister.gridx = 2;
		gbc_btnRegister.gridy = 3;
		frame.getContentPane().add(btnRegister, gbc_btnRegister);
	}

}
