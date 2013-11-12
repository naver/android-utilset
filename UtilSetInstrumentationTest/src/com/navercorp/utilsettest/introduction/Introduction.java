package com.navercorp.utilsettest.introduction;

import android.support.v4.app.FragmentActivity;

import com.navercorp.utilsettest.dialog.IntroductionDialogController;
import com.navercorp.utilsettest.dialog.IntroductionDialogFactory;

public enum Introduction {
	CipherUtilsTestCase_testCipher() {
		@Override
		public String getIntroduction() {
			return "This method simulates simple Cipher";
		}
	},
	
	VolumeUpDownTestCase_testVolumeUpAndDown() {
		@Override
		public String getIntroduction() {
			return "This method simulates simple volume up down test";
		}
	},
	
	KeyboardUtilsTestCase_testKeyboard() {
		@Override
		public String getIntroduction() {
			return "This method simulates simeple keyboard test";
		}
	},
	
	NetworkListenerTestCase_showIntroductionDialog() {
		@Override
		public String getIntroduction() {
			return "This method simulates simple network listener";
		}
		
	}, 
	
	ScreenUtilsTestCase_testSetBrightness() {
		@Override
		public String getIntroduction() {
			return "Testing ScreenUtilsTestCase...";
		}
		
	},
	
	StringUtils_testHighCompressionRatio() {
		@Override
		public String getIntroduction() {
			return "Testing StringCompression showing high ratio";
		}
		
	},
	
	StringUtils_testLowCompressionRatio() {
		@Override
		public String getIntroduction() {
			return "Testing StringCompression showing low ratio";
		}
	}
	;
	
	abstract public String getIntroduction();
	
	static public void showIntroductionDialog(FragmentActivity fa, Introduction introduction) {
		IntroductionDialogController idc = IntroductionDialogFactory.getInstance(fa, introduction.getIntroduction());
		
		try {
			Thread.sleep(500);
			idc.show();
			Thread.sleep(3000);
			idc.dismiss();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
