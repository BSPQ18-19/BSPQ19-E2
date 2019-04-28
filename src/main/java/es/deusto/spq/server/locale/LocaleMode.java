package es.deusto.spq.server.locale;

/**
 * LocaleMode sets the expected behaviour of the LocaleManager class
 * by specifying what to do when a key is not found.
 * @author Iñigo Apellániz
 */
public enum  LocaleMode {

    /**
     * Fall back to default locale when a key is not found
     */
    NORMAL,

    /**
     * Don't fall back to the default locale when a key is not found
     */
    DEBUG
}
