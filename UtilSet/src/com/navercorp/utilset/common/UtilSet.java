package com.navercorp.utilset.common;

import android.content.Context;
import android.util.Log;

public class UtilSet {
	public static final String TAG = "UtilSet";

	private static PhoneNumberUtils phoneNumberUtils;
	
	static {
		phoneNumberUtils = new PhoneNumberUtils();
	}
	
	// TODO FIXME Do not work properly.
	/**
	 * Prevents a problem that AsyncTask connects to incorrect Looper
	 * and then throws Exception. <br>
	 * This could be resolved by explicitly loading AsyncTask class. <br>
	 * This function must be called at application initialization time <br>
	 * or at least before creating AsyncTask. <br>
	 * Just one invocation will take effect for entire application life time. <br>
	 * However, It is suggested that one do not create tasks in a thread other than the main thread
	 * as AsyncTask is intended for UI related tasks.  
	 */
	public static void loadAsyncTaskClass() {
		try {
		    Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			Log.wtf(TAG, "Something that should not happen occured. I can't believe how it is possible to fail to load AsyncTask?");
		}
	}

	
	/**
	 * Returns launcher type.
	 * 
	 * @param context Context to provide package information
	 * @return LauncherType
	 */
	public static LauncherType getLauncherType(Context context) {
		return LauncherTypeDetector.getType(context);
	}

	/**
	 * Determines if user device has capability of SMS.
	 * @required READ_PHONE_STATE Permission must be set to use this function
	 * @param context Context to derive device information
	 * @return true if user device has SMS capability; false otherwise
	 */
	public static boolean hasSmsCapability(Context context) {
		return phoneNumberUtils.isAbleToReceiveSms(context);
	}
	
	/**
	 * Returns mobile phone number
	 * @param context Context to be used to get mobile phone number
	 * @return string containing mobile phone number
	 */
	public static String getMobilePhoneNumber(Context context) {
		return phoneNumberUtils.getMobilePhoneNumber(context);
	}
}