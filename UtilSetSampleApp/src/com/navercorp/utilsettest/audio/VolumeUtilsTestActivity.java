package com.navercorp.utilsettest.audio;

import static org.junit.Assert.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.navercorp.utilset.audio.VolumeUtils;
import com.navercorp.utilsettest.R;

public class VolumeUtilsTestActivity extends Activity {
	private Button volumeUpButton;
	private Button volumeDownButton;
	private TextView currentVolumeTextView;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivty_volumeutils);
		
		volumeUpButton = (Button) findViewById(R.id.volumeUpButton);
		volumeDownButton = (Button) findViewById(R.id.volumeDownButton);
		
		volumeUpButton.setOnClickListener(onClickListener);
		volumeDownButton.setOnClickListener(onClickListener);
		
		currentVolumeTextView = (TextView) findViewById(R.id.currentVolumeTextView);
		
		context = this;
		
		init();
	}
	
	private void init() {
		int curVolume = VolumeUtils.getCurrentVolume(this);
		setCurrentVolume(curVolume);
	}
	
	private void setCurrentVolume(int currentVolume) {
		currentVolumeTextView.setText("Current Volume is " + currentVolume);
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			
			if (id == R.id.volumeUpButton) {
				VolumeUtils.increaseVolume(context);
			}
			else if (id == R.id.volumeDownButton) {
				VolumeUtils.decreaseVolume(context);
			}
			
			setCurrentVolume(VolumeUtils.getCurrentVolume(context));
		}
	};
}
