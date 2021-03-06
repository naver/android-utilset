package com.navercorp.utilset.cipher;


/**
 * @author jaemin.woo
 */
interface CipherObject {
	
	public String encrypt(String seed, String plainText);
	
	
	public String decrypt(String seed, String cipherText);
}
