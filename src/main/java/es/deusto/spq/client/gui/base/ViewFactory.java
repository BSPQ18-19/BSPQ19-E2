package es.deusto.spq.client.gui.base;

import es.deusto.spq.client.gui.views.LocaleView;
import es.deusto.spq.client.gui.views.admin.HotelAdminView;
import es.deusto.spq.client.gui.views.auth.EditUserView;
import es.deusto.spq.client.gui.views.auth.LoginView;
import es.deusto.spq.client.gui.views.auth.RegisterAdminView;
import es.deusto.spq.client.gui.views.auth.RegisterView;
import es.deusto.spq.client.gui.views.guest.HotelGuestView;

/**
 * Factory for View
 * @author Iñigo Apellániz
 */
public class ViewFactory {

    /**
     * Create a new View from a ViewType
     * @param type the ViewType of the View to create
     * @param viewManager ViewManager from which the View will be managed
     * @return View of the corresponding ViewType
     */
    public static View buildView(ViewType type, ViewManager viewManager) {

        View view = null;
        switch (type) {

            case LOGIN:
                view = new LoginView(viewManager);
                break;

            case REGISTRATION:
                view = new RegisterView(viewManager);
                break;

            case ADMIN_HOTELS:
                view = new HotelAdminView(viewManager);
                break;

            case EDIT_USER:
            	view = new EditUserView(viewManager);
            	break;

            case LOCALE_SETTINGS:
                view = new LocaleView(viewManager);
                break;

            case GUEST_HOTELS:
            	view = new HotelGuestView(viewManager);
            	break;

            case REGISTER_ADMINISTRATOR:
            	view = new RegisterAdminView(viewManager);
            	break;
        }
        return view;

    }
}
