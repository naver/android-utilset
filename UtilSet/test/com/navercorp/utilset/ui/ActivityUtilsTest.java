package com.navercorp.utilset.ui;

import static org.hamcrest.CoreMatchers.*;
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
import org.robolectric.shadows.ShadowWindow;
import org.robolectric.util.ActivityController;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.WindowManager;

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
	private static final String TEST_ACTIVITY = "TestActivity";

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
		this.context = Robolectric.application;
	}

	@Test
	public void shouldBeFalseForNotInstalledPackage() {
		boolean installed = ActivityUtils.isPackageInstalled(context, NOT_INSTALLED_PACKAGE);
		assertThat(installed, is(false));
	}

	@Test
	public void shouldBeTrueForInstalledPackage() {
		addInstalledPackage(INSTALLED_PACKAGE);
		boolean installed = ActivityUtils.isPackageInstalled(context, INSTALLED_PACKAGE);
		assertThat(installed, is(true));
	}
	
	@Test
	public void shouldAddKeepScreenOnFlag() {
		TestActivity activity = createActivity(TestActivity.class);
		ScreenUtils.setScreenOn(activity);
		boolean flag = getFlag(activity, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldClearKeepScreenOnFlag() {
		TestActivity activity = createActivity(TestActivity.class);
		ScreenUtils.clearScreenOn(activity);
		boolean flag = getFlag(activity, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		assertThat(flag, is(false));
	}

	@Test
	public void shouldGetBaseActivityPackageName() {
		createRunningTaskInfo().baseActivity = new ComponentName(INSTALLED_PACKAGE, "");
		String packageName = ActivityUtils.getBaseActivityPackageName(context);
		assertThat(packageName, is(INSTALLED_PACKAGE));
	}

	@Test
	public void shouldGetBaseActivityClassName() {
		createRunningTaskInfo().baseActivity = new ComponentName(INSTALLED_PACKAGE, TEST_ACTIVITY);
		String className = ActivityUtils.getBaseActivityClassName(context);
		assertThat(className, is(TEST_ACTIVITY));
	}

	@Test
	public void shouldGetTopActivityPackageName() {
		createRunningTaskInfo().topActivity = new ComponentName(INSTALLED_PACKAGE, "");
		String packageName = ActivityUtils.getTopActivityPackageName(context);
		assertThat(packageName, is(INSTALLED_PACKAGE));
	}

	@Test
	public void shouldGetTopActivityClassName() {
		createRunningTaskInfo().topActivity = new ComponentName(INSTALLED_PACKAGE, TEST_ACTIVITY);
		String className = ActivityUtils.getTopActivityClassName(context);
		assertThat(className, is(TEST_ACTIVITY));
	}
	
	@Test
	public void shouldBeFalseWhenThisApplicationIsNotTop() {
		createRunningTaskInfo().topActivity = new ComponentName(INSTALLED_PACKAGE, TEST_ACTIVITY);
		boolean top = ActivityUtils.isTopApplication(context);
		assertThat(top, is(false));
	}
	
	@Test
	public void shouldBeTrueWhenThisContextIsForeground(){
		// android.os.Process.myPid() returns 0 in Robolectric
		createRunningAppProcessInfo(0);
		boolean foreground = ActivityUtils.isContextForeground(context);
		assertThat(foreground, is(true));
	}

	@Test
	public void shouldBeFalseWhenThisContextIsNotForeground(){
		// android.os.Process.myPid() returns 0 in Robolectric
		createRunningAppProcessInfo(3);
		boolean foreground = ActivityUtils.isContextForeground(context);
		assertThat(foreground, is(false));
	}

	private void addInstalledPackage(String packageName) {
		RobolectricPackageManager pm = Robolectric.packageManager;
		PackageInfo packInfo = new PackageInfo();
		packInfo.packageName = packageName;
		pm.addPackage(packInfo);
	}
	
	private RunningTaskInfo createRunningTaskInfo() {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ShadowActivityManager shadowAm = Robolectric.shadowOf(am);
		RunningTaskInfo info = new RunningTaskInfo();
		shadowAm.setTasks(Arrays.asList(info));
		return info;
	}

	private <T extends Activity> T createActivity(Class<T> activityClass) {
		ActivityController<T> controller = Robolectric.buildActivity(activityClass);
		controller.create();
		return controller.get();
	}

	private boolean getFlag(TestActivity activity, int flagToMatch) {
		ShadowWindow window = Robolectric.shadowOf(activity.getWindow());
		return window.getFlag(flagToMatch);
	}

	private void createRunningAppProcessInfo(int pid) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ShadowActivityManager shadowAm = Robolectric.shadowOf(am);
		
		RunningAppProcessInfo proc = new RunningAppProcessInfo();
		proc.importance = RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
		proc.pid = pid;
		shadowAm.setProcesses(Arrays.asList(proc));
	}
	
	static class TestActivity extends Activity {
	}
}