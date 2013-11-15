package com.navercorp.utilset.input;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 
 * @author jaemin.woo
 *
 */
public class KeyboardUtils {
	private static SoftwareKeyDetector deviceKeyboardHelper;

	static {
		deviceKeyboardHelper = new SoftwareKeyDetector();
	}
	
	/**
	 * Shows keypad
	 * 
	 * @param context
	 * @param view View to have keypad control
	 */
	public static void showSoftKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * Hides keypad
	 * 
	 * @param context
	 * @param view View holding keypad control
	 */
	public static void hideSoftKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * Toggles keypad
	 * 
	 * @param context
	 */
	public static void toggleKeyPad(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}

	/**
	 * Delayed version of {@link #hideSoftKeyboard(Context, View)
	 * hideSoftKeyboard} method
	 * 
	 * @param context
	 * @param view View holding keypad control
	 */
	public static void delayedHideSoftKeyboard(final Context context,
			final View view, int delay) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				hideSoftKeyboard(context, view);
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, delay);
	}

	/**
	 * Delayed version of {@link #showSoftKeyboard(Context, View)
	 * showSoftKeyboard} method
	 * 
	 * @param context
	 * @param view View to have keypad control
	 */
	public static void delayedShowSoftKeyboard(final Context context,
			final View view, int delay) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				showSoftKeyboard(context, view);
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, delay);
	}
	
	/**
	 * Determines if the device has software keys.
	 * 
	 * @return true if device has software keys; false otherwise
	 */
	public static boolean hasSoftwareKeys(Context context) {
		return deviceKeyboardHelper.hasSoftwareKeys(context);
	}
}
