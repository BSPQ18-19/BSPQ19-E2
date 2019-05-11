package es.deusto.spq.server.data.bloomfilter;

import java.util.BitSet;

public class SimpleBloomFilter<T> {

	private BitSet hashes;
	private int size = 1024 * 1024 * 8; // 1 MiB
	private int maximumNumberElements = -1;
	private int numberAddedElements = 0;
	
	public SimpleBloomFilter() {
		hashes = new BitSet(size);
	}
	
	public SimpleBloomFilter(int maximumNumberElements) {
		if(maximumNumberElements > 0)
			this.maximumNumberElements = maximumNumberElements;
		hashes = new BitSet(size);
	}
	
	public void add(T object) {
		if(maximumNumberElements > 0) {
			int hashCode = object.hashCode();
			hashes.set(hashCode % size);
			hashes.set((hashCode >> 16) % size); // TODO implement a nice hash function
			hashes.set((hashCode >> 8) % size); // TODO implement a nice hash function
			numberAddedElements += 1;
		}
	}
	
	public boolean contains(T object) {
		int hashCode = object.hashCode();
		if(hashes.get(hashCode % size) || hashes.get((hashCode >> 16) % size) || hashes.get((hashCode >> 8) % size))
			return true;
		return false;
	}
	
	public void clear() {
		hashes.clear();
		numberAddedElements = 0;
	}
	
}
