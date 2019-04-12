package es.deusto.spq.locale;

import es.deusto.spq.server.locale.LocaleManager;
import es.deusto.spq.server.locale.LocaleMode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * Unit test suite for the LocaleManager class.
 * @author Iñigo Apellániz
 */
public class LocaleTest {

    /**
     * The default locale should always be English (en_EN)
     */
	@Test
	public void getMessage_DefaultLocale_IsEnglish() {

		Locale englishLocale = new Locale("en", "EN");

		// Check expected default value
		Assert.assertEquals(englishLocale, LocaleManager.getDefaultLocale());
	}

    /**
     * Test that the getter and setter for the Locale are behaving as expected
     */
    @Test
    public void setLocale_GetterSetter_ExpectedBehaviour() {

        // Test to change the locale
        Locale spanishLocale = new Locale("es", "ES");
        LocaleManager.setLocale(spanishLocale);
        Assert.assertEquals(spanishLocale, LocaleManager.getLocale());

        // Test again, in English
        Locale englishLocale = new Locale("en", "EN");
        LocaleManager.setLocale(englishLocale);
        Assert.assertEquals(englishLocale, LocaleManager.getLocale());

    }

    @Test
    public void getMessage_LocaleChanges_CorrectMessage() {

        // First, with default locale
        LocaleManager.setLocale(LocaleManager.getDefaultLocale());

        Assert.assertEquals("Hello world!", LocaleManager.getMessage("test.hello"));
        Assert.assertEquals("Bye bye!", LocaleManager.getMessage("test.bye"));

        // Switching locale...
        LocaleManager.setLocale(new Locale("es", "ES"));

        // Special chars test (note you might need to update to Java 9+ to make this test pass)
        Assert.assertEquals("¡Hola!", LocaleManager.getMessage("test.hello"));
        Assert.assertEquals("Adiós.", LocaleManager.getMessage("test.bye"));
        Assert.assertEquals("áéíóúÁÉÍÓÚñÑç¡¿\"\"", LocaleManager.getMessage("test.specialchars"));

        // Parametrized examples
        LocaleManager.setLocale(LocaleManager.getDefaultLocale());
        Assert.assertEquals("If you add 1 and 2, you get 3", LocaleManager.getMessage("test.parameters.1", 1+2));
        Assert.assertEquals("Hello, John. You have 2 pending payments.",
                LocaleManager.getMessage("test.parameters.2", "John", 2));

    }

    /**
     * Get the default LocaleMode (NORMAL)
     */
    @Test
    public void getDefaultLocalMode_DefaultLocale_IsNormal() {

        Assert.assertEquals(LocaleMode.NORMAL, LocaleManager.getDefaultLocaleMode());
    }

    /**
     * Test that the getters and setters are behaving as expected
     */
    @Test
    public void getLocaleMode_GetterSetter_ExpectedBehaviour() {

        LocaleManager.setMode(LocaleMode.DEBUG);
        Assert.assertEquals(LocaleMode.DEBUG, LocaleManager.getMode());

        LocaleManager.setMode(LocaleMode.NORMAL);
        Assert.assertEquals(LocaleMode.NORMAL, LocaleManager.getMode());

    }

    /**
     * Test that if we try to get a non-translated key for a non-default locale, and if we are in normal mode,
     * the translated key from the default locale should be returned. (fallback)
     */
    @Test
    public void getMessage_InvalidKeyNormalModePresentInDefault_DefaultFallback() {

        LocaleManager.setMode(LocaleMode.NORMAL);
        LocaleManager.setLocale(new Locale("es", "ES"));

        Assert.assertEquals("This message is only available in the english locale.",
                LocaleManager.getMessage("test.exclusive"));
    }

    /**
     * Test that if we're in DEBUG LocaleMode there's no fallback to the default locale and instead we get the key
     * of the translation itself.
     */
    @Test
    public void getMessage_InvalidKeyDebugModePresentInDefault_NoFallback() {

        LocaleManager.setMode(LocaleMode.DEBUG);
        LocaleManager.setLocale(new Locale("es", "ES"));

        Assert.assertEquals("test.exclusive", LocaleManager.getMessage("test.exclusive"));

    }

    /**
     * Test that when a key doesn't exist, the key itself gets printed
     */
    @Test
    public void getMessage_InvalidKeyNotPresent_PrintKey() {

        Assert.assertEquals("test.invalid", LocaleManager.getMessage("test.invalid"));

    }

}
