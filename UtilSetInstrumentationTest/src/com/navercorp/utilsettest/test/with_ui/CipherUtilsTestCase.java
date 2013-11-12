package com.navercorp.utilsettest.test.with_ui;

import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.cipher.CipherTestActivity;
import com.navercorp.utilsettest.introduction.Introduction;

public class CipherUtilsTestCase extends
		ActivityInstrumentationTestCase2<CipherTestActivity> {
	private Solo solo;
	private String seed = "do re mi"; // "do re mi pa so la ti do";
	private String plainText = "Slow";
	private FragmentActivity activity;

	public CipherUtilsTestCase() {
		// TODO Auto-generated constructor stub
		super(CipherTestActivity.class);
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
	
	public void testCipher() {
		solo.waitForActivity(activity.getClass().getSimpleName());
		
		Introduction.showIntroductionDialog(activity, Introduction.CipherUtilsTestCase_testCipher);
		
		int size = seed.length();
		for (int i=0;i<size;++i) {
			solo.enterText(0, seed.charAt(i) + "");
		}
		
		size = plainText.length();
		for (int i=0;i<size;++i) {
			solo.enterText(1, plainText.charAt(i) + "");
		}
		
		solo.clickOnButton(0);
		solo.sleep(2000);
		
		solo.clickOnButton(1);
		solo.sleep(1500);
	}
}
