package com.navercorp.utilset.cipher;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.navercorp.utilset.exception.InternalExceptionHandler;

public class AesCipher implements CipherObject {
	private final static int JELLY_BEAN_MR1 = 17;

	@Override
	public String encrypt(String seed, String plaintext) {
		try {
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] result = encrypt(rawKey, plaintext.getBytes());
			return toHex(result);
		} catch (Exception e) {
			InternalExceptionHandler.handlingException(e, getClass(), "encrypt");
			return null;
		}
	}

	@Override
	public String decrypt(String seed, String ciphertext) {
		try {
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] enc = toByte(ciphertext);
			byte[] result = decrypt(rawKey, enc);
			return new String(result);
		} catch (Exception e) {
			InternalExceptionHandler.handlingException(e, getClass(), "decrypt");
			return null;
		}
	}

	private static byte[] getRawKey(byte[] seed) throws GeneralSecurityException {
		KeyGenerator kgen = null;

		try {
			kgen = KeyGenerator.getInstance("AES");
			SecureRandom sr = null;
			if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN_MR1) {
				sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
			} else {
				sr = SecureRandom.getInstance("SHA1PRNG");
			}
			sr.setSeed(seed);
			kgen.init(128, sr); // 192 and 256 bits may not be available
		} catch (NoSuchAlgorithmException ex) {
			// Impossible
		}

		if (kgen != null) {
			SecretKey skey = kgen.generateKey();
			return skey.getEncoded();
		} else {
			return null;
		}
	}

	private byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	private byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}
		return result;
	}

	private String toHex(byte[] buf) {
		if (buf == null) {
			return "";
		}
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte bt) {
		sb.append(HEX.charAt((bt >> 4) & 0x0f)).append(HEX.charAt(bt & 0x0f));
	}
}
