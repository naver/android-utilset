package com.navercorp.utilsettest.test.without_ui;

import org.junit.Before;
import org.junit.Test;

import com.navercorp.utilset.cipher.AesCipher;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AesCipherTest {
	String seedText = "I'm a kong";
	String seedText2 = "I'm a gong";
	String plainText = "This is a plaintext for test";
	AesCipher aesCipher;
	
	@Before
	public void setUp() {
		aesCipher = new AesCipher();
	}
	
	@Test
	public void testEncryption() {
		String encrypted = aesCipher.encrypt(seedText, plainText);
		assertThat(encrypted, not(plainText));
	}
	
	@Test
	public void testDecryption() {
		String encrypted = aesCipher.encrypt(seedText, plainText);
		String decrypted = aesCipher.decrypt(seedText, encrypted);
		assertEquals(plainText, decrypted);
	}
}
