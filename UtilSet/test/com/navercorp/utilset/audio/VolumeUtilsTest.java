package com.navercorp.utilset.audio;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import android.content.Context;

/**
 * @author sanghyuk.jung
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class VolumeUtilsTest {
	private Context context;

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
		this.context = Robolectric.application;
	}
	
	@Test
	public void shouldSetVolume(){
		VolumeUtils.setVolume(context, 3);
		int volume = VolumeUtils.getCurrentVolume(context);
		assertThat(volume, is(3));
	}
	
	@Test
	public void shouldIncreaseVolume(){
		VolumeUtils.setVolume(context, 3);
		VolumeUtils.increaseVolume(context);
		int volume = VolumeUtils.getCurrentVolume(context);
		assertThat(volume, is(4));
	}
	
	@Test
	public void shouldIncreaseVolumeWithLevel(){
		VolumeUtils.setVolumeWithLevel(context, 3);
		VolumeUtils.increaseVolumeWithLevel(context);
		int volume = VolumeUtils.getCurrentVolume(context);
		assertThat(volume, is(4));
	}

	@Test
	public void shouldDecreaseVolume(){
		VolumeUtils.setVolume(context, 3);
		VolumeUtils.decreaseVolume(context);
		int volume = VolumeUtils.getCurrentVolume(context);
		assertThat(volume, is(2));
	}
	
	@Test
	public void shouldDecreaseVolumeWithLevel(){
		VolumeUtils.setVolumeWithLevel(context, 3);
		VolumeUtils.decreaseVolumeWithLevel(context);
		int volume = VolumeUtils.getCurrentVolume(context);
		assertThat(volume, is(2));
	}

	@Test
	public void shouldGetMaxiumVolume(){
		int maxVolume = VolumeUtils.getMaximumVolume(context);
		int maxVolumeInRobolectric = 15;
		assertThat(maxVolume, is(maxVolumeInRobolectric));	
	}
}
