package es.deusto.spq.server.data;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**This class' only goal is to implement a getter method, and to assure a single persistence
 * manager is used in the whole project.
 * 
 * @author Iker
 *
 */
public class MyPersistenceManager {

	private static PersistenceManager pm;
	private static PersistenceManagerFactory pmf;
	
	static {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		pm = pmf.getPersistenceManager();
	}
	
	public static PersistenceManager getPersistenceManager() {
		return pm;
	}
	
}
