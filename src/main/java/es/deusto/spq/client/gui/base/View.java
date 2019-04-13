package es.deusto.spq.client.gui.base;

/**
 * Underlying class for app Views
 * @author Iñigo Apellániz
 */
public abstract class View {

    /**
     * The ViewManager responsible for
     */
    private ViewManager viewManager;

    /**
     * Getter for the ViewType of the View
     * @return the ViewType
     */
    public ViewType getViewType() {
        return ViewType.OTHER;
    }

    /**
     * Whether or not only one instance of the View should ever be opened.
     * @return whether or not the View is unique
     */
    boolean isUnique() {
        return false;
    }

    /**
     * Initializes the View and shows it
     * @param viewManager a reference to the ViewManager that created the view
     */
    void initialize(ViewManager viewManager) {

    }

    /**
     * Brings the view to the top.
     */
    void bringToTop() {

    }

    /**
     * Minimizes the view to the background.
     */
    void minimize() {

    }

    /**
     * Closes the view and disposes it.
     * After this call, the View instance will never be called again.
     */
    public void dispose() {

    }

}
