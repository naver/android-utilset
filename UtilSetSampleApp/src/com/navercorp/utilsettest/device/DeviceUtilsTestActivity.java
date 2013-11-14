package com.navercorp.utilsettest.device;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.navercorp.utilset.device.DeviceUtils;
import com.navercorp.utilsettest.R;

public class DeviceUtilsTestActivity extends FragmentActivity {
	TextView textViewUtilSet;
	AsyncTask<Void, Void, String> deviceUtilsTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deviceutils);
		
		textViewUtilSet = (TextView) findViewById(R.id.textViewDeviceUtilsTest);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		deviceUtilsTask = new AsyncTask<Void, Void, String>() {
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
		
		deviceUtilsTask.execute();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		deviceUtilsTask.cancel(true);
		
		deviceUtilsTask = null;
	}

	public String utilSetTest() {
		String result = "*************** UtilSetTest ***************\n";
		result += "LauncherType : " + DeviceUtils.getLauncherType(this).toString() +"\n";
		result += "IsSMSAvailable : " + DeviceUtils.hasSmsCapability(getApplicationContext()) + "\n";
		result += "getDeviceType : " + DeviceUtils.getDeviceType(this) + "\n";
		result += "*******************************************\n";
		
		return result;
		
	}

}
