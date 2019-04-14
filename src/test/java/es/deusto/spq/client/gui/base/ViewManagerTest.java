package es.deusto.spq.client.gui.base;

import es.deusto.spq.client.Client;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test suite for the ViewManager class
 * @author Iñigo Apellániz
 */
public class ViewManagerTest {

    /**
     * Tests that the default ViewPermission is NOT_LOGGED_IN
     */
    @Test
    public void permissionLevel_Default_NotLoggedIn() {

        Client client = Mockito.mock(Client.class);
        ViewManager viewManager = new ViewManager(client);

        Assert.assertEquals(ViewPermission.NOT_LOGGED_IN, viewManager.getPermission());
    }

}
