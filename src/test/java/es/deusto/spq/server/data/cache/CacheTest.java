package es.deusto.spq.server.data.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class CacheTest {

	private static Cache<Integer, Integer> cache;
	private static int maximumNumberElements = 2;
	private static Integer key1 = 1;
	private static Integer value1 = 1;
	private static Integer key2 = 2;
	private static Integer value2 = 2;
	private static Integer key3 = 3;
	private static Integer value3 = 3;
	
	@BeforeClass
	public static void initialize() {
		cache = new Cache<Integer, Integer>();
	}
	
	@Before
	public void setUp() {
		cache = new Cache<Integer, Integer>(maximumNumberElements);
	}
	
	@After
	public void tearDown() {
		cache.clear();
	}
	
	@Test
	public void getSetTest() {
		cache.set(key1, value1);
		Assert.assertEquals(value1, cache.get(key1));
		Assert.assertEquals(null, cache.get(key2));
	}
	
	@Test
	public void tooManyElementsTest() {
		cache.set(key1, value1);
		cache.set(key2, value2);
		Assert.assertEquals(value1, cache.get(key1));
		Assert.assertEquals(value2, cache.get(key2));
		Assert.assertNull(cache.get(key3));
		cache.set(key3, value3); //Removes <key1, value1>
		Assert.assertNull(cache.get(key1));
		Assert.assertEquals(value2, cache.get(key2));
		Assert.assertEquals(value3, cache.get(key3));
	}
	
	@Test
	public void containsTest() {
		cache.set(key1, value1);
		Assert.assertTrue(cache.contains(key1));
	}

	@Test
	public void removeTest() {
		cache.set(key1, value1);
		cache.remove(key1);
		Assert.assertFalse(cache.contains(key1));
	}
	
	@Test
	public void maximumNumberElementsTest() {
		Assert.assertEquals(maximumNumberElements, cache.getMaximumNumberOfElements());
	}
	
	@Test
	public void currentNumberElementsTest() {
		Assert.assertEquals(0, cache.getCurrentNumberOfElements());
		cache.set(key1, value1);
		Assert.assertEquals(1, cache.getCurrentNumberOfElements());
		cache.set(key2, value2);
		Assert.assertEquals(2, cache.getCurrentNumberOfElements());
		cache.set(key3, value3);
		Assert.assertEquals(2, cache.getCurrentNumberOfElements());
	}
}
