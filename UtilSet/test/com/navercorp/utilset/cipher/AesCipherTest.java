//package com.navercorp.utilset.cipher;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;
//import static org.junit.Assert.*;
//
//@RunWith(RobolectricTestRunner.class)
//public class AesCipherTest {
//	String seedText = "I'm a kong";
//	String seedText2 = "I'm a gong";
//	String plainText = "This is plaintext for test";
//	AesCipher aesCipher;
//	
//	@Before
//	public void setUp() {
//		aesCipher = new AesCipher();
//	}
//	
//	@Test
//	public void testEncryption() {
//		String encrypted = aesCipher.encrypt(seedText, plainText);
//		assertNotSame(plainText, encrypted);
//	}
//	
//	@Test
//	public void testDecryption() {
//		String encrypted = aesCipher.encrypt(seedText, plainText);
//		String decrypted = aesCipher.decrypt(seedText, encrypted);
//		assertSame(plainText, decrypted);
//	}
//}
