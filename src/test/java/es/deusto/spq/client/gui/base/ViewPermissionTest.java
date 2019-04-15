package es.deusto.spq.client.gui.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the ViewPermission class.
 * @author Iñigo Apellániz
 */
public class ViewPermissionTest {

    /**
     * Tests the NONE permission level for the checkPermission method.
     */
    @Test
    public void checkPermission_None() {
        ViewPermission current = ViewPermission.NONE;
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.NONE));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.NOT_LOGGED_IN));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_GUEST));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_ADMIN));
    }


    /**
     * Tests the NOT_LOGGED_IN permission level for the checkPermission method.
     */
    @Test
    public void checkPermission_NotLoggedIn() {
        ViewPermission current = ViewPermission.NOT_LOGGED_IN;
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.NONE));
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.NOT_LOGGED_IN));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_GUEST));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_ADMIN));
    }

    /**
     * Tests the LOGGED_IN permission level for the checkPermission method.
     */
    @Test
    public void checkPermission_LoggedIn() {
        ViewPermission current = ViewPermission.LOGGED_IN;

        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.NONE));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.NOT_LOGGED_IN));
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_GUEST));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_ADMIN));
    }


    /**
     * Tests the LOGGED_IN_GUEST permission level for the checkPermission method.
     */
    @Test
    public void checkPermission_LoggedInGuest() {
        ViewPermission current = ViewPermission.LOGGED_IN_GUEST;

        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.NONE));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.NOT_LOGGED_IN));
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN));
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_GUEST));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_ADMIN));
    }

    /**
     * Tests the LOGGED_IN_ADMIN permission level for the checkPermission method.
     */
    @Test
    public void checkPermission_LoggedInAdmin() {
        ViewPermission current = ViewPermission.LOGGED_IN_ADMIN;

        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.NONE));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.NOT_LOGGED_IN));
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN));
        Assert.assertFalse(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_GUEST));
        Assert.assertTrue(ViewPermission.hasPermission(current, ViewPermission.LOGGED_IN_ADMIN));
    }
}
