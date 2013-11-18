package com.navercorp.utilset.string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
			while ((count = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
				gzos.write(buffer, 0, count);
			}
			gzos.finish();
			compressBase64EncodeStr = new String(Base64.encode(
					baos.toByteArray(), Base64.DEFAULT));
		} catch (UnsupportedEncodingException e1) {
			// This can't be happened
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception occured while compressing String \"%s\"", str));
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException("Exception occured while closing InputStream");
				}
			}
			
			if (gzos != null) {
				try {
					gzos.close();
				} catch (IOException e) {
					throw new RuntimeException("Exception occured while closing GZIPOutputStream");
				}
			}
		}
		
		return compressBase64EncodeStr;
	}

	private static String urlDecode(String src) {
		String dst = null;
		try {
			dst = URLDecoder.decode(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e("UtilSet",
					"An UnsupportedEncodingException occured in "
							+ StringCompressor.class
							+ ".urlDecode. However, it does not terminate application and continues decode with default encoding character set.");
			dst = Uri.decode(src);
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
			is = new ByteArrayInputStream(Base64.decode(urlDecode(str)
					.getBytes(), Base64.DEFAULT));
			gzis = new GZIPInputStream(is);
			baos = new ByteArrayOutputStream();

			byte buffer[] = new byte[BUFFER_SIZE];
			int count = -1;
			while ((count = gzis.read(buffer, 0, BUFFER_SIZE)) != -1) {
				baos.write(buffer, 0, count);
			}

			decompressStr = new String(baos.toByteArray(), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception occured while decompressing string \"%s\""));
		} 
		 finally {
			 if (baos != null) {
					try {
						baos.close();
					} catch (IOException e) {
						throw new RuntimeException("Exception occured while closing ByteArrayOutputStream");
					}
				}
			 
			 if (gzis != null) {
					try {
						gzis.close();
					} catch (IOException e) {
						throw new RuntimeException("Exception occured while closing GZIPInputStream");
					}
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
			bais = new ByteArrayInputStream(Base64.decode(
					convertStreamToByteArray(is), Base64.DEFAULT));
			gzis = new GZIPInputStream(bais);
			baos = new ByteArrayOutputStream();

			byte buffer[] = new byte[BUFFER_SIZE];
			int count = -1;
			while ((count = gzis.read(buffer, 0, BUFFER_SIZE)) != -1) {
				baos.write(buffer, 0, count);
			}

			retIs = new ByteArrayInputStream(baos.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Exception occured while decompressing InputStream");
		}  finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					throw new RuntimeException("Exception occured while closing ByteArrayOutputStream");
				}
			}
			
			if (gzis != null) {
				try {
					gzis.close();
				} catch (IOException e) {
					throw new RuntimeException("Exception occured while closing GZIPInputStream");
				}
			}
		}

		return retIs;
	}

	private byte[] convertStreamToByteArray(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// Just bypasses exception so that the caller can handle finalization works or propagate exception 	
			throw e;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// This barely happens.
				// Just bypasses exception so that the caller can handle finalization works or propagate exception
				throw e;
			}
		}

		return sb.toString().getBytes();
	}
}