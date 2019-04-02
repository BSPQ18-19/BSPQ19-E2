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

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.jdo.*;

public class TestDB {
	private PersistenceManager pm = null;
	private Transaction tx = null;
	
//	@Before
//	public void setUp() throws Exception{
//        System.out.println("DataNucleus AccessPlatform with JDO");
//        System.out.println("===================================");
//
//        this.pm = MyPersistenceManager.getPersistenceManager();
//        this.tx = this.pm.currentTransaction();
//	}
	

	@Test
	public void test() {
//		try {
//			tx.begin();
//			//Saving user
//			System.out.println("Persisting users");
//			Guest guest = new Guest("0", "Test", "email@email.email", "Password", 11223344, "Test Street");
//			System.out.println("> " + guest);
//			pm.makePersistent(guest);
//			System.out.println("");
//			
//			//User Extend
//			System.out.println("Retrieving Extent for Users");
//            Extent<Guest> e = pm.getExtent(Guest.class, true);
//            Iterator<Guest> iter = e.iterator();
//            if(iter.hasNext()) {
//           	 Object obj = iter.next();
//           	 System.out.println("> " +obj);
//            }
//            System.out.println("");
//            
//            //User Query
//            System.out.println("Executing Query for getting the user 'Test'");
//			Query<Guest> q = pm.newQuery(e,"name=='Test'");
//			for(Guest g : (List<Guest>) q.execute()) {
//				System.out.println("> " +g);
//			}
//			System.out.println("");
//			
//			//User deletion
//			System.out.println("Delete the Test user");
//            long deletedUsers = q.deletePersistentAll();
//            System.out.println("Deleted Users: " + deletedUsers);
//            System.out.println("");
//            
//			tx.commit();
//		}catch(Exception e) {
//        	System.out.println("Exception thrown during retrieval of Extent : " + e.getMessage());
//		}finally {
//			 if (tx.isActive())
//	            {
//	                tx.rollback();
//	            }
//	            pm.close();
//	    }
//		System.out.println("");
		fail("Not implemented yet");
	}
	
//	@After
//    public void tearDown() throws Exception {
//		
//        if (this.pm != null) {
//			this.pm.close();
//		}
//		
//    }
}
