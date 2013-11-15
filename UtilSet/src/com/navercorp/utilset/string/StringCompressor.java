package com.navercorp.utilset.string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.navercorp.utilset.exception.InternalExceptionHandler;

import android.net.Uri;
import android.util.Log;

public class StringCompressor {
	private static final int BUFFER_SIZE = 2 * 1024;
	
	public String compress(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		
		String compressBase64EncodeStr = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		GZIPOutputStream gzos = null;
		
		try {
			is = new ByteArrayInputStream(str.getBytes("UTF-8"));
			baos = new ByteArrayOutputStream();
			gzos = new GZIPOutputStream(baos);
			
			byte buffer[] = new byte[BUFFER_SIZE];
			int count = -1;
			while((count = is.read(buffer, 0,  BUFFER_SIZE)) != -1) {
            	gzos.write(buffer, 0, count);
			}
			gzos.finish();
			
			compressBase64EncodeStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
		} catch (Exception e) {
			InternalExceptionHandler.handlingException(e, getClass(), "compress");
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				
				if (gzos != null) {
					gzos.close();
				}
			} catch (Exception e) {
				InternalExceptionHandler.handlingException(e, getClass(), "compress");
			}
		}
		
		return compressBase64EncodeStr;
	}
	
	private static String urlDecode(String src) {
		String dst = null;
		try {
			dst = URLDecoder.decode(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e("UtilSet", "An UnsupportedEncodingException occured in " + StringCompressor.class + ".urlDecode. However, it does not terminate app and continues decode with default encoding character set.");
			dst = Uri.decode(src);
//			 dst = URLDecoder.decode(src);
		}
		
		return dst;
	}
	
	public String decompress(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		
		String decompressStr = null;
		InputStream is = null;
		GZIPInputStream gzis = null;
		ByteArrayOutputStream baos = null;
		
		try {
			is = new ByteArrayInputStream(Base64.decode(urlDecode(str).getBytes(), Base64.DEFAULT));
			gzis = new GZIPInputStream(is);
			baos = new ByteArrayOutputStream();
			
			byte buffer[] = new byte[BUFFER_SIZE];
			int count = -1;
			while((count = gzis.read(buffer, 0,  BUFFER_SIZE)) != -1) {
            	baos.write(buffer, 0, count);
			}
			
			decompressStr = new String(baos.toByteArray(), "UTF-8");
		} catch (Exception e) {
			InternalExceptionHandler.handlingException(e, getClass(), "decompress(String)");
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
				
				if (gzis != null) {
					gzis.close();
				}
			} catch (Exception e) {
				InternalExceptionHandler.handlingException(e, getClass(), "decompress(String)");
			}
		}
		
		return decompressStr;
	}
	
	public InputStream decompress(InputStream is) {
		if (is == null) {
			return is;
		}
		
		InputStream retIs = null;
		ByteArrayInputStream bais = null;
		GZIPInputStream gzis = null;
		ByteArrayOutputStream baos = null;
		
		try {
			bais = new ByteArrayInputStream(Base64.decode(convertStreamToByteArray(is), Base64.DEFAULT));
			gzis = new GZIPInputStream(bais);
			baos = new ByteArrayOutputStream();
			
			byte buffer[] = new byte[BUFFER_SIZE];
			int count = -1;
			while((count = gzis.read(buffer, 0,  BUFFER_SIZE)) != -1) {
            	baos.write(buffer, 0, count);
			}
			
			retIs = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			InternalExceptionHandler.handlingException(e, getClass(), "decompress(InputStream)");
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
				
				if (gzis != null) {
					gzis.close();
				}
			} catch (Exception e) {
				InternalExceptionHandler.handlingException(e, getClass(), "decompress(InputStream)");
			}
		}
		
		return retIs;
	}
	
	private byte[] convertStreamToByteArray(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;

	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }

	    is.close();

	    return sb.toString().getBytes();
	}
}