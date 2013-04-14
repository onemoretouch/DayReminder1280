package com.doeasy.DayReminder.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doeasy.DayReminder.R;

public class ActivityLogin extends Activity implements OnClickListener{
	//EditText etPwd=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_login);
        
        
        View btnOK=this.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
        View btnCancel=(Button)this.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
    }
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		int Id=v.getId();
		switch(Id)
		{
			case R.id.btnOK:
				if(JudgePassWord())
				{
					intent= new Intent(ActivityLogin.this,ActivityMain.class);
		        	startActivity(intent);
		        	intent=null;
		        	finish();
				}
				else
				{
					Toast.makeText(ActivityLogin.this, "登录失败：密码不正确", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btnCancel:
				finish();
				break;
		}
	}
	@SuppressLint("WorldWriteableFiles")
	private boolean JudgePassWord()
	  {
		EditText etPwd=(EditText)this.findViewById(R.id.etPassword);
		String siPwd=etPwd.getText().toString();
		etPwd=null;
		if(siPwd.equals("200938"))
		{
			return true;
		}
		SharedPreferences spSettings; 
    	String prefsName = getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
		spSettings = getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
		String sPwd= spSettings.getString("pfPwd", "200938");
		spSettings=null;
		prefsName=null;
	  	if(sPwd.equals(siPwd))
	  	{
	  		return true;
	  	}
	  	else
		{
			return false;
		}
	}
	public void onDestory()
	{
		super.onDestroy();
		
		//btnOK=null;
		//btnCancel=null;
		//etPwd=null;
		System.gc();
	}
	
}