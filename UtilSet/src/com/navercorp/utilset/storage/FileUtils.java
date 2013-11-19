package com.navercorp.utilset.storage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

/**
 * 
 * @author jaemin.woo
 *
 */
public class FileUtils {
	private static final String TAG = "FileUtils";
	/**
	 * Utility method to create and copy the content to a file system.
	 *
	 * @param destFilePath absolute file path
	 * @param fileName file name to create
	 * @param source file content
	 * @return true if file was created successfully otherwise false
	 */
	public static boolean createAndCopyContentToFile(String destFilePath, String fileName, InputStream source) {
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			
			File file = new File(destFilePath);
			file.mkdirs();
			try {
				copyFromInputStreamToOutputStream(source, arrayOutputStream);
			} catch (IOException e) {
				Log.e(TAG, "Failed to copy InputStream to OutputStream");
				return false;
			}
			
			ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());

			file = new File(destFilePath + File.separator + fileName);
			try {
				copyFromInputStreamToOutputStream(arrayInputStream, new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				Log.e(TAG, String.format("Failed to open output file \"%s\"", file.getAbsolutePath()));
				file.delete();
				return false;
			} catch (IOException e) {
				Log.e(TAG, String.format("Exception occured while writing file \"%s\"", file.getAbsolutePath()));
				file.delete();
				return false;
			} finally {
				try {
					arrayOutputStream.close();
				} catch (IOException e) {
					throw new RuntimeException("Exception occured while closing arrayOutputStream");
				}
			}
			
			return true;
	}
	
	private static long copyFromInputStreamToOutputStream(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		
		output.close();
		
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
			Log.e(TAG, String.format("Error occured while reading contents from \"%s\"", file.getName()));
			return null;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// This barely happens
					throw new RuntimeException(String.format("Failed to close filed \"%s\"", file.getName()));
				}
		}
		
		return text.toString();
	}
}
