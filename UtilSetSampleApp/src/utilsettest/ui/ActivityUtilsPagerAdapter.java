package com.navercorp.utilsettest.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ActivityUtilsPagerAdapter extends FragmentPagerAdapter {
	private static final int NUMBER_OF_FRAGMENTS = 2;
	private static final String TAG = "UtilSet";

	
	public ActivityUtilsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		if (index == ActivityUtilsConstants.KEEP_SCREEN_ON) {
			Log.d(TAG, "getItem 0");
			return new ActivityUtilsKeepScreenOnFragment();
		}
		
		Log.d(TAG, "getItem 1");
		return new ActivityUtilsKeepScreenOnFragment();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NUMBER_OF_FRAGMENTS;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if (position == ActivityUtilsConstants.KEEP_SCREEN_ON) {
			return "Keep Screen On";
		}
		return "Clear Screen On";
	}
}
