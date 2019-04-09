package es.deusto.spq;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.client.Client;
import es.deusto.spq.server.Server;
import junit.framework.Assert;

public class FullClientServerTest {

	private Client client;
	private Server server;
	private String ip;
	private int port;
	private String serviceName;
	private String url;
	
	@BeforeClass
	public void initialize() {
		client = new Client();
		server = new Server();

		System.setProperty("java.rmi.server.hostname","127.0.0.1");


		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		ip = "127.0.0.1";
		port = 1099;
		serviceName = "HotelManagementServer";
		url = "//" + ip + ":" + port + "/" + serviceName;
		
		server.initializeServer(url);
		client.initializeClient(ip, port, serviceName);
		
		initializeData();
	}
	
	private void initializeData() {
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void initializationTest() {
		Assert.assertTrue(client.getRMIServiceLocator() != null);
		Assert.assertTrue(client.getController() != null);
	}
	
	@Test
	public void signInGuestTest() {
		
	}
	
	@Test
	public void logInTest() {
		
	}
	
	@Test
	public void logOutTest() {
		
	}
	
	@Test
	public void createHotelTest() {
		
	}
	
	@Test
	public void deleteHotelTest() {
		
	}
	
	@Test
	public void getHotelsTest() {
		
	}

	@Test
	public void getHotelbyIDTest() {
		
	}
	
}
