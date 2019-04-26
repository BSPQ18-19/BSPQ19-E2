package es.deusto.spq.client.gui.views.admin;
import java.awt.*;
import javax.swing.*;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.controller.HotelManagementController;
import org.apache.log4j.Logger;

public class ClientWindow extends JInternalFrame {

	private HotelAdminView adminView;
	private ClientWindow clientWindow;
	private ScreenType currentScreenType;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private JPanel mainPanel;
	private Logger log;

	ClientWindow(HotelAdminView adminView) {

		this.adminView = adminView;

		log = ClientLogger.getLogger();

		this.controller = adminView.getViewManager().getClient().getController();
		
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
			mainPanel = new ViewHotel(screenWidth, screenHeight, this);
			break;
		case CREATE_HOTEL_ADMIN:
			mainPanel = new CreateHotel(screenWidth, screenHeight, this);
			break;
		default:
			break;
		}
		this.setContentPane(mainPanel);
		this.revalidate();
	}

	public HotelManagementController getController() {
		return controller;
	}

	public HotelAdminView getAdminView() {
		return adminView;
	}
}
