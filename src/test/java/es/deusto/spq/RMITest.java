package es.deusto.spq;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.Assert;

/**The RMI registry must be running
 * @author Iker
 *
 */
public class RMITest {

	@Rule
	public ContiPerfRule rule = new ContiPerfRule();

	private static Registry locateRegistry;
	private static String serverUrl;
	private static String clientUrl;
	private static String cwd = RMITest.class.getProtectionDomain().getCodeSource().getLocation().getFile();

	
	@BeforeClass
	public static void setUpParameters() {

		try {
			locateRegistry = LocateRegistry.createRegistry(1098);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		serverUrl = "//127.0.0.1:1098/HotelManagementServer";
		clientUrl = "//127.0.0.1:1098/HotelManagementServer";

		//System.setProperty("java.rmi.server.codebase", "file:" + cwd);
	}
		
	@SuppressWarnings("deprecation")
	@PerfTest(invocations = 20)
	@Required(max = 300)
	@Test
	public void clientServerRMITest() throws RemoteException, MalformedURLException, NotBoundException {
		// Server side
		IServerTest serverObject = new ServerTest();
		Naming.rebind(serverUrl, serverObject);
		java.io.InputStreamReader iSR = new java.io.InputStreamReader (System.in);
		java.io.BufferedReader stdin = new java.io.BufferedReader (iSR);

		//Client side
		String testMessage = "this is a test";
		IServerTest serverStub = (IServerTest) java.rmi.Naming.lookup(clientUrl);
		String serverResponse = serverStub.retrieveMessage(testMessage);
		Assert.assertEquals(serverResponse, testMessage);
	}
	
	
	private static class ServerTest extends UnicastRemoteObject implements IServerTest, Serializable{

		private static final long serialVersionUID = 1L;

		private ServerTest() throws RemoteException {
			super();
		}

		public String retrieveMessage(String text) throws RemoteException {
			return text;
		}
		
	}
	
	private interface IServerTest extends Remote {
		public String retrieveMessage(String text) throws RemoteException;
	}

}