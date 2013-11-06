package com.navercorp.utilset.common;
/*
 * DeviceInfoHelper.java
 *
 * Copyright 2011 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

class DeviceTypeDetector {
	private static final int SCREENLAYOUT_SIZE_XLARGE = 4;
	private static final int DENSITY_TV = 213;
	private static final int DENSITY_XHIGH = 320;

	/**
	 * Checks if the device is a tablet or a phone
	 * 
	 * @param activityContext The Activity Context.
	 * @return Returns true if the device is a Tablet
	 */
	public DeviceType getDeviceType(Context activityContext) {
		// Verifies if the Generalized Size of the device is XLARGE to be
		// considered a Tablet
		boolean xlarge = ((activityContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE);

		// If XLarge, checks if the Generalized Density is at least MDPI
		// (160dpi)
		if (xlarge) {
			DisplayMetrics metrics = new DisplayMetrics();
			Activity activity = (Activity)activityContext;
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			
			// MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
			// DENSITY_TV=213, DENSITY_XHIGH=320
			if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
				|| metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
				|| metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
				|| metrics.densityDpi == DENSITY_TV
				|| metrics.densityDpi == DENSITY_XHIGH) {

				// this is a tablet!
				return DeviceType.Tablet;
			}
		}

		// this is not a tablet!
		return DeviceType.Handset;
	}
}
