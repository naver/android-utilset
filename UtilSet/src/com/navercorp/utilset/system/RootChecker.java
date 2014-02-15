package com.navercorp.utilset.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

class RootChecker {
	private static final String TAG = "RootChecker";
	private static final String[] PLACES = { "/sbin/", "/system/bin/",
			"/system/xbin/", "/data/local/xbin/", "/data/local/bin/",
			"/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };

	private static final String ROOT_CHECKING_BINARY_NAME = "su";

	public boolean isRootAvailable() {
		for (String where : PLACES) {
			if (hasFile(where + ROOT_CHECKING_BINARY_NAME)) {
				Log.d(TAG, where + ROOT_CHECKING_BINARY_NAME + " was found!");
				return true;
			}
		}
		Log.d(TAG, ROOT_CHECKING_BINARY_NAME + " was not found!");
		return false;
	}

	private boolean hasFile(String fullPath) {
		try {
			File file = new File(fullPath);
			return file.exists();
		} catch (Exception e) {
			Log.e(TAG, "An error occured while checking " + fullPath, e);
			return false;
		}
	}

	
	// Refer : http://lsit81.tistory.com/entry/Android-%EB%A3%A8%ED%8C%85-%EC%97%AC%EB%B6%80-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0
	public boolean checkRootingDevice() {
		boolean isRootingFlag = false;
		BufferedReader reader = null;
		try {
			Process process = Runtime.getRuntime().exec("find / -name su");

			reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			
			String result = reader.readLine();
			if (result == null) {
				Log.d(TAG, "Failed to execute find command to check if a file which is evidence of root exists");
				throw new RuntimeException("Unable to check if device is rooted");
			}
				
			if (result.contains("/su") == true) {
				isRootingFlag = true;
			}

		} catch (Exception e) {
			isRootingFlag = false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.d(TAG, "Error occured while closing input stream");
				}
			}
		}

		if (!isRootingFlag) {
			isRootingFlag = checkRootingFiles(PLACES);
		}

		return isRootingFlag;
	}
	
	private boolean checkRootingFiles(String[] filePaths) {
	    boolean result = false;
	    File    file;
	     
	    for (String path : filePaths) {
	        file = new File(path + "su");
	         
	        if (file != null && file.exists() && file.isFile()) {
	            result = true;
	            break;
	        } else {
	            result = false;
	        }
	    }
	     
	    return result;
	}
}