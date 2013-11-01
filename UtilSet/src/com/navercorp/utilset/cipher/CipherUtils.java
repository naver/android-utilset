package com.navercorp.utilset.cipher;

import com.navercorp.utilset.cipher.CipherObject;
import com.navercorp.utilset.cipher.CipherObjectFactory;
import com.navercorp.utilset.cipher.CipherMode;

public class CipherUtils {
	CipherMode cipherMode;
	CipherObject cipherObject;
	
	public CipherUtils() {
		this(CipherMode.AES);
	}
	
	public CipherUtils(CipherMode cipherMode) {
		this.cipherMode = cipherMode;
		cipherObject = CipherObjectFactory.getInstance(this.cipherMode);
	}

	/**
	 * 
	 * @param seed Seed string which is used for encryption and decryption  
	 * @param plaintext String to be encrypted
	 * @return ciphertext
	 */
	public String encrypt(String seed, String plainText) {
		return cipherObject.encrypt(seed, plainText);
	}

	/**
	 * 
	 * @param seed Seed string which is used for encryption and decryption
	 * @param cipherText String encrypted by encrypt method 
	 * @return plaintext
	 */
	public String decrypt(String seed, String cipherText) {
		return cipherObject.decrypt(seed, cipherText);
	}
}
