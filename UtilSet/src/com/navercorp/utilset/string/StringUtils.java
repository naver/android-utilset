package com.navercorp.utilset.string;


/**
 * StringUtils provides string related function<br>
 * By using compressString and deompressString, string long and has repeated part can be shrunk  
 *
 */
public class StringUtils {
	private static StringCompressor stringCompressor;
	
	static {
		stringCompressor = new StringCompressor();
	}
	
	public static String compressString(String stringToBeCompressed) {
		return stringCompressor.compress(stringToBeCompressed);
	}
	
	public static String decompressString(String stringToBeDecompressed) {
		return stringCompressor.decompress(stringToBeDecompressed);
	}
}
