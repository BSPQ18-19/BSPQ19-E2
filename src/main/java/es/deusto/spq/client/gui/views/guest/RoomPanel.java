package es.deusto.spq.client.gui.views.guest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.RoomDTO;

public class RoomPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Model of the table
	 */
	private DefaultTableModel tableModel;
	/**
	 * Table where the rooms will be displayed
	 */
	private JTable roomsTable;
	/**
	 * Scroll pane for the table
	 */
	private JScrollPane tableScrollPane;
	/**
	 * confirm Confirm button
	 * back Return to the HotelGuestSearchingPanel
	 */
	private JButton	confirm, back;
	/**
	 * upperButtons Panel for the buttons at the top
	 */
	private JPanel upperButtons;
	/**
	 * Client logger
	 */
	private Logger log;
	
	/**
	 * Random object
	 */
	private Random r;
	
	/** Constructor of the class RoomPanel
	 * @param screenWidth Width of the window
	 * @param screenHeight Height of the window
	 * @param clientWindowGuest Reference to ClientWindowGuest class
	 * @param hotelId id of the hotel
	 * @param calendarDate date selected in the calendar
	 */
	public RoomPanel(int screenWidth, int screenHeight, ClientWindowGuest clientWindowGuest, String hotelId, String calendarDate) {
		log = ClientLogger.getLogger();
			
		this.setLayout(new BorderLayout());
						
		r =  new Random();
		
		confirm = new JButton("room.button.confirm");
		confirm.setSize(100, 30);
		confirm.setBackground(Color.GREEN);
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roomsTable.getSelectedRow() != -1) {
					RoomDTO roomDTO = clientWindowGuest.getController().retrieveRoomById((String)roomsTable.getValueAt(roomsTable.getSelectedRow(), 0));
					String daysForRoom = JOptionPane.showInputDialog(new JTextField("", 10), "room.inputDialog.nights", JOptionPane.QUESTION_MESSAGE);
					if(!(daysForRoom == "")) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
						LocalDate localDateStart = LocalDate.parse(calendarDate.trim(), formatter);
						LocalDate localDateEnding = localDateStart.plusDays(Integer.valueOf(daysForRoom));
						float finalPrize = Integer.parseInt(daysForRoom) * roomDTO.getPrice();
						
						UserPayView view;
						clientWindowGuest.getGuestView().getViewManager().openView(view = (UserPayView) ViewFactory.buildView(ViewType.MAKE_PAYMENT, clientWindowGuest.getGuestView().getViewManager()));
						view.setLocalDateEnding(localDateEnding);
						view.setLocalDateStart(localDateStart);
						view.setPrize(finalPrize);
						view.setRoomDTO(roomDTO);
						
						clientWindowGuest.changeScreen(ScreenTypeGuest.GUEST_SEARCH);
					}
				}else {
					JOptionPane.showMessageDialog(null, "room.messageDialog.errorRoomSelection", "room.error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
				
		back = new JButton("room.button.back");
		back.setSize(100, 30);
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientWindowGuest.changeScreen(ScreenTypeGuest.GUEST_SEARCH);
			}
		});
		
		upperButtons = new JPanel();
		upperButtons.setBackground(Color.LIGHT_GRAY);
		upperButtons.add(confirm);
		upperButtons.add(back);
		
		
		roomsTable = new JTable();
		tableModel = (DefaultTableModel) roomsTable.getModel();
		roomsTable.setSize(700, 100);
		roomsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tableModel.addColumn("Id");
		tableModel.addColumn("room.column.size");
		tableModel.addColumn("room.column.type");
		tableModel.addColumn("room.column.price");
		
		roomsTable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {		
				roomsTable.setDefaultRenderer(Object.class, new MyTableCellRenderer() );
				log.info(roomsTable.getValueAt(roomsTable.getSelectedRow(),0) + "");
			}
		});
		
		
		clientWindowGuest.getController().setCurrentRooms();
		ArrayList<RoomDTO> retrievedRooms = clientWindowGuest.getController().retrieveRoomsByHotelId(hotelId);
		if(retrievedRooms == null || retrievedRooms.size() == 0) {
			JOptionPane.showMessageDialog(null, "room.messageDialog.noRooms", "room.error", JOptionPane.ERROR_MESSAGE);
			if(tableModel.getRowCount() != 0) {
				for(int i = tableModel.getRowCount()-1; i >= 0; i--) {
					tableModel.removeRow(i);
				}
			}
		}else {
			clientWindowGuest.getController().setCurrentRooms();
			if(tableModel.getRowCount() != 0) {
				for(int i = tableModel.getRowCount()-1; i >= 0; i--) {
					tableModel.removeRow(i);
				}
			}
			
			for (RoomDTO room: retrievedRooms) {
				if(room.isOccupied() == false) {
					tableModel.addRow(new String[] {room.getRoomID(),
							String.valueOf(room.getSize()),
							String.valueOf(room.getType()),
							String.valueOf(room.getPrice())
					});
				}
				clientWindowGuest.getController().setCurrentRooms(room);
			}
		}
		
		
		tableScrollPane = new JScrollPane(roomsTable);
		tableScrollPane.setSize(roomsTable.getWidth() , 100);
		tableScrollPane.setLocation((int) (screenWidth / 2.05 - tableScrollPane.getWidth() / 2), (int) (screenHeight / 3 - tableScrollPane.getHeight() / 2));
		
		this.add(upperButtons, BorderLayout.NORTH);
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
