package com.doeasy.DayReminder;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import com.doeasy.DayReminder.DB.DrInfoDB;


public class InitApp {
	private Context context;
	LicenseAppliction pApplication;

	public InitApp() {

	}

	public InitApp(Context _context) {
		context = _context;
		pApplication = (LicenseAppliction) context.getApplicationContext();
	}

	public void SetApp(int _flag) {
		try {
			if (!pApplication.getIsInit()) {

				DrInfoDB dbHelper = new DrInfoDB(context);
				pApplication.setDataHelper(dbHelper);
				pApplication.setIsInit(true);
			}
			if (_flag == 1) {
				pApplication.setIsInit(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String sTmp=info.getMacAddress();
		sTmp=sTmp.replace(":", "");
		return sTmp;
	}
	public String getDeviceID()
	{
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getDeviceId();
	}
	public String getSerialNo()
	{
		String serialNo=android.os.Build.SERIAL;
		int iCount=0;
		while(serialNo.length()==0&&iCount<3)
		{
			serialNo=android.os.Build.SERIAL;
			SystemClock.sleep(1000);
			iCount++;
		}
		return serialNo;
	}
}
