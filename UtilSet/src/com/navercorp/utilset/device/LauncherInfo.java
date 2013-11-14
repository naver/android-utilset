package com.navercorp.utilset.device;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

class LauncherInfo {
	public static String getName(Context context) {
		PackageManager pm = context.getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_HOME);
		ResolveInfo resolveInfo = pm.resolveActivity(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
		if (resolveInfo.activityInfo.applicationInfo.className == null) {
			return "ANDROID";
		}
		return resolveInfo.activityInfo.applicationInfo.packageName + resolveInfo.activityInfo.applicationInfo.className;
	}
}