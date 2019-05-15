package es.deusto.spq.client.gui.base;

import es.deusto.spq.client.logger.ClientLogger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
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
     * @return the ViewManager responsible for the View
     */
    public ViewManager getViewManager() {
        return viewManager;
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
     * application. See {@link ViewPermission}
     * @return the view permission
     */
    public ViewPermission getViewPermission() {
        return ViewPermission.NONE;
    }

    /**
     * @return the internal frame of the View
     */
    @Nullable
    public JInternalFrame getInternalFrame() {
        ClientLogger.getLogger()
                .warn(getClass().getName() + " getInternalFrame method not implemented or calling super!");
        return null;
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
        ClientLogger.getLogger()
                .warn(getClass().getName() + " initialize method not implemented or calling super!");
    }

    /**
     * Brings the view to the top.
     */
    public void bringToFront() {
        ClientLogger.getLogger()
                .warn(getClass().getName() + " bringToFront method not implemented or calling super!");
    }

    /**
     * Minimizes the view to the background.
     */
    public void minimize() {
        ClientLogger.getLogger()
                .warn(getClass().getName() + " minimize method not implemented or calling super!");
    }

    /**
     * Tells the window to force-check for new data and update it.
     * Should also re-paint it in case the locale has changed.
     */
    public void refresh() {
        ClientLogger.getLogger()
                .warn(getClass().getName() + " refresh method not implemented or calling super!");
    }

    /**
     * Closes the view and disposes it.
     * After this call, the View instance will never be called again.
     */
    public void dispose() {
        try {
            getInternalFrame().dispose();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a dispose event listener for the InternalFrame.
     * REQUIRED call after finishing the internal frame creation.
     */
    public void addDisposeEventHandler() {
        try {
            getInternalFrame().addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    getViewManager().notifyDisposal(getView());
                }
            });
        } catch (NullPointerException e) {
            ClientLogger.getLogger().warn(getClass().getName() + " Trying to setup InternalFrame listeners before" +
                    "creating the InternalFrame");
        }
    }

    /**
     * I know it's a silly call, but required due to scope issues.
     * @return the View itself.
     */
    @Contract(value = " -> this", pure = true)
    private View getView() {
        return this;
    }

}
