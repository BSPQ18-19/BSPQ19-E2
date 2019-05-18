package es.deusto.spq.client.gui.locale;

import java.util.Locale;

/**
 * Represents an usable Locale for the application.
 * @author Iñigo Apellániz
 */
public class AllowedLocale {

    /**
     * Constant corresponding to the English AllowedLocale
     */
    public final static AllowedLocale ENGLISH = new AllowedLocale(
            new Locale("en"),
            "en",
            "English",
            "English"
    );

    /**
     * Constant corresponding to the Spanish AllowedLocale
     */
    public final static AllowedLocale SPANISH = new AllowedLocale(
            new Locale("es", "ES"),
            "es_ES",
            "Spanish/Spain",
            "Español/España"
    );

    /**
     * Constant corresponding to the Basque AllowedLocale
     */
    public final static AllowedLocale BASQUE = new AllowedLocale(
            new Locale("eu"),
            "eu",
            "Basque",
            "Euskara"
    );

    /**
     * Constant corresponding to the Czech AllowedLocale
     */
    public final static AllowedLocale CZECH = new AllowedLocale(
            new Locale("cs", "CZ"),
            "cs_CZ",
            "Czech",
            "Česky"
    );

    /**
     * An array with the pre-set allowed locales for the app
     */
    public final static AllowedLocale[] ALLOWED_LOCALES = {ENGLISH, SPANISH, BASQUE, CZECH};

    private Locale locale;

    /**
     * The language code (e.g.: es_ES for Spanish, Spain)
     */
    private String code;

    /**
     * The name of the locale in English
     */
    private String englishName;

    /**
     * The localized name of the locale
     */
    private String localizedName;

    private AllowedLocale(Locale locale, String code, String englishName, String localizedName) {
        this.locale = locale;
        this.code = code;
        this.englishName = englishName;
        this.localizedName = localizedName;
    }

    /**
     * @return the Locale
     */
    public Locale getLocale() {
        return locale;
    }

    public String getCode() {
        return code;
    }

    /**
     * @return the name in English of the locale
     */
    public String getEnglishName() {
        return englishName;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    @Override
    public String toString() {
        return "AllowedLocale{" +
                "locale=" + locale +
                ", code='" + code + '\'' +
                ", englishName='" + englishName + '\'' +
                ", localizedName='" + localizedName + '\'' +
                '}';
    }
}
