package es.deusto.spq.client.gui.views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.logger.ClientLogger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import es.deusto.spq.server.data.dto.HotelDTO;

/** Panel for seeing and editing all the hotels in the db
 * @author gonzalo
 *
 */
public class ViewHotel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable hotelsTable;
	private JScrollPane tableScrollPane;
	private JButton	confirm;
	private JButton	createHotel, viewHotel, editHotel, deleteHotel;
	private JButton registerAdmin;
	private JPanel upperButtons, centerPanel, bottomPanel;
	private int screenWidth, screenHeight;
	private Logger log;
	private ClientWindowAdmin clientWindowAdmin;

	public ViewHotel(int screenWidth, int screenHeight, ClientWindowAdmin clientWindowAdmin) {
		log = ClientLogger.getLogger();
		
		this.clientWindowAdmin = clientWindowAdmin;
		
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
		
		createHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.button.create"));
		createHotel.setSize(100, 30);
		createHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowAdmin.changeScreen(ScreenTypeAdmin.CREATE_HOTEL_ADMIN);
				confirm.setEnabled(true);
				
			}
		});
		
		viewHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.button.view"));
		viewHotel.setSize(100, 30);
		viewHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowAdmin.changeScreen(ScreenTypeAdmin.VIEW_HOTEL_ADMIN);			
			}
		});
		
		editHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.button.edit"));
		editHotel.setSize(100, 30);
		editHotel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowAdmin.getController().cleanHotelsDB();
				for(int i = 0; i < hotelsTable.getRowCount(); i++) {
					clientWindowAdmin.getController().createHotel((String) hotelsTable.getValueAt(i, 0),
							(String) hotelsTable.getValueAt(i, 1), 
							(String) hotelsTable.getValueAt(i, 2),
							(String) hotelsTable.getValueAt(i, 3), 
							(String) hotelsTable.getValueAt(i, 4));
				}
			}
		});
		
		deleteHotel = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.button.delete"));
		deleteHotel.setSize(100, 30);
		deleteHotel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hotelsTable.getSelectedRow() != -1) {
					String id = (String) (hotelsTable.getValueAt(hotelsTable.getSelectedRow(), 0));
					if(clientWindowAdmin.getController().deleteHotel(id)) {
						JOptionPane.showMessageDialog(null, "Hotel deleted", "Done", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Select an hotel", "Error", JOptionPane.ERROR_MESSAGE);
				}

				
			}
		});
		
		confirm = new JButton(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.button.confirm"));
		confirm.setSize(100, 30);
		confirm.setBackground(Color.GREEN);
		
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
		
		hotelsTable = new JTable();
		tableModel = (DefaultTableModel) hotelsTable.getModel();
		hotelsTable.setSize(700, 100);
		hotelsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tableModel.addColumn("Id");
		tableModel.addColumn(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.table.label.name"));
		tableModel.addColumn(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.table.label.location"));
		tableModel.addColumn(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.table.label.seasonStart"));
		tableModel.addColumn(clientWindowAdmin.getAdminView().getViewManager().getClient().getLocaleManager().getMessage("view.table.label.seasonEnding"));
		
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
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.add(tableScrollPane, BorderLayout.CENTER);
		
		clientWindowAdmin.getController().setCurrentHotels();
		clientWindowAdmin.getController().getCurrentHotels();
		ArrayList<HotelDTO> retrievedHotels = clientWindowAdmin.getController().retrieveHotels();
		if(retrievedHotels == null || retrievedHotels.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no hotels available", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
			clientWindowAdmin.getController().setCurrentHotels();
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
					seasonStartDate = "0" + (hotel.getSeasonStart().getDate());
				}else {
					seasonStartDate = "" + (hotel.getSeasonStart().getDate());
				}
				if((hotel.getSeasonEnding().getMonth()+1) < 10) {
					seasonEndingMonth = "0" + (hotel.getSeasonEnding().getMonth()+1); 
				}else {
					seasonEndingMonth = "" + (hotel.getSeasonEnding().getMonth()+1); 
				}
				if((hotel.getSeasonEnding().getDate()+1) < 10) {
					seasonEndingDate = "0" + (hotel.getSeasonEnding().getDate());
				}else {
					seasonEndingDate = "" + (hotel.getSeasonEnding().getDate());
				}
				tableModel.addRow(new String[] {hotel.getHotelId(), hotel.getName(), hotel.getLocation(),
						String.valueOf(hotel.getSeasonStart().getYear() + 1900)
						+ "-" + seasonStartMonth 
						+ "-" + seasonStartDate,
						String.valueOf(hotel.getSeasonEnding().getYear() + 1900)
						+ "-" + seasonEndingMonth
						+ "-" + seasonEndingDate});

				clientWindowAdmin.getController().setCurrentHotels(hotel);
			}
		}
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
