package es.deusto.spq.server.locale;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Client locale management master class.
 * <p>
 *     The main localization helper class.
 * </p>
 * @author Iñigo Apellániz
 */
public class LocaleManager {

    /**
     * The singleton instance of the class.
     * Will be the only active instance for it.
     */
    private static LocaleManager instance = null;

    /**
     * The name of the ResourceBundle files
     */
    private static final String RESOURCE_BUNDLE_FILE_NAME = "localization";

    /**
     * The default locale for the app.
     * Will always be en_EN fue to teacher's requirements.
     * (the course is in English)
     */
    private static final Locale DEFAULT_LOCALE = new Locale("en", "EN");

    /**
     * The current locale of the system.
     */
    private static Locale locale = new Locale("en", "EN");

    /**
     * The mode for displaying non-localized keys
     * by default we fall back to the default locale
     */
    private static LocaleMode mode = LocaleMode.NORMAL;

    private LocaleManager() { }

    /**
     * Get the single instance of the Class.
     * @return the static instance for the class
     */
    public static LocaleManager getInstance()
    {
        if (instance == null) {
            instance = new LocaleManager();
        }

        return instance;
    }

    /**
     * Get the current locale for the system.
     * @return the current locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * Set the locale of the system
     * @param locale the locale to be set as active
     */
    public static void setLocale(Locale locale) {
        LocaleManager.locale = locale;
    }

    /**
     * Get the default system Locale
     * @return the default Locale
     */
    public static Locale getDefaultLocale() {
        return DEFAULT_LOCALE;
    }

    /**
     *
     * @return the current LocaleMode
     */
    public static LocaleMode getMode() {
        return mode;
    }

    /**
     * Sets the current LocaleMode for future translations
     * @param mode the mode to set as active
     */
    public static void setMode(LocaleMode mode) {
        LocaleManager.mode = mode;
    }

    /**
     * Get the current ResourceBundle for the selected Locale
     * @return the ResourceBundle associated to the current locale
     */
    private static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME, locale);
    }


    /**
     * Get the ResourceBundle for the specified Locale
     * @return the ResourceBundle associated to the specified locale
     */
    private static ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME, locale);
    }

    /**
     * Gets a translated String, given a key
     * @param key the key of the message to be localized
     */
    public static String getMessage(String key, String... parameters) {

        // Check if the key is present for the current locale
        if (!getResourceBundle().containsKey(key)) {

            // DEBUG mode: we don't fall back to default locale
            // useful for detecting untranslated UI elements
            if (mode == LocaleMode.DEBUG) {
                return key; // return just the translation key
            }

            // If we're in NORMAL mode, we fall back to the default locale
            if(!getResourceBundle(DEFAULT_LOCALE).containsKey(key)) {
                return key; // locale not present even in the default locale, return key
            }

            // Return the key from the default locale
            return getResourceBundle(DEFAULT_LOCALE).getString(key);

        }

        // Return the translated String from the current Locale
        return getResourceBundle().getString(key);
    }

    // TODO: support parameters in translation messages
}
