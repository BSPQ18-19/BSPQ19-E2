package es.deusto.spq.client.gui.base;

/**
 * Represents the type
 * @author Inigo Apellaniz
 */
public enum ViewType
{
    /**
     * Registration View.
     */
    REGISTRATION,

    /**
     * Login View.
     */
    LOGIN,

    /**
     * All other types of Views.
     * Should almost never be used, except for temporary views.
     * Will almost likely be disposed for many operations.
     */
    OTHER,

    /**
     * Admin window to manage existing hotels, delete them and create new.
     */
    ADMIN_HOTELS,
    
    /**
     * View to change the current locale.
     */
    LOCALE_SETTINGS,

    /**
     * View to register an admin
     */
    REGISTER_ADMINISTRATOR,
    
    /**
     * Guest window to manage requested hotels, searching them and booking rooms
     */
     GUEST_HOTELS,
     
     /**
      * Window for writing a review
      */
     WRITE_REVIEW
}
