package com.navercorp.utilset.storage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.navercorp.utilset.exception.InternalExceptionHandler;

/**
 * 
 * @author jaemin.woo
 *
 */
public class FileUtils {
	/**
	 * Utility method to create and copy the content to a file system.
	 *
	 * @param destFilePath absolute file path
	 * @param fileName file name to create
	 * @param source file content
	 * @return true if file was created successfully otherwise false
	 */
	public static boolean createAndCopyContentToFile(String destFilePath, String fileName, InputStream source) {
		try {

			File file = new File(destFilePath);
			file.mkdirs();

			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			copyFromInputStreamToOutputStream(source, arrayOutputStream);

			ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());

			file = new File(destFilePath + File.separator + fileName);
			copyFromInputStreamToOutputStream(arrayInputStream, new FileOutputStream(file));
			return true;
		} catch (Exception e) {
			InternalExceptionHandler.handlingException(e, DiskUtils.class, "createAndCopyContentToFile");
			// String msg = e.getMessage();
			return false;
		}
	}
	
	private static long copyFromInputStreamToOutputStream(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int)count;
	}
	
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
	private static long copyLarge(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Delete file from a given path
	 * @param path File path to be deleted
	 * @return true if deleting file was successful; false otherwise
	 */
	public static boolean delete(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.delete();
		}
		
		return false;
	}
	
	/**
	 * Checks if a given file exists
	 * @param path File path to be inspected
	 * @return true if file is exists; false otherwise
	 */
	public static boolean exist(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * Returns file content
	 * @param path File path to be read
	 * @return String containing file content
	 */
	public static String getFileContent(String path) {
		File file = new File(path);
		StringBuilder text = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			InternalExceptionHandler.handlingException(e, DiskUtils.class, "getFileContent");
			return null;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					InternalExceptionHandler.handlingException(e, DiskUtils.class, "getFileContent");
					return null;
				}
		}
		return text.toString();
	}
}
