package com.navercorp.utilsettest.common;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.navercorp.utilset.common.UtilSet;
import com.navercorp.utilsettest.R;

public class CommonTestActivity extends FragmentActivity {
	TextView textViewUtilSet;
	AsyncTask<Void, Void, String> utilSetTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utilset);
		
		textViewUtilSet = (TextView) findViewById(R.id.textViewUtilSetTest);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		utilSetTask = new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return utilSetTest();
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				textViewUtilSet.setText(result);
			}
			
		};
		
		utilSetTask.execute();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		utilSetTask.cancel(true);
		
		utilSetTask = null;
	}

	public String utilSetTest() {
		UtilSet.loadAsyncTaskClass();
		
		String result = "*************** UtilSetTest ***************\n";
		result += "DeviceType : " + UtilSet.getDeviceType(this).toString() + "\n";
		result += "IsRooted : " + UtilSet.isRooted() + "\n";
		result += "LauncherType : " + UtilSet.getLauncherType(this).toString() +"\n";
		result += "Has Software Keys : " + UtilSet.hasSoftwareKeys(this) + "\n";
		result += "Processor Number : " + UtilSet.getProcessorNumbers() + "\n";
		result += "IsSMSAvailable : " + UtilSet.hasSmsCapability(getApplicationContext()) + "\n";
		result += "getMobilePhoneNumber : " + UtilSet.getMobilePhoneNumber(this) + "\n";
		result += "*******************************************\n";
		
		return result;
		
	}

}
