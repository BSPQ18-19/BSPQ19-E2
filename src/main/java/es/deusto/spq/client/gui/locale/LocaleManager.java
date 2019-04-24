package es.deusto.spq.client.gui.locale;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Client locale management master class.
 * <p>
 *     The main localization helper class.
 *
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
     * Get the default LocaleMode
     * @return the default LocaleMode
     */
    public static LocaleMode getDefaultLocaleMode() {
        return LocaleMode.NORMAL;
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
     * Localize a message resource.
     * @param key the key of the resource
     * @param parameters vararg of the parameters for the resource
     * @return an already-formatted String
     */
    public static String getMessage(String key, Object... parameters) {

        // The locale to use for the formatting below
        Locale localeToUse = getLocale();

        // Check if the key is present for the current locale
        if (!getResourceBundle().containsKey(key)) {

            // DEBUG mode: we don't fall back to default locale
            // useful for detecting untranslated UI elements
            if (mode == LocaleMode.DEBUG || !getResourceBundle(DEFAULT_LOCALE).containsKey(key)) {
                return key; // return just the translation key
            }

            // In this case we use the default locale (key not found in Locale)
            localeToUse = getDefaultLocale();

        }

        // Format with parameters
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(localeToUse);
        formatter.applyPattern(getResourceBundle(localeToUse).getString(key));
        return formatter.format(parameters);

    }

}
