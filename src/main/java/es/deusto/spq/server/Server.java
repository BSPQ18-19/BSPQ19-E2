package es.deusto.spq.server;

import java.rmi.Naming;

import es.deusto.spq.server.logger.ServerLogger;
import es.deusto.spq.server.remote.HotelManager;
import es.deusto.spq.server.remote.IHotelManager;


public class Server {

	public static void main(String[] args) {

		if (args.length != 3) {
			ServerLogger.getLogger().severe("How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}


		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String url = "//" + args[0] + ":" + args[1] + "/" + args[2];
		
		Server server = new Server();
		server.initializeServer(url);
	}
	
	public void initializeServer(String url) {
		try {
			IHotelManager objServer = new HotelManager();
			Naming.rebind(url, objServer);
			ServerLogger.getLogger().info("Server '" + url + "' active and waiting...");
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(System.in);
			java.io.BufferedReader stdin = new java.io.BufferedReader(inputStreamReader);
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
		} catch (Exception e) {
			ServerLogger.getLogger().severe("RMI error. Turning off... - " + e.getMessage());
			e.printStackTrace();
		}
	}

}