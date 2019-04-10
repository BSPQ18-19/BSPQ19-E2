/*package es.deusto.spq;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.client.Client;
import es.deusto.spq.server.Server;
import junit.framework.Assert;

public class FullClientServerTest {

	private static Client client;
	private static Server server;
	private static String ip;
	private static int port;
	private static String serviceName;
	private static String url;
	
	@BeforeClass
	public static void initialize() {
		client = new Client();
		server = new Server();

		System.setProperty("java.rmi.server.hostname", "127.0.0.1");

		ip = "127.0.0.1";
		port = 1099;
		serviceName = "HotelManagementServer";
		url = "//" + ip + ":" + port + "/" + serviceName;
		
		server.initializeServer(url);
		client.initializeClient(ip, port, serviceName);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void initializationTest() {
		Assert.assertTrue(client.getRMIServiceLocator() != null);
		Assert.assertTrue(client.getController() != null);
	}
	
	@Test
	public void signInGuestTest() {
		// TODO test
	}
	
	@Test
	public void logInTest() {
		// TODO test
	}
	
	@Test
	public void logOutTest() {
		// TODO test
	}
	
	@Test
	public void createHotelTest() {
		// TODO test
	}
	
	@Test
	public void deleteHotelTest() {
		// TODO test
	}
	
	@Test
	public void getHotelsTest() {
		// TODO test
	}

	@Test
	public void getHotelbyIDTest() {
		// TODO test
	}
	
}
*/