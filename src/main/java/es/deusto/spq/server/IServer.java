package es.deusto.spq.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	
	/**
	 * Prints out the message.
	 * @param message to be printed.
	 * @throws RemoteException launched by RMI.
	 */
	void sayMessage(String message) throws RemoteException;
	/**
	 * Prints "Hello!"
	 * @throws RemoteException launched by RMI.
	 */
	void sayHello() throws RemoteException;

}
