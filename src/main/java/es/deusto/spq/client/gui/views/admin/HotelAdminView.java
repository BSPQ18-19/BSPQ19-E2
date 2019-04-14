package es.deusto.spq.client.gui.views.admin;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class HotelAdminView extends View {

    ClientWindow clientWindow;

    public HotelAdminView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ADMIN_HOTELS;
    }

    @Override
    public ViewPermission getViewPermission() {
        return ViewPermission.NOT_LOGGED_IN; // TODO change
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public void initialize() {
        clientWindow = ClientWindow.getClientWindow(getViewManager().getClient().getController());
        clientWindow.setVisible(true);
    }

    @Override
    public @Nullable JInternalFrame getInternalFrame() {
        return clientWindow;
    }
}
