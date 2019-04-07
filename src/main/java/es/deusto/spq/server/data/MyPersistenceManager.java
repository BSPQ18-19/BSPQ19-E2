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

	/**
	 * The {@link javax.jdo.PersistenceManager} class to deal with JDO,
	 */
	private static PersistenceManager pm;
	/**
	 * <p>
	 * The {@link javax.jdo.PersistenceManagerFactory} is used to retrieve a {@link javax.jdo.PersistenceManager}
	 * so that we can manage the number of generations and not have too many.
	 * </p>
	 */
	private static PersistenceManagerFactory pmf;
	
	static {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		pm = pmf.getPersistenceManager();
	}
	
	/**
	 * @return The {@link javax.jdo.PersistenceManager} class for the JDO.
	 */
	public static PersistenceManager getPersistenceManager() {
		return pm;
	}
}
