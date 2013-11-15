package com.navercorp.utilsettest.string;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.navercorp.utilset.string.CompressUtils;
import com.navercorp.utilsettest.R;

public class StringUtilsTestActivity extends FragmentActivity {
	EditText uncompressedText;
	TextView compressedText;
	Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stringutils);
		
		uncompressedText = (EditText) findViewById(R.id.uncompressedTextStringUtils);
		compressedText = (TextView) findViewById(R.id.compressedTextStringUtils);
		submit = (Button) findViewById(R.id.submitButtonStringUtils);
		submit.setOnClickListener(onClickListener);
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String s = uncompressedText.getText().toString();
			String compressed = CompressUtils.compressString(s);
			compressedText.setText(compressed);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
