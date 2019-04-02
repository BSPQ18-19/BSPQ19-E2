package es.deusto.spq.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
		super();
	}
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());


	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String url = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			IServer objServer = new Server();
			Naming.rebind(url, objServer);
			LOGGER.info("Server '" + url + "' active and waiting...");
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader ( System.in );
			java.io.BufferedReader stdin = new java.io.BufferedReader ( inputStreamReader );
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
		} catch (Exception e) {
			LOGGER.warning("Hello exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void sayMessage(String message) throws RemoteException {
		System.out.println(message);
	}

	@Override
	public void sayHello() throws RemoteException {
		LOGGER.info("Hello!");
	}
}
