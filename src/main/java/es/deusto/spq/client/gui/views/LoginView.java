package es.deusto.spq.client.gui.views;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;

import java.util.logging.Logger;

public class LoginView extends View {

    /**
     *
     * @param viewManager
     */
    public LoginView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public ViewPermission getViewPermission() {
        return ViewPermission.NOT_LOGGED_IN;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.LOGIN;
    }

    @Override
    public void initialize() {
        Logger.getAnonymousLogger().info("opened login screen!!!!!!!!");
    }
}
