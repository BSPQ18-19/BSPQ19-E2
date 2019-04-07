package es.deusto.spq.client.gui;
import java.awt.*;
import javax.swing.*;

import es.deusto.spq.client.Client;
import es.deusto.spq.client.controller.HotelManagementController;

public class ClientWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static ClientWindow clientWindow;
	private ScreenType currentScreenType;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private JPanel mainPanel;
	

	// private constructor using lazy singleton
	private ClientWindow(HotelManagementController controller) {
		this.controller = controller;
		
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
	
	public void changeScreen(ScreenType nextScreenType) {
		this.currentScreenType = nextScreenType;
		
		switch(nextScreenType) {
		case VIEW_HOTEL_ADMIN:
			mainPanel = new HotelView(screenWidth, screenHeight, controller);
			break;
		case CREATE_HOTEL_ADMIN:
			mainPanel = new HotelCreate(screenWidth, screenHeight, controller);
			break;
		default:
			break;
		}
		this.setContentPane(mainPanel);
		this.revalidate();
	}

	// lazy singleton
	public static ClientWindow getClientWindow(HotelManagementController controller) {
		if (clientWindow == null)
			clientWindow = new ClientWindow(controller);
		return clientWindow;
	}

	public HotelManagementController getController() {
		return controller;
	}
}
