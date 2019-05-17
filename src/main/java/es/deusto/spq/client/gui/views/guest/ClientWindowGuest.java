package es.deusto.spq.client.gui.views.guest;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;


import es.deusto.spq.client.controller.HotelManagementController;

/** Main frame of the guest user
 * @author gonzalo
 *
 */
public class ClientWindowGuest extends JInternalFrame{

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
	 * guestView Instance of class HotelGuestView
	 */
	private HotelGuestView guestView;

	/** Private constructor using lazy singleton
	 * @param guestView Reference to class HotelGuestView
	 */
	public ClientWindowGuest(HotelGuestView guestView) {
		
		this.guestView = guestView;
		this.controller = guestView.getViewManager().getClient().getController();;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle(guestView.getViewManager().getClient().getLocaleManager().getMessage("windowGuest.title"));
		Dimension windowSize = new Dimension((int) (screenSize.getWidth() / 1.3), (int) (screenSize.getHeight() / 1.3));
		this.setSize(windowSize);
		mainPanel = (JPanel) this.getContentPane();

		this.screenWidth = (int) windowSize.getWidth();
		this.screenHeight = (int) windowSize.getHeight();
		
		changeScreen(ScreenTypeGuest.GUEST_SEARCH);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/** Change the UI of the Guest panel
	 * @param nextScreenType Type of the next screen that is wanted to be displayed
	 * @param strings Strings needed. Can be 0
	 */
	public void changeScreen(ScreenTypeGuest nextScreenType, String... strings) {
		
		switch(nextScreenType) {
	
		case GUEST_SEARCH:
			mainPanel = new HotelGuestSearchingPanel(screenWidth, screenHeight, this);
			break;
		case ROOM_PANEL:
			mainPanel = new RoomPanel(screenWidth, screenHeight, this, strings[0], strings[1]);
			break;
		default:
			break;
		}
		this.setContentPane(mainPanel);
		this.revalidate();
	}

	/** Retrieve the guest view
	 * @return HotelGuestView object
	 */
	public HotelGuestView getGuestView() {
		return guestView;
	}

	/** Return the controller
	 * @return HotelManagementController object
	 */
	public HotelManagementController getController() {
		return controller;
	}

}
