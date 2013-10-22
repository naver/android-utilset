/*
 * NetworkUtils.java
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nhncorp.utilset.network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.ArrayList;
import java.util.Enumeration;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;


/** Detect network state changes and provide network related information.
 * In order to use this class, following permissions are required.
 * 
 * ACCESS_WIFI_STATE
 * INTERNET
 * CHANGE_WIFI_STATE
 *
 */
public class NetworkUtils {
	private static final String PHONE_STATER_PREFS = "PHONE_STATER_PREFS";
	private static final String KEY_ROAMING_ON = "KEY_ROAMING_ON";
	private static final String TAG = "NetworkUtils";
	private static NetworkUtils instance = null;
	private static boolean isWifiConnectedPrevious = false;
	
	private static final int TYPE_MOBILE = 0;
	private static final int TYPE_WIFI = 1;
	private static final int TYPE_MOBILE_MMS = 2;
	private static final int TYPE_MOBILE_SUPL = 3;
	private static final int TYPE_MOBILE_DUN = 4;
	private static final int TYPE_MOBILE_HIPRI = 5;
	private static final int TYPE_WIMAX = 6;
	private static final int TYPE_BLUETOOTH = 7;
    private static final int TYPE_DUMMY  = 8;
    private static final int TYPE_ETHERNET   = 9;

    /**
	 * 
	 * @param context Context to be used to get network information.<br>
	 *                To avoid memory leak, pass Application Context as argument. 
	 */
	public static NetworkUtils getInstance(Context context) {
		if (instance == null) {
			instance = new NetworkUtils(context);
		}

		return instance;
	}

	public static void destroy() {
		if (instance == null) {
			return;
		}

		instance.unregisterConnectivityReceiver();
		instance.clearPhoneCalledListener();
		instance.clearWifiStateChangedListener();
		instance.clearNetworkConnectedListener();
		instance = null;
	}

	public interface INetworkStateChangedListener {
		public void onNetworkStateChanged();
	};
	
	public interface INetworkConnectedListener {
		public void onNetworkConnected();
	};

	public interface IPhoneCalledListener {
		public void onPhoneCallStateChanged(int state);
	}

	private static final int STATE_NONE = -1;
    private int state = STATE_NONE;
    
	private Context context;
	private BroadcastReceiver connectivityReceiver = null;
	private ArrayList<INetworkStateChangedListener> listeners;
	private ArrayList<INetworkConnectedListener> connectedListeners;
	private ArrayList<IPhoneCalledListener> callListeners;
	private Handler handler;
	private Runnable networkChangedRunnable;
	private Runnable networkConnectedRunable;
	private ConnectivityManager connMan;
	private TelephonyManager telMgr;
	private PhoneStateListener phoneStateListener = null;

	private NetworkUtils(Context context) {
		this.context = context;
		handler = new Handler();
		listeners = new ArrayList<INetworkStateChangedListener>();
		connectedListeners = new ArrayList<INetworkConnectedListener>();
		callListeners = new ArrayList<NetworkUtils.IPhoneCalledListener>();

		networkChangedRunnable = new Runnable() {
			@Override
			public void run() {
				synchronized (NetworkUtils.this) {
					if (listeners == null) {
						return;
					}
					for (INetworkStateChangedListener l : listeners) {
						l.onNetworkStateChanged();
					}
				}
			}
		};
		
		networkConnectedRunable = new Runnable() {
			@Override
			public void run() {
				synchronized (NetworkUtils.this) {
					if (connectedListeners == null) {
						return;
					}
					for (INetworkConnectedListener l : connectedListeners) {
						l.onNetworkConnected();
					}
				}
			}
		};

		connectivityReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();
				if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
					if (state == STATE_NONE) {
						initNetworkState(context);
					} else {
						setNetworkState(context);
					}
	                handleNetworkChanged();
				}
			}
		};

		registerConnectivityReceiver();

		telMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		telMgr.listen(new PhoneStateListener() {
			public void onCallStateChanged(int state, String incomingNumber) {
				handleOnCallStateChanged(state);
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);

		setWifiConnectedPreviously(isWifiConnected());
	}

	private void handleOnCallStateChanged(final int state) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (NetworkUtils.this) {
					if (callListeners == null) {
						return;
					}
					for (IPhoneCalledListener l : callListeners) {
						l.onPhoneCallStateChanged(state);
					}
				}
			}
		});
	}

	/**
	 * Adds Phone Call Listener.
	 * This function is called when the device has a phone call.
	 * @param listener Listener which implements IPhoneCalledListener.
	 * @return true if already that listener is registered; false otherwise;
	 */
	public boolean addPhoneCalledListener(IPhoneCalledListener listener) {
		synchronized (this) {
			if (callListeners.contains(listener)) {
				return true;
			}
			return callListeners.add(listener);
		}
	}

	/**
	 * Removes Phone Call Listener
	 * @param listener Listener to be removed.
	 * @return true if it succeeds to remove listener; false otherwise;
	 */
	public boolean removePhoneCalledListener(IPhoneCalledListener listener) {
		synchronized (this) {
			if (callListeners.size() == 0) {
				return false;
			}
			return callListeners.remove(listener);
		}
	}

	private void clearPhoneCalledListener() {
		synchronized (this) {
			callListeners.clear();
		}
	}

	private void handleNetworkChanged() {
		handler.post(networkChangedRunnable);
	}
	
	private void handleNetworkConnected() {
		handler.post(networkConnectedRunable);
	}

	/**
	 * Registers receiver to be notified broadcast event when network connection changes
	 */
	private void registerConnectivityReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(connectivityReceiver, intentFilter);
	}

	/**
	 * Remove receiver to be notified broadcast event
	 */
	private void unregisterConnectivityReceiver() {
		try {
			context.unregisterReceiver(connectivityReceiver);
		} catch (Exception e) {
			Log.w(TAG, "unregisterForWifiBroadcasts - exception.");
		}
	}

	public boolean addWifiStateChangedListener(INetworkStateChangedListener listener) {
		synchronized (this) {
			if (listeners.contains(listener)) {
				return true;
			}
			return listeners.add(listener);
		}
	}

	
	public boolean removeWifiStateChangedListener(INetworkStateChangedListener listener) {
		synchronized (this) {
			if (listeners.size() == 0) {
				return false;
			}
			return listeners.remove(listener);
		}
	}

	private void clearWifiStateChangedListener() {
		synchronized (this) {
			listeners.clear();
		}
	}

	/**
	 * Registers Network Connection Listener.
	 * Once registered, connection listeners can be alerted when the device comes to have network connection. 
	 * @param listener Listener which implements INetworkConnectedListener.
	 * @return true if already that listener is registered; false otherwise;
	 */
	public boolean addNetworkConnectedListener(INetworkConnectedListener listener) {
		synchronized (this) {
			if (connectedListeners.contains(listener)) {
				return true;
			}
			return connectedListeners.add(listener);
		}
	}

	/**
	 * Removes Phone Call Listener
	 * @param listener Listener to be removed.
	 * @return true if it succeeds to remove listener; false otherwise;
	 */
	public boolean removeNetworkConnectedListener(INetworkConnectedListener listener) {
		synchronized (this) {
			if (connectedListeners.size() == 0) {
				return false;
			}
			return connectedListeners.remove(listener);
		}
	}

	private void clearNetworkConnectedListener() {
		synchronized (this) {
			connectedListeners.clear();
		}
	}

	/**
	 * Returns WiFi connection state.<br>
	 * Requires ACCESS_NETWORK_SATE permission<br>
	 * @return true if WiFi is connected; false otherwise
	 */
	public boolean isWifiConnected() {
		try {
			if (connMan == null) {
				connMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			}
			NetworkInfo wifiNetInfo = connMan.getNetworkInfo(TYPE_WIFI);
			NetworkInfo wimaxNetInfo = connMan.getNetworkInfo(TYPE_WIMAX);

			if (wifiNetInfo == null && wimaxNetInfo == null) {
				Log.e(TAG, "wifiNetworkInfo is null.");
				return false;
			}

			if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
				return true;
			}

			if (wimaxNetInfo != null && wimaxNetInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "Exception during isWifiConnected(). - " + e.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * Checks if WiFi is turned on.
	 *
	 * @return true if WiFi is turned on; false otherwise
	 */
	public boolean isWifiEnabled() {
		try {
			WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			if (wm == null) {
				return false;
			}
			
			return wm.isWifiEnabled();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Check if Mobile network is connectecd.
	 *
	 * @return true if Mobile network has connection; false otherwise
	 */
	public boolean isMobileConnected() {
		try {
			if (connMan == null) {
				connMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			}
			NetworkInfo mobileNetworkInfo = connMan.getNetworkInfo(TYPE_MOBILE);
			if (mobileNetworkInfo == null) {
				return false;
			}

			return mobileNetworkInfo.isConnected();
		} catch (Exception e) {
			Log.e(TAG, "Exception during isMobileConnected(). - " + e.getLocalizedMessage());
		}
		return false;
	}
	
	/**
	 * Checks if Mobile Network is turned on.
	 *
	 * @return true if Mobile Network is turned on; false otherwise
	 */
	public State getMobileState() {
		try {
			if (connMan == null) {
				connMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			}
			NetworkInfo mobileNetworkInfo = connMan.getNetworkInfo(TYPE_MOBILE);
			if (mobileNetworkInfo == null) {
				return State.UNKNOWN;
			}

			return mobileNetworkInfo.getState();
		} catch (Exception e) {
			Log.e(TAG, "Exception during isMobileConnected(). - " + e.getLocalizedMessage());
		}
		
		return State.UNKNOWN;
	}
	
	public State getWifiState() {
		try {
			if (connMan == null) {
				connMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			}
			NetworkInfo wifiNetworkInfo = connMan.getNetworkInfo(TYPE_WIFI);
			if (wifiNetworkInfo == null) {
				return State.UNKNOWN;
			}

			return wifiNetworkInfo.getState();
		} catch (Exception e) {
			Log.e(TAG, "Exception during wifiNetworkInfo(). - " + e.getLocalizedMessage());
		}
		
		return State.UNKNOWN;
	}

	/**
	 * Tells if network is currently connected
	 *
	 * @return true if connected; false otherwise
	 */
	public boolean isNetworkConnected() {
		try {
			if (connMan == null) {
				connMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			}
			
			NetworkInfo niMobile = connMan.getNetworkInfo(TYPE_MOBILE);
			NetworkInfo niWifi = connMan.getNetworkInfo(TYPE_WIFI);
			NetworkInfo niMms = connMan.getNetworkInfo(TYPE_MOBILE_MMS);
			NetworkInfo niSupl = connMan.getNetworkInfo(TYPE_MOBILE_SUPL);
			NetworkInfo niDun = connMan.getNetworkInfo(TYPE_MOBILE_DUN);
			NetworkInfo niHipri = connMan.getNetworkInfo(TYPE_MOBILE_HIPRI);
			NetworkInfo niWimax = connMan.getNetworkInfo(TYPE_WIMAX);
			NetworkInfo niBlueTooth = connMan.getNetworkInfo(TYPE_BLUETOOTH);
			NetworkInfo niDummy = connMan.getNetworkInfo(TYPE_DUMMY);
			NetworkInfo niEthernet = connMan.getNetworkInfo(TYPE_ETHERNET);

			if ((niMobile != null && niMobile.isConnected())
				|| (niWifi != null && niWifi.isConnected())
				|| (niMms != null && niMms.isConnected())
				|| (niSupl != null && niSupl.isConnected())
				|| (niDun != null && niDun.isConnected())
				|| (niHipri != null && niHipri.isConnected())
				|| (niWimax != null && niWimax.isConnected())
				|| (niBlueTooth != null && niBlueTooth.isConnected())
				|| (niDummy != null && niDummy.isConnected())
				|| (niEthernet != null && niEthernet.isConnected())) {
				return true;
			}

			return false;
		} catch (Exception e) {
			Log.e(TAG, "Exception during isMobileConnected(). - " + e.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * Checks if airplane mode is on.
	 * Requires following permission<br>
	 * WRITE_SETTINGS
	 * 
	 * @return true if airplane mode is on; false otherwise
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public boolean isAirplaneModeOn() {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
				return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
			
			return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Returns SIM state
	 *
	 * @return SIM State (Refer DEV Guide, TelephonyManager)
	 */
	public int getSimState() {
		try {
			TelephonyManager telMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			final int simState = telMgr.getSimState();
			return simState;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TelephonyManager.SIM_STATE_UNKNOWN;
	}

	public void listenRoamingState() {
		if (phoneStateListener == null) {
			phoneStateListener = new PhoneStateListener() {
				@Override
				public void onServiceStateChanged(ServiceState serviceState) {
					super.onServiceStateChanged(serviceState);

					try {
						final int state = serviceState.getState();
						if (state == ServiceState.STATE_IN_SERVICE || state == ServiceState.STATE_POWER_OFF) {
							final boolean roamingState = serviceState.getRoaming();
							if (roamingState != isRoamingOn()) {
								setRoamingOn(roamingState);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}

		TelephonyManager telMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		telMgr.listen(phoneStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
	}

	public boolean isRoamingOn() {
		try {
			SharedPreferences prefs = context.getSharedPreferences(PHONE_STATER_PREFS, 0);
			return prefs.getBoolean(KEY_ROAMING_ON, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private void setRoamingOn(boolean bRoamingOn) {
		try {
			SharedPreferences.Editor prefs = context.getSharedPreferences(PHONE_STATER_PREFS, 0).edit();
			prefs.putBoolean(KEY_ROAMING_ON, bRoamingOn);
			prefs.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setWifiConnectedPreviously(boolean isWifiConnected) {
		isWifiConnectedPrevious = isWifiConnected;
	}

	public boolean getWifiConnectedPreviously() {
		return isWifiConnectedPrevious;
	}
	
	private void setNetworkState(Context context) {
		if (state == STATE_NONE) {
			return;
		}
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getNetworkInfo(state);

		if (info != null && isNetworkDisconnected(info.getState())) {
			Log.d("NetworkUtils", "disconnected(state : " + state + ")");
			state = STATE_NONE;
		}
	}
	
	private boolean isNetworkDisconnected(State state) {
		switch (state) {
			case DISCONNECTED:
			case DISCONNECTING:
				return true;

			default:
				return false;
		}
	}

	private void initNetworkState(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo niMobile = cm.getNetworkInfo(TYPE_MOBILE);
		NetworkInfo niWifi = cm.getNetworkInfo(TYPE_WIFI);
		NetworkInfo niMms = cm.getNetworkInfo(TYPE_MOBILE_MMS);
		NetworkInfo niSupl = cm.getNetworkInfo(TYPE_MOBILE_SUPL);
		NetworkInfo niDun = cm.getNetworkInfo(TYPE_MOBILE_DUN);
		NetworkInfo niHipri = cm.getNetworkInfo(TYPE_MOBILE_HIPRI);
		NetworkInfo niWimax = cm.getNetworkInfo(TYPE_WIMAX);
		NetworkInfo niBlueTooth = cm.getNetworkInfo(TYPE_BLUETOOTH);
		NetworkInfo niDummy = cm.getNetworkInfo(TYPE_DUMMY);
		NetworkInfo niEthernet = cm.getNetworkInfo(TYPE_ETHERNET);
		
		if (niWifi != null && niWifi.getState() == State.CONNECTED) {
			state = TYPE_WIFI;
			handleNetworkConnected();
		} else if (niMobile != null && niMobile.getState() == State.CONNECTED) {
			state = TYPE_MOBILE;
			handleNetworkConnected();
		} else if (niBlueTooth != null && niBlueTooth.getState() == State.CONNECTED) {
			state = TYPE_BLUETOOTH;
			handleNetworkConnected();
		} else if (niMms != null && niMms.getState() == State.CONNECTED) {
			state = TYPE_MOBILE_MMS;
			handleNetworkConnected();
		} else if (niSupl != null && niSupl.getState() == State.CONNECTED) {
			state = TYPE_MOBILE_SUPL;
			handleNetworkConnected();
		} else if (niDun != null && niDun.getState() == State.CONNECTED) {
			state = TYPE_MOBILE_DUN;
			handleNetworkConnected();
		} else if (niHipri != null && niHipri.getState() == State.CONNECTED) {
			state = TYPE_MOBILE_HIPRI;
			handleNetworkConnected();
		} else if (niWimax != null && niWimax.getState() == State.CONNECTED) {
			state = TYPE_WIMAX;
			handleNetworkConnected();
		} else if (niDummy != null && niDummy.getState() == State.CONNECTED) {
			state = TYPE_DUMMY;
			handleNetworkConnected();
		} else if (niEthernet != null && niEthernet.getState() == State.CONNECTED) {
			state = TYPE_ETHERNET;
			handleNetworkConnected();
		}
	}
	
	/** Returns IP Address.<br>
	 * Requires ACCESS_WIFI_STATE, INTERNET permissions<br>
	 * 
	 * @param ipv6 Option to choose IP address type. 
	 * @return IP address made up of IPv6 if parameter ipv6 is true or IPv4 if parameter ipv6 is false; null if it do not have IP address.
	 * @throw RuntimeException when it fails due to poor network condition. 
	 */
	public String getIpAddress(boolean ipv6) {
		try {
	        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements();) {
	            NetworkInterface networkInterface = networkInterfaces.nextElement();
	            for (Enumeration<InetAddress> addresses = networkInterface.getInetAddresses(); addresses.hasMoreElements();) {
	                InetAddress inetAddress = addresses.nextElement();
	                if (inetAddress.isLoopbackAddress()) {
	                    continue;
	                }
	                if (ipv6 && inetAddress instanceof Inet6Address) {
	                	
	                    return inetAddress.getHostAddress();
	                } else if (ipv6 == false && inetAddress instanceof Inet4Address) {
	                    return inetAddress.getHostAddress();
	                }
	            }
	        }
	    } catch (SocketException e) {
	    	throw new RuntimeException(e);
	    }
	    return null;
	}
	
	
	/** Returns MAC Address of WiFi Adapter<br>
	 * 
	 * @return String representing MAC Address of WiFi Adapter; Empty String in some cases such as No WiFi Adapter is installed or WiFi is turned off. 
	 */
	public String getWifiMacAddress() {
		WifiManager wifiMan = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		if (wifiMan != null) {
			WifiInfo wifiInf = wifiMan.getConnectionInfo();
			return wifiInf.getMacAddress();
		}
		return "";
	}
	
	/**
	 * Turns on WiFi Adapter<br>
	 * 
	 * Requires READ_PHONE_STATE, CHANGE_WIFI_STATE and ACCESS_NETWORK_STATE permissions<br>
	 * 
	 * @param context
	 */
	public void setWifiEnabled(Context context) {
		WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiMan.setWifiEnabled(true);
	}
	
	/**
	 * Turns off WiFi Adapter<br>
	 * 
	 * requires READ_PHONE_STATE, CHANGE_WIFI_STATE and ACCESS_NETWORK_STATE permissions<br>
	 * 
	 * @param context
	 */
	public void setWifiDisabled(Context context) {
		WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiMan.setWifiEnabled(false);
	}
	
	public boolean isWifiFake() {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress("173.194.126.131", 80));
		} catch (IOException e) {
			Log.d(TAG, "Current Wifi is stupid!!! by IOException");
			return true;
		} catch (UnresolvedAddressException e) {
			Log.d(TAG, "Current Wifi is stupid!!! by InetSocketAddress");
			return true;
		}
		
		return false;
	}
}
