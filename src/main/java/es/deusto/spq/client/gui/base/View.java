package es.deusto.spq.client.gui.base;

import java.util.logging.Logger;

/**
 * Underlying class for app Views
 * @author Iñigo Apellániz
 */
public abstract class View {

    /**
     * The ViewManager responsible for the View.
     */
    private ViewManager viewManager;

    public View(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    /**
     * Getter for the ViewType of the View
     * @return the ViewType
     */
    public ViewType getViewType() {
        return ViewType.OTHER;
    }

    /**
     * The ViewPermission of a View represents when it can remain opened whenever the permission level changes in the
     * application. @see es.deusto.spq.client.gui.base.ViewPermission
     * @return the view permission
     */
    public ViewPermission getViewPermission() {
        return ViewPermission.NONE;
    }

    /**
     * Whether or not only one instance of the View should ever be opened.
     * @return whether or not the View is unique
     */
    public boolean isUnique() {
        return false;
    }

    /**
     * Initializes the View and shows it
     */
    public void initialize() {
        Logger.getLogger(this.getClass().getName())
                .warning("initialize method not implemented or calling super!");
    }

    /**
     * Brings the view to the top.
     */
    public void bringToFront() {
        Logger.getLogger(this.getClass().getName())
                .warning("bringToFront method not implemented or calling super!");
    }

    /**
     * Minimizes the view to the background.
     */
    public void minimize() {
        Logger.getLogger(this.getClass().getName())
                .warning("minimize method not implemented or calling super!");
    }

    /**
     * Tells the window to force-check for new data and update it.
     * Should also re-paint it in case the locale has changed.
     */
    public void refresh() {
        Logger.getLogger(this.getClass().getName())
                .warning("refresh method not implemented or calling super!");
    }

    /**
     * Closes the view and disposes it.
     * After this call, the View instance will never be called again.
     */
    public void dispose() {
        Logger.getLogger(this.getClass().getName())
                .warning("dispose method not implemented or calling super!");
    }


}
