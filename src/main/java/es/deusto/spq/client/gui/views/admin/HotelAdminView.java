package es.deusto.spq.client.gui.views.admin;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class HotelAdminView extends View {

    private ClientWindow clientWindow;

    public HotelAdminView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ADMIN_HOTELS;
    }

    @Override
    public ViewPermission getViewPermission() {
        return ViewPermission.LOGGED_IN; // TODO change
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public void initialize() {
        clientWindow = new ClientWindow(this);
        clientWindow.setVisible(true);
        addDisposeEventHandler();
    }

    @Override
    public void dispose() {
        clientWindow.dispose();
    }

    @Override
    public void bringToFront() {
        clientWindow.toFront();
    }

    @Override
    public @Nullable JInternalFrame getInternalFrame() {
        return clientWindow;
    }
}
