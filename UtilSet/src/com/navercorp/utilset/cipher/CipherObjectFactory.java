package com.navercorp.utilset.cipher;

class CipherObjectFactory {
	private CipherObjectFactory() {}
	
	public static CipherObject getInstance(CipherMode mode) {
		if (CipherMode.AES == mode) {
			return new AesCipher();
		}
		return new AesCipher();
	}
}
