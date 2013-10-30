package com.navercorp.utilsettest.test;

import android.app.Activity;
import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.MainActivity;
import com.navercorp.utilset.ui.ScreenUtils;

public class ScreenUtilsTestCase extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;

	public ScreenUtilsTestCase() {
		// TODO Auto-generated constructor stub
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
	}

	@SmallTest
	public void testSetBrightness() {
		final Activity activity = solo.getCurrentActivity();
		final Handler handler = new Handler();

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ScreenUtils.setScreenBrightness(activity, 0.04f);
				
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ScreenUtils.setScreenBrightness(activity, 1.0f);
					}
				}, 1000);
			}
		});

		solo.sleep(2000);
	}

}
