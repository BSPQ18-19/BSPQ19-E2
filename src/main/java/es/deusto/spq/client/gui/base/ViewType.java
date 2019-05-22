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
     * User view where you can edit the your actual user
     */
    EDIT_USER,

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
      * View that shows reviews
      */
     SHOW_REVIEWS,

     /**
      * View that handles payments
      */
     MAKE_PAYMENT,
     
     /**
      * Window for writing a review
      */
     WRITE_REVIEW,

    /**
     * View that show a given list of Reservations
     */
    RESERVATION_LIST,

    /**
     * View to show detail about a specific Reservation
     */
    RESERVATION_DETAIL
}
