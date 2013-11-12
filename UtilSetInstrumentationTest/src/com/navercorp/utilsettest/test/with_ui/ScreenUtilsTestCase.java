package com.navercorp.utilsettest.test.with_ui;

import java.util.concurrent.CountDownLatch;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilset.ui.ScreenUtils;
import com.navercorp.utilsettest.MainActivity;
import com.navercorp.utilsettest.introduction.Introduction;

public class ScreenUtilsTestCase extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;
	private FragmentActivity activity;

	public ScreenUtilsTestCase() {
		// TODO Auto-generated constructor stub
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();

		activity = getActivity();
		
		solo = new Solo(getInstrumentation(), activity);
	}
	
	public void tearDown() throws Exception
	{
        solo.finishOpenedActivities();
	}
	
	private Handler handler = new Handler();

	public void testSetBrightness() {
		solo.waitForActivity(activity.getClass().getSimpleName());
		
		Introduction.showIntroductionDialog(activity, Introduction.ScreenUtilsTestCase_testSetBrightness);
		
		final CountDownLatch latchForDimness = new CountDownLatch(1);
		final CountDownLatch latchForBrightness = new CountDownLatch(1);
		
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ScreenUtils.setScreenBrightness(activity, 0.04f);
				latchForDimness.countDown();
			}
		}, 500);
		
		try {
			latchForDimness.await();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		handler.post(new Runnable() {
			@Override
			public void run() {
				ScreenUtils.setScreenBrightness(activity, 1.0f);
				latchForBrightness.countDown();
			}
		});
		
		try {
			latchForBrightness.await();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
