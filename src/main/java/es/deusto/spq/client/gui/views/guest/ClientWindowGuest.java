package es.deusto.spq.client.gui.views.guest;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.views.admin.CreateHotel;
import es.deusto.spq.client.gui.views.admin.HotelAdminView;
import es.deusto.spq.client.gui.views.admin.ScreenTypeAdmin;
import es.deusto.spq.client.gui.views.admin.ViewHotel;
import es.deusto.spq.client.logger.ClientLogger;

/** Main frame of the guest user
 * @author gonzalo
 *
 */
public class ClientWindowGuest extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	private static ClientWindowGuest clientWindow;
	private ScreenTypeGuest currentScreenType;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private JPanel mainPanel;
	private Logger log;
	private HotelGuestView guestView;

	// private constructor using lazy singleton
	public ClientWindowGuest(HotelGuestView guestView) {
		log = ClientLogger.getLogger();
		
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
	 */
	public void changeScreen(ScreenTypeGuest nextScreenType) {
		this.currentScreenType = nextScreenType;
		
		switch(nextScreenType) {
	
		case GUEST_SEARCH:
			mainPanel = new HotelGuestSearchingPanel(screenWidth, screenHeight, this);
			break;
		default:
			break;
		}
		this.setContentPane(mainPanel);
		this.revalidate();
	}

	public HotelGuestView getGuestView() {
		return guestView;
	}

	public HotelManagementController getController() {
		return controller;
	}

}