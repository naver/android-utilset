package com.navercorp.utilset.device;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 
 * @author jaemin.woo
 *
 */
class PhoneNumberUtils {
	public String getMobilePhoneNumber(Context context) {
		TelephonyManager telManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
		return telManager.getLine1Number();
	}
	
	public boolean isAbleToReceiveSms(Context context) {
		String phoneNumber = getMobilePhoneNumber(context);
		
		if (phoneNumber == null)
			return false;
		
		if ("".equals(phoneNumber) == false)
			return true;
		
		return false;
	}
}