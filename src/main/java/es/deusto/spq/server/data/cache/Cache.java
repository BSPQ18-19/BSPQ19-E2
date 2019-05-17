package es.deusto.spq.server.data.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {

	private Map<K, Node<K, V>> map;
	private Node<K, V> head;
	private Node<K, V> tail;
	private int maximumNumberElements = 1000;//Default maximum size == 1000
	private int currentNumberElements = 0;
	
	public Cache(int maximumNumberOfElements) {
		this.maximumNumberElements = maximumNumberOfElements;
		this.map = new HashMap<K, Node<K, V>>(maximumNumberElements);
	}
	
	public void set(K key, V value) {
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
	
	public V get(K key) {
		if(map.containsKey(key)) {
			Node<K, V> resultNode = map.get(key);
			moveFront(resultNode);
			return (V) resultNode.getValue();
		}
		return null;
	}
	
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
	
	private void removeLastNode() {
		if(tail.previous == null)
			return;
		map.remove(tail.getKey());
		tail = tail.previous;
		tail.next.previous = null;
		tail.next = null;
		currentNumberElements--;
	}
	
	public int getMaximumNumberOfElements() {
		return maximumNumberElements;
	}
	
	public int getCurrentNumberOfElements() {
		return currentNumberElements;
	}
	
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
