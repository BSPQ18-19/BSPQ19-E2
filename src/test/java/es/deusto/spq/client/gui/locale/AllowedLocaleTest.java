package es.deusto.spq.client.gui.locale;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * Test suite for the AllowedLocale class
 * @author Iñigo Apellániz
 */
public class AllowedLocaleTest {

    /**
     * Test if the ENGLISH constant for AllowedArray is what is expected
     */
    @Test
    public void englishConstant_Correct() {
        Assert.assertEquals(new Locale("en", "US"), AllowedLocale.ENGLISH.getLocale());
        Assert.assertEquals("en_US", AllowedLocale.ENGLISH.getCode());
        Assert.assertEquals("English/US", AllowedLocale.ENGLISH.getEnglishName());
        Assert.assertEquals("English/US", AllowedLocale.ENGLISH.getLocalizedName());
    }

    /**
     * Test if the SPANISH constant for AllowedArray is what is expected
     */
    @Test
    public void spanishConstant_Correct() {
        Assert.assertEquals(new Locale("es", "ES"), AllowedLocale.SPANISH.getLocale());
        Assert.assertEquals("es_ES", AllowedLocale.SPANISH.getCode());
        Assert.assertEquals("Spanish/Spain", AllowedLocale.SPANISH.getEnglishName());
        Assert.assertEquals("Español/España", AllowedLocale.SPANISH.getLocalizedName());
    }

    /**
     * Test if the BASQUE constant for AllowedArray is what is expected
     */
    @Test
    public void basqueConstant_Correct() {
        Assert.assertEquals(new Locale("eu"), AllowedLocale.BASQUE.getLocale());
        Assert.assertEquals("eu", AllowedLocale.BASQUE.getCode());
        Assert.assertEquals("Basque", AllowedLocale.BASQUE.getEnglishName());
        Assert.assertEquals("Euskara", AllowedLocale.BASQUE.getLocalizedName());
    }

    /**
     * Test if the CZECH constant for AllowedArray is what is expected
     */
    @Test
    public void czechConstant_Correct() {
        Assert.assertEquals(new Locale("cs", "CZ"), AllowedLocale.CZECH.getLocale());
        Assert.assertEquals("cs_CZ", AllowedLocale.CZECH.getCode());
        Assert.assertEquals("Czech", AllowedLocale.CZECH.getEnglishName());
        Assert.assertEquals("Česky", AllowedLocale.CZECH.getLocalizedName());
    }

    /**
     * Test if the ALLOWED_LOCALES constant array is what we expect
     */
    @Test
    public void allowedLocalesConstant_ExactMatch() {
        AllowedLocale[] expectedArray =
                {AllowedLocale.ENGLISH, AllowedLocale.SPANISH, AllowedLocale.BASQUE, AllowedLocale.CZECH};
        Assert.assertArrayEquals(expectedArray, AllowedLocale.ALLOWED_LOCALES);
    }

    /**
     * Test to see if the toString method works as expected
     */
    @Test
    public void toString_Correct() {
        Assert.assertEquals(
                "AllowedLocale{locale=en_US, code='en_US', englishName='English/US', localizedName='English/US'}",
                AllowedLocale.ENGLISH.toString()
        );
        Assert.assertEquals(
                "AllowedLocale{locale=es_ES, code='es_ES', englishName='Spanish/Spain', localizedName='Español/España'}",
                AllowedLocale.SPANISH.toString()
        );
    }

}
