package es.deusto.spq.client.gui.views.guest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.github.lgooddatepicker.components.CalendarPanel;

import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewType;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.HotelDTO;

/** Panel for searching all the hotels as a guest user
 * @author gonzalo
 *
 */
public class HotelGuestSearchingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Model of the table
	 */
	private DefaultTableModel tableModel;
	/**
	 * Table where the hotels will be displayed
	 */
	private JTable hotelsTable;
	/**
	 * Scroll pane for the table
	 */
	private JScrollPane tableScrollPane;
	/**
	 * confirm Confirm button
	 */
	private JButton confirm;
	/**
	 * viewHotel Button to open the panel for viewing all the hotels
	 */
	private JButton	viewHotel;
	/**
	 * The button to access the update user view
	 */
	private JButton updateUser;
	/**
	 * upperButtons Panel for the buttons at the top
	 */
	private JPanel upperButtons;
	/**
	 * Client logger
	 */
	private Logger log;
	/**
	 * Calendar to choose the date to arrive to the hotel
	 */
	private CalendarPanel calendar;
	
	/** Constructor of the class HotelGuestSearchingPanel
	 * @param screenWidth Width of the window
	 * @param screenHeight Height of the window
	 * @param clientWindowGuest Reference to ClientWindowGuest class
	 */
	public HotelGuestSearchingPanel(int screenWidth, int screenHeight, ClientWindowGuest clientWindowGuest) {
		log = ClientLogger.getLogger();
		
		this.setLayout(new BorderLayout());
		
		viewHotel = new JButton(clientWindowGuest.getGuestView().getViewManager().getClient().getLocaleManager().getMessage("search.button.view"));
		viewHotel.setSize(100, 30);
		viewHotel.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calendar.getSelectedDate() == null) {
					JOptionPane.showMessageDialog(null, "Please select a date", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					clientWindowGuest.getController().setCurrentHotels();
					List<HotelDTO> retrievedHotels = clientWindowGuest.getController().retrieveHotels(calendar.getSelectedDate().toString());
					if(retrievedHotels == null || retrievedHotels.size() == 0) {
						JOptionPane.showMessageDialog(null, "There are no hotels available", "Error", JOptionPane.ERROR_MESSAGE);
						if(tableModel.getRowCount() != 0) {
							for(int i = tableModel.getRowCount()-1; i >= 0; i--) {
								tableModel.removeRow(i);
							}
						}
					}else {
						clientWindowGuest.getController().setCurrentHotels();
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

							clientWindowGuest.getController().setCurrentHotels(hotel);
						}
					}
				}
			}
		});
		
		confirm = new JButton(clientWindowGuest.getGuestView().getViewManager().getClient().getLocaleManager().getMessage("search.button.confirm"));
		confirm.setSize(100, 30);
		confirm.setBackground(Color.GREEN);
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hotelsTable.getSelectedRow() != -1) {
					clientWindowGuest.changeScreen(ScreenTypeGuest.ROOM_PANEL, (String)hotelsTable.getValueAt(hotelsTable.getSelectedRow(), 0), calendar.getSelectedDate().toString());
				}
				else {
					JOptionPane.showMessageDialog(null, "There are no rooms available for that hotel", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		updateUser = new JButton(clientWindowGuest.getGuestView().getViewManager().getClient().getLocaleManager().getMessage("search.button.updateUser"));
		updateUser.setSize(100, 30);
		updateUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowGuest.getGuestView().getViewManager().openView(ViewFactory.buildView(ViewType.EDIT_USER, clientWindowGuest.getGuestView().getViewManager()));
			}
		});

		upperButtons = new JPanel();
		upperButtons.setBackground(Color.LIGHT_GRAY);
		upperButtons.add(viewHotel);
		upperButtons.add(confirm);
		upperButtons.add(updateUser);
		
		calendar = new CalendarPanel();
		calendar.setSize(600, 200);
		
		hotelsTable = new JTable();
		tableModel = (DefaultTableModel) hotelsTable.getModel();
		hotelsTable.setSize(700, 100);
		hotelsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tableModel.addColumn("Id");
		tableModel.addColumn(clientWindowGuest.getGuestView().getViewManager().getClient().getLocaleManager().getMessage("search.table.label.name"));
		tableModel.addColumn(clientWindowGuest.getGuestView().getViewManager().getClient().getLocaleManager().getMessage("search.table.label.location"));
		tableModel.addColumn(clientWindowGuest.getGuestView().getViewManager().getClient().getLocaleManager().getMessage("search.table.label.seasonStart"));
		tableModel.addColumn(clientWindowGuest.getGuestView().getViewManager().getClient().getLocaleManager().getMessage("search.table.label.seasonEnding"));
		
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
