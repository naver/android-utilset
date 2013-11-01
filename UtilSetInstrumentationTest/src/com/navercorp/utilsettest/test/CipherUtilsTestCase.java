package com.navercorp.utilsettest.test;

import android.app.Activity;
import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.MainActivity;
import com.navercorp.utilsettest.cipher.CipherTestActivity;
import com.navercorp.utilset.cipher.CipherUtils;
import com.navercorp.utilset.ui.ScreenUtils;
import com.navercorp.utilsettest.R;

public class CipherUtilsTestCase extends
		ActivityInstrumentationTestCase2<CipherTestActivity> {
	private Solo solo;
	private String doReMiPaSoLaTiDo = "do re mi pa so la ti do";
	private String plainText = "I like Genymotion!!! XD";

	public CipherUtilsTestCase() {
		// TODO Auto-generated constructor stub
		super(CipherTestActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
	}

	@SmallTest
	public void testCipher() {
		final Activity activity = solo.getCurrentActivity();

		EditText seedText = (EditText) activity.findViewById(R.id.seedTextCipherTest);
		assertEquals(seedText.getId(), R.id.seedTextCipherTest);
		EditText editText = (EditText) activity.findViewById(R.id.plainTextCipherTest);
		assertEquals(editText.getId(), R.id.plainTextCipherTest);
		
		solo.clickOnView(seedText);
		int size = doReMiPaSoLaTiDo.length();
		for (int i=0;i<size;++i) {
			solo.enterText(seedText, doReMiPaSoLaTiDo.charAt(i) + "");
		}
		
		solo.clickOnView(editText);
		size = plainText.length();
		for (int i=0;i<size;++i) {
		solo.enterText(editText, plainText.charAt(i) + "");
		}
		
		solo.clickOnView(activity.findViewById(R.id.encryptButtonCipherTest));
		solo.sleep(2000);
		
		solo.clickOnView(activity.findViewById(R.id.decryptButtonCipherTest));
		solo.sleep(1500);
	}

}
