package es.deusto.spq.server.data.bloomfilter;

import java.util.BitSet;

import org.apache.log4j.Logger;

import es.deusto.spq.server.logger.ServerLogger;

public class SimpleBloomFilter<T> {

	private BitSet hashes;
	private int size = 1024 * 1024 * 8; // Default size: 1 MiB
	private int maximumNumberElements = -1;
	private int numberAddedElements = 0;
	private Logger log;
	
	public SimpleBloomFilter() {
		hashes = new BitSet(size);
		log = ServerLogger.getLogger();
	}
	
	public SimpleBloomFilter(int maximumNumberElements) {
		if(maximumNumberElements > 0)
			this.maximumNumberElements = maximumNumberElements;
		hashes = new BitSet(size);
		log = ServerLogger.getLogger();
	}
	
	public void add(T object) {
		// Adds the object to the filter either if there's no maximum number of elements or it has not been reached 
		if(maximumNumberElements == -1 || 
				(maximumNumberElements > 0 && numberAddedElements < maximumNumberElements)) {
			int hashCode = object.hashCode();
			hashes.set(hashCode % size);
			String sha1 = HashProvider.sha1(object);
			hashes.set(sha1.hashCode() % size);
			String sha256 = HashProvider.sha256(object);
			hashes.set(sha256.hashCode() % size);
			numberAddedElements += 1;
			log.debug(object.getClass().getName() + " object added to the filter");
		} else {
			log.warn("Maximum number of elements exceeded");
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
		log.info("Filter cleared");
	}
	
}
