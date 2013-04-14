package com.doeasy.DayReminder.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import com.doeasy.DayReminder.R;

public class ActivityAbout extends Activity
{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lo_about);
        String sTmp="@2010-2013成都多易科技有限公司，版权所有\r\n　　生日提醒是一款以生日、纪念日和节日，按照农历、公历，以年为单位循环提醒的生活小软件，有桌面组件和定时闹铃两种提醒方式。\r\n　　本软件界面友好、操作简单、方便实用。\r\n　　本软件在魅族软件商店销售，其他公司或个人不得转载。再次感谢您对本公司的支持，同时也欢迎您提出宝贵的建议和意见。";
        TextView tv=(TextView)this.findViewById(R.id.tvDesp);
        tv.setText(sTmp);
	}
	public void onDestory()
	{
		super.onDestroy();
		System.gc();
	}
}
