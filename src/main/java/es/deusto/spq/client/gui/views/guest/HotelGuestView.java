package es.deusto.spq.client.gui.views.guest;

import javax.swing.*;

import org.jetbrains.annotations.Nullable;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;

public class HotelGuestView extends View{

    ClientWindowGuest clientWindow;

    public HotelGuestView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.GUEST_HOTELS;
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
        clientWindow = ClientWindowGuest.getClientWindow(getViewManager().getClient().getController());
        clientWindow.setVisible(true);
    }

    @Override
    public @Nullable JInternalFrame getInternalFrame() {
        return clientWindow;
    }
}
