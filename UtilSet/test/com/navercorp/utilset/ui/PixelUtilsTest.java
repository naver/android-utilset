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

import android.content.Context;

/**
*
* @author sanghyuk.jung
*/
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PixelUtilsTest {
	private Context context;

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
		this.context = Robolectric.application;
	}

	@Test
	public void shouldGetDpFromPixel(){
		Robolectric.setDisplayMetricsDensity(1.5f);
		int dp = PixelUtils.getDpFromPixel(context, 50);
		assertThat(dp, is(33));
	}
	
	@Test
	public void shouldPixelDpFromDp(){
		Robolectric.setDisplayMetricsDensity(1.5f);
		int pixel = PixelUtils.getPixelFromDp(context, 33);
		assertThat(pixel, is(50));
	}
}
