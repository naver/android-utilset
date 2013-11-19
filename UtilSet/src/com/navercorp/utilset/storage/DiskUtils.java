package com.navercorp.utilset.storage;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * <b>Cautious!</b><br>
 * External disk do not necessarily mean that it is a SD Card.<br>
 * 
 * Any large size of space can be external disk.<br>
 * As such, do not make assumptions that you are working on SDCard.<br>
 * 
 * getMicroSDPath method that finds MicroSDCard path if it exists has been removed since it depends on /system/etc/vold.fstab file.<br>
 * Because that file was removed as of JELLY_BEAN_MR2, this function could not work as solid as it should do.<br>
 * For that reason, this method is excluded.
 * 
 * @author jaemin.woo
 */
public final class DiskUtils {
	private static final String DATA_FOLDER = "/Android/data";
	protected static final String TEMPORARY_FOLDER = "/temp";
	protected static final String CACHE_FOLDER = "/cache";

	/**
	 * Checks if External storage is mounted
	 * @return true if mounted; false otherwise
	 */
	public static boolean isExternalStorageMounted() {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * Returns directory name to save cache data in the external storage<p>
	 * 
	 * This method always returns path of external storage even if it does not exist.<br>
	 * As such, make sure to call isExternalStorageMounted method as state-testing method and then call this function only if state-testing method returns true.
	 * 
	 * @param context Context to get external storage information
	 * @return String containing cache directory name
	 */
	public static String getExternalDirPath(Context context) {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + DATA_FOLDER + File.separator + context.getPackageName() + CACHE_FOLDER;
	}

	/**
	 * Returns directory name to save temporary files in the external storage for temporary<p>
	 * 
	 * This method always returns path of external storage even if it does not exist.<br>
	 * As such, make sure to call isExternalStorageMounted method as state-testing method and then call this function only if state-testing method returns true.
	 * 
	 * @param context Context to get external storage information
	 * @return String containing temporary directory name
	 */
	public static String getExternalTemporaryDirPath(Context context) {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + DATA_FOLDER + File.separator + context.getPackageName() + TEMPORARY_FOLDER;
	}


	/**
	 * Returns root directory of the external storage.<p>
	 * 
	 * This method always returns path of external storage even if it does not exist.<br>
	 * As such, make sure to call isExternalStorageMounted method as state-testing method and then call this function only if state-testing method returns true.
	 * 
	 * @param context Context to get external storage information
	 * @return String containing external root directory name
	 */
	public static String getExternalContextRootDir(Context context) {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + DATA_FOLDER + File.separator + context.getPackageName();
	}
}