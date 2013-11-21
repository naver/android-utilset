package com.navercorp.utilset.input;

import java.lang.reflect.Method;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

/**
 * 
 * @author jaemin.woo
 *
 */
class SoftwareKeyDetector {
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public boolean hasSoftwareKeys(Context context) {
		int sdkVersion = Build.VERSION.SDK_INT;

		// Gingerbread and below are considered to have physical buttons.
		if (sdkVersion <= Build.VERSION_CODES.GINGERBREAD_MR1)
			return false;

		// Honeycomb is dedicated to the tablets having no physical buttons.
		if (sdkVersion >= Build.VERSION_CODES.GINGERBREAD_MR1 && sdkVersion <= Build.VERSION_CODES.HONEYCOMB_MR2)
			return true;

		// ICS and above provide convenient function able to determine if the
		// device has
		// physical buttons or not.
		// This function is not available below ICS
		
		boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
		boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
		
		return !hasMenuKey && !hasBackKey;
	}
	
	private boolean softkeyNavigationCalled;
	private boolean softkeyNavigationBar;
	private Object lock = new Object();
	
	public boolean getHasSoftkeyNavigationBar() {
		// Prevent reflection from being called indefinitely 
		if (softkeyNavigationCalled) {
			return softkeyNavigationBar;
		}
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			return false;
		}
		
		try {
			Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
			Method getService = serviceManagerClass.getDeclaredMethod("getService", String.class);
			Object ws = getService.invoke(null,  "window");
			Class<?> wmClassStub = Class.forName("android.view.IWindowManager$Stub");
			Method asInterface = wmClassStub.getDeclaredMethod("asInterface", IBinder.class);
			Object iWindowManager = asInterface.invoke(null, (IBinder)ws);
			Method declaredMethod = iWindowManager.getClass().getDeclaredMethod("hasNavigationBar");
			
			synchronized (lock) {
				softkeyNavigationBar = (Boolean) declaredMethod.invoke(iWindowManager);
			}
		}
		catch (Exception e) {
			// If this method does not exist, then it may be thought that the device does not have one
		}

		synchronized (lock) {
			softkeyNavigationCalled = true;
		}
		
		return softkeyNavigationBar;
	}
}
