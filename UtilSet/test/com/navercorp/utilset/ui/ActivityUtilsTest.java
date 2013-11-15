package com.navercorp.utilset.ui;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import android.content.Context;

/**
 *
 * @author jaemin.woo
 * 
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ActivityUtilsTest {
	private Context context;
	private static final String NON_EXISTENT_INSTALLED_PACKAGE = "seventh.son.of.a.seventh.son";

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
}