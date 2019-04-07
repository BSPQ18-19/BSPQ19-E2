package es.deusto.spq.client.gui;
import java.awt.*;
import javax.swing.*;

import es.deusto.spq.client.Client;

public class ClientWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static ClientWindow clientWindow;
	private ScreenType currentScreenType;
	private int screenWidth, screenHeight;
	private Client client;
	private JPanel mainPanel;
	

	// private constructor using lazy singleton
	private ClientWindow(Client client) {
		this.client = client;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("Hotel client");
		Dimension windowSize = new Dimension((int) (screenSize.getWidth() / 1.3), (int) (screenSize.getHeight() / 1.3));
		this.setSize(windowSize);
		this.setLocationRelativeTo(null);
		mainPanel = (JPanel) this.getContentPane();

		this.screenWidth = (int) windowSize.getWidth();
		this.screenHeight = (int) windowSize.getHeight();
		
		changeScreen(ScreenType.VIEW_HOTEL_ADMIN);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void changeScreen(ScreenType nextScreenType, String... strings) {
		this.currentScreenType = nextScreenType;
		
		switch(nextScreenType) {
		case VIEW_HOTEL_ADMIN:
			mainPanel = new HotelView(screenWidth, screenHeight, client);
			break;
		case CREATE_HOTEL_ADMIN:
			mainPanel = new HotelCreate(screenWidth, screenHeight, client);
			break;
		default:
			break;
		}
		this.setContentPane(mainPanel);
		this.revalidate();
	}

	// lazy singleton
	public static ClientWindow getClientWindow(Client client) {
		if (clientWindow == null)
			clientWindow = new ClientWindow(client);
		return clientWindow;
	}

	public Client getClient() {
		return client;
	}
}
