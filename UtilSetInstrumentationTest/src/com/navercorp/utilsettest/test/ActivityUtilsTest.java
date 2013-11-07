package com.navercorp.utilsettest.test;

import org.junit.Assert;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.navercorp.utilset.ui.ActivityUtils;

public class ActivityUtilsTest extends ApplicationTestCase<Application> {
	private static final String NOT_INSTALLED_PACKAGE = "seventh.son.of.a.seventh.son";
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
	
	public void testPackageNotInstalled() {	
		Assert.assertFalse(ActivityUtils.isPackageInstalled(context, NOT_INSTALLED_PACKAGE));
	}
	
	// Android Instrumentation Test is executed on the android launcher and that launcher can be different for each manufacturer.
	public void testIsTopApplication() {
		assertFalse(ActivityUtils.isTopApplication(context));
	}
	
	public void testIsContextForeground() {
		assertTrue(ActivityUtils.isContextForeground(context));
	}

}
