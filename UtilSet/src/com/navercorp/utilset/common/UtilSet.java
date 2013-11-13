package com.navercorp.utilset.common;

import android.content.Context;
import android.util.Log;

public class UtilSet {
	public static final String TAG = "UtilSet";

	private static PhoneNumberUtils phoneNumberUtils;
	private static RootChecker rootChecker;
	private static DeviceTypeDetector deviceTypeHelper;
	
	static {
		phoneNumberUtils = new PhoneNumberUtils();
		rootChecker = new RootChecker();
		deviceTypeHelper = new DeviceTypeDetector();
	}
	
	/**
	 * Returns boolean that indicates whether current device is rooted or not.
	 * 
	 * <p>This method is <i>not</i> guaranteed to cover all the root methods.
	 * It takes simple approach;
	 * It tries to finds 'su' command and then executes.
	 * If 'su' command is not found or unable to execute then an exception will be thrown.
	 * This indicates that device is rooted.
	 * Because this is well known way to check root, Root tools today may not be detected by this method.
	 * For more detailed implementation, refer to '<a href=https://code.google.com/p/roottools/>RootTools</a>' library.
	 * 
	 * @return true if the device is rooted; false otherwise
	 */
	public static boolean isRooted() {
		return rootChecker.checkRootingDevice();
//		return rootChecker.isRootAvailable();
	}

	
	/**
	 * Gives type information of user device.
	 * 
	 * @param context Context derived from Activity. ApplicationContext can not be used to take advantage of this function.
	 * 
	 * @return DeviceType.Tablet if the screen size is equal to or larger than
	 *         XLarge, which is defined as display size from 7 to 10 inches; <br>
	 *         DeviceType.Handset if the screen size is smaller than XLarge
	 */
	public static DeviceType getDeviceType(Context context) {
		return deviceTypeHelper.getDeviceType(context);
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
	 * Returns the number of cores of device.
	 * 
	 * @return the number of cores
	 */
	public static int getProcessorNumbers() {
		return ProcessorUtils.getNumCores();
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