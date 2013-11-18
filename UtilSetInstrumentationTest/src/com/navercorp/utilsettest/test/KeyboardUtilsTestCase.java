package com.navercorp.utilsettest.test;

import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.input.KeyboardUtilsTestActivity;
import com.navercorp.utilsettest.introduction.Introduction;

public class KeyboardUtilsTestCase extends ActivityInstrumentationTestCase2<KeyboardUtilsTestActivity> {
	private Solo solo;
	FragmentActivity activity;
	
	public KeyboardUtilsTestCase() {
		super(KeyboardUtilsTestActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		activity = getActivity();
		
		solo = new Solo(getInstrumentation(), activity);
	}
	
	@SmallTest
	public void testKeyboard() {
		solo.waitForActivity(activity.getClass().getSimpleName());
		
		Introduction.showIntroductionDialog(activity, Introduction.KeyboardUtilsTestCase_testKeyboard);
		
		try {
			solo.clickOnButton("Show");
			Thread.sleep(500);
			solo.clickOnButton("Hide");
			Thread.sleep(500);
			solo.clickOnButton("Toggle");
			Thread.sleep(500);
			solo.clickOnButton("Toggle");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
