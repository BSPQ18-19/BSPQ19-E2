package es.deusto.spq.client.gui.base;

/**
 * Represents the required permission level for the View to be open.
 * @author Iñigo Apellániz
 */
public enum ViewPermission {

    /**
     * No permission check.
     * Will always be allowed, no matter what.
     * Lowest permission level possible.
     * Note that if set as permission level, no windows will show.
     */
    NONE,

    /**
     * User logged out.
     */
    NOT_LOGGED_IN,

    /**
     * User logged in.
     */
    LOGGED_IN,

    /**
     * User logged in as a Guest.
     */
    LOGGED_IN_GUEST,

    /**
     * User logged in as an Admin.
     */
    LOGGED_IN_ADMIN;

    /**
     * Checks if we have a permission based on what permission level we are at.
     * @param current the current permission level of the UI
     * @param specific the permission level to check if it's contained inside the current permission level
     * @return whether or not we have a specific permission level
     */
    public static boolean hasPermission(ViewPermission current, ViewPermission specific) {

        // If we're on the same permission level, we have permission
        if (current == specific) {
            return true;
        }

        // We always have permission for NONE
        if (specific == NONE) {
            return true;
        }

        // If we're logged in as a Guest, we have logged in permission
        if (current == LOGGED_IN_GUEST && specific == LOGGED_IN) {
            return true;
        }

        // If we're logged in as an Admin, we have logged in permission
        if (current == LOGGED_IN_ADMIN && specific == LOGGED_IN) {
            return true;
        }

        // Otherwise, we don't have permission.
        return false;
    }
}
