package com.navercorp.utilset.device;

import android.content.Context;

/**
 * 
 * @author jaemin.woo
 * 
 */
public class DeviceUtils {
	private static DeviceTypeDetector deviceTypeHelper;
	private static PhoneNumberUtils phoneNumberUtils;

	static {
		phoneNumberUtils = new PhoneNumberUtils();
		deviceTypeHelper = new DeviceTypeDetector();
	}

	/**
	 * Gives type information of user device.
	 * 
	 * @param context
	 *            Context derived from Activity. ApplicationContext can not be
	 *            used to take advantage of this function.
	 * 
	 * @return DeviceType.Tablet if the screen size is equal to or larger than
	 *         XLarge, which is defined as display size from 7 to 10 inches; <br>
	 *         DeviceType.Handset if the screen size is smaller than XLarge
	 */
	public static DeviceType getDeviceType(Context context) {
		return deviceTypeHelper.getDeviceType(context);
	}

	/**
	 * Returns launcher type.
	 * 
	 * @param context
	 *            Context to provide package information
	 * @return LauncherType
	 */
	public static LauncherType getLauncherType(Context context) {
		return LauncherTypeDetector.getType(context);
	}

	/**
	 * Determines if user device has capability of SMS.
	 * 
	 * @required READ_PHONE_STATE Permission must be set to use this function
	 * @param context
	 *            Context to derive device information
	 * @return true if user device has SMS capability; false otherwise
	 */
	public static boolean hasSmsCapability(Context context) {
		return phoneNumberUtils.isAbleToReceiveSms(context);
	}
}
