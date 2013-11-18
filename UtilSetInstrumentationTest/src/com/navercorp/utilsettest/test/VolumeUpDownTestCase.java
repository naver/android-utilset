package com.navercorp.utilsettest.test;


import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.R;
import com.navercorp.utilsettest.audio.VolumeUtilsTestActivity;
import com.navercorp.utilsettest.introduction.Introduction;

public class VolumeUpDownTestCase extends ActivityInstrumentationTestCase2<VolumeUtilsTestActivity> {
	private Solo solo;
	private FragmentActivity activity;
	private Button volumeUpButton;
	private Button volumeDownButton;
	
	public VolumeUpDownTestCase() {
		super(VolumeUtilsTestActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		activity = getActivity();
		solo = new Solo(getInstrumentation(), activity);
		volumeUpButton = (Button) activity.findViewById(R.id.volumeUpButton);
		volumeDownButton = (Button) activity.findViewById(R.id.volumeDownButton);
	}
	
	public void tearDown() throws Exception
	{
        solo.finishOpenedActivities();
	}

	public void testVolumeUpAndDown() {
		solo.waitForActivity(activity.getClass().getSimpleName());
		
		Introduction.showIntroductionDialog(activity, Introduction.VolumeUpDownTestCase_testVolumeUpAndDown, 2500);
		
		solo.clickOnView(volumeUpButton);
		solo.sleep(500);
		solo.clickOnView(volumeUpButton);
		solo.sleep(500);
		solo.clickOnView(volumeUpButton);
		solo.sleep(500);
		solo.clickOnView(volumeDownButton);
		solo.sleep(500);
		solo.clickOnView(volumeDownButton);
		solo.sleep(500);
		solo.clickOnView(volumeDownButton);
		solo.sleep(500);
	}
}
