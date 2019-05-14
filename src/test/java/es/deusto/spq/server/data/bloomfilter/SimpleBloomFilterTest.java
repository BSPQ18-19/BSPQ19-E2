package es.deusto.spq.server.data.bloomfilter;

import java.io.Serializable;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class SimpleBloomFilterTest {

	private static SimpleBloomFilter<TestingClass> filter;
	private static TestingClass test1;
	private static TestingClass test2;
	
	@BeforeClass
	public static void initialize() {
		test1 = new TestingClass("T1");
		test2 = new TestingClass("T2");
	}
	
	@Before
	public void setUp() {
		filter = new SimpleBloomFilter<TestingClass>();
	}
	
	@Test
	public void addTest() {
		filter.add(test1);
		Assert.assertTrue(filter.contains(test1));
		filter.clear();
		Assert.assertFalse(filter.contains(test1));
	}
	
	@Test
	public void addingTooManyElementsTest() {
		filter = new SimpleBloomFilter<TestingClass>(1);
		filter.add(test1);//Maximum number of elements reached
		filter.add(test2);//Not added, too many elements added
		Assert.assertTrue(filter.contains(test1));
		Assert.assertFalse(filter.contains(test2));
	}

	@Test(expected = IllegalArgumentException.class) 
	public void mergeTest() {
		SimpleBloomFilter<TestingClass> filterTest1 = new SimpleBloomFilter<TestingClass>();
		filter.merge(filterTest1);//ok
		filter.merge(null);//IllegalArgumentException
	}
	
	private static class TestingClass implements Serializable {
		
		private static final long serialVersionUID = 1L;
		private String id = "ID";
		
		public TestingClass(String id) {
			this.id = id;
		}
		
		@Override
		public int hashCode() {
			return id.hashCode();
		}
	}

}
