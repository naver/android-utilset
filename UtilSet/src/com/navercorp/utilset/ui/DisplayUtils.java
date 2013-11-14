/*
 * DisplayUtil.java
 *
 * Copyright 2011 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.navercorp.utilset.ui;

import android.content.Context;

/**
 * @author Created by
 * @tag DisplayUtil
 */
public class DisplayUtils {
	/**
	 * Converts Pixel to DP<br>
	 * @param pixel Pixel
	 * @return DP
	 * @see <a href="http://developer.android.com/guide/practices/screens_support.html#dips-pels">http://developer.android.com/guide/practices/screens_support.html#dips-pels</a>
	 */
	public static int getDpFromPixel(Context context, int pixel) {
		float scale = context.getResources().getDisplayMetrics().density; // get display density

		return (int)(pixel / scale);
	}

	/**
	 * Converts DP to Pixel
	 * 
	 * @param  dp DP
	 * @return Pixel
	 * @see <a href="http://developer.android.com/guide/practices/screens_support.html#dips-pels">http://developer.android.com/guide/practices/screens_support.html#dips-pels</a>
	 */
	public static int getPixelFromDP(Context context, int dp) {
		// Get the screen's density scale		
		float scale = context.getResources().getDisplayMetrics().density;

		// Convert the dps to pixels, based on density scale
		return (int)(dp * scale + 0.5f);
	}
}