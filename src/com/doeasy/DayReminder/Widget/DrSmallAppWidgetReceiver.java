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

public class DrSmallAppWidgetReceiver extends BroadcastReceiver {
	//static final String ACTION_START_LARGEAPPWIDGETUPDATED = "com.doeasy.DayReminder.ACTION_START_LARGEAPPWIDGETUPDATED";
	static final String ACTION_START_SMALLAPPWIDGETUPDATED = "com.doeasy.DayReminder.ACTION_START_SMALLAPPWIDGETUPDATED";@SuppressLint("WorldWriteableFiles")
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if(intent.getAction().equals(ACTION_START_SMALLAPPWIDGETUPDATED))
		{
			
			//Toast.makeText(context, "DrAppWidgetReceiver", Toast.LENGTH_SHORT).show();
			clsCalendar cc;
			InitApp initApp = new InitApp(context);
			initApp.SetApp(0);
			pApplication=(LicenseAppliction)context.getApplicationContext();
			dbHelper = pApplication.getDataHelper();
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.aw_alarm_small);
			
			Date dNow=new Date();
			cc=new clsCalendar(dNow.getYear()+1900,dNow.getMonth()+1,dNow.getDate(),1);
			String sToDay=String.format("%02d-%02d\r\n%s\r\n%s", dNow.getMonth()+1,dNow.getDate(),cc.CaculateWeekDayC(),cc.GetLunarShortString());
			rv.setTextViewText(R.id.tvToDay, sToDay);
		   	SharedPreferences spSettings; 
			String prefsName = context.getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
			spSettings = context.getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
			boolean bIsAppWidget= spSettings.getBoolean("cbIsAppWidget", true);
			if(bIsAppWidget)
			{
				dbHelper.updateNextDate();
			    String[] sTmp=dbHelper.getSmallAppWidgetList();
				rv.setTextViewText(R.id.tvDr1, sTmp[0]);
				rv.setTextViewText(R.id.tvDr2, sTmp[1]);
				rv.setTextViewText(R.id.tvDr3, sTmp[2]);
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
			
			Intent iMain=new Intent(context,ActivityWelcome.class);
		    PendingIntent piMain=PendingIntent.getActivity(context, 0, iMain, 0);
		    rv.setOnClickPendingIntent(R.id.tvToDay, piMain);
		    
		    ComponentName thisWidget = new ComponentName(context, DrSmallAppWidget.class);
		    AppWidgetManager awManager = AppWidgetManager.getInstance(context);
		    awManager.updateAppWidget(thisWidget, rv);
		    
		    SetReminder sr=new SetReminder(context);
			sr.setSmallZeroAppWidgetRemind();
		    iMain=null;
		    piMain=null;
		    awManager=null;
		    thisWidget=null;
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
