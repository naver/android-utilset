package com.navercorp.utilset.system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.*;

/**
*
* @author jaemin.woo
* 
*/
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SystemUtilsTest {
	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
	}
	
	@Test
	public void shouldReturnAtLeastOneAsProcessorNumbers() {
		assertEquals(SystemUtils.getProcessorNumbers(), 4);
//		assertThat(SystemUtils.getProcessorNumbers(), is(greaterThanOrEqualTo(1)));
	}
}
