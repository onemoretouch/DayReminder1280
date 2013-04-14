package com.doeasy.DayReminder.Reminder; //���������

import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.doeasy.DayReminder.ActivityWelcome;
import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.DrData;
import com.doeasy.DayReminder.DB.DrInfoDB;
import com.doeasy.DayReminder.UI.ActivityAlarmDialog;

public class DrRemindReceiver extends BroadcastReceiver {
	static final String ACTION_START_APPWIDGETUPDATED = "com.doeasy.DayReminder.ACTION_START_APPWIDGETUPDATED";
	static final String ACTION_START_REMIND = "com.doeasy.DayReminder.ACTION_START_REMIND";
	Calendar c = Calendar.getInstance();

	LicenseAppliction pApplication = null;
	DrInfoDB dbHelper;

	clsCalendar cc;

	private boolean bIsUsePhone = false;
	private boolean bIsPwd = false;
	private boolean bIsAlarm = false;
	private boolean bIsNotify = false;
	private String sDrType;
	private String sDrText;
	private String sTelPhone;
	private String sSMSText;
	private String sPhoto;
	private int iDrType;

	private boolean bIsAutoSMS;

	@SuppressWarnings("static-access")
	@SuppressLint("WorldWriteableFiles")
	@Override
	public void onReceive(Context context, Intent intent) { // ��дonReceive����
		if (intent.getAction().equals(ACTION_START_REMIND)) {
			// ��ʼ����ݿ�����
			InitApp initApp = new InitApp(context);
			initApp.SetApp(0);
			pApplication = (LicenseAppliction) context.getApplicationContext();
			dbHelper = pApplication.getDataHelper();

			SharedPreferences spSettings;
			String prefsName = context.getPackageName() + "_preferences"; // [PACKAGE_NAME]_preferences
			spSettings = context.getSharedPreferences(prefsName,
					Context.MODE_WORLD_WRITEABLE);
			bIsPwd = spSettings.getBoolean("cbPwdProtect", false);
			bIsAlarm = spSettings.getBoolean("cbIsAlarm", true);
			bIsNotify = spSettings.getBoolean("cbIsNotify", true);
			bIsAutoSMS = spSettings.getBoolean("cbIsAutoSMS", true);

			cc = new clsCalendar();
			// ��ȡ�����ѵ���Ϣ
			int iDrId = dbHelper.getReminderDrId();
			if (iDrId == -1) {
				sDrType="提醒";
				sDrText="无法读取系统数据,呵呵";
			} else {
				GetDrInfo(iDrId);
			}
			if (bIsAutoSMS) {
				try {
					SmsManager smsMgr = null;
					smsMgr = SmsManager.getDefault();
					PendingIntent sentIntent = PendingIntent.getBroadcast(
							context, 0, new Intent(), 0);
					smsMgr.sendTextMessage(sTelPhone, null, sSMSText,
							sentIntent, null);
					// ContentValues values = new ContentValues();
					// values.put("address", sTelPhone);
					// values.put("body", sSMSText);
					// context.getContentResolver().insert(Uri.parse("content://sms/sent"),
					// values);
					// context.getContentResolver().insert(Uri.parse("content://sms/inbox"),
					// values);

					Toast.makeText(context, "短信发送成功！", Toast.LENGTH_SHORT).show();
				  }
				  catch(java.lang.Exception e)
				  {
					  Toast.makeText(context, "自发短信失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
				  }
			}
			if (bIsNotify) {
				// ״̬��֪ͨ
				NotificationManager m_NotificationManager;
				Notification m_Notification;
				Intent m_Intent;
				PendingIntent m_PendingIntent;
				m_NotificationManager = (NotificationManager) context
						.getSystemService(context.NOTIFICATION_SERVICE);
				m_Notification = new Notification();
				m_Intent = new Intent(context, ActivityWelcome.class);
				m_PendingIntent = PendingIntent.getActivity(context, 0,
						m_Intent, 0);
				// ����֪ͨ��״̬����ʾ��ͼ��
				m_Notification.icon = R.drawable.smallicon;
				// �����Ƿ���֪ͨʱ��״̬����ʾ������ �����ʾʱ��ʱ�Ĵ�����Ծ�֪����
				m_Notification.tickerText = sDrText;
				// ֪ͨʱ����Ĭ�ϵ����� ���defaults��Notification��һ��int�͵ı���
				// DEFAULT_SOUND��Notification������Ѿ�����õĳ���ֱ���þ�O ��
				m_Notification.defaults = Notification.DEFAULT_SOUND;
				// ����֪ͨ��ʾ�Ĳ��� (Context context, CharSequence contentTitle,
				// CharSequence contentText, PendingIntent contentIntent)
				// ����1�����Ķ���Context ����2 ���Ʊ���Title ����������
				// ������Щ������ʾ���Ǹ�λ��������˼��OK
				m_Notification.setLatestEventInfo(context, context
						.getResources().getString(R.string.app_name), sDrText,
						m_PendingIntent);
				// �������Ϊִ�����֪ͨ ����˵
				// ��NotificationManager����������һ�����ð�
				// ֪ͨ����ȥȻ��ͻ���״̬����ʾ��
				// ��ϸ��Ϣ��ҿ��Բ鿴�ĵ�
				m_NotificationManager.notify(0, m_Notification);
			}
			if (bIsAlarm) {
				Intent i = new Intent(context, ActivityAlarmDialog.class);
				Bundle bundleRet = new Bundle();
				bundleRet.putString("STR_CALLER", "");
				bundleRet.putString("DrType", sDrType);
				bundleRet.putString("DrText", sDrText);
				if (!bIsUsePhone) {
					bundleRet.putString("TelPhone", "");
					bundleRet.putString("SMSText", "");
				} else {
					bundleRet.putString("TelPhone", sTelPhone);
					bundleRet.putString("SMSText", sSMSText);
				}
				bundleRet.putString("Photo", sPhoto);
				i.putExtras(bundleRet);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
			SetReminder sr = new SetReminder(context);
			sr.setAlarmRemind();
			spSettings = null;
			prefsName = null;
		}
	}

	private void GetDrInfo(int _DrId) {
		DrData dData = dbHelper.getDrInfo(_DrId);
		if (dData.iDrId > 0) {

			String sDrName = dData.sDrName;
			sTelPhone = dData.sTelPhone;
			iDrType = dData.iDrType;
			long lNextDate = dData.lNextDate;
			sPhoto = dData.sPhoto;
			if (dData.iIsAutoSMS == 1) {
				bIsAutoSMS = bIsAutoSMS && true;
			} else {
				bIsAutoSMS = false;
			}
			if (dData.iRemindType == 0) {
				bIsAlarm = false;
				bIsNotify = false;
			} else if (dData.iRemindType % 2 == 1) {
				bIsAlarm = bIsAlarm && true;
			} else {
				bIsAlarm = false;
			}
			if ((int) (dData.iRemindType / 2) == 1) {
				bIsNotify = bIsNotify && true;
			} else {
				bIsNotify = false;
			}
			sSMSText = dData.sSMSText;
			Date dNow = new Date();
			int iAfDays = (int) ((lNextDate - dNow.getTime()) / 60000 / 60 / 24);
			if(iAfDays==0)
    		{
    			sDrText="今天是[";
    		}
    		else
    		{
    			sDrText="还有"+String.valueOf(iAfDays)+"天是[";
    		}
    		
    		switch(iDrType)
    		{
	    		case 1:
	    			if(bIsPwd)
	    			{
	    				sDrText+="密码保护]";
	    			}
	    			else
	    			{
	    				sDrText+=sDrName+"]";
	    			}
	    			sDrText+="的生日,\r\n记得要送上祝福哦";
	    			sDrType="生日提醒";
	    			break;
	    		case 2:
	    			if(bIsPwd)
	    			{
	    				sDrText+="密码保护]";
	    			}
	    			else
	    			{
	    				sDrText+=sDrName+"]";
	    			}
	    			sDrType="纪念日提醒";
	    			break;
	    		case 3:
	    			sDrText+=sDrName+"]";
	    			sDrType="节日提醒";
	    			break;
	    		default:
	    			sDrText+=sDrName+"]";
	    			sDrType="提醒";
	    			break;
    		}
    		Date dt=new Date(lNextDate);
    		if(iAfDays>0)
    		{
    			sDrText+=String.format("\r\n具体时间：%d年%02d月%02d日 　%s", dt.getYear()+1900,dt.getMonth()+1,dt.getDate(),cc.GetCnWeekDay(dt.getDay()));
    		}
			bIsUsePhone = false;
			if ((iDrType == 1 || iDrType == 2) && !bIsPwd && iAfDays == 0
					&& sTelPhone != "") {
				if (PhoneNumberUtils.isGlobalPhoneNumber(sTelPhone)) {
					bIsUsePhone = true;
				}
			}
			bIsAutoSMS = bIsAutoSMS && bIsUsePhone;
			dNow = null;
		}
		dData = null;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		dbHelper = null;
	}

}