package es.deusto.spq;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;import javax.sound.sampled.LineListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class MockitoExampleTest {

	@Test
	public void example1Test() {
		List mockedList = mock(List.class);
		
		mockedList.add("one");
		mockedList.clear();
		
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}
	
	@Test
	public void example2Test() {
		LinkedList mockedList = mock(LinkedList.class);
		
		when(mockedList.get(0)).thenReturn("first");
		
		Assert.assertEquals("first", mockedList.get(0));
	}

}
