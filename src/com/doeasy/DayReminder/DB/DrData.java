package com.doeasy.DayReminder.DB;

public class DrData {
	public		int		iDrId;
	public 	String 	sDrName;
	public 	String 	sPhoto;
	public 	String 	sTelPhone;
	public		int		iDrType;
	public		int		iYear;
	public		int		iMonth;	
	public		int		iDay;
	public		int		iDayType;
	public 	String 	sSolar;
	public 	String 	sLunar;
	public		int		iRmDateType;
	public		long	lNextDate;
	public		int		iIsAppWidget;
	public		int		iApDays;
	public		int		iRemindType;
	public		int		iAfDays;
	public		int		iRemindTime;
	public		int		iIsAutoSMS;
	public 	String 	sSMSText;
	public 	String 	sRemark;
	public 	String 	sDrInitials;
	public DrData()
	{
		iDrId=0;
	}
	public DrData(int _DrId,String _DrName,String _Photo,String _TelPhone,int _DrType,int _Year,int _Month,int _Day,int _DayType,String _Solar,String _Lunar,long _NextDate,int _RmDateType,int  _IsAppWidget,int _ApDays,int _RemindType,int _AfDays,int _RemindTime,int _IsAutoSMS,String _SMSText,String _Remark,String _DrInitials)
	{
		iDrId=_DrId;
		sDrName=_DrName;
		sPhoto=_Photo;
		sTelPhone=_TelPhone;
		iDrType=_DrType;
		iYear=_Year;
		iMonth=_Month;	
		iDay=_Day;
		iDayType=_DayType;
		sSolar=_Solar;
		sLunar=_Lunar;
		iRmDateType=_RmDateType;
		lNextDate=_NextDate;
		iIsAppWidget=_IsAppWidget;
		iApDays=_ApDays;
		iRemindType=_RemindType;
		iAfDays=_AfDays;
		iRemindTime=_RemindTime;
		iIsAutoSMS=_IsAutoSMS;
		sSMSText=_SMSText;
		sRemark=_Remark;
		sDrInitials=_DrInitials;
	}
}
