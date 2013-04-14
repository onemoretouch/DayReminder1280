package com.doeasy.DayReminder.UI;

import java.io.File;

import java.util.Timer;
import java.util.TimerTask;

import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.PicView.ImageUtil;
import com.doeasy.DayReminder.Reminder.SetReminder;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* 实际跳出闹铃DialogActivity */
@SuppressLint("WorldWriteableFiles")
public class ActivityAlarmDialog extends Activity
{
	private Vibrator vibrator;
	private MediaPlayer mp;
	int sdk_Version=0;
	Bitmap bit=null;
	ImageView ivBg=null;
	//ImageView ivAvatar=null;
	String sDrType=null;
	String sTelPhone=null;
	String sDrText=null;
	String sSMSText=null;
	String sPhoto=null;
	SharedPreferences spSettings; 
	@SuppressLint("HandlerLeak")
	AlertDialog dlDrInfo;
	private final Timer timer = new Timer();
	private TimerTask task;
	Handler handler = new Handler() {
	   public void handleMessage(Message msg) {
		   StopAlarm();
	   }
	};
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
	  super.onCreate(savedInstanceState);
	  try
	  {
			sdk_Version = android.os.Build.VERSION.SDK_INT; 
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED, WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD, WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			
			String prefsName = getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
			spSettings = getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
			prefsName=null;
			//获取系统服务
			vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			mp=new MediaPlayer();
			
			setContentView(R.layout.lo_alarm);
			
			//ivBg=(ImageView)this.findViewById(R.id.ivBg);
			//ivBg.setBackgroundDrawable(getWallpaper());
			
			//ivAvatar=(ImageView)this.findViewById(R.id.ivAvatar);
			
			Bundle bundleRet=getIntent().getExtras();
			sDrType=bundleRet.getString("DrType");
			sDrText=bundleRet.getString("DrText");
			sTelPhone=bundleRet.getString("TelPhone");
			sSMSText=bundleRet.getString("SMSText");
			sPhoto=bundleRet.getString("Photo");
			

			if(sTelPhone.length()>0)
			{
				/*
			  	dlDrInfo=new AlertDialog.Builder(ActivityAlarmDialog.this)
			  	.setIcon(R.drawable.icon_alarm)
			   	.setTitle(sDrType)
			   	.setMessage(sDrText)
			   	.setCancelable(false)
			   	.setPositiveButton("电话",new DialogInterface.OnClickListener()
			   	{
			   		public void onClick(DialogInterface dialog, int whichButton)
			   		{
			   		    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sTelPhone));
			   			startActivity(intent);
			   			ExitDayReminder();
			  		}
			   	})
				.setNeutralButton("短信",new DialogInterface.OnClickListener()
			    {
					public void onClick(DialogInterface dialog, int whichButton)
				    {
				    	Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + sTelPhone));
				    	intent.putExtra("sms_body", sSMSText);
				    	startActivity(intent); 
				    	ExitDayReminder();
				    }
			    })
				.setNegativeButton("知道啦！",new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						ExitDayReminder();
					}
				})
				.create();
				*/
				showCustomAlarmDialog(true);
			}
			else
			{
				/*
				dlDrInfo=new AlertDialog.Builder(ActivityAlarmDialog.this)
				
				.setIcon(R.drawable.icon_alarm)
				.setTitle(sDrType)
				.setMessage(sDrText)
				.setCancelable(false)
				.setNegativeButton("知道啦！",new DialogInterface.OnClickListener()
				{
				  	public void onClick(DialogInterface dialog, int whichButton)
				   	{
				    	ExitDayReminder();
				    }
				 })
				 .create();
				 */
				showCustomAlarmDialog(false);
			}
	  }
	  catch(Exception e)
	  {
		  Toast.makeText(this, "启动闹铃失败:"+e.getMessage(), Toast.LENGTH_LONG).show();
	  }	
 } 
  private void showCustomAlarmDialog(boolean isTel)
	{
		//final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.setContentView(R.layout.lo_confirm_dialog);
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);
		Window window = dialog.getWindow();
		window.setContentView(R.layout.lo_alarm_dialog);
		ImageView ivIcon=(ImageView) window.findViewById(R.id.dialog_icon);
		if(sPhoto.length()>0)
		{
			File fi=new File(sPhoto);
			if(fi.exists())
			  {
				ivIcon.setImageBitmap(ImageUtil.DrBitmap(BitmapFactory.decodeFile(sPhoto,null)));
			  }
			else
			{
				if(sDrType.equalsIgnoreCase("节日提醒"))
				{
					ivIcon.setImageResource(R.drawable.defaulthd);
				}
				else
				{
					ivIcon.setImageResource(R.drawable.defaulttx);
				}
			}
		}
		else
		{
			if(sDrType.equalsIgnoreCase("节日提醒"))
			{
				ivIcon.setImageResource(R.drawable.defaulthd);
			}
			else
			{
				ivIcon.setImageResource(R.drawable.defaulttx);
			}
		}
		((TextView) window.findViewById(R.id.dialog_title)).setText(sDrType); 
		((TextView) window.findViewById(R.id.dialog_message)).setText(sDrText);
		Button button3=(Button) window.findViewById(R.id.dialog_button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override                    
			public void onClick(View v) {                        
				// write your code to do things after users clicks OK                        
				ExitDayReminder();
				dialog.dismiss();
				}                
			}); 
		Button button2=(Button) window.findViewById(R.id.dialog_button2);
		button2.setEnabled(isTel);
		button2.setOnClickListener(new OnClickListener() {
			@Override                    
			public void onClick(View v) {                        
				// write your code to do things after users clicks OK                        
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + sTelPhone));
		    	intent.putExtra("sms_body", sSMSText);
		    	startActivity(intent); 
				dialog.dismiss();
				}                
			}); 
		Button button1=(Button) window.findViewById(R.id.dialog_button1);
		button1.setEnabled(isTel);
		button1.setOnClickListener(new OnClickListener() {
			@Override                    
			public void onClick(View v) {                        
				// write your code to do things after users clicks OK                        
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + sTelPhone));
	   			startActivity(intent);
	   			ExitDayReminder();
				dialog.dismiss();
				}                
			});   
		
	}
  protected void onStart()
  { 
	  super.onStart();
	  StartAlarm();
  }
  protected void onResume()
  {
	  super.onResume();
	  //StartAlarm();
  }
  protected void onPause()
  {
	  super.onPause();
	  //StopAlarm();
  }
  protected void onStop()
  {
	  super.onStop();
	  StopAlarm();
  }
  private void StartAlarm()
  { 
	  try
	  {
		  task = new TimerTask() {
			  @Override
		      public void run() {
		          	// TODO Auto-generated method stub
				  Message message = new Message();
		          message.what = 1;
		          handler.sendMessage(message);
			  }
		  };
		  timer.schedule(task, 120*1000);
		 
			
		  //��ȡ����Ϣ
		  if(spSettings.getBoolean("cbIsVibrate", false))
		  {
			  // ���ֻ�һ���� 
			  long[] pattern = { 1000, 400, 1000, 300 }; // ������
			  //vibrator.vibrate(pattern);
			  vibrator.vibrate(pattern, 0);
		  }
			
		  //��ȡ��������
		  boolean bIsBell=spSettings.getBoolean("cbIsBell", false);
		  String sBell=spSettings.getString("pfBell", "");
		  if(bIsBell&&sBell.length()!=0)
		  {
			  File fi=new File(sBell);
			  if(fi.exists())
			  {
				  mp.setDataSource(sBell);
				  mp.prepare();
				  mp.start();
			  }
		  }
		  sBell=null;
		  SetReminder sr=new SetReminder(this);
		  sr.setAlarmRemind();
		  spSettings=null;
		  
	  }
	  catch(Exception e)
	  {
		  Toast.makeText(this, "启动闹铃失败:"+e.getMessage(), Toast.LENGTH_LONG).show();
		  return;
	  }		 
  }
  private void StopAlarm()
  {
	  try
	  {
		  if(vibrator!=null)
		  {
				vibrator.cancel();
		  }
	
		  if(mp!=null&&mp.isPlaying())
		  {
			  mp.stop();
		  }
	  }
	 catch(Exception e)
	  {
		  Toast.makeText(this, "启动闹铃失败:"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
		  return ;
	  }
  }
  public static void goToSleep(Context context,long time){ 
	  context.enforceCallingOrSelfPermission(android.Manifest.permission.DEVICE_POWER, null); 
	  PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE); 
      pm.goToSleep(SystemClock.uptimeMillis());
  }
  public boolean onKeyDown(int keyCode, KeyEvent event) {
	  	ExitDayReminder();
	     return super.onKeyDown(keyCode, event);
  }
  private void ExitDayReminder()
  {
	  StopAlarm();
	  finish();
  }
  public void onDestory()
  {
	  	super.onDestroy();
		sDrType=null;
		sTelPhone=null;
		sDrText=null;
		sSMSText=null;
		sPhoto=null;

		vibrator=null;
		mp=null;

		if(bit!=null&&!bit.isRecycled())
    	{
    		bit.recycle();
    	}
    	bit=null;
    	if(mp!=null)
    	{
    		mp.release();
    	}
    	mp=null;

		//ivBg=null;
		System.gc();
		System.exit(0);
  }
}