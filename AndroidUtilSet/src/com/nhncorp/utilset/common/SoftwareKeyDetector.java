package com.nhncorp.utilset.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

public class SoftwareKeyDetector {
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public boolean hasSoftwareKeys(Context context) {
		int sdkVersion = Build.VERSION.SDK_INT;

		// Gingerbread and below are considered to have physical buttons.
		if (sdkVersion <= Build.VERSION_CODES.GINGERBREAD_MR1)
			return false;

		// Honeycomb is dedicated to the tablets having no physical buttons.
		if (sdkVersion >= Build.VERSION_CODES.GINGERBREAD_MR1 && sdkVersion <= Build.VERSION_CODES.HONEYCOMB_MR2)
			return true;

		// ICS and above provide convinient function able to determine if the
		// device has
		// physical buttons or not.
		// This function is not available below ICS
		
		boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
		boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
		
		return !hasMenuKey && !hasBackKey;
	}
}
