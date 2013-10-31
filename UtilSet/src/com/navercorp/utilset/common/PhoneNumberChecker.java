package com.navercorp.utilset.common;

import android.content.Context;
import android.telephony.TelephonyManager;

class PhoneNumberChecker {
	public String getPhoneNumber(Context context) {
		TelephonyManager telManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
		return telManager.getLine1Number();
	}
	
	public boolean isAbleToReceiveSms(Context context) {
		String phoneNumber = getPhoneNumber(context);
		
		if (phoneNumber == null)
			return false;
		
		if ("".equals(phoneNumber) == false)
			return true;
		
		return false;
	}
}