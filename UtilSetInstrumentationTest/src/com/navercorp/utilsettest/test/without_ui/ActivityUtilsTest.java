package com.navercorp.utilsettest.test.without_ui;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.navercorp.utilset.ui.ActivityUtils;

public class ActivityUtilsTest extends ApplicationTestCase<Application> {
	private Context context;
	
	public ActivityUtilsTest() {
		super(Application.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		createApplication();
		context = getApplication();
	}
	
	// Android Instrumentation Test is executed on the android launcher and the launcher name can be different for each manufacturer.
	public void testIsTopApplication() {
		assertFalse(ActivityUtils.isTopApplication(context));
	}
	
	public void testIsContextForeground() {
		assertTrue(ActivityUtils.isContextForeground(context));
	}

}
