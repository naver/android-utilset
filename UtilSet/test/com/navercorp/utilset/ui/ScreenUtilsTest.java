package com.navercorp.utilset.ui;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.ActivityController;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager.LayoutParams;


/**
 * @author sanghyuk.jung
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ScreenUtilsTest {
	private Context context;

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
		this.context = Robolectric.application;
	}
	@Test
	public void shouldChangeScreenBrightness() {
		TestActivity activity = createActivity(TestActivity.class);
		float brightness = 0.5f;
		ScreenUtils.setScreenBrightness(activity, brightness);
		
		LayoutParams lp = activity.getWindow().getAttributes();

		assertThat(lp.screenBrightness, is(brightness));
	}
	
	@Test
	public void shouldSetScreenOfTimeout() throws SettingNotFoundException {
		int timeout = 15000;
		ScreenUtils.setScreenOffTimeout(context, timeout);

		int timeoutFromSettings = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
		
		assertThat(timeoutFromSettings, is(timeout));
	}

	private <T extends Activity> T createActivity(Class<T> activityClass) {
		ActivityController<T> controller = Robolectric.buildActivity(activityClass);
		controller.create();
		return controller.get();
	}

	static class TestActivity extends Activity {
	}
}
