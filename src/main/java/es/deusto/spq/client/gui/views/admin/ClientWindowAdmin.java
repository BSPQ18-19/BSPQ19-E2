package es.deusto.spq.client.gui.views.admin;
import java.awt.*;
import javax.swing.*;

import es.deusto.spq.client.controller.HotelManagementController;

/** Main frame of the admin user
 * @author gonzalo
 *
 */
public class ClientWindowAdmin extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	/**
	 * screenWidth Width of the current screen
	 * screenHeight Height of the current screen
	 */
	private int screenWidth, screenHeight;
	/**
	 * controller Instance of class HotelManagementController
	 */
	private HotelManagementController controller;
	/**
	 * mainPanel The main panel of the window
	 */
	private JPanel mainPanel;
	/**
	 * adminView Instance of class HotelAdminView
	 */
	private HotelAdminView adminView;

	
	/** Private constructor using lazy singleton
	 * @param adminView Reference to class HotelAdminView
	 */
	public ClientWindowAdmin(HotelAdminView adminView) {
		
		this.adminView = adminView;
		this.controller = adminView.getViewManager().getClient().getController();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle(adminView.getViewManager().getClient().getLocaleManager().getMessage("windowAdmin.title"));
		Dimension windowSize = new Dimension((int) (screenSize.getWidth() / 1.3), (int) (screenSize.getHeight() / 1.3));
		this.setSize(windowSize);
		mainPanel = (JPanel) this.getContentPane();

		this.screenWidth = (int) windowSize.getWidth();
		this.screenHeight = (int) windowSize.getHeight();
		
		changeScreen(ScreenTypeAdmin.VIEW_HOTEL_ADMIN);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/** Change the UI of the Admin panel
	 * @param nextScreenType Type of the next screen that is wanted to be displayed
	 */
	public void changeScreen(ScreenTypeAdmin nextScreenType) {
		
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
	
	/** Retrieve the admin view
	 * @return HotelAdminView object
	 */
	public HotelAdminView getAdminView() {
		return adminView;
	}


	/** Return the controller
	 * @return HotelManagementController object
	 */
	public HotelManagementController getController() {
		return controller;
	}
}
