package com.navercorp.utilset.storage;

import android.content.Context;
import android.os.Environment;

/**
 * <b>Cautious!</b><br>
 * External disk do not necessarily mean that it is a SD Card.<br>
 * Any large size of space can be external disk.<br>
 * As such, do not make assumptions that you are working on SDCard. 
 * 
 * @author jaemin.woo
 */
public final class DiskUtils {
	private static final String DATA_FOLDER = "/Android/data/";
	protected static final String TEMPORARY_FOLDER = "/temp/";
	protected static final String CACHE_FOLDER = "/cache/";

	/**
	 * Checks if External storage is mounted
	 * @return true if mounted; false otherwise
	 */
	public static boolean isExternalStorageMounted() {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * Returns directory name to cache data in the external storage
	 * @param context Context to get external storage information
	 * @return String containing cache directory name
	 */
	public static String getExternalDirPath(Context context) {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + DATA_FOLDER + context.getPackageName() + CACHE_FOLDER;
	}

	/**
	 * Returns directory name for temporary files in the external storage for temporary
	 * @param context Context to get external storage information
	 * @return String containing temporary directory name
	 */
	public static String getExternalTemporaryDirPath(Context context) {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + DATA_FOLDER + context.getPackageName() + TEMPORARY_FOLDER;
	}


	/**
	 * Returns root directory of the external storage.
	 * @param context Context to get external storage information
	 * @return String containing external root directory name
	 */
	public static String getExternalContextRootDir(Context context) {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + DATA_FOLDER + context.getPackageName();
	}
}