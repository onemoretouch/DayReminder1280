package com.doeasy.DayReminder.Widget;

import com.doeasy.DayReminder.Reminder.SetReminder;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class DrSmallAppWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) 
	{
		//context.startService(new Intent(context, DrService.class));
		SetReminder sr=new SetReminder(context);
		sr.setSmallCurrentAppWidgetRemind();
	}
	
	@Override
	public void onDisabled(Context context) {
		//context.stopService(new Intent(context, DrService.class));
		super.onDisabled(context);
	}
}