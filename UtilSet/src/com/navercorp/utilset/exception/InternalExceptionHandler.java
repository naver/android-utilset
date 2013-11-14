package com.navercorp.utilset.exception;

import android.util.Log;

/**
 * This class is only for internal exception handling
 * 
 * @author jaemin.woo
 */
public class InternalExceptionHandler {
	private static final String TAG = "UtilSet";
	
	public static void handlingException(Exception e, Class<?> clazz, String methodName) {
		Log.e(TAG, "An exception occured in " + clazz.getSimpleName() + "." + methodName + " method");
		throw new RuntimeException(e);
	}
}
