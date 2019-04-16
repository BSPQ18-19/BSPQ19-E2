package es.deusto.spq.client.gui.base;

import es.deusto.spq.client.Client;
import es.deusto.spq.client.gui.views.admin.HotelAdminView;
import es.deusto.spq.client.gui.views.auth.LoginView;
import es.deusto.spq.client.gui.views.auth.RegisterView;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Test suite for the ViewFactory clas
 */
public class ViewFactoryTest {

    @Test
    public void buildView_Other_Null() {

        ViewManager viewManager = Mockito.mock(ViewManager.class);

        Assert.assertNull(ViewFactory.buildView(ViewType.OTHER, viewManager));
    }

    @Test
    public void buildView_Login_LoginView() {

        ViewManager viewManager = Mockito.mock(ViewManager.class);
        Client client = Mockito.mock(Client.class);
        when(viewManager.getClient()).thenReturn(client);

        Assert.assertTrue(ViewFactory.buildView(ViewType.LOGIN, viewManager) instanceof LoginView);
    }

    @Test
    public void buildView_Registration_RegisterView() {

        ViewManager viewManager = Mockito.mock(ViewManager.class);
        Client client = Mockito.mock(Client.class);
        when(viewManager.getClient()).thenReturn(client);

        Assert.assertTrue(ViewFactory.buildView(ViewType.REGISTRATION, viewManager) instanceof RegisterView);
    }

    @Test
    public void buildView_AdminHotels_RegisterView() {

        ViewManager viewManager = Mockito.mock(ViewManager.class);
        Client client = Mockito.mock(Client.class);
        when(viewManager.getClient()).thenReturn(client);

        Assert.assertTrue(ViewFactory.buildView(ViewType.ADMIN_HOTELS, viewManager) instanceof HotelAdminView);
    }
}
