package com.navercorp.utilset.cipher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CipherObjectFactoryTest {

	@Before
	public void setUp() {
	}
	
	@Test
	public void testCipherObjectFactory() {
		CipherObject co = CipherObjectFactory.getInstance(CipherMode.AES);
		assertNotNull(co);
		assertTrue(co instanceof AesCipher);
	}
}
