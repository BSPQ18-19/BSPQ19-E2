package es.deusto.spq.client.gui.base;

import es.deusto.spq.client.Client;

import java.util.ArrayList;

/**
 * Manages the whole UI View system.
 * @author Iñigo Apellániz
 */
public class ViewManager {

    /**
     * The current Client class serving the app.
     */
    private Client client;

    /**
     * The current ViewPermission level.
     */
    private ViewPermission permission;

    /**
     * List of active views.
     */
    private ArrayList<View> views = new ArrayList<>();

    /**
     * Creates a new ViewManager with no Views.
     * @param client The current active Client class
     */
    public ViewManager(Client client) {
        this.client = client;
    }

    /**
     * Get the current Client class.
     * @return the Client the ViewManager is serving
     */
    public Client getClient() {
        return client;
    }

    /**
     * Closes all views.
     */
    public void closeAll() {
        // TODO close all
    }

    /**
     * The ViewPermission represents the current permission level for the UI. The
     * @return the ViewPermission to set
     */
    public ViewPermission getPermission() {
        return permission;
    }

    public void setPermission(ViewPermission permission) {
        this.permission = permission;
    }
}
