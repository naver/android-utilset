package com.navercorp.utilsettest.storage;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.navercorp.utilset.storage.DiskUtils;
import com.navercorp.utilsettest.R;

public class DiskUtilsTestAcitivity extends Activity {
	private TextView textViewDiskUtils;
	private Context context;
	AsyncTask<Void, Void, String> diskUtilsTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diskutils);
		
		textViewDiskUtils = (TextView) findViewById(R.id.textViewDiskUtils);
		context = this;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		diskUtilsTask = new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return diskUtils();
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				textViewDiskUtils.setText(result);
			}
			
		};
		
		diskUtilsTask.execute();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		diskUtilsTask.cancel(true);
		
		diskUtilsTask = null;
	}

	private String diskUtils() {
		String result = "*************** DiskUtils ***************\n";
		result += "isExternalStorageMounted : " + DiskUtils.isExternalStorageMounted() + "\n";
		result += "getExternalDirPath : " + DiskUtils.getExternalDirPath(context) + "\n";
		result += "getExternalTemporaryDirPath : " + DiskUtils.getExternalTemporaryDirPath(context) + "\n";
		result += "getExternalContextRootDir : " + DiskUtils.getExternalContextRootDir(context) + "\n";
		result += "getSDCardPath : " + DiskUtils.getMicroSDCardPath() + "\n";
		result += "*****************************************" + "\n";
		return result;
	}
}