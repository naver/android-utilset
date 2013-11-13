package com.navercorp.utilsettest.dialog;

import android.support.v4.app.FragmentManager;

public class IntroductionDialogController {
	private static final String TAG = "IntroductionDialogFragment";
	private IntroductionDialogFragment idf;
	private FragmentManager fm;

	public IntroductionDialogController(FragmentManager fm, String description, int time) {
		this.idf = new IntroductionDialogFragment("What is this test for", description, time);
		this.fm = fm;
	}
	
	public void show() {
        idf.show(fm, TAG);
	}
	
	public boolean isShowing() {
		return idf.isShowing();
	}
	
	public void dismiss() {
		idf.dismiss();
	}
}
