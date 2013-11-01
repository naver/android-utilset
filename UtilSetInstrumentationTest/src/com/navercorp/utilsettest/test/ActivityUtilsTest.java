package com.navercorp.utilsettest.test;

import org.junit.Assert;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.navercorp.utilset.ui.ActivityUtils;

public class ActivityUtilsTest extends ApplicationTestCase<Application> {
	private static final String NOT_INSTALLED_PACKAGE = "seventh.son.of.a.seventh.son";
//	private static final String BASE_ACTIVITY_PACKAGE_NAME = "com.navercorp.utilsettest";
//	private static final String BASE_ACTIVITY_CLASS_NAME = "com.navercorp.utilsettest.MainActivity";
//	private static final String TOP_ACTIVITY_PACKAGE_NAME = "com.navercorp.utilsettest";
//	private static final String TOP_ACTIVITY_CLASS_NAME = "com.navercrop.utiltesttest.ui.ActivityUtilsTestActivity";
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
	
//	public void testGetBaseActivityPackageName() {
//		Assert.assertEquals(BASE_ACTIVITY_PACKAGE_NAME, ActivityUtils.getBaseActivityPackageName(context));
//	}
//	
//	public void testGetBaseActivityClassName() {
//		Assert.assertEquals(BASE_ACTIVITY_CLASS_NAME, ActivityUtils.getBaseActivityClassName(context));
//	}
//	
//	public void testGetTopActivityPackageName() {
//		Assert.assertEquals(TOP_ACTIVITY_PACKAGE_NAME, ActivityUtils.getTopActivityPackageName(context));
//	}
//	
//	public void testGetTopActivityClassName() {
//		assertEquals(TOP_ACTIVITY_CLASS_NAME, ActivityUtils.getTopActivityClassName(context));
//	}
}
