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
import org.robolectric.res.builder.RobolectricPackageManager;
import org.robolectric.shadows.ShadowActivityManager;
import org.robolectric.shadows.ShadowLog;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;

/**
 *
 * @author jaemin.woo
 * @author sanghyuk.jung
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ActivityUtilsTest {
	private Context context;
	private static final String NOT_INSTALLED_PACKAGE = "seventh.son.of.a.seventh.son";
	private static final String INSTALLED_PACKAGE = "com.navercorp.utilset";

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
		this.context = Robolectric.application;
	}

	@Test
	public void shouldNotReturnTrueForNotInstalledPackage() {
		boolean installed = ActivityUtils.isPackageInstalled(context, NOT_INSTALLED_PACKAGE);
		assertThat(installed, is(false));
	}

	@Test
	public void shouldReturnTrueForInstalledPackage() {
		addInstalledPackage(INSTALLED_PACKAGE);
		boolean installed = ActivityUtils.isPackageInstalled(context, INSTALLED_PACKAGE);
		assertThat(installed, is(true));
	}
	
	@Test
	public void shouldGetBaseActivityPakcageName() {
		setBaseActivityPackage(INSTALLED_PACKAGE);
		String packageName = ActivityUtils.getBaseActivityPackageName(context);
		assertThat(packageName, is(INSTALLED_PACKAGE));
	}
	
	private void addInstalledPackage(String packageName) {
		RobolectricPackageManager pm = Robolectric.packageManager;
		PackageInfo packInfo = new PackageInfo();
		packInfo.packageName = packageName;
		pm.addPackage(packInfo);
	}
	
	private void setBaseActivityPackage(String packageName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ShadowActivityManager shadowAm = Robolectric.shadowOf(am);
		RunningTaskInfo info = new RunningTaskInfo();
		info.baseActivity = new ComponentName(packageName, "test");
		shadowAm.setTasks(Arrays.asList(info));
	}
}