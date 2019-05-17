package es.deusto.spq.client.gui.locale;

import es.deusto.spq.client.Client;

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
     * The name of the ResourceBundle files
     */
    private static final String RESOURCE_BUNDLE_FILE_NAME = "localization";

    /**
     * The default locale for the app.
     * Will always be en_EN fue to teacher's requirements.
     * (the course is in English)
     */
    public static final Locale DEFAULT_LOCALE = AllowedLocale.ENGLISH.getLocale();

    /**
     * The Client associated to this LocaleManager
     */
    private Client client;

    /**
     * The current locale of the system.
     * Initialized to the default locale constant
     */
    private Locale locale = DEFAULT_LOCALE;

    /**
     * The mode for displaying non-localized keys
     * by default we fall back to the default locale
     */
    private LocaleMode mode = LocaleMode.NORMAL;

    /**
     * Create a new LocaleManager instance
     * @param client the Client class that will be associated to the LocaleManager
     */
    public LocaleManager(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Get the current locale for the system.
     * @return the current locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Set the locale of the system
     * @param locale the locale to be set as active
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     *
     * @return the current LocaleMode
     */
    public LocaleMode getMode() {
        return mode;
    }

    /**
     * Sets the current LocaleMode for future translations
     * @param mode the mode to set as active
     */
    public void setMode(LocaleMode mode) {
        this.mode = mode;
    }

    /**
     * Get the default LocaleMode
     * @return the default LocaleMode
     */
    public LocaleMode getDefaultLocaleMode() {
        return LocaleMode.NORMAL;
    }

    /**
     * Get the current ResourceBundle for the selected Locale
     * @return the ResourceBundle associated to the current locale
     */
    private ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME, locale);
    }

    /** Get the ResourceBundle for the specified Locale
     * @param locale
     * @return the ResourceBundle associated to the specified locale
     */
    private ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME, locale);
    }


    /**
     * Localize a message resource.
     * @param key the key of the resource
     * @param parameters vararg of the parameters for the resource
     * @return an already-formatted String
     */
    public String getMessage(String key, Object... parameters) {

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
            localeToUse = DEFAULT_LOCALE;

        }

        // Format with parameters
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(localeToUse);
        formatter.applyPattern(getResourceBundle(localeToUse).getString(key));
        return formatter.format(parameters);

    }

}
