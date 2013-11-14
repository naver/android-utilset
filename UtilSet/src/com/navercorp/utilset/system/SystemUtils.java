package com.navercorp.utilset.system;

import android.content.Context;


public class SystemUtils {
	private static RootChecker rootChecker;
	private static DeviceTypeDetector deviceTypeHelper;
	
	static {
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
	 * Returns the number of cores of device.
	 * 
	 * @return the number of cores
	 */
	public static int getProcessorNumbers() {
		return ProcessorUtils.getNumCores();
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
}
