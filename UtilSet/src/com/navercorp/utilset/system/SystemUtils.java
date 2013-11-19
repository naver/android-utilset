package com.navercorp.utilset.system;


/**
 * 
 * @author jaemin.woo
 *
 */
public class SystemUtils {
	private static RootChecker rootChecker;
	
	static {
		rootChecker = new RootChecker();
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
	 * Returns the number of cores of device.<br>
	 * Until JELLY_BEAN_MR1, It is possible for processors to be off-line for power saving purpose and those off-line CPUs may not be counted.<br>
	 * Use this method if Runtime.availableProcessors() seems not to return exact core numbers.
	 * 
	 * @return the number of cores
	 */
	public static int getProcessorNumbers() {
		return ProcessorUtils.getNumCores();
	}
}
