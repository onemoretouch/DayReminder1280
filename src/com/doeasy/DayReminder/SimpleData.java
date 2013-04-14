package com.doeasy.DayReminder;

import java.util.Date;

public class SimpleData {
	private String sName;
	private Date dNext;
	private int iDrType;
	public SimpleData() {
		// TODO Auto-generated constructor stub
		
	}
	public SimpleData(String sName,Date dNext,int iDrType)
	{
		this.sName=sName;
		this.dNext=dNext;
		this.iDrType=iDrType;
	}
	public String getName()
	{
		return this.sName;
	}
	public Date getNext()
	{
		return this.dNext;
	}
	public int getDrType() {
		return this.iDrType;
		
	}
}
