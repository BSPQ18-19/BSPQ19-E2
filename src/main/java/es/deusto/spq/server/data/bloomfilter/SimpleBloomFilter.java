package es.deusto.spq.server.data.bloomfilter;

import java.util.BitSet;

import org.apache.log4j.Logger;

import es.deusto.spq.server.logger.ServerLogger;

/**A simple implementation of a BloomFilter.
 * <p>
 * The functionality is as simple as {@code add}, {@code merge}, {@code contains} and {@code clear}.
 *  
 * @author Iker
 *
 * @param <T> the class this filter will work with.
 */
public class SimpleBloomFilter<T> {

	/** The set bits of hashes. */
	private BitSet hashes;
	/** The size (in bytes) of the bloom filter (default value: 1MiB). */
	private int size = 1024 * 1024 * 8;
	/** The number of hash functions used in the current filter. */
	private int numberHashFunctions = 3;
	/** The maximum number of elements to be stored (-1 == no limit). */
	private int maximumNumberElements = -1;
	/** Number of elements that have been added. */
	private int numberAddedElements = 0;
	/** The logger to log to. */
	private Logger log;
	
	/**Creates a new instance of the {@code SimpleBloomFilter}.
	 * The default size is used to build the filter: 1MiB.
	 */
	public SimpleBloomFilter() {
		hashes = new BitSet(size);
		log = ServerLogger.getLogger();
	}
	
	/**Creates a new instance of the {@code SimpleBloomFilter}.
	 * @param maximumNumberElements the maximum number of elements the filter can support.
	 */
	public SimpleBloomFilter(int maximumNumberElements) {
		if(maximumNumberElements > 0)
			this.maximumNumberElements = maximumNumberElements;
		hashes = new BitSet(size);
		log = ServerLogger.getLogger();
	}
	
	/**Adds the object to the filter.
	 * @param object the object to be added.
	 */
	public void add(T object) {
		// Adds the object to the filter either if there's no maximum number of elements or it has not been reached 
		if(maximumNumberElements == -1 || 
				(maximumNumberElements > 0 && numberAddedElements < maximumNumberElements)) {
			int hashCode = object.hashCode();
			hashes.set(Math.abs(hashCode) % size);
			String sha1 = HashProvider.sha1(object);
			hashes.set(Math.abs(sha1.hashCode()) % size);
			String sha256 = HashProvider.sha256(object);
			hashes.set(Math.abs(sha256.hashCode()) % size);
			numberAddedElements += 1;
			log.debug(object.getClass().getName() + " object added to the filter");
		} else {
			log.warn("Maximum number of elements exceeded");
		}
	}
	
	/**Merges the given SimpleBloomFilter into the current one. Note that two filters must have the same size and 
	 * use the same hash functions to be able to be merged.
	 * @param toBeMerged the filter to be merged.
	 * @throws IllegalArgumentException launched when {@code toBeMerged} is null or does not fulfill any of the 
	 * requirements described above (same size and hash functions).
	 */
	public void merge(SimpleBloomFilter<T> toBeMerged) throws IllegalArgumentException {
		if(toBeMerged == null || toBeMerged.getSize() != size || toBeMerged.getNumberHashFunctions() != numberHashFunctions)
			throw new IllegalArgumentException("Filter's size and hash functions must be equal to be merged");
		hashes.or(toBeMerged.getHashes());
		log.info("Merged another SimpleBloomFilter");
	}

	/**Returns whether the object has been stored before in the filter. Note that there's chance of returning false positives. 
	 * @param object the object to be checked.
	 * @return {@code false} when the object has not been added before, and 
	 * 			{@code true} when the object has been added (or a false positive).
	 */
	public boolean contains(T object) {
		int hashCode = object.hashCode();
		if(hashes.get(hashCode % size) || hashes.get((hashCode >> 16) % size) || hashes.get((hashCode >> 8) % size))
			return true;
		return false;
	}
	
	/**Clears the filter.
	 */
	public void clear() {
		hashes.clear();
		numberAddedElements = 0;
		log.info("Filter cleared");
	}

	/**Returns the size of the filter.
	 * @return the size in bytes.
	 */
	public int getSize() {
		return size;
	}
	
	/**Returns the number of hash functions that are being used in the current filter.
	 * @return the number of hash functions.
	 */
	public int getNumberHashFunctions() {
		return numberHashFunctions;
	}
	
	/**Returns the hashes of the filter.
	 * @return the BitSet of hashes.
	 */
	public BitSet getHashes() {
		return hashes;
	}
	
}
