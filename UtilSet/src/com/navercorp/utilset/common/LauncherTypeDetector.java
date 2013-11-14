package com.navercorp.utilset.common;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Build;

/**
 * @author jaemin.woo
 */
class LauncherTypeDetector {
	private static List<String> EXCEPT_DEVICES = new ArrayList<String>();
	
	static {
		// Galaxy S
		EXCEPT_DEVICES.add("SHW-M180S");
	}
	
	private static boolean containExceptDevices() {
		String device = android.os.Build.DEVICE;
		return EXCEPT_DEVICES.contains(device);
	}
	
	public static LauncherType getType(Context context) {
		String packageName = LauncherInfo.getName(context);
		
		if ("".equals(packageName)) {
			return LauncherType.ANDROID;
		}

		if (containExceptDevices()) {
			return LauncherType.ANDROID;
		}

		for (LauncherType type : LauncherType.values()) {
			if (packageName.contains(type.packageName)) {
				if (type == LauncherType.ICS_DEFAULT
						&& !(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)) {
					return LauncherType.GINGERBREAD_DEFAULT;
				}
				return type;
			}
		}
		return LauncherType.ANDROID;
	}
}