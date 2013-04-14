package com.doeasy.DayReminder.Reminder;

import java.util.Date;

import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.DB.DrInfoDB;
import com.doeasy.DayReminder.Widget.DrLargeAppWidgetReceiver;
import com.doeasy.DayReminder.Widget.DrSmallAppWidgetReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import static com.doeasy.DayReminder.DB.DBUtil.*;

public class SetReminder {
	static final String ACTION_START_LARGEAPPWIDGETUPDATED = "com.doeasy.DayReminder.ACTION_START_LARGEAPPWIDGETUPDATED";
	static final String ACTION_START_SMALLAPPWIDGETUPDATED = "com.doeasy.DayReminder.ACTION_START_SMALLAPPWIDGETUPDATED";
	static final String ACTION_START_REMIND = "com.doeasy.DayReminder.ACTION_START_REMIND";
	Context contex;
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	public SetReminder(Context contex){
		
		this.contex = contex;
		InitApp initApp = new InitApp(contex);
		initApp.SetApp(0);
		pApplication=(LicenseAppliction)contex.getApplicationContext();
		dbHelper = pApplication.getDataHelper();
	}
	public void setAllRemind()
	{
		try
    	{
			Intent ilAppWidget = new Intent(contex, DrLargeAppWidgetReceiver.class);
			ilAppWidget.setAction(ACTION_START_LARGEAPPWIDGETUPDATED);
			PendingIntent pilAppWidget = PendingIntent.getBroadcast(contex, llAlarmAppWidgetId, ilAppWidget, 0);
			AlarmManager amlAppWidget = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
			Date dNow=new Date();
			amlAppWidget.cancel(pilAppWidget);
			amlAppWidget.set(AlarmManager.RTC_WAKEUP, dNow.getTime()+200, pilAppWidget);
			ilAppWidget=null;
			amlAppWidget=null;
			pilAppWidget=null;
			//dNow=null;
			
			Intent isAppWidget = new Intent(contex, DrSmallAppWidgetReceiver.class);
			isAppWidget.setAction(ACTION_START_SMALLAPPWIDGETUPDATED);
			PendingIntent pisAppWidget = PendingIntent.getBroadcast(contex, lsAlarmAppWidgetId, isAppWidget, 0);
			AlarmManager amsAppWidget = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
			amsAppWidget.cancel(pisAppWidget);
			amsAppWidget.set(AlarmManager.RTC_WAKEUP, dNow.getTime()+400, pisAppWidget);
			isAppWidget=null;
			amsAppWidget=null;
			pisAppWidget=null;
			dNow=null;
			
			
			long lNextDate=dbHelper.getNextAlarmDate();
			Intent iAlarm = new Intent(contex, DrRemindReceiver.class);
			iAlarm.setAction(ACTION_START_REMIND);
	    	PendingIntent piAlarm=PendingIntent.getBroadcast(contex, lAlarmDialogId,iAlarm, 0); 
	    	AlarmManager amAlarm = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
	    	amAlarm.cancel(piAlarm);
	    	if(lNextDate>0)
	    	{
	    		amAlarm.set(AlarmManager.RTC_WAKEUP, lNextDate, piAlarm);   
	    	}
	    	iAlarm=null;
	    	piAlarm=null;
	    	amAlarm=null;
	    	System.gc();
    	}
    	catch(java.lang.Exception e)
		{
			Toast.makeText(contex, "������������ʧ�ܣ�"+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	public void setAlarmRemind()
	{
		try
    	{
			long lNextDate=dbHelper.getNextAlarmDate();
			Intent iAlarm = new Intent(contex, DrRemindReceiver.class);
			iAlarm.setAction(ACTION_START_REMIND);
	    	PendingIntent piAlarm=PendingIntent.getBroadcast(contex, lAlarmDialogId,iAlarm, 0); 
	    	AlarmManager amAlarm = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
	    	amAlarm.cancel(piAlarm);
	    	if(lNextDate>0)
	    	{
	    		amAlarm.set(AlarmManager.RTC_WAKEUP, lNextDate, piAlarm);   
	    	}	    
	    	iAlarm=null;
	    	piAlarm=null;
	    	amAlarm=null;
	    	System.gc();
    	}
    	catch(java.lang.Exception e)
		{
			Toast.makeText(contex, "������������ʧ�ܣ�"+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	public void setLargeZeroAppWidgetRemind()
	{
		try
    	{
			Intent iAppWidget = new Intent(contex, DrLargeAppWidgetReceiver.class);
			iAppWidget.setAction(ACTION_START_LARGEAPPWIDGETUPDATED);
			PendingIntent piAppWidget = PendingIntent.getBroadcast(contex, llAlarmAppWidgetId, iAppWidget, 0);
			AlarmManager amAppWidget = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
			Date dNow=new Date();
			dNow.setHours(0);
			dNow.setMinutes(0);
			dNow.setSeconds(1);
			Date dNext=new Date(dNow.getTime()+24*60*60*1000);
			amAppWidget.cancel(piAppWidget);
			amAppWidget.set(AlarmManager.RTC_WAKEUP, dNext.getTime()+200, piAppWidget);
			iAppWidget=null;
			amAppWidget=null;
			piAppWidget=null;
			dNow=null;
			System.gc();
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(contex, "������������ʧ�ܣ�"+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	public void setSmallZeroAppWidgetRemind()
	{
		try
    	{
			Intent iAppWidget = new Intent(contex, DrSmallAppWidgetReceiver.class);
			iAppWidget.setAction(ACTION_START_SMALLAPPWIDGETUPDATED);
			PendingIntent piAppWidget = PendingIntent.getBroadcast(contex, lsAlarmAppWidgetId, iAppWidget, 0);
			AlarmManager amAppWidget = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
			Date dNow=new Date();
			dNow.setHours(0);
			dNow.setMinutes(0);
			dNow.setSeconds(1);
			Date dNext=new Date(dNow.getTime()+24*60*60*1000);
			amAppWidget.cancel(piAppWidget);
			amAppWidget.set(AlarmManager.RTC_WAKEUP, dNext.getTime()+400, piAppWidget);
			iAppWidget=null;
			amAppWidget=null;
			piAppWidget=null;
			dNow=null;
			System.gc();
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(contex, "������������ʧ�ܣ�"+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	public void setLargeCurrentAppWidgetRemind()
	{
		try
    	{
			Intent iAppWidget = new Intent(contex, DrLargeAppWidgetReceiver.class);
			iAppWidget.setAction(ACTION_START_LARGEAPPWIDGETUPDATED);
			PendingIntent piAppWidget = PendingIntent.getBroadcast(contex, llAlarmAppWidgetId, iAppWidget, 0);
			AlarmManager amAppWidget = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
			Date dNow=new Date();
			amAppWidget.cancel(piAppWidget);
			amAppWidget.set(AlarmManager.RTC_WAKEUP, dNow.getTime()+200, piAppWidget);
			iAppWidget=null;
			amAppWidget=null;
			piAppWidget=null;
			dNow=null;
			System.gc();
    	}
		catch(java.lang.Exception e)
		{
			Toast.makeText(contex, "������������ʧ�ܣ�"+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	public void setSmallCurrentAppWidgetRemind()
	{
		try
    	{
			Intent iAppWidget = new Intent(contex, DrSmallAppWidgetReceiver.class);
			iAppWidget.setAction(ACTION_START_SMALLAPPWIDGETUPDATED);
			PendingIntent piAppWidget = PendingIntent.getBroadcast(contex, lsAlarmAppWidgetId, iAppWidget, 0);
			AlarmManager amAppWidget = (AlarmManager)contex.getSystemService(Context.ALARM_SERVICE);
			Date dNow=new Date();
			amAppWidget.cancel(piAppWidget);
			amAppWidget.set(AlarmManager.RTC_WAKEUP, dNow.getTime()+500, piAppWidget);
			iAppWidget=null;
			amAppWidget=null;
			piAppWidget=null;
			dNow=null;
			System.gc();
    	}
		catch(java.lang.Exception e)
		{
			Toast.makeText(contex, "������������ʧ�ܣ�"+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		dbHelper=null;
	}
}
