package com.navercorp.utilset.cipher;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author jaemin.woo
 *
 */
public class AesCipher implements CipherObject {
	private final static int JELLY_BEAN_MR1 = 17;

	@Override
	public String encrypt(String seed, String plaintext) {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, plaintext.getBytes());
		return toHex(result);
	}

	@Override
	public String decrypt(String seed, String ciphertext) {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(ciphertext);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
		
	}

	private static byte[] getRawKey(byte[] seed) {
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
		} catch (NoSuchAlgorithmException e) {
			// Impossible
		} catch (NoSuchProviderException e) {
			// Android whose version is equal or above than JELLY_BEAN_MR1 provides Crypto provider
			// If it doesn't, this method can work as it is supposed to do
			throw new RuntimeException(e);
		}

		if (kgen != null) {
			SecretKey skey = kgen.generateKey();
			return skey.getEncoded();
		} else {
			return null;
		}
	}

	private byte[] encrypt(byte[] raw, byte[] clear) {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher;
		byte [] encrypted = null;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encrypted = cipher.doFinal(clear);
		} catch (NoSuchAlgorithmException e) {
			// Android provides AES Encryption by default
		}
		// Exceptions under here occurs because of programming error
		catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		}
		
		return encrypted;
	}

	private byte[] decrypt(byte[] raw, byte[] encrypted) {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher;
		byte [] decrypted = null;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			decrypted = cipher.doFinal(encrypted);
		} catch (NoSuchAlgorithmException e) {
			// Can't be happened
		}
		// Exceptions under here occurs because of programming error
		catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		}
		
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
