package es.deusto.spq.server.data.cache;

import java.util.HashMap;
import java.util.Map;

/**A simple LFU (Least Frequently Used) cache to be used in the server.
 * <p>
 * Keeps track of the most most and least used data, and all the operations (get, set, 
 * clear) work in O(1) time. Uses O(n) space, where n is the number of mapping values.
 *  
 * @author Iker
 *
 * @param <K> The key's to map from.
 * @param <V> The value's class to map to.
 */
public class Cache<K, V> {

	/** Map to map values. */
	private Map<K, Node<K, V>> map;
	/** The head of the list (most used value). */
	private Node<K, V> head;
	/** The tail of the list (least used value). */
	private Node<K, V> tail;
	/** The maximum number of elements allowed in the cache. Default value == 1000. */
	private int maximumNumberElements = 1000;
	/** The current number of elements in the cache. */
	private int currentNumberElements = 0;
	
	/**Creates a new instance of a Cache with the given number of elements.
	 * Note that since no maximum number of elements has been set, the default value is 
	 * used (1000).
	 */
	public Cache() {
		this.map = new HashMap<K, Node<K, V>>(maximumNumberElements);
	}
	
	/**Creates a new instance of a Cache with the given number of elements.
	 * @param maximumNumberOfElements
	 */
	public Cache(int maximumNumberOfElements) {
		this.maximumNumberElements = maximumNumberOfElements;
		this.map = new HashMap<K, Node<K, V>>(maximumNumberElements);
	}
	
	/**Sets the mapping between the given key and given value.
	 * @param key The key to map from.
	 * @param value The value to map to.
	 */
	public void set(K key, V value) {
		if(key == null || value == null)
			return;
		if(map.containsKey(key)) {
			map.get(key).setValue(value);
		} else {
			if(currentNumberElements == maximumNumberElements)
				removeLastNode();
			Node<K, V> tmp = new Node<K, V>(key, value);
			map.put(key, tmp);
			if(currentNumberElements == 0) {
				head = tmp;
				tail = tmp;
			} else {
				tmp.next = head;
				head.previous = tmp;
				head = tmp;
			}
			currentNumberElements++;
		}
	}
	
	/**Returns the value mapping from the given key. If the key does not exist in the cache, 
	 * a null is return.
	 * @param key The key mapping from to the value.
	 * @return {@code null} if the key does not exist, and the value if it does.
	 */
	public V get(K key) {
		if(key != null && map.containsKey(key)) {
			Node<K, V> resultNode = map.get(key);
			moveFront(resultNode);
			return (V) resultNode.getValue();
		}
		return null;
	}
	
	/**Returns whether the given key is currently in the cache.
	 * @param key The key to be checked.
	 * @return {@code true} if the key is in the cache.
	 * 			{@code false} if not.
	 */
	public boolean contains(K key) {
		if(key != null && map.containsKey(key))
			return true;
		return false;
	}
	
	/**Removes the key from the cache.
	 * @param key The key to be removed.
	 */
	public void remove(K key) {
		if(key == null || !map.containsKey(key))
			return;
		Node<K, V> node = map.get(key);
		if(node == tail)//Node at the tail
			removeLastNode();
		else if(node == head) {//Node at the head
			head.next.previous = null;
			head = node.next;
			node.next = null;
			map.remove(key);
			currentNumberElements--;
		} else {//Node somewhere else
			node.previous.next = node.next;
			node.next.previous = node.previous;
			node.next = null;
			node.previous = null;
			map.remove(key);
			currentNumberElements--;
		}
	}
	
	/**Moves the given node to the front of the list, setting it as the most recently used value.
	 * @param node The node to be updated.
	 */
	private void moveFront(Node<K, V> node) {
		if(node.previous == null)//Node already in the front
			return;
		if(node.next == null) {//Node is at the tail
			node.previous.next = null;
			tail = node.previous;
			node.previous = null;
			node.next = head;
			head.previous = node;
			head = node;
		} else {//Node between another two
			node.previous.next = node.next;
			node.next.previous = node.previous;
			node.previous = null;
			node.next = head;
			head = node;
		}
	}
	
	/**Removes the last node (least frequently used value) from the list. */
	private void removeLastNode() {
		if(tail.previous == null)
			return;
		map.remove(tail.getKey());
		tail = tail.previous;
		tail.next.previous = null;
		tail.next = null;
		currentNumberElements--;
	}
	
	/**Returns the maximum number of elements allowed in the cache.
	 * @return The maximum number of elements.
	 */
	public int getMaximumNumberOfElements() {
		return maximumNumberElements;
	}
	
	/**Returns the current number of elements in the cache.
	 * @return The current number of elements.
	 */
	public int getCurrentNumberOfElements() {
		return currentNumberElements;
	}
	
	/**Clears the cache.
	 */
	public void clear() {
		map.clear();
		head = null;
		tail = null;
		currentNumberElements = 0;
	}
	
	@Override
	public String toString() {
		if(currentNumberElements == 0) {
			return "Cache (0/" + maximumNumberElements + ")";
		}
		StringBuilder str = new StringBuilder();
		str.append("Cache (" + currentNumberElements + "/" + maximumNumberElements + ") - ");
		Node<K, V> node = head;
		while(node != null) {
			str.append("[" + node.getKey() + ":" + node.getValue() + "]");
			node = node.next;
		}
		return str.toString();
	}
}
