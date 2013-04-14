package com.doeasy.DayReminder;

public class LSDate
{
	public int 		Year;
	public int 		Month;
	public int 		Day;
	public boolean 	IsLeap;
	public LSDate(int _Year,int _Month,int _Day,boolean _IsLeap)
	{
		Year=_Year;
		Month=_Month;
		Day=_Day;
		IsLeap=_IsLeap;
	}
	public LSDate()
	{
		Year=2009;
		Month=3;
		Day=8;
		IsLeap=false;
	}
}