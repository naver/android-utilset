package com.navercorp.utilset.ui;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * 
 * @author jaemin.woo
 *
 */
public class ScreenUtils {
	
	/**
	 * @param activity
	 * @param dim 0.004f is the least brightness to work. if it is set below 0.004f, it won't work.
	 */
	public static void setScreenBrightness(Activity activity, float dim) {
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
		Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, millis);
	}
	
	/**
	 * Prevents screen from being turned off.
	 * 
	 * @param activity
	 */
	public static void setScreenOn(Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	/**
	 * Let screen to be turned off
	 * 
	 * @param activity
	 */
	public static void clearScreenOn(Activity activity) {
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
}
