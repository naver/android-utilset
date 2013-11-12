package com.navercorp.utilsettest.test.without_ui;

import com.navercorp.utilset.audio.VolumeUtils;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VolumeUtilsTest extends ApplicationTestCase<Application> {
	private static final int MAX_VOLUME = 15;
	private static final int MIN_VOLUME = 0;
	
	private Context context;
	
	public VolumeUtilsTest() {
		super(Application.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		createApplication();
		
		context = getApplication().getApplicationContext();
	}
	
	public void testGetMaximumVolume() {
		assertEquals(MAX_VOLUME, VolumeUtils.getMaximumVolume(context));
	}
	
	public void testGetMinimumVolume() {
		assertEquals(MIN_VOLUME, VolumeUtils.getMinimumVolume(context));
	}
	
	public void testGetCurrentVolume() {
		int cur = VolumeUtils.getCurrentVolume(context);
		assertThat(cur, lessThanOrEqualTo(MAX_VOLUME));
		assertThat(cur, greaterThanOrEqualTo(MIN_VOLUME));
	}
	
	public void setVolume() {
		VolumeUtils.setVolume(context, MAX_VOLUME/2);
		assertEquals(MAX_VOLUME/2, VolumeUtils.getCurrentVolume(context));
	}
	
	public void testVolumeUp() {
		int prev = VolumeUtils.getCurrentVolume(context);
		VolumeUtils.increaseVolume(context);
		int cur = VolumeUtils.getCurrentVolume(context);
		assertTrue(checkVolumeAdjustment(prev, cur, 1));
	}
	
	public void testVolumeDown() {
		int prev = VolumeUtils.getCurrentVolume(context);
		VolumeUtils.decreaseVolume(context);
		int cur = VolumeUtils.getCurrentVolume(context);
		assertTrue(checkVolumeAdjustment(prev, cur, -1));
	}
	
	private boolean checkVolumeAdjustment(int prev, int cur, int delta) {
		int maxVolume = VolumeUtils.getMaximumVolume(context);
		int minVolume = VolumeUtils.getMinimumVolume(context);
		
		if (prev + delta >= maxVolume && cur == maxVolume)
			return true;
		else if (prev + delta <= minVolume && cur == minVolume)
			return true;
		else if (prev + delta == cur)
			return true;
		
		return false;
	}
}
