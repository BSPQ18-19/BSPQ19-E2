package es.deusto.spq.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.github.lgooddatepicker.components.CalendarPanel;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.HotelDTO;

public class HotelGuestSearchingPanel extends JPanel {

	private DefaultTableModel tableModel;
	private JTable hotelsTable;
	private JScrollPane tableScrollPane;
	private JButton	logout, confirm;
	private JButton	viewHotel;
	private JPanel upperButtons, southPanel;
	private int screenWidth, screenHeight;
	private Logger log;
	private CalendarPanel calendar;
	
	public HotelGuestSearchingPanel(int screenWidth, int screenHeight, HotelManagementController controller) {
		log = ClientLogger.getLogger();
		
		this.setLayout(new BorderLayout());
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		viewHotel = new JButton("View hotels");
		viewHotel.setSize(100, 30);
		viewHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calendar.getSelectedDate() == null) {
					JOptionPane.showMessageDialog(null, "Please select a date", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					controller.setCurrentHotels();
					ArrayList<HotelDTO> retrievedHotels = controller.retrieveHotels(calendar.getSelectedDate().toString());
					if(retrievedHotels == null || retrievedHotels.size() == 0) {
						JOptionPane.showMessageDialog(null, "There are no hotels available", "Error", JOptionPane.ERROR_MESSAGE);
						if(tableModel.getRowCount() != 0) {
							for(int i = tableModel.getRowCount()-1; i >= 0; i--) {
								tableModel.removeRow(i);
							}
						}
					}else {
						controller.setCurrentHotels();
						if(tableModel.getRowCount() != 0) {
							for(int i = tableModel.getRowCount()-1; i >= 0; i--) {
								tableModel.removeRow(i);
							}
						}
						
						for (HotelDTO hotel: retrievedHotels) {
							String seasonStartMonth = "";
							String seasonStartDate = "";
							String seasonEndingMonth = "";
							String seasonEndingDate = "";
							if((hotel.getSeasonStart().getMonth()+1) < 10) {
								seasonStartMonth = "0" + (hotel.getSeasonStart().getMonth()+1);
							}else {
								seasonStartMonth = "" + (hotel.getSeasonStart().getMonth()+1);
							}
							
							if((hotel.getSeasonStart().getDate()+1) < 10) {
								seasonStartDate = "0" + (hotel.getSeasonStart().getDate()+1);
							}else {
								seasonStartDate = "" + (hotel.getSeasonStart().getDate()+1);
							}
							if((hotel.getSeasonEnding().getMonth()+1) < 10) {
								seasonEndingMonth = "0" + (hotel.getSeasonEnding().getMonth()+1); 
							}else {
								seasonEndingMonth = "" + (hotel.getSeasonEnding().getMonth()+1); 
							}
							if((hotel.getSeasonEnding().getDate()+1) < 10) {
								seasonEndingDate = "0" + (hotel.getSeasonEnding().getDate()+1);
							}else {
								seasonEndingDate = "" + (hotel.getSeasonEnding().getDate()+1);
							}
							tableModel.addRow(new String[] {hotel.getHotelId(), hotel.getName(), hotel.getLocation(),
									String.valueOf(hotel.getSeasonStart().getYear() + 1900)
									+ "-" + seasonStartMonth 
									+ "-" + seasonStartDate,
									String.valueOf(hotel.getSeasonEnding().getYear() + 1900)
									+ "-" + seasonEndingMonth
									+ "-" + seasonEndingDate});

							controller.setCurrentHotels(hotel);
						}
					}
				}
			}
		});
		
		confirm = new JButton("Confirm");
		confirm.setSize(100, 30);
		confirm.setBackground(Color.GREEN);
				
		logout = new JButton("Log out");
		logout.setSize(100, 30);
		logout.setBackground(Color.white);
		
		upperButtons = new JPanel();
		upperButtons.setBackground(Color.LIGHT_GRAY);
		upperButtons.add(viewHotel);
		upperButtons.add(confirm);
		upperButtons.add(logout);
		
		calendar = new CalendarPanel();
		calendar.setSize(600, 200);
		
		hotelsTable = new JTable();
		tableModel = (DefaultTableModel) hotelsTable.getModel();
		hotelsTable.setSize(700, 100);
		hotelsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tableModel.addColumn("Id");
		tableModel.addColumn("Name");
		tableModel.addColumn("Location");
		tableModel.addColumn("Season start");
		tableModel.addColumn("Season end");
		
		hotelsTable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {		
				hotelsTable.setDefaultRenderer(Object.class, new MyTableCellRenderer() );
				log.info(hotelsTable.getValueAt(hotelsTable.getSelectedRow(),0) + "");
			}
		});
		
		tableScrollPane = new JScrollPane(hotelsTable);
		tableScrollPane.setSize(hotelsTable.getWidth() , 100);
		tableScrollPane.setLocation((int) (screenWidth / 2.05 - tableScrollPane.getWidth() / 2), (int) (screenHeight / 3 - tableScrollPane.getHeight() / 2));
		
		this.add(upperButtons, BorderLayout.NORTH);
		this.add(calendar, BorderLayout.SOUTH);
		this.add(tableScrollPane, BorderLayout.CENTER);
	}
	
	static class MyTableCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (isSelected)
			{
				this.setOpaque(true);
				this.setBackground(Color.RED);
			} else {
				this.setBackground(Color.white);
			}

			return this;
		}
	}
	
}
