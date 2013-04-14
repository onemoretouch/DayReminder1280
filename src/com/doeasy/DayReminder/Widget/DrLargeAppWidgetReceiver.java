package com.doeasy.DayReminder.Widget;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.doeasy.DayReminder.ActivityWelcome;
import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.DrInfoDB;
import com.doeasy.DayReminder.Reminder.SetReminder;

public class DrLargeAppWidgetReceiver extends BroadcastReceiver {
	static final String ACTION_START_LARGEAPPWIDGETUPDATED = "com.doeasy.DayReminder.ACTION_START_LARGEAPPWIDGETUPDATED";
	//static final String ACTION_START_SMALLAPPWIDGETUPDATED = "com.doeasy.DayReminder.ACTION_START_SMALLAPPWIDGETUPDATED";
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	@SuppressLint("WorldWriteableFiles")
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if(intent.getAction().equals(ACTION_START_LARGEAPPWIDGETUPDATED))
		{
			InitApp initApp = new InitApp(context);
			initApp.SetApp(0);
			pApplication=(LicenseAppliction)context.getApplicationContext();
			dbHelper = pApplication.getDataHelper();
			//Toast.makeText(context, "DrAppWidgetReceiver", Toast.LENGTH_SHORT).show();
			clsCalendar cc;
			
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.aw_alarm_large);
			
			Date dNow=new Date();
			cc=new clsCalendar(dNow.getYear()+1900,dNow.getMonth()+1,dNow.getDate(),1);
			rv.setTextViewText(R.id.tvMonth, String.valueOf(dNow.getMonth()+1));
			rv.setTextViewText(R.id.tvDay, String.valueOf(dNow.getDate()));
			rv.setTextViewText(R.id.tvWeekDay, cc.CaculateWeekDayC());
			rv.setTextViewText(R.id.tvLunar,cc.GetLunarShortString());
			rv.setTextViewText(R.id.tvLunarCycle, cc.GetGanZhi());
			
			String sSolarHoliday=cc.GetSoloarHoliday();
		    String sLunarHoliday=cc.GetLunarHoliday();
		    String sWeekHoliday=cc.GetWeekHoliday();
		    String sSolarTerm=cc.GetSoralTerm();
		    String sToDay="";
		    if(sSolarHoliday.length()>0)
		    {
		    	sToDay+=sSolarHoliday+"、";
		    }
		    if(sLunarHoliday.length()>0)
		    {
		    	sToDay+=sLunarHoliday+"、";
		   }
		   if(sWeekHoliday.length()>0)
		    {
			   sToDay+=sWeekHoliday+"、";
		    }
		   	if(sSolarTerm.length()>0)
		    {
		   		sToDay+=sSolarTerm+"、";
		    }
		   	if(sToDay.length()>0)
		    {
		   		sToDay=sToDay.substring(0,sToDay.length()-1);
		    }
		    
		   	SharedPreferences spSettings; 
			String prefsName = context.getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
			spSettings = context.getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
			boolean bIsAppWidget= spSettings.getBoolean("cbIsAppWidget", true);
			if(bIsAppWidget)
			{
				dbHelper.updateNextDate();
			    String sTmp=dbHelper.getLargeCurrAppWidget();
			    if(sTmp.length()>0&&sToDay.length()>0)
			    {
			    	sToDay=sToDay+";"+sTmp;
			    }else if(sTmp.length()>0)
			    {
			    	sToDay=sTmp;
			    }
			    sTmp=dbHelper.getLargeAppWidgetList();
				rv.setTextViewText(R.id.tvDrList, sTmp);
				sTmp=null;
				dbHelper=null;
			}
			if(sToDay.length()>0)
			{
				sToDay="今日提醒："+sToDay;
			}
			else
			{
				sToDay="今日提醒：无";
			}
			rv.setTextViewText(R.id.tvToDay, sToDay);
			Intent iMain=new Intent(context,ActivityWelcome.class);
		    PendingIntent piMain=PendingIntent.getActivity(context, 0, iMain, 0);
		    rv.setOnClickPendingIntent(R.id.llDate, piMain);
		    
		    ComponentName thisWidget = new ComponentName(context, DrLargeAppWidget.class);
		    AppWidgetManager awManager = AppWidgetManager.getInstance(context);
		    awManager.updateAppWidget(thisWidget, rv);
		    
		    SetReminder sr=new SetReminder(context);
			sr.setLargeZeroAppWidgetRemind();
		    iMain=null;
		    piMain=null;
		    awManager=null;
		    thisWidget=null;
			sSolarHoliday=null;
			sLunarHoliday=null;
			sWeekHoliday=null;
			sSolarTerm=null;
			sToDay=null;
		    dNow=null;
		    cc=null;
		    rv=null;
		    context=null;
		    intent=null;
		    System.gc();
		}
	}
}
