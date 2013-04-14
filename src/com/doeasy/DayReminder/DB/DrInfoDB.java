package com.doeasy.DayReminder.DB;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.doeasy.DayReminder.SimpleData;
import com.doeasy.DayReminder.clsCalendar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Xml;
import android.widget.Toast;
import static com.doeasy.DayReminder.DB.DBUtil.*;
public class DrInfoDB {
	
	DBUtil DrDb;
	clsCalendar cc;
	Date dToday;
	Context context;
	SQLiteDatabase db;
	public DrInfoDB(Context mcontext)
	{
		context=mcontext;
		DrDb = new DBUtil(mcontext, DB_NAME, null, 1);
		db = DrDb.getWritableDatabase();		//��ȡ��ݿ�����
        cc=new clsCalendar();
        dToday=new Date();
	}
	public boolean saveDrInfo(DrData _drData)
	{
		boolean bIsSuccess=false;
		try
		{
			
			ContentValues values = new ContentValues();			//��ʶ
			values.put(drNAME, _drData.sDrName);
			values.put(drPHOTO, "");
			values.put(drPHONE, _drData.sTelPhone);
			values.put(drDrType, _drData.iDrType);
			values.put(drDayYear, _drData.iYear);
			values.put(drDayMonth, _drData.iMonth);	
			values.put(drDayDay, _drData.iDay);
			values.put(drDayType,_drData.iDayType);
			values.put(drSolarDay, _drData.sSolar);
			values.put(drLunarDay, _drData.sLunar);
			values.put(drRmDateType, _drData.iRmDateType);
			values.put(drDateOfNext, _drData.lNextDate);
			values.put(drIsAppWidget, _drData.iIsAppWidget);
			values.put(drADayOfAppWidget,_drData. iApDays);
			values.put(drRemindType, _drData.iRemindType);
			values.put(drADayOfRemind, _drData.iAfDays);
			values.put(drRemindTime, _drData.iRemindTime);
			values.put(drIsAutoSMS, _drData.iIsAutoSMS);
			values.put(drSMSText, _drData.sSMSText);
			values.put(drRemark, _drData.sRemark);
			values.put(drInitials, _drData.sDrInitials);
			long count =0;
			if(_drData.iDrId==0)
			{
				count=db.insert(Main_TABLENAME, drID, values);			//�������
			}
			else
			{
				count=db.update(Main_TABLENAME, values, drID+"=?", new String[]{String.valueOf(_drData.iDrId)});
			}
			if(count!=-1)
			{
				bIsSuccess=true;
			}
			values.clear();
			values=null;
			return bIsSuccess;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "保存失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
	public boolean saveDrInfo(int _DrId,ContentValues values)
	{
		boolean bIsSuccess=false;
		try
		{
			long count =0;
			if(_DrId==0)
			{
				count=db.insert(Main_TABLENAME, drID, values);			//�������
			}
			else
			{
				count=db.update(Main_TABLENAME, values, drID+"=?", new String[]{String.valueOf(_DrId)});
			}
			if(count!=-1)
			{
				bIsSuccess=true;
			}
			values.clear();
			values=null;
			return bIsSuccess;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "保存失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
	public boolean updateDrInfo(int _DrId,ContentValues values)
	{
		boolean bIsSuccess=false;
		try
		{
			long count=db.update(Main_TABLENAME, values, drID+"=?", new String[]{String.valueOf(_DrId)});
			if(count==1)
			{
				bIsSuccess=true;
			}
			values.clear();
			values=null;
			return bIsSuccess;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "更新失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
	public boolean updateDrInfo(int _DrId,int _IsAppWidget,int _ApDays,int _RemindType,int _AfDays,int _RemindTime,int _IsAutoSMS)
	{
		boolean bIsSuccess=false;
		try
		{
			ContentValues values = new ContentValues();			//��ʶ
			values.put(drIsAppWidget, _IsAppWidget);
			values.put(drADayOfAppWidget, _ApDays);
			values.put(drRemindType, _RemindType);
			values.put(drADayOfRemind, _AfDays);
			values.put(drRemindTime, _RemindTime);
			values.put(drIsAutoSMS, _IsAutoSMS);
			long count=db.update(Main_TABLENAME, values, drID+"=?", new String[]{String.valueOf(_DrId)});
			if(count==1)
			{
				bIsSuccess=true;
			}
			values.clear();
			values=null;
			return bIsSuccess;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "更新失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
	public boolean updateDrInfo(int _DrId,long _NextDate)
	{
			boolean bIsSuccess=false;
			try
			{
				ContentValues values = new ContentValues();			//��ʶ
				values.put(drDateOfNext, _NextDate);
				long count=db.update(Main_TABLENAME, values, drID+"=?", new String[]{String.valueOf(_DrId)});
				if(count==1)
				{
					bIsSuccess=true;
				}
				values.clear();
				values=null;
				return bIsSuccess;
			}
			catch(java.lang.Exception e)
			{
				Toast.makeText(context, "更新失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
				return  false;
			}
		}
	public boolean updateDrInfo(int _ApDays,int _AfDays,int _RemindTime)
	{
			boolean bIsSuccess=false;
			try
			{
				ContentValues values = new ContentValues();			//��ʶ
				values.put(drADayOfAppWidget, _ApDays);
				values.put(drADayOfRemind, _AfDays);
				values.put(drRemindTime, _RemindTime);
				long count=db.update(Main_TABLENAME, values, null, null);
				if(count!=-1)
				{
					bIsSuccess=true;
				}
				values.clear();
				values=null;
				return bIsSuccess;
			}
			catch(java.lang.Exception e)
			{
				Toast.makeText(context, "更新失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
				return  false;
			}
		}
	public boolean AddConfig(int _cID,String _ConfigName,String _ConfigValue)
	{
		boolean bIsSuccess=false;
		try
		{
			ContentValues values = new ContentValues();			//��ʶ
			values.put(cID, _cID);
			values.put(cConfigName, _ConfigName);
			values.put(cConfigValue, _ConfigValue);
			long count=db.insert(Config_TABLENAME,cID, values);
			if(count!=-1)
			{
				bIsSuccess=true;
			}
			values.clear();
			values=null;
			return bIsSuccess;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "添加配置数据失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
	public boolean updateConfig(String _ConfigName,String _ConfigValue)
		{
			boolean bIsSuccess=false;
			try
			{
				ContentValues values = new ContentValues();			//��ʶ
				values.put(cConfigValue, _ConfigValue);
				long count=db.update(Config_TABLENAME, values, cConfigName+"=?", new String[]{_ConfigName});
				if(count==1)
				{
					bIsSuccess=true;
				}
				values.clear();
				values=null;
				return bIsSuccess;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "更新配置数据失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
	public void updateNextDate()
    {
    	
    	Cursor c = db.query(Main_TABLENAME, new String[]{drID,drDrType,drDayYear,drDayMonth,drDayDay,drDayType,drRmDateType,drDateOfNext}, drDateOfNext+"<?", new String[]{String.valueOf(dToday.getTime())}, null, null, null);
    	int iDrId=0;
		int iDrType=0;
		int iYear=0;
		int iMonth=0;
		int iDay=0;
		int iDayType=0;
		int iRmDateType=0;
		Date dNextDate;
		ContentValues values = new ContentValues();
    	while(c.moveToNext())
    	{
    		iDrId=c.getInt(0);
    		iDrType=c.getInt(1);
    		iYear=c.getInt(2);
    		iMonth=c.getInt(3);
    		iDay=c.getInt(4);
    		iDayType=c.getInt(5);
    		iRmDateType=c.getInt(6);
    		
    		if(iDrType==1||iDrType==2)
    		{
    			if(iYear==0)
    			{
    				cc.SetDay(iMonth,iDay,iDayType);
    			}
    			else
    			{
    				cc.SetDay(iYear, iMonth, iDay, iDayType);
    			}
    		}
    		else
    		{
    			cc.SetDay(iMonth,iDay,iDayType);
    		}
    		dNextDate=cc.GetNextDateSolar(iRmDateType);
    		
    		values.put(drDateOfNext,dNextDate.getTime());
    		int count = db.update(Main_TABLENAME, values, drID+"=?", new String[]{String.valueOf(iDrId)});	//�������
    		values.clear();
    		if(count == -1)
    		{
    			Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG).show();
    		}
    	}
    	dNextDate=null;
    	values=null;
    	c.close();
    	c=null;
    }
	@SuppressLint("WorldWriteableFiles")
	public long getNextAlarmDate()
	{
		SharedPreferences spSettings; 
    	String prefsName = context.getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
		spSettings = context.getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
		boolean bIsRemind= spSettings.getBoolean("cbIsRemind", false);
		spSettings=null;
		prefsName=null;
		if(bIsRemind)
    	{
	    	Date dAlarmDate=null;
	    	Date  dNow=new Date();
	    	dNow=new Date(dNow.getTime()+100);
	    	Cursor c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drADayOfRemind,drDateOfNext+"-"+drADayOfRemind+"*24*60*60*1000 as "+drRmDate,drDateOfNext,drRemindTime}, drRemindType+">0", null, null, null, drDateOfNext+" ASC");
	    	int iCount=c.getCount();
	    	if(iCount==0)
	    	{
	    		return 0;
	    	}
	    	int iAlarmDrId=0;
	    	Date dt=null;
	    	int iDrId=0;
	    	int iADayOfRemind=0;
	    	int iRemindTime=0;
	    	long lAlarmDate=0;
	    	boolean bIsFound=false;
	    	while(c.moveToNext())
	    	{
	    		iDrId=c.getInt(0);
	    		iADayOfRemind=c.getInt(2);
	    		lAlarmDate=c.getLong(3);
	    		iRemindTime=c.getInt(5);
	    		
	    		bIsFound=false;
	    		for(int i=0;i<=iADayOfRemind;i++)
	    		{
	    			dt=new Date(lAlarmDate+i*24*60*60*1000);
	        		dt.setHours((int)iRemindTime/60);
	        		dt.setMinutes((int)iRemindTime%60);
	        		dt.setSeconds(0);
	        		if(dt.after(dNow))
	        		{
	        			bIsFound=true;
	        			break;
	        		}
	    		}
	    		if(bIsFound)
	    		{
	    			if(dAlarmDate==null)
	    			{
	    				dAlarmDate=dt;
	    				iAlarmDrId=iDrId;
	    			}
	    			else
	    			{
		    			if(dAlarmDate.after(dt))
		    			{
		    				dAlarmDate=dt;
		    				iAlarmDrId=iDrId;
		    			}
		    			else
		    			{
		    				if(dt.getTime()-dAlarmDate.getTime()>30*24*60*60*1000)
		    				{
		    					break;
		    				}
		    			}
	    			}
	    		}
	    	}
	    	dt=null;
	    	c.close();
	    	c=null;
	    	if(dAlarmDate!=null)
	    	{
		    	ContentValues values = new ContentValues();
				values.put(cConfigValue,String.valueOf(iAlarmDrId));
		    	db.update(Config_TABLENAME, values, cConfigName+"='ReminderDrId'", null);
		    	values.clear();
		    	values.put(cConfigValue, String.valueOf(dAlarmDate.getTime()));
		    	db.update(Config_TABLENAME, values, cConfigName+"='ReminderDateTime'", null);
		    	return dAlarmDate.getTime();
	    	}
	    	else
	    	{
	    		return 0;
	    	}
    	}
		else
		{
			return 0;
		}
	}
	
	public int getReminderDrId()
    {
    	Cursor c = db.query(Config_TABLENAME, new String[]{cConfigValue}, cConfigName+"='ReminderDrId'", null, null, null, null);
    	String sConfigValue="";
    	if(c.getCount()==0)
    	{
    		return -1;
    	}
    	while(c.moveToNext())
    	{
    		sConfigValue=c.getString(0);
    	}
    	c.close();
    	c=null;
    	return Integer.parseInt(sConfigValue);
    }
	public int getUseCount()
    {
    	Cursor c = db.query(Config_TABLENAME, new String[]{cConfigValue}, cConfigName+"='UseCount'", null, null, null, null);
    	String sConfigValue="";
    	if(c.getCount()==0)
    	{
    		return 0; 
    	}
    	while(c.moveToNext())
    	{
    		sConfigValue=c.getString(0);
    	}
    	c.close();
    	c=null;
    	return Integer.parseInt(sConfigValue);
    }
	public String[][] getConfigInfo()
	{
		Cursor c = db.query(Config_TABLENAME, new String[]{cConfigName,cConfigValue}, null, null, null, null, null);
		int iCount=c.getCount();
		String[][] aCgList=new String[2][iCount];
		//while(c.moveToNext())
		c.moveToFirst();
		for(int i=0;i<iCount;i++)
	  	{
			aCgList[0][i]=c.getString(0).trim();
			aCgList[1][i]=c.getString(1).trim();
			c.moveToNext();
	  	}
		c.close();
		c=null;
		return aCgList;
	}
	public String[][] getDrMainList(int DrType,String KeyValue)
    {
    	Cursor c=null;
    	switch(DrType)
    	{
    		case 0:
    			c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDayType,drSolarDay,drLunarDay,drDateOfNext,drDayYear,drDayMonth,drDayDay,drPHOTO}, null,null, null, null, drDateOfNext+ " ASC");
    			break;
    		case -1:
    			c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDayType,drSolarDay,drLunarDay,drDateOfNext,drDayYear,drDayMonth,drDayDay,drPHOTO}, drNAME+" like '%"+KeyValue+"%'",null, null, null, drDateOfNext+ " ASC");
    			break;
    		default:
    			c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDayType,drSolarDay,drLunarDay,drDateOfNext,drDayYear,drDayMonth,drDayDay,drPHOTO}, drDrType+"=?", new String[]{String.valueOf(DrType)}, null, null, drDateOfNext+ " ASC");
    			break;
    	}
    	
    	int iCount=c.getCount();
    	String[][] aDrList=new String[9][iCount];
    	int i=0;
    	Date dt=null;
    	int iDrType=0;
    	int iYear=0;
		int iMonth=0;
		int iDay=0;
		int iDayType=0;
		Date dSolar=null;
		long iDays=0;
		int iYears=0;
		String sNextDate="";
		String sDrDesp="";
		String sAgoDays="";
		String sAfDays="";
		int iAfDays=0;
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
    	{
    		aDrList[0][i]=String.valueOf(c.getInt(0));
    		aDrList[1][i]=c.getString(1);
    		iDrType=c.getInt(2);
    		aDrList[8][i]=String.valueOf(iDrType);
    		iDayType=c.getInt(3);
    		if(iDayType==1||iDayType==4||iDayType==5)
    		{
    			sDrDesp=c.getString(4);
    		}
    		else
    		{
    			sDrDesp=c.getString(5);
    		}
    		dt=new Date(c.getLong(6));
    		
    		sNextDate= String.format("%d年%02d月%02d日",dt.getYear()+1900,dt.getMonth()+1,dt.getDate())+cc.GetCnWeekDay(dt.getDay());
    		iYear=c.getInt(7);
    		iMonth=c.getInt(8);
    		iDay=c.getInt(9);
    		
    		sAfDays="";
    		sAgoDays="";
    		if(iDrType==1||iDrType==2)
    		{
    			if(iYear!=0)
    			{
	    			dSolar=cc.GetSolar(iYear, iMonth, iDay, iDayType);
	    			iDays=(long)((dToday.getTime()-dSolar.getTime())/60000/60/24);
	    			iYears=dt.getYear()-dSolar.getYear();
	    			if(iDrType==1)
	    			{
	    				sNextDate+=" ["+String.valueOf(iYears)+"岁]";
	    			}
	    			else
	    			{
	    				sNextDate+=" ["+String.valueOf(iYears)+"年]";
	    			}
	    			sAgoDays="距今"+String.valueOf(iDays)+"天";
    			}
    		}
    		aDrList[2][i]=sDrDesp;
    		aDrList[3][i]=sAgoDays;
    		aDrList[4][i]=sNextDate;
    		iAfDays=(int)((dt.getTime()-dToday.getTime())/60000/60/24);
    		if(iAfDays==0)
    		{
    			sAfDays="0";
    		}
    		else
    		{
    			sAfDays=String.valueOf(iAfDays);
    		}
    		aDrList[5][i]=sAfDays;
    		aDrList[6][i]=String.valueOf(dt.getMonth()+1);
    		aDrList[7][i]=c.getString(10);
    		i++;
    	}
		dt=null;
		dSolar=null;
		sNextDate=null;
		sDrDesp=null;
    	c.close();
    	c=null;
    	return aDrList;
    }
	public DrData getDrInfo(int _DrId)
	{
		Cursor c= db.query(Main_TABLENAME, new String[]{drID,drDrType,drPHOTO,drNAME,drPHONE,drDayYear,drDayMonth,drDayDay,drDayType,drSolarDay,drLunarDay,drRmDateType,drDateOfNext,drIsAppWidget,drADayOfAppWidget,drRemindType,drADayOfRemind,drRemindTime,drIsAutoSMS,drSMSText,drRemark}, drID+"=?", new String[]{String.valueOf(_DrId)}, null, null,null);
    	int iCount=c.getCount();
    	DrData drData=new DrData();
    	if(iCount>0)
    	{
    		c.moveToFirst();
    		drData.iDrId=c.getInt(0);
    		drData.iDrType=c.getInt(1);
    		drData.sPhoto=c.getString(2);
    		drData.sDrName=c.getString(3);
    		drData.sTelPhone=c.getString(4);
    		drData.iYear=c.getInt(5);
    		drData.iMonth=c.getInt(6);
    		drData.iDay=c.getInt(7);
    		drData.iDayType=c.getInt(8);
    		drData.sSolar=c.getString(9);
			drData.sLunar=c.getString(10);
			drData.iRmDateType=c.getInt(11);
			drData.lNextDate=c.getLong(12);
			drData.iIsAppWidget=c.getInt(13);
			drData.iApDays=c.getInt(14);
			drData.iRemindType=c.getInt(15);
			drData.iAfDays=c.getInt(16);
			drData.iRemindTime=c.getInt(17);
			drData.iIsAutoSMS=c.getInt(18);
			drData.sSMSText=c.getString(19);
			drData.sRemark=c.getString(20);
    	}
    	c.close();
    	c=null;
    	return drData;
	}
	public List<SimpleData> getSimpleDatas(Date dtFirstDay,Date dtLastDay)
	{
		List<SimpleData> sDataList = new ArrayList<SimpleData>();
		Cursor c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDateOfNext}, "("+drDrType+"=1 or "+drDrType+"=2) and ("+drDateOfNext+">=? and "+drDateOfNext+"<=?)", new String[]{String.valueOf(dtFirstDay.getTime()),String.valueOf(dtLastDay.getTime()+999)}, null, null, drDateOfNext+" ASC");
	    while(c.moveToNext())
	    {

	    		SimpleData sData=new SimpleData(c.getString(1),new Date(c.getLong(3)),c.getInt(2));
	    		sDataList.add(sData);
	    	}
    	c.close();
		return sDataList;
	}
	public String getLargeCurrAppWidget()
	{
		try
		{
			boolean bIsPwd=GetIsPwd();
			
			Date dNow=new Date();
			dNow.setHours(0);
			dNow.setMinutes(0);
			dNow.setSeconds(1);
	    	
	    	Cursor c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDateOfNext+"-"+"drADayOfAppWidget * 24*60*60*1000 as LastTime"}, "("+ drDrType+"=1 or "+drDrType+"=2) and ("+drDateOfNext+"-"+String.valueOf(dNow.getTime())+"<24*60*60*1000)", null, null, null, "LastTime ASC");
	    	
	    	String sAppWidgetResult="";
			String sDrName="";
	    	
			String sTmp="";
			int iDrType=0;
			while(c.moveToNext())
	    	{	    		
		    	sDrName=c.getString(1);
		    	iDrType=c.getInt(2);
	    		if(bIsPwd)
	    		{
	    			sTmp="[密保]";
	    		}
	    		else
	    		{
	    			sTmp=sDrName;
	    		}
	    		if(iDrType==1)
	    		{
	    			sTmp+="的生日";
	    		}
				sAppWidgetResult=sAppWidgetResult+sTmp+",";
	    	}
	    	sDrName=null;
	    	c.close();
	    	c=null;
	    	if(sAppWidgetResult.length()>0)
	    	{
	    		sAppWidgetResult=sAppWidgetResult.substring(0, sAppWidgetResult.length()-1);
	    	}
	    	return sAppWidgetResult;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "加载桌面组件失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return "";
		}
	}
	@SuppressWarnings("unused")
	public String getLargeAppWidgetList()
	{
		try
		{
			boolean bIsPwd=GetIsPwd();
			
			Date dNow=new Date();
			dNow.setHours(0);
			dNow.setMinutes(0);
			dNow.setSeconds(1);
	    	//drIsAppWidget+"=1 and "+drDateOfNext+"-"+"drADayOfAppWidget * 24*60*60*1000<"+String.valueOf(dToday.getTime())
	    	Cursor c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDayYear,drDayMonth,drDayDay,drDayType,drDateOfNext}, drIsAppWidget+"=1 and "+drDateOfNext+"-"+"drADayOfAppWidget * 24*60*60*1000<"+String.valueOf(dNow.getTime()), null, null, null, drDateOfNext+" ASC");
	    	
	    	String sAppWidgetResult="";
			String sDrName="";
	    	int iDrType=0;
	    	int iYear=0;
	    	int iMonth=0;
	    	int iDay=0;
	    	int iDayType=0;
	    	long lNextDate=0;
	    	Date dt=null;
	    	Date dSolar=null;
	    	int iAge=0;
	    	int iTmp=0;
	    	String sTmpName="";
			String sTmpDate="";
			String sTmp="";
			int iLoop=0;
	    	while(c.moveToNext())
	    	{
	    		iLoop++;
	    		if(iLoop>6)
	    		{
	    			break;
	    		}
	    		lNextDate=c.getLong(7);
		    	dt=new Date(lNextDate);
		    	if(lNextDate-dNow.getTime()<24*60*60*1000)
		    	{
		    		continue;
		    	}
		    	sDrName=c.getString(1);
		    	iDrType=c.getInt(2);
		    	iYear=c.getInt(3);
		    	iMonth=c.getInt(4);
		    	iDay=c.getInt(5);
		    	iDayType=c.getInt(6);
		    	
		    	if(iYear!=0)
		    	{
		    		dSolar=cc.GetSolar(iYear, iMonth, iDay, iDayType);
		    		iAge=dt.getYear()-dSolar.getYear();
		    	}
			    iTmp=(int)((lNextDate-dNow.getTime())/60000/60/24);
				sTmpDate=String.format("%02d-%02d[%s] ", dt.getMonth()+1,dt.getDate(),cc.GetCnZWeekDay(dt.getDay()));
		    	
				switch(iDrType) 
	    		{
	    			case 1:
	    				if(bIsPwd)
	    				{
	    					sTmpName="密保";
	    				}
	    				else
	    				{
	    					sTmpName=sDrName;
	    				}
	    				/*
	    				if(iYear!=0)
	    				{
	    					sTmpName+=String.format("[%d��]", iAge);
	    				}
	    				*/
	    				break;
	    			case 2:
	    				if(bIsPwd)
	    				{
	    					sTmpName="密保";
	    				}
	    				else
	    				{
	    					sTmpName=sDrName;
	    				}
	    				/*
	    				if(iYear!=0)
	    				{
	    					sTmpName+=String.format("[%d��]", iAge);
	    				}
	    				*/
	    				break;
	    			case 3:
	    				sTmpName=sDrName;
	    				sTmpName+="";
	    				break;
	    		}
				
				if(sTmpName.length()>4)
				{
					sTmpName=sTmpName.substring(0,3)+"....";
				}
				int iLength=sTmpName.length();
				for(int i=4;i>iLength;i--)
				{
					sTmpName+="　";
				}
				sTmp=String.format("%s %s%4d", sTmpName,sTmpDate,iTmp);
				sAppWidgetResult=sAppWidgetResult+sTmp+"\r\n";
	    	}
	    	sDrName=null;
	    	dt=null;
	    	dSolar=null;
	    	sTmpName=null;
			sTmpDate=null;
	    	c.close();
	    	c=null;
	    	if(sAppWidgetResult.length()>0)
	    	{
	    		sAppWidgetResult=sAppWidgetResult.substring(0, sAppWidgetResult.length()-2);
	    	}
	    	return sAppWidgetResult;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "加载4×2桌面组件失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return "";
		}
		
	}
	public String[] getSmallAppWidgetList()
	{
		String[] asList=new String[3];
		try
		{
			boolean bIsPwd=GetIsPwd();
			
			Date dNow=new Date();
			dNow.setHours(0);
			dNow.setMinutes(0);
			dNow.setSeconds(1);
	    	
	    	Cursor c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDateOfNext}, drIsAppWidget+"=1", null, null, null, drDateOfNext+" ASC");
	    
			String sDrName="";
	    	int iDrType=0;
	    	long lNextDate=0;
	    	Date dt=null;
	    	int iTmp=0;
	    	String sTmpName="";
			String sTmpDate="";
			String sTmp="";
			int iLoop=0;
	    	while(c.moveToNext())
	    	{
	    		
	    		lNextDate=c.getLong(3);
		    	dt=new Date(lNextDate);
		    	sDrName=c.getString(1);
		    	iDrType=c.getInt(2);

			    iTmp=(int)((lNextDate-dNow.getTime())/60000/60/24);
			    if(iTmp==0)
			    {
			    	sTmpDate="今天";
			    }
			    else
			    {
			    	sTmpDate=String.format("%d天\r\n%02d-%02d", iTmp,dt.getMonth()+1,dt.getDate());
			    }
				switch(iDrType) 
	    		{
	    			case 1:
	    				if(bIsPwd)
	    				{
	    					sTmpName="密保";
	    				}
	    				else
	    				{
	    					sTmpName=sDrName;
	    				}
	    				break;
	    			case 2:
	    				if(bIsPwd)
	    				{
	    					sTmpName="密保";
	    				}
	    				else
	    				{
	    					sTmpName=sDrName;
	    				}
	    				break;
	    			case 3:
	    				sTmpName=sDrName;
	    				break;
	    		}
				
				if(sTmpName.length()>3)
				{
					sTmpName=sTmpName.substring(0,3)+"..";
				}
				sTmp=String.format("%s\r\n%s", sTmpName,sTmpDate);
				asList[iLoop]=sTmp;
				iLoop++;
	    		if(iLoop>2)
	    		{
	    			break;
	    		}
	    	}
	    	sDrName=null;
	    	dt=null;
	    	sTmpName=null;
			sTmpDate=null;
	    	c.close();
	    	c=null;
	    	return asList;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "加载4×1桌面组件失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return asList;
		}
	}
	/*
	public String[][] getAppWidgetDrInfo()
    {
		String[][] sTmp=new String[3][2];
		try
		{
			boolean bIsPwd=GetIsPwd();
			
			Date dNow=new Date();
			dNow.setHours(0);
			dNow.setMinutes(0);
			dNow.setSeconds(1);
	    	SQLiteDatabase db = DrDb.getWritableDatabase();		//��ȡ��ݿ�����
	    	Cursor c = db.query(Main_TABLENAME, new String[]{drID,drNAME,drDrType,drDayYear,drDayMonth,drDayDay,drDayType,drDateOfNext}, drIsAppWidget+"=1 and "+drDateOfNext+"-"+"drADayOfAppWidget * 24*60*60*1000>=?", new String[]{String.valueOf(dToday.getTime())}, null, null, drDateOfNext+" ASC");
	    	int iThree=0;
			
			String sDrName="";
	    	int iDrType=0;
	    	int iYear=0;
	    	int iMonth=0;
	    	int iDay=0;
	    	int iDayType=0;
	    	long lNextDate=0;
	    	Date dt=null;
	    	Date dSolar=null;
	    	int iAge=0;
	    	int iTmp=0;
	    	String sTmpName="";
			String sTmpDate="";
			
	    	while(c.moveToNext())
	    	{
		    	sDrName=c.getString(1);
		    	iDrType=c.getInt(2);
		    	iYear=c.getInt(3);
		    	iMonth=c.getInt(4);
		    	iDay=c.getInt(5);
		    	iDayType=c.getInt(6);
		    	lNextDate=c.getLong(7);
		    	dt=new Date(lNextDate);
		    	if(iYear!=0)
		    	{
		    		dSolar=cc.GetSolar(iYear, iMonth, iDay, iDayType);
		    		iAge=dt.getYear()-dSolar.getYear();
		    	}
			    iTmp=(int)((lNextDate-dNow.getTime())/60000/60/24);
				sTmpDate=String.format("%d-%d[%s] ", dt.getMonth()+1,dt.getDate(),cc.GetCnZWeekDay(dt.getDay()));
		    	
				switch(iDrType) 
	    		{
	    			case 1:
	    				if(bIsPwd)
	    				{
	    					sTmpName="���뱣��";
	    				}
	    				else
	    				{
	    					sTmpName=sDrName;
	    				}
	    				if(iYear!=0)
	    				{
	    					sTmpName+=String.format("[%d��]", iAge);
	    				}
	    				break;
	    			case 2:
	    				if(bIsPwd)
	    				{
	    					sTmpName="���뱣��";
	    				}
	    				else
	    				{
	    					sTmpName=sDrName;
	    				}
	    				if(iYear!=0)
	    				{
	    					sTmpName+=String.format("[%d��]", iAge);
	    				}
	    				break;
	    			case 3:
	    				sTmpName=sDrName;
	    				sTmpName+="";
	    				break;
	    		}
				if(iTmp==0)
				{
					sTmpDate="���졡"+sTmpDate;
				}
				else
				{
					sTmpDate+=String.format(" %3d��",iTmp);
				}
				sTmp[iThree][0]=sTmpName;
				sTmp[iThree][1]=sTmpDate;
				
				iThree++;
				
				if(iThree>2)
				{
					break;
				}
	    	}
	    	sDrName=null;
	    	dt=null;
	    	dSolar=null;
	    	sTmpName=null;
			sTmpDate=null;
	    	c.close();
	    	c=null;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(context, "�����������ʧ�ܣ�"+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return sTmp;
    }
    */
	public int[][] getHolidayDrList()
	{
		
    	Cursor c = db.query(Main_TABLENAME, new String[]{drID,drDayType,drDayMonth,drDayDay}, drDrType+"=?", new String[]{"3"}, null, null, drDayType+ " ASC,"+drDayMonth+" ASC,"+drDayDay+" ASC");
    	int cCount=c.getCount();
    	int[][] aDrList=new int[2][cCount];
    	if(cCount>0)
    	{
    		int i=0;
	    	//while(c.moveToNext())
    		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
	    	{
	    		aDrList[0][i]=c.getInt(0);
	    		int iDT=c.getInt(1);
	    		switch(iDT)
	    		{
		    		case 5:
		    			aDrList[1][i]=5*10000+c.getInt(3);
		    			break;
		    		default:
		    			aDrList[1][i]=iDT*10000+c.getInt(2)*100+c.getInt(3);
		    			break;
	    		}
	    		i++;
	    	}
    	}
    	c.close();
    	c=null;
    	return aDrList;
	}
	public boolean delDrInfo(int _DrId)
	{
		try
		{
			db.delete(Main_TABLENAME, drID+"=?", new String[]{String.valueOf(_DrId)});
			return true;
		}
		catch(java.lang.Exception e)
		{
			return false;
		}
	}
	public boolean delDrInfo(String _DrId)
	{
		try
		{
			db.delete(Main_TABLENAME, drID+"=?", new String[]{_DrId});
			return true;
		}
		catch(java.lang.Exception e)
		{
			return false;
		}
	}
	public boolean delAllDrInfo()
	{
		try
		{
			db.delete(Main_TABLENAME, null,null);
			return true;
		}
		catch(java.lang.Exception e)
		{
			return false;
		}
	}
	public String WriteXml()
	  {
		  XmlSerializer serializer = Xml.newSerializer(); 
		  StringWriter writer = new StringWriter(); 
		  try
		  { 
			  serializer.setOutput(writer); 
	
			  // <?xml version=��1.0�� encoding=��UTF-8�� standalone=��yes��?> 
			  serializer.startDocument("UTF-8", true);
			  serializer.startTag("","DayReminder"); 
			  Cursor c= db.query(Main_TABLENAME, new String[]{drID,drDrType,drPHOTO,drNAME,drPHONE,drDayYear,drDayMonth,drDayDay,drDayType,drSolarDay,drLunarDay,drRmDateType,drDateOfNext,drIsAppWidget,drADayOfAppWidget,drRemindType,drADayOfRemind,drRemindTime,drIsAutoSMS,drSMSText,drRemark}, null, null, null, null,drDateOfNext+" ASC");
			  int iDrId=0;
			  String sDrName="";
			  String sTelphone="";
			  int iDrType=0;
			  int iYear=0;
			  int iMonth=0;
			  int iDay=0;
			  int iDayType=0;
			  String sSolar="";
			  String sLunar="";
			  long lNextDate=0;
			  int iRmDateType=0;
			  int iIsAppWidget=0;
			  int iApDays=0;
			  int iRemindType=0;
			  int iAfDays=0;
			  int iRemindTime=0;
			  int iIsAutoSMS=0;
			  String sPhoto;
			  String sSMSText="";
			  String sRemark="";
			  while(c.moveToNext())
			  {
				  iDrId=c.getInt(0);
				  iDrType=c.getInt(1);
				  sPhoto=c.getString(2);
				  sDrName=c.getString(3);
				  
				  sTelphone=c.getString(4);
				  
				  iYear=c.getInt(5);
				  iMonth=c.getInt(6);
				  iDay=c.getInt(7);
				  iDayType=c.getInt(8);
				  sSolar=c.getString(9);
				  sLunar=c.getString(10);
				  iRmDateType=c.getInt(11);
				  lNextDate=c.getLong(12);
				  
				  iIsAppWidget=c.getInt(13);
				  iApDays=c.getInt(14);
				  iRemindType=c.getInt(15);
				  iAfDays=c.getInt(16);
				  iRemindTime=c.getInt(17);
				  
				  iIsAutoSMS=c.getInt(18);
				  sSMSText=c.getString(19);
				  sRemark=c.getString(20);
				  
				  serializer.startTag("","DrInfo"); 
				  
				  //serializer.startTag("","BaseInfo"); 
				  serializer.attribute("","DrId",String.valueOf(iDrId)); 
				  serializer.attribute("","DrType",String.valueOf(iDrType));
				  serializer.attribute("","DrPhoto",sPhoto);
				  serializer.attribute("","DrName",sDrName); 
				  serializer.attribute("","TelPhone",sTelphone);
				  serializer.attribute("","DrYear",String.valueOf(iYear)); 
				  serializer.attribute("","DrMonth",String.valueOf(iMonth));
				  serializer.attribute("","DrDate",String.valueOf(iDay));
				  serializer.attribute("","DayType",String.valueOf(iDayType));
				  serializer.attribute("","DrSolar",sSolar);
				  serializer.attribute("","DrLunar",sLunar);
				  serializer.attribute("","RmDateType",String.valueOf(iRmDateType));
				  serializer.attribute("","NextDate",String.valueOf(lNextDate));
				  serializer.attribute("","IsAppWidget",String.valueOf(iIsAppWidget)); 
				  serializer.attribute("","ApDays",String.valueOf(iApDays));
				  serializer.attribute("","RemindType",String.valueOf(iRemindType)); 
				  serializer.attribute("","AfDays",String.valueOf(iAfDays));
				  serializer.attribute("","RemindTime",String.valueOf(iRemindTime));
				  serializer.attribute("","IsAutoSMS",String.valueOf(iIsAutoSMS)); 
				  serializer.attribute("","SMSText",sSMSText);
				  serializer.attribute("","DrRemark",sRemark);
				  
				  serializer.endTag("","DrInfo");
			  }
			  sDrName=null;
			  sTelphone=null;
			  sSolar=null;
			  sLunar=null;
			  sSMSText=null;
			  serializer.endTag("","DayReminder"); 
			  serializer.endDocument(); 
			  c.close();
			  c=null;
			  return writer.toString(); 
		  } 
		  catch(java.lang.Exception e)
		  {
			  return "";
		  }
	  }
	public void Close()
	{
		if(db!=null)
		{
			db.close();
		}
		if(DrDb!=null)
		{
			DrDb.close();
		}
		
	}
	protected void finalize()
	{
		if(db!=null)
		{
			db.close();
		}
		if(DrDb!=null)
		{
			DrDb.close();
		}
		DrDb=null;
		cc=null;
		dToday=null;
		context=null;
		db=null;
	}
	@SuppressLint("WorldWriteableFiles")
	public boolean GetIsPwd()
    {
    	SharedPreferences spSettings; 
    	String prefsName = context.getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
		spSettings = context.getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
		boolean bIsPwdProtect= spSettings.getBoolean("cbPwdProtect", false);
		spSettings=null;
		prefsName=null;
		return bIsPwdProtect;
    }
	
}
