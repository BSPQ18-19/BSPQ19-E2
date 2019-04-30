package es.deusto.spq.client.gui.views.admin;
import java.awt.*;
import javax.swing.*;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.locale.LocaleManager;

import org.apache.log4j.Logger;

/** Main frame of the admin user
 * @author gonzalo
 *
 */
public class ClientWindowAdmin extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private static ClientWindowAdmin clientWindow;
	private ScreenTypeAdmin currentScreenType;
	private int screenWidth, screenHeight;
	private HotelManagementController controller;
	private JPanel mainPanel;
	private Logger log;
	private HotelAdminView adminView;

	
	// private constructor using lazy singleton
	public ClientWindowAdmin(HotelAdminView adminView) {
		log = ClientLogger.getLogger();
		
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
	
	public HotelAdminView getAdminView() {
		return adminView;
	}


	public HotelManagementController getController() {
		return controller;
	}
}
