package com.navercorp.utilset.cipher;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import com.navercorp.utilset.cipher.CipherMode;
import com.navercorp.utilset.cipher.CipherUtils;

/**
 * @author jaemin.woo
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CipherUtilsTest {
	private static String SEED = "SEED";
	private static String PLAIN_TEXT = "PLAIN_TEXT";

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
	}
	
	private String encrypt(String seed, String plainText) {
		CipherUtils cipherUtils = new CipherUtils(CipherMode.AES);
		return cipherUtils.encrypt(seed, plainText);
	}
	
	@Test
	public void shouldEncryptPlainTextWithAESCipher() {
		String encrypted = encrypt(SEED, PLAIN_TEXT);
		assertThat(encrypted, is(not(PLAIN_TEXT)));
	}
	
	private String decrypt(String seed, String cipherText) {
		CipherUtils cipherUtils = new CipherUtils(CipherMode.AES);
		return cipherUtils.decrypt(seed, cipherText);
	}
	
	@Test
	public void shouldDecryptCipherTextWithAESCipher() {
		String encrypted = encrypt(SEED, PLAIN_TEXT);
		String decrypted = decrypt(SEED, encrypted);
		assertEquals(PLAIN_TEXT, decrypted);
	}
}
