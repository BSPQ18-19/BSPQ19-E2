package es.deusto.spq.client.gui.locale;

import es.deusto.spq.client.Client;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Locale;

/**
 * Unit test suite for the LocaleManager class.
 * @author Iñigo Apellániz
 */
public class LocaleTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    /**
     * The default locale should always be English (en_EN)
     */
	@Test
	public void getMessage_DefaultLocale_IsEnglish() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        Locale englishLocale = new Locale("en", "US");

        // Check expected default value
        Assert.assertEquals(englishLocale, LocaleManager.DEFAULT_LOCALE);
        Assert.assertEquals(localeManager.getLocale(), LocaleManager.DEFAULT_LOCALE);
    }

    /**
     * Test that the getter and setter for the Locale are behaving as expected
     */
    @Test
    public void setLocale_GetterSetter_ExpectedBehaviour() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        // Test to change the locale
        Locale spanishLocale = new Locale("es", "ES");
        localeManager.setLocale(spanishLocale);
        Assert.assertEquals(spanishLocale, localeManager.getLocale());

        // Test again, in English
        Locale englishLocale = new Locale("en", "US");
        localeManager.setLocale(englishLocale);
        Assert.assertEquals(englishLocale, localeManager.getLocale());

    }

    @Test
    public void getMessage_LocaleChanges_CorrectMessage() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        // First, with default locale
        localeManager.setLocale(LocaleManager.DEFAULT_LOCALE);

        Assert.assertEquals("Hello world!", localeManager.getMessage("test.hello"));
        Assert.assertEquals("Bye bye!", localeManager.getMessage("test.bye"));

        // Switching locale...
        localeManager.setLocale(new Locale("es", "ES"));

        // Special chars test (note you might need to update to Java 9+ to make this test pass)
        Assert.assertEquals("¡Hola!", localeManager.getMessage("test.hello"));
        Assert.assertEquals("Adiós.", localeManager.getMessage("test.bye"));
        Assert.assertEquals("áéíóúÁÉÍÓÚñÑç¡¿\"\"", localeManager.getMessage("test.specialchars"));

        // Parametrized examples
        localeManager.setLocale(LocaleManager.DEFAULT_LOCALE);
        Assert.assertEquals("If you add 1 and 2, you get 3", localeManager.getMessage("test.parameters.1", 1+2));
        Assert.assertEquals("Hello, John. You have 2 pending payments.",
                localeManager.getMessage("test.parameters.2", "John", 2));

    }

    /**
     * Get the default LocaleMode (NORMAL)
     */
    @Test
    public void getDefaultLocalMode_DefaultLocale_IsNormal() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        Assert.assertEquals(LocaleMode.NORMAL, localeManager.getDefaultLocaleMode());
    }

    /**
     * Test that the getters and setters are behaving as expected
     */
    @Test
    public void getLocaleMode_GetterSetter_ExpectedBehaviour() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        localeManager.setMode(LocaleMode.DEBUG);
        Assert.assertEquals(LocaleMode.DEBUG, localeManager.getMode());

        localeManager.setMode(LocaleMode.NORMAL);
        Assert.assertEquals(LocaleMode.NORMAL, localeManager.getMode());

    }

    /**
     * Test that if we try to get a non-translated key for a non-default locale, and if we are in normal mode,
     * the translated key from the default locale should be returned. (fallback)
     */
    @Test
    public void getMessage_InvalidKeyNormalModePresentInDefault_DefaultFallback() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        localeManager.setMode(LocaleMode.NORMAL);
        localeManager.setLocale(new Locale("es", "ES"));

        Assert.assertEquals("This message is only available in the english locale.",
                localeManager.getMessage("test.exclusive"));
    }

    /**
     * Test that if we're in DEBUG LocaleMode there's no fallback to the default locale and instead we get the key
     * of the translation itself.
     */
    @Test
    public void getMessage_InvalidKeyDebugModePresentInDefault_NoFallback() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        localeManager.setMode(LocaleMode.DEBUG);
        localeManager.setLocale(new Locale("es", "ES"));

        Assert.assertEquals("test.exclusive", localeManager.getMessage("test.exclusive"));

    }

    /**
     * Test that when a key doesn't exist, the key itself gets printed
     */
    @Test
    public void getMessage_InvalidKeyNotPresent_PrintKey() {

        Client client = Mockito.mock(Client.class);
        LocaleManager localeManager = new LocaleManager(client);

        Assert.assertEquals("test.invalid", localeManager.getMessage("test.invalid"));

    }

}
