package es.deusto.spq.server.data.cache;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class NodeTest {

	private static Node<Integer, Integer> node;
	private static Integer key1 = 1;
	private static Integer value1 = 1;
	private static Integer key2 = 2;
	private static Integer value2 = 2;

	@Before
	public void initialize() {
		node = new Node<Integer, Integer>(key1, value1);
	}
	
	@Test
	public void keyTest() {
		Assert.assertEquals(key1, node.getKey());
		node.setKey(key2);
		Assert.assertEquals(key2, node.getKey());
	}
	
	@Test
	public void valueTest() {
		Assert.assertEquals(value1, node.getValue());
		node.setValue(value2);
		Assert.assertEquals(value2, node.getValue());
	}
	
}
