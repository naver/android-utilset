package com.navercorp.utilsettest.input;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.navercorp.utilset.input.KeyboardUtils;
import com.navercorp.utilsettest.R;

public class KeyboardUtilsTestActivity extends FragmentActivity implements OnClickListener {
	Context context;
	EditText editTextKeyboardUtils;
	Button keyboardUtilsShowButton;
	Button keyboardUtilsHideButton;
	Button keyboardUtilsToggleButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyboardutils);
		editTextKeyboardUtils = (EditText) findViewById(R.id.editTextKeyboardUtils);
		
		keyboardUtilsShowButton = (Button) findViewById(R.id.keyboardUtilsShowButton);
		keyboardUtilsHideButton = (Button) findViewById(R.id.keyboardUtilsHideButton);
		keyboardUtilsToggleButton = (Button) findViewById(R.id.keyboardUtilsToggleButton);
		
		
		keyboardUtilsShowButton.setOnClickListener(this);
		keyboardUtilsHideButton.setOnClickListener(this);
		keyboardUtilsToggleButton.setOnClickListener(this);
		
		context = this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.keyboardUtilsShowButton :
			KeyboardUtils.showSoftKeyboard(this, editTextKeyboardUtils);
			break;
			
		case R.id.keyboardUtilsHideButton :
			KeyboardUtils.hideSoftKeyboard(this, editTextKeyboardUtils);
			break;
			
		case R.id.keyboardUtilsToggleButton :
			KeyboardUtils.toggleKeyPad(this);
			break;
		}
	}
}
