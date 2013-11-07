package com.navercorp.utilsettest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.navercorp.utilsettest.audio.VolumeUtilsTestActivity;
import com.navercorp.utilsettest.cipher.CipherTestActivity;
import com.navercorp.utilsettest.common.CommonTestActivity;
import com.navercorp.utilsettest.input.InputTestActivity;
import com.navercorp.utilsettest.network.NetworkListenerTestActivity;
import com.navercorp.utilsettest.network.NetworkTestActivity;
import com.navercorp.utilsettest.storage.DiskUtilsTestAcitivity;
import com.navercorp.utilsettest.string.StringUtilsTestActivity;
import com.navercorp.utilsettest.ui.ActivityUtilsTestActivity;

public class MainActivity extends Activity implements OnClickListener {
	private static Activity activity;
	
	Button cipherTestButton;
	Button utilSetTestButton;
	Button activityUtilsTestButton;
	Button diskUtilsTestButton;
	Button keyboardUtilsTestButton;
	Button networkUtilsTestButton;
	Button networkListenerTestButton;
	Button volumeUtilsTestButton;
	Button stringUtilsTestButton;
	
	public static Activity getActivity() {
		return activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		activity = this;

		cipherTestButton = (Button) findViewById(R.id.cipherUtilsTestButton);
		cipherTestButton.setOnClickListener(this);
		
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
		
		networkListenerTestButton = (Button) findViewById(R.id.networkChangeListenerButton);
		networkListenerTestButton.setOnClickListener(this);
		
		volumeUtilsTestButton = (Button) findViewById(R.id.volumeUtilsTestButton);
		volumeUtilsTestButton.setOnClickListener(this);
		
		stringUtilsTestButton = (Button) findViewById(R.id.stringUtilsTestButton);
		stringUtilsTestButton.setOnClickListener(this);
		
		initButtonColor();
	}
	
	private void initButtonColor() {
		setButtonColor(cipherTestButton, 0);
		setButtonColor(utilSetTestButton, 1);
		setButtonColor(activityUtilsTestButton, 2);
		setButtonColor(diskUtilsTestButton, 3);
		setButtonColor(keyboardUtilsTestButton, 4);
		setButtonColor(networkUtilsTestButton, 5);
		setButtonColor(networkListenerTestButton, 6);
		setButtonColor(volumeUtilsTestButton, 7);
		setButtonColor(stringUtilsTestButton, 8);
	}
	
	private void setButtonColor(Button button, int index) {
		ButtonColor bc = ButtonColorList.getButtonColor(index);
		
		GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {bc.getE(), bc.getC() });
		gd.setCornerRadius(0f);
		gd.setStroke(1, bc.getE());
		gd.setCornerRadius(3f);
		
		button.setBackground(gd);
		button.setTextColor(bc.getTextColor());
		button.setShadowLayer(1f, 1f, 1f, bc.getE());
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) button.getLayoutParams();
		
		params.setMargins(5, 5, 5, 5);
		button.setLayoutParams(params);
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
		case R.id.cipherUtilsTestButton :
			i = new Intent(this, CipherTestActivity.class);
			break;
		
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
			break;
			
		case R.id.networkChangeListenerButton :
			i = new Intent(this, NetworkListenerTestActivity.class);
			break;
			
		case R.id.volumeUtilsTestButton :
			i = new Intent(this, VolumeUtilsTestActivity.class);
			break;
			
		case R.id.stringUtilsTestButton :
			i = new Intent(this, StringUtilsTestActivity.class);
			break;
		}

		startActivity(i);
	}

}
