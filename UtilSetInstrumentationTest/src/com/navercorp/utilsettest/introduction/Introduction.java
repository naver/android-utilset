package com.navercorp.utilsettest.introduction;

import android.support.v4.app.FragmentActivity;

import com.navercorp.utilsettest.dialog.IntroductionDialogController;
import com.navercorp.utilsettest.dialog.IntroductionDialogFactory;

public enum Introduction {
	CipherUtilsTestCase_testCipher() {
		@Override
		public String getIntroduction() {
			return "CipherUtils provides simple AES encryption.\n" +
					"So this test shows the ciphertext by applying AES encryption utility function on user entered plaintext." +
					"Short after the encryption, you can verify the encrypted string can be restored to the exactly same string as the original one by decryption utility function.";
		}
	},
	
	VolumeUpDownTestCase_testVolumeUpAndDown() {
		@Override
		public String getIntroduction() {
			return "This method manipulates media volume.\n" +
					"Three times of volume up and then the same number of volume down will be done.";
		}
	},
	
	KeyboardUtilsTestCase_testKeyboard() {
		@Override
		public String getIntroduction() {
			return "This method shows a software keyboard being shown, hid, and toggled.\n" +
					"\n\n*** Should you not see the keyboard appear, when this being run on emulator, uncheck Hardware keyboard present option on your emulator property in AVD Manager. " +
					"This will make it work ***";
		}//  
	},
	
	NetworkListenerTestCase_showIntroductionDialog() {
		@Override
		public String getIntroduction() {
			return "When network state changes, for example, from 3G/4G to WiFi or in the opposite case, INetworkStateChangedListener will be notified if registered.\n" +
					"This shows network state change by turning on and off the WiFi Adapter.\n" +
					"If USIM is not inserted or no Access Point is near, this test shows nothing more than changing WiFi state.";
		}
	}, 
	
	ScreenUtilsTestCase_testSetBrightness() {
		@Override
		public String getIntroduction() {
			return "This test makes screen the darkest and then the brightest\n";
		}
		
	},
	
	StringUtils_testHighCompressionRatio() {
		@Override
		public String getIntroduction() {
			return "This test simulates string compression util.\n" +
					"This is good example of how efficiently recurring characters can be compressed, " +
					"this is not usual case though.\n" +
					"Following this test, an inefficient test will be executed.\n";
		}
	},
	
	StringUtils_testLowCompressionRatio() {
		@Override
		public String getIntroduction() {
			return "Followed by efficient test previously done, " +
					"this test demonstrates how disordered characters serverely affects on compression ratio";
		}
	}
	;
	
	abstract public String getIntroduction();
	
	static public void showIntroductionDialog(FragmentActivity fa, Introduction introduction, int time) {
		IntroductionDialogController idc = IntroductionDialogFactory.getInstance(fa, introduction.getIntroduction(), time);
		
		try {
			Thread.sleep(500);
			idc.show();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static public void showIntroductionDialog(FragmentActivity fa, Introduction introduction) {
		showIntroductionDialog(fa, introduction, DEFAULT_TIME);
	}
	
	private static int DEFAULT_TIME = 5000;
}
