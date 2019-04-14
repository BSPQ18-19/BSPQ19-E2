package es.deusto.spq.client.gui.views.admin;
import java.awt.*;
import javax.swing.*;
import java.util.logging.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.controller.HotelManagementController;

public class ClientWindow extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private static ClientWindow clientWindow;
	private ScreenType currentScreenType;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private JPanel mainPanel;
	private Logger log;
	

	// private constructor using lazy singleton
	private ClientWindow(HotelManagementController controller) {
		log = ClientLogger.getLogger();
		
		this.controller = controller;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("Hotel client");
		Dimension windowSize = new Dimension((int) (screenSize.getWidth() / 1.3), (int) (screenSize.getHeight() / 1.3));
		this.setSize(windowSize);
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
			mainPanel = new ViewHotel(screenWidth, screenHeight, controller);
			break;
		case CREATE_HOTEL_ADMIN:
			mainPanel = new CreateHotel(screenWidth, screenHeight, controller);
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
