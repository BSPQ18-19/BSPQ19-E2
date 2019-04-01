package es.deusto.spq;

import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Extent;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.data.jdo.*;
public class DBTest {

	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;
	private Transaction tx = null;
	
	@Before
	public void setUp() throws Exception{
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

        System.out.println("DataNucleus AccessPlatform with JDO");
        System.out.println("===================================");

        this.pm = this.pmf.getPersistenceManager();
        this.tx = this.pm.currentTransaction();
	}
	
	private String convert2Upcase(String str) {
		return str.toUpperCase();
	}
	
	@Test
	public void testUserCreation() {
		try {
			tx.begin();
			System.out.println(this.convert2Upcase("Persisting users"));
			Guest g = new Guest("4", "Egoitz", "email@email.email", "Password", 11223344, "Test Street");
			pm.makePersistent(g);
			
			tx.commit();
			System.out.println("User have been persisted");
		}finally {
			 if (tx.isActive())
	            {
	                tx.rollback();
	            }
	            pm.close();
	    }
		System.out.println("");
	}
	
	@Test
    public void testUserExtent() {
		this.pm = this.pmf.getPersistenceManager();
        this.tx = this.pm.currentTransaction();
        try {
        	 tx.begin();
             System.out.println("Retrieving Extent for Users");
             Extent<User> e = pm.getExtent(User.class, true);
             Iterator<User> iter = e.iterator();
             while(iter.hasNext()) {
            	 Object obj = iter.next();
            	 System.out.println("> " +obj);
             }
             tx.commit();
        }catch(Exception e) {
        	System.out.println("Exception thrown during retrieval of Extent : " + e.getMessage());
        } finally {
        	if(tx.isActive()) {
        		tx.rollback();
        	}
        	pm.close();
        }
        System.out.println("");
	}
	
	@Test
	public void testUserQuery() {
		this.pm = this.pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
		try {
			tx.begin();
			System.out.println("Executing Query for getting the user 'Egoitz'");
			Extent<User> e = pm.getExtent(User.class,true);
			Query<User> q = pm.newQuery(e,"NAME='Egoitz'");
			for(User u : (List<User>) q.execute()) {
				System.out.println(u);
			}
		}finally {
			 if (tx.isActive())
	            {
	                tx.rollback();
	            }
	            pm.close();
		}
		System.out.println();
	}
	
	@Test
	public void testUserDeletion() {
		pm = this.pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        try
        {
            tx.begin();
            System.out.println("Deleting all Users from persistence");
            Query<User> q = pm.newQuery(User.class);
            long numberInstancesDeleted = q.deletePersistentAll();
            System.out.println("Deleted " + numberInstancesDeleted + " users");

            tx.commit();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }

        System.out.println("");
	}
	
	@After
    public void tearDown() throws Exception {
		
        if (this.pm != null) {
			this.pm.close();
		}
		
    }
}
