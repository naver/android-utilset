package com.navercorp.utilset.system;

import java.io.BufferedReader;
import java.io.File;
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

		try {
			Process process = Runtime.getRuntime().exec("find / -name su");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			
			// FIXME
			// This makes result confusing.
			// The result significantly depends on the delay. 
			// Without debugging or delay, reader.ready() returns false, which means device is not rooted.
			// However, in debugging mode, it returns true, so the device proves to be rooted.
//			if (reader.ready() == false) {
//				return false;
//			}

			String result = reader.readLine();
			if (result.contains("/su") == true) {
				isRootingFlag = true;
			}

		} catch (Exception e) {
			isRootingFlag = false;
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