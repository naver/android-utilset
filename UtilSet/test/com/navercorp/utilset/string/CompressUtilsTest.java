package com.navercorp.utilset.string;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * @author jaemin.woo
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CompressUtilsTest {
	private static String LONG_LONG_STRING = "qwertyuiopassdfghjklxcvbnm!@#$%^&*()";

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
	}

	@Test
	public void shouldCompressString() {
		String compressed = CompressUtils.compressString(LONG_LONG_STRING);
		assertThat(compressed, is(not(LONG_LONG_STRING)));
	}
	
	@Test
	public void shouldRestoreCompressedStringToOriginalString() {
		String compressed = CompressUtils.compressString(LONG_LONG_STRING);
		assertThat(compressed, is(not(LONG_LONG_STRING)));
		
		String restored = CompressUtils.decompressString(compressed);
		assertThat(restored, is(not(compressed)));
		assertThat(restored, is(LONG_LONG_STRING));		
	}
}
