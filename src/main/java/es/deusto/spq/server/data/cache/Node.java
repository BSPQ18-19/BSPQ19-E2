package es.deusto.spq.server.data.cache;

/**This Node class is to be used as a doubly LinkedList.
 * @author Iker
 *
 * @param <K> The key's class.
 * @param <V> The value's class.
 */
public class Node<K, V> {

	/** The key to map from. */
	private K key;
	/** The value to map to. */
	private V value;
	/** The previous node. */
	public Node<K, V> previous;
	/** The next node. */
	public Node<K, V> next;
	
	/**Creates a new node with the given key and value.
	 * @param key The key to map from.
	 * @param value The value to map to.
	 */
	public Node(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**Returns the key in this node.
	 * @return The key.
	 */
	public K getKey() {
		return key;
	}

	/**Updates the key in this node.
	 * @param key The key to be updated.
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**Returns the value in this node.
	 * @return The value.
	 */
	public V getValue() {
		return value;
	}

	/**Updates the value in this node.
	 * @param value The value to be updated.
	 */
	public void setValue(V value) {
		this.value = value;
	}
	
}
