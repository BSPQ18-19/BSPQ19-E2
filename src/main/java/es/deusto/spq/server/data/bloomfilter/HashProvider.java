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
		if(object == null)
			return null;
        MessageDigest messageDigest = null;
        byte [] before = null;
        byte [] hashInBytes = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			before = toByteArray(object);
			hashInBytes = messageDigest.digest(before);
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn("SHA-1: " + e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn("SHA-1: " + e.getMessage());
		}
        return bytesToHex(hashInBytes);
	}
	
	/**Returns the SHA-256 of the given object.
	 * @param object the object to be hashed.
	 * @return the SHA-256 hash.
	 */
	public static String sha256(Object object) {
		if(object == null)
			return null;
        MessageDigest messageDigest = null;
        byte [] before = null;
        byte [] hashInBytes = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			before = toByteArray(object);
			hashInBytes = messageDigest.digest(before);
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn("SHA-256: " + e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn("SHA-256: " + e.getMessage());
		}
        return bytesToHex(hashInBytes);
	}

	/**Returns the SHA-384 of the given object.
	 * @param object the object to be hashed.
	 * @return the SHA-384 hash.
	 */
	public static String sha384(Object object) {
		if(object == null)
			return null;
		MessageDigest messageDigest = null;
        byte [] before = null;
        byte [] hashInBytes = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-384");
			before = toByteArray(object);
			hashInBytes = messageDigest.digest(before);
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn("SHA-384: " + e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn("SHA-384: " + e.getMessage());
		}
        return bytesToHex(hashInBytes);
	}
	
	/**Returns the SHA-512 of the given object.
	 * @param object the object to be hashed.
	 * @return the SHA-512 hash.
	 */
	public static String sha512(Object object) {
		if(object == null)
			return null;
		MessageDigest messageDigest = null;
        byte [] before = null;
        byte [] hashInBytes = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-512");
			before = toByteArray(object);
			hashInBytes = messageDigest.digest(before);
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn("SHA-512: " + e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn("SHA-512: " + e.getMessage());
		}
        return bytesToHex(hashInBytes);
	}
	
	/**Parses the object to an array of bytes.
	 * @param obj the object to be serialized.
	 * @return an array of bytes - the serialized object.
	 * @throws IOException launched while trying to serialize.
	 */
    public static byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
        return bytes;
    }
	
	/**Parses the hashed byte array to a human-readable hex string.
	 * @param bytes the byte array to be parsed.
	 * @return the parsed string.
	 */
	private static String bytesToHex(byte [] bytes) {
		if(bytes == null)
			return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
	}

}
