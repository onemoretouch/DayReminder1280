package com.doeasy.DayReminder.Reminder;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver
{
	static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		// TODO Auto-generated method stub
		if(intent.getAction().equals(ACTION)){

			SetReminder sr=new SetReminder(context);
    		sr.setAllRemind();
		}

	}
}
