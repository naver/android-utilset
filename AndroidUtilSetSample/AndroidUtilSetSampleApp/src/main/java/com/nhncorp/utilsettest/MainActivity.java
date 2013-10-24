package com.nhncorp.utilsettest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nhncorp.utilsettest.common.CommonTestActivity;
import com.nhncorp.utilsettest.input.InputTestActivity;
import com.nhncorp.utilsettest.network.NetworkTestActivity;
import com.nhncorp.utilsettest.storage.DiskUtilsTestAcitivity;
import com.nhncorp.utilsettest.ui.ActivityUtilsTestActivity;

public class MainActivity extends Activity implements OnClickListener {
	private static Activity activity;
	
	Button utilSetTestButton;
	Button activityUtilsTestButton;
	Button diskUtilsTestButton;
	Button keyboardUtilsTestButton;
	Button networkUtilsTestButton;
	
	public static Activity getActivity() {
		return activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		activity = this;

		utilSetTestButton = (Button) findViewById(R.id.utilSetTestButton);
		utilSetTestButton.setOnClickListener(this);
		
		activityUtilsTestButton = (Button) findViewById(R.id.acitivtyUtilsTestButton);
		activityUtilsTestButton.setOnClickListener(this);
		
		
		diskUtilsTestButton = (Button) findViewById(R.id.diskUtilsButton);
		diskUtilsTestButton.setOnClickListener(this);
		
		keyboardUtilsTestButton = (Button) findViewById(R.id.keyboardUtilsButton);
		keyboardUtilsTestButton.setOnClickListener(this);
		
		networkUtilsTestButton = (Button) findViewById(R.id.networkUtilsButton);
		networkUtilsTestButton.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent i = null;
		
		switch (id) {
		case R.id.utilSetTestButton :
			i = new Intent(this, CommonTestActivity.class);
			break;
			
		case R.id.acitivtyUtilsTestButton :
			i = new Intent(this, ActivityUtilsTestActivity.class);
			break;
			
		case R.id.diskUtilsButton :
			i = new Intent(this, DiskUtilsTestAcitivity.class);
			break;
			
		case R.id.keyboardUtilsButton :
			i = new Intent(this, InputTestActivity.class);
			break;
			
		case R.id.networkUtilsButton :
			i = new Intent(this, NetworkTestActivity.class);
		}

		startActivity(i);
	}

}
