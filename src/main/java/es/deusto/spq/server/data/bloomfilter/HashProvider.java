package es.deusto.spq.server.data.bloomfilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.deusto.spq.server.logger.ServerLogger;

/**A simple class to provide some hasing functions.
 * 
 * @author Iker
 *
 */
public class HashProvider {

	/**Returns the SHA-1 of the given object.
	 * @param object the object to be hashed.
	 * @return the SHA-1 hash.
	 */
	public static String sha1(Object object) {
		if(object != null) {
			MessageDigest messageDigest = null;
			try {
				messageDigest = MessageDigest.getInstance("SHA-1");
				byte [] serializedObject = toByteArray(object);
				return new String(messageDigest.digest(serializedObject).toString());
			} catch (NoSuchAlgorithmException e) {
				ServerLogger.getLogger().warn("SHA-1 1: " + e.getMessage());
			} catch (IOException e) {
				ServerLogger.getLogger().warn("SHA-1 2: " + e.getMessage());
			}
		}
		return new String("");
	}
	
	/**Returns the SHA-256 of the given object.
	 * @param object the object to be hashed.
	 * @return the SHA-256 hash.
	 */
	public static String sha256(Object object) {
		if(object != null) {
			MessageDigest messageDigest = null;
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
				byte [] serializedObject = toByteArray(object);
				return new String(messageDigest.digest(serializedObject).toString());
			} catch (NoSuchAlgorithmException e) {
				ServerLogger.getLogger().warn("SHA-256 1: " + e.getMessage());
			} catch (IOException e) {
				ServerLogger.getLogger().warn("SHA-256 2: " + e.getMessage());
			}
		}
		return new String("");
	}

	/**Returns the SHA-384 of the given object.
	 * @param object the object to be hashed.
	 * @return the SHA-384 hash.
	 */
	public static String sha384(Object object) {
		if(object != null) {
			MessageDigest messageDigest = null;
			try {
				messageDigest = MessageDigest.getInstance("SHA-384");
				byte [] serializedObject = toByteArray(object);
				return new String(messageDigest.digest(serializedObject).toString());
			} catch (NoSuchAlgorithmException e) {
				ServerLogger.getLogger().warn("SHA-384: " + e.getMessage());
			} catch (IOException e) {
				ServerLogger.getLogger().warn("SHA-384: " + e.getMessage());
			}
		}
		return new String("");
	}
	
	/**Returns the SHA-512 of the given object.
	 * @param object the object to be hashed.
	 * @return the SHA-512 hash.
	 */
	public static String sha512(Object object) {
		if(object != null) {
			MessageDigest messageDigest = null;
			try {
				messageDigest = MessageDigest.getInstance("SHA-512");
				byte [] serializedObject = toByteArray(object);
				return new String(messageDigest.digest(serializedObject).toString());
			} catch (NoSuchAlgorithmException e) {
				ServerLogger.getLogger().warn("SHA-512: " + e.getMessage());
			} catch (IOException e) {
				ServerLogger.getLogger().warn("SHA-512: " + e.getMessage());
			}
		}
		return new String("");
	}
	
	/**Parses the object to an array of bytes.
	 * @param obj the object to be serialized.
	 * @return an array of bytes - the serialized object.
	 * @throws IOException launched while trying to serialize.
	 */
    public static byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }
	
}
