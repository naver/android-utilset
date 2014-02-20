package com.navercorp.utilset.system;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import android.util.Log;

class ProcessorUtils {
	/**
	http://stackoverflow.com/questions/7962155/how-can-you-detect-a-dual-core-cpu-on-an-android-device-from-code 
	 * Gets the number of cores available in this device, across all processors.
	 * Requires: Ability to peruse the file system at "/sys/devices/system/cpu"
	 * @return The number of cores, or Runtime.getRuntime().availableProcessors() if failed to get result
	 */
	public static int getNumCores() {
		//Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {

			@Override	 
			public boolean accept(File pathname) {	 
				//Check if filename is "cpu", followed by a single digit number	 
				if(Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				
				return false;
			}      
		}

		try {
			//Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");

			//Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());

			if (files == null)
				return Runtime.getRuntime().availableProcessors();
			
			//Return the number of cores (virtual CPU devices)
			return files.length;
		} catch(Exception e) {
			// The number of cores can vary with JVM status
			return Runtime.getRuntime().availableProcessors();
		}
	}
}
