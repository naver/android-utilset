package com.navercorp.utilsettest.cipher;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.navercorp.utilset.cipher.CipherUtils;
import com.navercorp.utilsettest.R;

public class CipherTestActivity extends FragmentActivity implements OnClickListener {
	EditText plainTextCipherTest;
	EditText seedTextCipherTest;
	Button encryptButtonCipherTest;
	Button decryptButtonCipherTest;
	CipherUtils cipherUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cipher);
		
		plainTextCipherTest = (EditText) findViewById(R.id.plainTextCipherTest);
		seedTextCipherTest = (EditText) findViewById(R.id.seedTextCipherTest);
		encryptButtonCipherTest = (Button) findViewById(R.id.encryptButtonCipherTest);
		decryptButtonCipherTest = (Button) findViewById(R.id.decryptButtonCipherTest);
		
		encryptButtonCipherTest.setOnClickListener(this);
		decryptButtonCipherTest.setOnClickListener(this);
		
		cipherUtils = new CipherUtils();
	}
	
	public void onClick(View v) {
		int id = v.getId();
		
		String seed = seedTextCipherTest.getText().toString();
		String text = plainTextCipherTest.getText().toString();
		
		if (id == R.id.encryptButtonCipherTest) {
			afterEncryptButtonClicked();
			
			String encrypted = cipherUtils.encrypt(seed, text);
			
			plainTextCipherTest.setText(encrypted);
		} else if (id == R.id.decryptButtonCipherTest) {
			String decrypted = cipherUtils.decrypt(seed, text);
			
			plainTextCipherTest.setText(decrypted);
			
			afterDecryptButtonClicked();
		}
	}
	
	private void afterEncryptButtonClicked() {
		plainTextCipherTest.setEnabled(false);
		seedTextCipherTest.setEnabled(false);
		encryptButtonCipherTest.setEnabled(false);
		decryptButtonCipherTest.setEnabled(true);
	}
	
	private void afterDecryptButtonClicked() {
		plainTextCipherTest.setEnabled(true);
		seedTextCipherTest.setEnabled(true);
		encryptButtonCipherTest.setEnabled(true);
		decryptButtonCipherTest.setEnabled(false);
	}
}
