package es.deusto.spq.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import es.deusto.spq.client.Client;

public class HotelAdminPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton	logout, confirm;
	private JButton	createHotel, viewHotel, editHotel, deleteHotel;
	private JPanel upperButtons, centerPanel;
	private int screenWidth, screenHeight;
	private Client client;

	public HotelAdminPanel(int screenWidth, int screenHeight, Client client) {
		
		this.client = client;
		
		this.setLayout(new BorderLayout());
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		createHotel = new JButton("New hotel");
		createHotel.setSize(100, 30);
		createHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeHotelScreen(HotelPanelType.CREATE);
				confirm.setEnabled(true);
				
			}
		});
		
		viewHotel = new JButton("View hotels");
		viewHotel.setSize(100, 30);
		viewHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeHotelScreen(HotelPanelType.VIEW);
				confirm.setEnabled(true);
				
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
		confirm.setEnabled(false);
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(centerPanel instanceof HotelCreate) {
					if(((HotelCreate) centerPanel).getNameTextArea().equals("")
							|| ((HotelCreate) centerPanel).getLocationTextArea().equals("")
							|| ((HotelCreate) centerPanel).getServicesTextArea().equals("")
							|| ((HotelCreate) centerPanel).getRoomTextArea().equals("")
							|| ((HotelCreate) centerPanel).getSeasonStartTextArea().equals("")
							|| ((HotelCreate) centerPanel).getSeasonEndingTextArea().equals("")) {
						JOptionPane.showMessageDialog(null, "Please fill everything.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(centerPanel instanceof HotelView) {
					System.out.println("2");
				}
				
			}
		});
				
		logout = new JButton("Log out");
		logout.setSize(100, 30);
		logout.setBackground(Color.white);
		
		upperButtons = new JPanel();
		upperButtons.setBackground(Color.LIGHT_GRAY);
		upperButtons.add(createHotel);
		upperButtons.add(viewHotel);
		upperButtons.add(editHotel);
		upperButtons.add(deleteHotel);
		upperButtons.add(confirm);
		upperButtons.add(logout);
		
		this.add(upperButtons, BorderLayout.NORTH);
		
	}
	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		testFrame.add(new HotelAdminPanel(800, 600, null));
		testFrame.setVisible(true);
	}
	
	public void changeHotelScreen(HotelPanelType nextScreenType) {
		
		switch(nextScreenType) {
		case CREATE:
			centerPanel = new HotelCreate();
			break;
		case VIEW:
			centerPanel = new HotelView(this.screenWidth, this.screenHeight-upperButtons.getHeight(), client);
			break;
		default:
			break;
		}
		this.add(centerPanel, BorderLayout.CENTER);
		this.revalidate();
	}
}
