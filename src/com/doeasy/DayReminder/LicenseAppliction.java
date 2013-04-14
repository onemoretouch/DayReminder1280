package com.doeasy.DayReminder;

import android.app.Application;

import com.doeasy.DayReminder.DB.DrInfoDB;
public class LicenseAppliction extends Application {
	private int intLicenseResult=0;
	private boolean isInit = false;
	private DrInfoDB drDB = null;
	
	public void setIsInit(boolean _b) {
		this.isInit = _b;
	}

	public boolean getIsInit() {
		return this.isInit;
	}
	
	public void setLicenseReuslt(int iLR)
	{
		intLicenseResult=iLR;
	}
	public int getLicenseReuslt()
	{
		return intLicenseResult;
	}
	public String getErrorMessage()
	{
		String strError="";
		switch(intLicenseResult)
		{
			case 0:
				break;
			case 1:
				strError="此为试用版，请到魅族软件中心购买正式版";
				break;
			case -1:
				strError="license无效，请到魅族软件中心下载";
				break;
			case -2:
				strError="无对应license，请到魅族软件中心下载";
				break;
			case -3:
				strError="读取license失败，请到魅族软件中心下载";
		}
		return strError;
	}
	public String getNotFuncMessage()
	{
		return "您现在使用的测试版，无此功能，请到魅族软件中心购买正式版";
	}
	public void setDataHelper(DrInfoDB _Helper) {
		this.drDB = _Helper;
	}

	public DrInfoDB getDataHelper() {
		return this.drDB;
	}

}
