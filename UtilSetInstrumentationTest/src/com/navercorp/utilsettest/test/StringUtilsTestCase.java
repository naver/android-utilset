package com.navercorp.utilsettest.test;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.navercorp.utilsettest.introduction.Introduction;
import com.navercorp.utilsettest.string.StringUtilsTestActivity;

public class StringUtilsTestCase extends
		ActivityInstrumentationTestCase2<StringUtilsTestActivity> {
	private Solo solo;
	private FragmentActivity activity;
	private static String highCompressionRatioString = "aaaaaaaaaaaa";
	private static String lowCompressionRatioString  = "abcdefghijkl";

	public StringUtilsTestCase() {
		// TODO Auto-generated constructor stub
		super(StringUtilsTestActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();

		activity = getActivity();
		solo = new Solo(getInstrumentation(), activity);
	}
	
	public void tearDown() throws Exception
	{
        solo.finishOpenedActivities();
	}
	
	private AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
		@Override
		protected Void doInBackground(String... params) {
			String string = params[0];
			int length = string.length();
			for (int i=0;i<length;++i) {
				solo.enterText(0, string.charAt(i)+"");
			}
			
			solo.clickOnButton(0);
			return null;
		}
	};
	
	private void executeTypingAndCompression(String string) {
		task.execute(string);
		
		try {
			task.get();
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void testHighCompressionRatio() {
		
		solo.waitForActivity(activity.getClass().getSimpleName());
		
		Introduction.showIntroductionDialog(activity, Introduction.StringUtils_testHighCompressionRatio);
		
		executeTypingAndCompression(highCompressionRatioString);
	}

	public void testLowCompressionRatio() {
		solo.waitForActivity(activity.getClass().getSimpleName());
		
		Introduction.showIntroductionDialog(activity, Introduction.StringUtils_testLowCompressionRatio);
		
		executeTypingAndCompression(lowCompressionRatioString);
	}
	
}