package com.nhncorp.utilset.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import android.util.Log;

/**
 * 폰의 푸팅 여부를 확인할 수 있는 기능을 제공함. 해당 로직에 대한 처리는
 * "http://roottools.googlecode.com/svn/trunk/Developmental/doc/com/stericson/RootTools/RootTools.html#isRootAvailable%28%29"
 * 를 참고함 해당 라이브러리는 shell의 ls를 통해 존재 유무를 파악하였으나 해당 클래스에서는 file의 존재 유무를 직접 체크함 혹시나
 * file의 권한 문제등의 오류가 발생하면 동일하게 척리해야 할 것으로 생각됨.
 * 
 * @author seongsu kim(KR13813)
 */
class RootChecker {
	private static final String TAG = "RootChecker";
	private static final String[] PLACES = { "/sbin/", "/system/bin/",
			"/system/xbin/", "/data/local/xbin/", "/data/local/bin/",
			"/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };

	private static final String ROOT_CHECKING_BINARY_NAME = "su";

	/**
	 * 폰의 root 사용 여부를 확인한다. 특정 위치에 권한 변경 명령어인 'su'의 존재 유무를 파악함.
	 * 
	 * @return root 사용 여부
	 */
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
		} catch (Exception e) { // 혹시나 발생할 exception을 위한 방어 로직
			Log.e(TAG, fullPath + "를 존재 유무를 확인하는 중 오류가 발생하였습니다.", e);
			return false;
		}
	}

	
	// 참고 : http://lsit81.tistory.com/entry/Android-%EB%A3%A8%ED%8C%85-%EC%97%AC%EB%B6%80-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0
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
