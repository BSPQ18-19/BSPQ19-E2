package es.deusto.spq.client;

import es.deusto.spq.server.IServer;

public class Client {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
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
			System.err.println("RMI Example exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}