package com.navercorp.utilset.ui;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivityManager;
import org.robolectric.shadows.ShadowLog;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

/**
 *
 * @author jaemin.woo
 * @author sanghyuk.jung
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ActivityUtilsTest {
	private Context context;
	private static final String NON_EXISTENT_INSTALLED_PACKAGE = "seventh.son.of.a.seventh.son";
	private static final String BASE_ACTIVITY_PACKAGE = "com.navercorp.utilset";

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
		this.context = Robolectric.application;
	}

	@Test
	public void shouldNotReturnTrueForNonExistentInstalledPackage() {
		assertFalse(ActivityUtils.isPackageInstalled(context,
				NON_EXISTENT_INSTALLED_PACKAGE));
	}
	
	@Test
	public void shouldGetBaseActivityPakcageName() {
		setBaseActivityPackage(BASE_ACTIVITY_PACKAGE);
		String packageName = ActivityUtils.getBaseActivityPackageName(context);
		assertThat(packageName, is(BASE_ACTIVITY_PACKAGE));
	}
	
	private void setBaseActivityPackage(String packageName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ShadowActivityManager shadowAm = Robolectric.shadowOf(am);
		RunningTaskInfo info = new RunningTaskInfo();
		info.baseActivity = new ComponentName(packageName, "test");
		shadowAm.setTasks(Arrays.asList(info));
	}
}