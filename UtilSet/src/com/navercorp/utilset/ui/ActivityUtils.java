package com.navercorp.utilset.ui;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * 
 * @author jaemin.woo
 *
 */
public class ActivityUtils {
	/** Checks if a package is installed.
	 * 
	 * @param context Context to be used to verify the existence of the package.
	 * @param packageName Package name to be searched.
	 * @return true if the package is discovered; false otherwise
	 */
	public static boolean isPackageInstalled(Context context, String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, 0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/** Returns Package name of base activity.<p>
	 * Requires GET_TASK permission
	 * @param context Context to get base activity information
	 * @return String containing base package name
	 */
	public static String getBaseActivityPackageName(Context context) {
		ComponentName activity = getBaseActivity(context);
		if (activity == null) {
			return null;
		}
		return activity.getPackageName();
	}

	/**
	 * Returns Class name of base activity
	 * @param context Context to provide activity information
	 * @return String representing class name of base activity
	 */
	public static String getBaseActivityClassName(Context context) {
		ComponentName activity = getBaseActivity(context);
		if (activity == null) {
			return null;
		}
		return activity.getClassName();
	}
	
	/**
	 * Returns Package name of top activity
	 * @param context Context to provide activity information
	 * @return String representing package name of top activity
	 */
	public static String getTopActivityPackageName(Context context) {
		ComponentName activity = getTopActivity(context);
		if (activity == null) {
			return null;
		}
		return activity.getPackageName();
	}
	
	/**
	 * Returns Class name of top activity
	 * @param context Context to provide activity information
	 * @return String representing class name of top activity
	 */
	public static String getTopActivityClassName(Context context) {
		ComponentName activity = getTopActivity(context);
		if (activity == null) {
			return null;
		}
		return activity.getClassName();
	}
	
	

	/**
	 * Determines if this application is top activity
	 * @param context Context to be examined
	 * @return true if this application is a top activity; false otherwise
	 */
	public static boolean isTopApplication(Context context) {
		ComponentName activity = getTopActivity(context);
		if (activity == null) {
			return false;
		}
		return activity.getPackageName().equals(context.getApplicationInfo().packageName);
	}
	

	/**
	 * Checks if this application is foreground
	 * 
	 * @param context Context to be examined
	 * @return true if this application is running on the top; false otherwise
	 */
	public static boolean isContextForeground(Context context) {

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		int pid = getPid();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.pid == pid) {
				return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
			}
		}
		return false;
	}
	
	private static int getPid() {
		return android.os.Process.myPid();
	}

	private static ComponentName getTopActivity(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) {
			return null;
		}
		List<RunningTaskInfo> info = am.getRunningTasks(1);
		if (info == null) {
			return null;
		}
		ComponentName activity = info.get(0).topActivity;
		return activity;
	}

	private static ComponentName getBaseActivity(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) {
			return null;
		}
		List<RunningTaskInfo> info = am.getRunningTasks(1);
		if (info == null) {
			return null;
		}
		ComponentName activity = info.get(0).baseActivity;
		return activity;
	}

	public static boolean isIntentAvailable(Context context, Intent intent) {
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> resolves = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		boolean isIntentAvailable = resolves.size() > 0;
		return isIntentAvailable;
	}
}