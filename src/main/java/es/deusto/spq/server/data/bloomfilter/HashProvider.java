package es.deusto.spq.server.data.bloomfilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.deusto.spq.server.logger.ServerLogger;

public class HashProvider {

	public static String sha1(Object object) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			byte [] serializedObject = serialize(object);
			return new String(messageDigest.digest(serializedObject));
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		}
		return new String("");
	}
	
	public static String sha256(Object object) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte [] serializedObject = serialize(object);
			return new String(messageDigest.digest(serializedObject));
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		}
		return new String("");
	}

	public static String sha384(Object object) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-384");
			byte [] serializedObject = serialize(object);
			return new String(messageDigest.digest(serializedObject));
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		}
		return new String("");
	}
	
	public static String sha512(Object object) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-512");
			byte [] serializedObject = serialize(object);
			return new String(messageDigest.digest(serializedObject));
		} catch (NoSuchAlgorithmException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		} catch (IOException e) {
			ServerLogger.getLogger().warn(e.getMessage());
		}
		return new String("");
	}
	
	private static byte[] serialize(Object obj) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(obj);
	    return out.toByteArray();
	}
	
}
