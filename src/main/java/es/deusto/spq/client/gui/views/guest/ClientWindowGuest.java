package es.deusto.spq.client.gui.views.guest;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.views.admin.ClientWindowAdmin;
import es.deusto.spq.client.gui.views.admin.CreateHotel;
import es.deusto.spq.client.gui.views.admin.ScreenTypeAdmin;
import es.deusto.spq.client.gui.views.admin.ViewHotel;
import es.deusto.spq.client.logger.ClientLogger;

public class ClientWindowGuest extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	private static ClientWindowGuest clientWindow;
	private ScreenTypeGuest currentScreenType;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private JPanel mainPanel;
	private Logger log;
	

	// private constructor using lazy singleton
	private ClientWindowGuest(HotelManagementController controller) {
		log = ClientLogger.getLogger();
		
		this.controller = controller;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("Hotel client");
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
	 */
	public void changeScreen(ScreenTypeGuest nextScreenType) {
		this.currentScreenType = nextScreenType;
		
		switch(nextScreenType) {
	
		case GUEST_SEARCH:
			mainPanel = new HotelGuestSearchingPanel(screenWidth, screenHeight, controller);
			break;
		default:
			break;
		}
		this.setContentPane(mainPanel);
		this.revalidate();
	}

	// lazy singleton
	public static ClientWindowGuest getClientWindow(HotelManagementController controller) {
		if (clientWindow == null)
			clientWindow = new ClientWindowGuest(controller);
		return clientWindow;
	}

	public HotelManagementController getController() {
		return controller;
	}

}
