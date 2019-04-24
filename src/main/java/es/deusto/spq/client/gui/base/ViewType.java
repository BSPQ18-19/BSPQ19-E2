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
     * Guest window to manage requested hotels, searching them and booking rooms
     */
     GUEST_HOTELS
}
