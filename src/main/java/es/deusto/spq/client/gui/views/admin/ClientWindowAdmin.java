package es.deusto.spq.client.gui.views.admin;
import java.awt.*;
import javax.swing.*;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.views.guest.HotelGuestSearchingPanel;

import org.apache.log4j.Logger;

public class ClientWindowAdmin extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private static ClientWindowAdmin clientWindow;
	private ScreenTypeAdmin currentScreenType;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private JPanel mainPanel;
	private Logger log;
	

	// private constructor using lazy singleton
	private ClientWindowAdmin(HotelManagementController controller) {
		log = ClientLogger.getLogger();
		
		this.controller = controller;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("Hotel client");
		Dimension windowSize = new Dimension((int) (screenSize.getWidth() / 1.3), (int) (screenSize.getHeight() / 1.3));
		this.setSize(windowSize);
		mainPanel = (JPanel) this.getContentPane();

		this.screenWidth = (int) windowSize.getWidth();
		this.screenHeight = (int) windowSize.getHeight();
		
		changeScreen(ScreenTypeAdmin.VIEW_HOTEL_ADMIN);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void changeScreen(ScreenTypeAdmin nextScreenType) {
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
	public static ClientWindowAdmin getClientWindow(HotelManagementController controller) {
		if (clientWindow == null)
			clientWindow = new ClientWindowAdmin(controller);
		return clientWindow;
	}

	public HotelManagementController getController() {
		return controller;
	}
}
