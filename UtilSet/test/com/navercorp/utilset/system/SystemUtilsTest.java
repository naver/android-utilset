package com.navercorp.utilset.system;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

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
		assertThat(SystemUtils.getProcessorNumbers(), is(greaterThanOrEqualTo(1)));
	}
}
