package com.nhncorp.utilset.ui;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

public class ScreenUtils {
	
	/**
	 * @param activity
	 * @param dim 0.004f is the least brightness to work. if it is set below 0.004f, it won't work.
	 */
	public static void setScreenBrightness(final Activity activity, float dim) {
		WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
		lp.screenBrightness = dim;
		activity.getWindow().setAttributes(lp);
	}
	
	/**
	 * @param context
	 * @param millis Time for screen to turn off. Setting this value to -1 will prohibit screen from turning off
	 *  
	 */
	public static void setScreenOffTimeout(Context context, int millis) {
		android.provider.Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, millis);
	}
}
