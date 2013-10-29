package com.nhncorp.utilsettest.test;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.nhncorp.utilsettest.network.NetworkListenerTestActivity;
import com.nhncorp.utilsettest.R;

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

	public void testNetworkListener() {
		solo.sleep(500);
		solo.clickOnView(solo.getView(R.id.network_listener_test_toggle_wifi));
		solo.sleep(10000);
	}
}
