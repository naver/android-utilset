package com.navercorp.utilsettest.test;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.network.NetworkListenerTestActivity;
import com.navercorp.utilsettest.R;

public class NetworkListenerTestCase extends ActivityInstrumentationTestCase2<NetworkListenerTestActivity> {
	private Solo solo;
	
	public NetworkListenerTestCase() {
		super(NetworkListenerTestActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void tearDown() throws Exception
	{
        solo.finishOpenedActivities();
	}

	public void testNetworkListener() {
		solo.sleep(500);
		solo.clickOnView(solo.getView(R.id.network_listener_test_toggle_wifi));
		solo.sleep(5000);
		solo.clickOnView(solo.getView(R.id.network_listener_test_toggle_wifi));
		solo.sleep(5000);
	}
}
