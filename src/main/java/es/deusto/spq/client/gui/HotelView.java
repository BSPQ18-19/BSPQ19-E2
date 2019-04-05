package es.deusto.spq.client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.sound.midi.ControllerEventListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import es.deusto.spq.client.Client;
import es.deusto.spq.server.data.dto.HotelDTO;

public class HotelView extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable hotelsTable;
	private JScrollPane tableScrollPane;
	
	public HotelView(int screenWidth, int screenHeight, Client client) {
		this.setLayout(null);
		
		hotelsTable = new JTable();
		tableModel = (DefaultTableModel) hotelsTable.getModel();
		hotelsTable.setSize(700, 100);
		hotelsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tableModel.addColumn("Name");
		tableModel.addColumn("Location");
		tableModel.addColumn("Services");
		tableModel.addColumn("Season start");
		tableModel.addColumn("Season end");
		
		hotelsTable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {		
				hotelsTable.setDefaultRenderer(Object.class, new MyTableCellRenderer() );
			}
		});
		
		tableScrollPane = new JScrollPane(hotelsTable);
		tableScrollPane.setSize(hotelsTable.getWidth() , 100);
		tableScrollPane.setLocation((int) (screenWidth / 2.05 - tableScrollPane.getWidth() / 2), (int) (screenHeight / 3 - tableScrollPane.getHeight() / 2));
		
		this.add(tableScrollPane);
		
		client.setCurrentHotels();
		client.getCurrentHotels();
		ArrayList<HotelDTO> retrievedHotels = client.retrieveHotels();
		if(retrievedHotels == null || retrievedHotels.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no hotels available", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
			client.setCurrentHotels();
			if(tableModel.getRowCount() != 0) {
				for(int i = tableModel.getRowCount()-1; i >= 0; i--) {
					tableModel.removeRow(i);
				}
			}
			
			for (HotelDTO hotel: retrievedHotels) {
				System.out.println(hotel.getName());
				
				tableModel.addRow(new String[] {hotel.getName(), hotel.getLocation(),
						"" , String.valueOf(hotel.getSeasonStart().getYear())
						+ "-" + String.valueOf(hotel.getSeasonStart().getMonth()) 
						+ "-" + String.valueOf(hotel.getSeasonStart().getDayOfMonth()),
						String.valueOf(hotel.getSeasonEnding().getYear())
						+ "-" + String.valueOf(hotel.getSeasonEnding().getMonth()) 
						+ "-" + String.valueOf(hotel.getSeasonEnding().getDayOfMonth())});

				client.setCurrentHotels(hotel);
			}
		}
		
		
		

//		tableModel.addColumn("Rooms");
		
		

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
	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		testFrame.add(new HotelView(800, 600, null));
		testFrame.setVisible(true);
	}
}
