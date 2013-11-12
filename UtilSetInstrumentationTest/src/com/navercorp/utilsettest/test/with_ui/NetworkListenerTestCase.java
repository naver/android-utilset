package com.navercorp.utilsettest.test.with_ui;

import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.R;
import com.navercorp.utilsettest.introduction.Introduction;
import com.navercorp.utilsettest.network.NetworkListenerTestActivity;

public class NetworkListenerTestCase extends ActivityInstrumentationTestCase2<NetworkListenerTestActivity> {
	private Solo solo;
	private FragmentActivity activity;
	
	public NetworkListenerTestCase() {
		super(NetworkListenerTestActivity.class);
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

	public void testNetworkListener() {
		solo.waitForActivity(activity.getClass().getSimpleName());
		
		Introduction.showIntroductionDialog(activity, Introduction.NetworkListenerTestCase_showIntroductionDialog);
		
		solo.sleep(500);
		solo.clickOnView(solo.getView(R.id.network_listener_test_toggle_wifi));
		solo.sleep(5000);
		solo.clickOnView(solo.getView(R.id.network_listener_test_toggle_wifi));
		solo.sleep(5000);
	}
}
