package com.navercorp.utilsettest.network;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.navercorp.utilset.network.NetworkMonitor;
import com.navercorp.utilsettest.R;

public class NetworkListenerTestActivity extends FragmentActivity {

	private TextView wifiStateTextView;
	private TextView networkStateTextView;
//	private TextView unusedTextView;
	private ToggleButton toggleWifiButton;
	private NetworkMonitor networkUtils;

	private NetworkMonitor.NetworkStateChangedListener networkStateChangedListener = new NetworkMonitor.NetworkStateChangedListener() {
		@Override
		public void onNetworkStateChanged() {
			Log.d("UtilSet", "NetworkStateChanged");
//			setWifiStateTextView(networkUtils.isWifiConnected());
			setNetworkStateTextView();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_listener);

		networkUtils = NetworkMonitor.getInstance(this);
		
		wifiStateTextView = (TextView) findViewById(R.id.network_listener_test_wifi_state);
		networkStateTextView = (TextView) findViewById(R.id.network_listener_test_network_state);
//		unusedTextView = (TextView) findViewById(R.id.network_listener_test_unused);

		toggleWifiButton = (ToggleButton) findViewById(R.id.network_listener_test_toggle_wifi);
		toggleWifiButton.setOnCheckedChangeListener(onCheckedChangeListener);
		
		init();
	}

	private void init() {
		boolean isWifiEnabled = networkUtils.isWifiEnabled();
		toggleWifiButton.setChecked(isWifiEnabled);

		setWifiStateTextView(isWifiEnabled);

//		unusedTextView.setText("I'm unused");
		networkUtils.addWifiStateChangedListener(networkStateChangedListener);
	}
	
	private void setNetworkStateTextView() {
		networkStateTextView.setText("Network State has changed");
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				networkStateTextView.setText("");				
			}
		}, 1500);
		
	}

	private void setWifiStateTextView(boolean isWifiEnabled) {
		wifiStateTextView.setText("WiFi is "
				+ (isWifiEnabled == true ? "On" : "Off"));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetworkMonitor.destroy();
		networkUtils = null;
	}

	OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			int id = buttonView.getId();
			if (id == R.id.network_listener_test_toggle_wifi) {
				if (isChecked) {
					networkUtils.setWifiEnabled(NetworkListenerTestActivity.this);
					Log.d("UtilSet", "WiFi is enabled");

				} else {
					networkUtils.setWifiDisabled(NetworkListenerTestActivity.this);
					Log.d("UtilSet", "WiFi is disabled");
				}
			}
			setWifiStateTextView(isChecked);
		}
	};
}
