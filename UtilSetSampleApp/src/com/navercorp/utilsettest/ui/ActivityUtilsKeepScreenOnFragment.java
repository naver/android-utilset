package com.navercorp.utilsettest.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navercorp.utilset.ui.ActivityUtils;
import com.navercorp.utilsettest.MainActivity;
import com.navercorp.utilsettest.R;


public class ActivityUtilsKeepScreenOnFragment extends Fragment {
	private TextView textViewActivityUtils;
	private Context context;
	AsyncTask<Void, Void, String> activityUtilsTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_keep_screen_on, container, false);
		textViewActivityUtils = (TextView) rootView.findViewById(R.id.textViewActivityUtils);
		context = getActivity();
		
		activityUtilsTask = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return activityUtils();
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				textViewActivityUtils.setText(result);				
			}
		};

		activityUtilsTask.execute();
		
		return rootView;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		
		activityUtilsTask.cancel(true);
		activityUtilsTask = null;
	}



	private String activityUtils() {
		String result = "*************** ActivityUtils ***************\n";
		result += "isPackageInstalled(\"com.exmaple.android.utilsettest\") \n - " + ActivityUtils.isPackageInstalled(context, "com.example.androidutilsettest") + "\n\n";
		result += "getBaseActivityPackageName \n - " + ActivityUtils.getBaseActivityPackageName(context) + "\n\n";
		result += "getBaseActivityClassName \n - " + ActivityUtils.getBaseActivityClassName(context) + "\n\n";
		result += "getTopActivityPackageName \n - " + ActivityUtils.getTopActivityPackageName(context) + "\n\n";
		result += "getTopActivityClassName \n - " + ActivityUtils.getTopActivityClassName(context) + "\n\n";
		result += "isTopApplication(this) : " + ActivityUtils.isTopApplication(context) + "\n";
		result += "isContextForeground(this) : " + ActivityUtils.isContextForeground(context) + "\n";
		result += "isTopApplication(MainActivity) : " + ActivityUtils.isTopApplication(MainActivity.getActivity()) + "\n";
		result += "isContextForeground(MainActivity) : " + ActivityUtils.isContextForeground(MainActivity.getActivity()) + "\n";
		result += "*********************************************\n";
		return result;
	}

}
