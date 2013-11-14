package com.navercorp.utilsettest.system;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.navercorp.utilset.common.UtilSet;
import com.navercorp.utilset.system.SystemUtils;
import com.navercorp.utilsettest.R;

public class SystemUtilsTestActivity extends FragmentActivity {
	TextView textViewSystemUtils;
	AsyncTask<Void, Void, String> utilSetTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_systemutils);
		
		textViewSystemUtils = (TextView) findViewById(R.id.textViewSystemUtils);
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
				textViewSystemUtils.setText(result);
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
		result += "getProcessorNumbers : " + SystemUtils.getProcessorNumbers() +"\n";
		result += "getDeviceType : " + SystemUtils.getDeviceType(this) + "\n";
		result += "isRooted : " + SystemUtils.isRooted() + "\n";
		result += "*******************************************\n";
		
		return result;
		
	}
}
