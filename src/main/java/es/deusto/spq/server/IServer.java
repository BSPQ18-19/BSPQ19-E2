package es.deusto.spq.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	
	void sayMessage(String message) throws RemoteException;
	void sayHello() throws RemoteException;

}
