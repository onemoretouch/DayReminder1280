package com.doeasy.DayReminder;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.doeasy.DayReminder.R.drawable;
import com.doeasy.DayReminder.DB.DrInfoDB;
import com.doeasy.DayReminder.PicView.DrawableImageLoader;
import com.doeasy.DayReminder.UI.ActivityLogin;
import com.doeasy.DayReminder.UI.ActivityMain;
/*
import com.meizu.mstore.license.ILicensingService;
import com.meizu.mstore.license.LicenseCheckHelper;
import com.meizu.mstore.license.LicenseResult;
*/
@SuppressLint("HandlerLeak")
public class ActivityWelcome extends Activity {
    
	//private ILicensingService mLicensingService = null;
	private DrawableImageLoader dImageLoader = null;
	
	private Context context=null;
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	clsCalendar cc;
	Date dToday;
	boolean bIsPwd=false;
	private final Timer timer = new Timer();
	private TimerTask task;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
	public void handleMessage(Message msg) {
		   //DoCheck(); 
		   //laResult=(LicenseAppliction)getApplicationContext();
	        switch(pApplication.getLicenseReuslt())
	        {
		        case 1:
		        	Toast.makeText(context, pApplication.getErrorMessage(), Toast.LENGTH_SHORT).show();
		        	break;
		        case 0:
		        	break;
		        default:
		        	AlertDialog d=new AlertDialog.Builder(context)
					.setMessage(pApplication.getErrorMessage())
					.setCancelable(false)
					.setNegativeButton("确定",new DialogInterface.OnClickListener()
					{
					  	public void onClick(DialogInterface dialog, int whichButton)
					   	{
					    	finish();
					    	System.exit(0);
					    }
					 })
					 .create();
		        	d.show();
		        	return;
	        }
		   //Ҫ��������
		   Intent intent=null;
		   if(bIsPwd)
	        {
	        	intent= new Intent(ActivityWelcome.this,ActivityLogin.class);
	        	startActivity(intent);
	        }
	        else
	        {
	        	intent= new Intent(ActivityWelcome.this,ActivityMain.class);
	        	startActivity(intent);
	        }
			ActivityWelcome.this.finish();
			intent=null;
			super.handleMessage(msg);
	   }
	};

    @SuppressWarnings("unused")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	context=this;
    	InitApp initApp = new InitApp(context);
		initApp.SetApp(0);
		pApplication=(LicenseAppliction)getApplicationContext();
		dbHelper = pApplication.getDataHelper();
    	dImageLoader = new DrawableImageLoader(context,2);

    	/*
    	if(mLicensingService == null){
			
			// ��License����
			Intent serviceIntent = new Intent();
			serviceIntent.setAction(ILicensingService.class.getName());
			
			bindService(serviceIntent, mLicenseConnection, Context.BIND_AUTO_CREATE);
		}
        */
    	SmsManager smsMgr= SmsManager.getDefault();
    	ContentResolver cr = getContentResolver(); 
    	String[] GROUPS_PROJECTION = new String[] { "_id","title"};//
        Cursor cgroups = cr.query(ContactsContract.Groups.CONTENT_URI, GROUPS_PROJECTION,null, null, "_id ASC");

        task = new TimerTask() {
            @Override
            public void run() {
            	// TODO Auto-generated method stub
            	Message message = new Message();
            	message.what = 1;
            	handler.sendMessage(message);
            }
        };
        timer.schedule(task, 1500);
        
        setContentView(R.layout.lo_welcome);
        View v1 = findViewById(R.id.llwelcome);
    	v1.setBackgroundDrawable(dImageLoader.LoadImage(drawable.welcome));
        cc=new clsCalendar();
        dToday=new Date();
        dbHelper.updateNextDate();
        bIsPwd=GetIsPwd();
    }
    @SuppressLint("WorldWriteableFiles")
	private boolean GetIsPwd()
    {
    	SharedPreferences spSettings; 
    	String prefsName = getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
		spSettings = getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
		boolean bIsPwdProtect= spSettings.getBoolean("cbPwdProtect", false);
		spSettings=null;
		prefsName=null;
		return bIsPwdProtect;
    }

    @Override
    //�����˳���
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
    	 return true;
     }
     return super.onKeyDown(keyCode, event);
    }
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		dImageLoader.Recycle();
		dImageLoader = null;
	}
    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
        if(mLicensingService != null){
    		unbindService(mLicenseConnection);
    	}
    	*/
        dbHelper=null;
    	cc=null;
    	dToday=null;
    	task=null;
    	handler=null;
    	System.gc();
      }
   /*
    private ServiceConnection mLicenseConnection = new ServiceConnection() 
    {

		public void onServiceDisconnected(ComponentName name)
		{
			mLicensingService = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) 
		{
			mLicensingService = ILicensingService.Stub.asInterface(service);
		}
	};
	
    private void DoCheck(){
    	final String APKPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDAaErq0Zed+Ku6P3pNM7A94P/C/2q/nDo/5lm9GuZDQADIhUyV57rTQfTZF5p0hdXgSm9bq2CjFkhnNRp2OYtHfoAXy60Fjfh++G5K5n3HhDaUxIadyylUwmE0+t9uZLiq76sy0NXlN27I4UxIp6g0uu1zhiGIyQt7Upo51yQDrQIDAQAB";
		final String packageName = getApplication().getPackageName();
		LicenseResult result;
		try 
		{
			result = mLicensingService.checkLicense(packageName);
		} 
		catch (RemoteException e) 
		{
			laResult.setLicenseReuslt(-3);
			return;
		}
		// �жϽ��Ϸ���
		if(result.getResponseCode() == LicenseResult.RESPONSE_CODE_SUCCESS)
		{
			//license��֤������֤ͨ��,��Ҫ���ŶԷ��񷵻صĽ���ٴν���У��(ʹ���Լ��Ĺ�Կ������֤)
			// ʹ��jar �ṩ�ĸ���ӿڶԷ��񷵻صĽ���ٴν���У��, ʹ�ù�Կ��֤ǩ��
			boolean bSuccess = LicenseCheckHelper.checkResult(APKPublicKey, result);
			if(bSuccess && result.getPurchaseType() == LicenseResult.PURCHASE_TYPE_NORMAL)
			{
				// ��֤�ɹ�������Ϊ��ʽ�汾
				// ��������ű�ʾ����ʽ�汾���������й���
				laResult.setLicenseReuslt(0);
			} 
			else 
			{
				if(bSuccess && result.getPurchaseType() == LicenseResult.PURCHASE_TYPE_TRIAL)
				{
					// ��֤�ɹ��������ð汾;�ɸ����Ҫ���������ƻ���ʱ������.
					laResult.setLicenseReuslt(1);
				}
				else
				{
					// ��֤��ͨ��,�����߿�����Ϊ����汾�����ð棬����������
					laResult.setLicenseReuslt(-1);
				}
			}
		} 
		else 
		{
			if (result.getResponseCode() == LicenseResult.RESPONSE_CODE_NO_LICENSE_FILE)
			{
				// ��������Ӧ�ö�Ӧ��license�ļ�
				laResult.setLicenseReuslt(-2);
			} 
			else 
			{
				// license�ļ���Ч
				laResult.setLicenseReuslt(-1);
			}
		}
	}
	*/
}
