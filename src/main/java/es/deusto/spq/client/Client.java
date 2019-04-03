package es.deusto.spq.client;

import java.util.logging.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.IServer;

public class Client {

	public static void main(String[] args) {

		Logger log = ClientLogger.getLogger();

		if (args.length != 3) {
			log.severe("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}


		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			IServer serverStub = (IServer) java.rmi.Naming.lookup(name);
			serverStub.sayHello();
			serverStub.sayMessage("Bye!");
		} catch (Exception e) {
			log.severe("RMI error. Turning down the client... - " + e.getMessage());
//			e.printStackTrace();
		}
	}
}