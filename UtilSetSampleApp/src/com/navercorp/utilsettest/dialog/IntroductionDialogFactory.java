package com.navercorp.utilsettest.dialog;

import android.support.v4.app.FragmentActivity;

public class IntroductionDialogFactory {
	public static IntroductionDialogController getInstance(FragmentActivity fa, String description, int time) {
		return new IntroductionDialogController(fa.getSupportFragmentManager(), description, time);
	}
}
