package es.deusto.spq.server.data.bloomfilter;

import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class HashProviderTest {

	@Test
	public void sha1Test() {
		String object = "testing sha1 ...";
		String hashed = HashProvider.sha1(object);
		String expected = "28e29767e2373838d280859a6cf7af85db1f80b3";
		Assert.assertEquals(expected, hashed);
	}
	
	@Test
	public void sha256Test() {
		String object = "testing sha256 ...";
		String hashed = HashProvider.sha256(object);
		String expected = "528ea4ca24aff5f581fba1ffb6c14ba1695aef632c64009a09cd66518af4e308";
		Assert.assertEquals(expected, hashed);
	}
	
	@Test
	public void sha384Test() {
		String object = "testing sha384 ...";
		String hashed = HashProvider.sha384(object);
		String expected = "3860a89f812a36937c03e66c734e030ab9b638845584dfa20817491a683009af1e80b6f02a5c33b1442f99f68fa7af41";
		Assert.assertEquals(expected, hashed);
	}
	
	@Test
	public void sha512Test() {
		String object = "testing sha512 ...";
		String hashed = HashProvider.sha512(object);
		String expected = "4995373d33b6af7a2ad974c3eddc1752f8eed29fc01a48280ba58e7c274ed26d42ace22bc97ac442d325ab6bf3e0d71fd8f6be45f87e22c215cf1ff2d1761fb1";
		Assert.assertEquals(expected, hashed);
	}

}
