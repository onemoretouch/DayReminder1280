package com.doeasy.DayReminder.DB;

import android.content.Context;				
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
public class DBUtil extends SQLiteOpenHelper{
	
	public static final String drServiceClassName = "com.doeasy.DayReminder.DrService"; 
	public static final int lAlarmDialogId = 2009030814;	//���ݿ��ļ�����
	public static final int llAlarmAppWidgetId = 2003090817;	//���ݿ��ļ�����
	public static final int lsAlarmAppWidgetId = 2003090818;	//���ݿ��ļ�����
	public static final String DB_NAME = "DayDB.db";	//���ݿ��ļ�����
	public static final String Main_TABLENAME = "tblDayRem";		//����������
	public static final String drID="_id";						//ID
	public static final String drNAME="DrName";					//��ʵ����
	public static final String drPHOTO="DrPhoto";					//�绰
	public static final String drPHONE="DrTelPhone";					//�绰
	public static final String drDrType="DrType";				//����
	public static final String drDayYear="DrdYear";					//��
	public static final String drDayMonth="DrdMonth";					//��
	public static final String drDayDay="DrdDay";					//��
	public static final String drDayType="DrdType";					//��������
	public static final String drSolarDay="DrSolarDay";					//��������
	public static final String drLunarDay="DrLunarDay";					//��������
	public static final String drRmDateType="DrRmDateType";	
	public static final String drDateOfNext="DateOfNext";					//�´�����
	public static final String drRemindType="DrRemindType";					//�Ƿ�ʱ����
	public static final String drADayOfRemind="DrADaysOfRemind";					//��ǰ��������
	public static final String drRemindTime="DrRmTime";					//����ʱ��
	public static final String drRemindSound="DrRmSound";					//�´�����
	public static final String drIsAutoSMS="DrIsAutoSMS";					//�Ƿ��Զ����Ͷ���
	public static final String drIsAppWidget="DrIsAppWidget";
	public static final String drADayOfAppWidget="DrADayOfAppWidget";					//��ǰ��������
	public static final String drSMSText="DrSMSText";					//��������
	public static final String drRemark="DrRemark";					//�´�����
	public static final String drInitials="DrInitials";					//��ʶ
	
	public static final String Config_TABLENAME = "tblConfig";		//���ñ�
	public static final String cID="_id";					//id
	public static final String cConfigName="ConfigName";					//��������
	public static final String cConfigValue="ConfigValue";					//����ֵ
	public static final String drRmDate="RmDate";					//����ʱ��

	public DBUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);		//���ø��๹����
	}
	@Override
	public void onCreate(SQLiteDatabase db) {		//��дonCreate����
		db.execSQL("create table if not exists "+Main_TABLENAME+" ("	//����execSQL����������
				+ drID + " integer primary key,"
				+ drNAME + " varchar,"
				+ drPHOTO + " varchar,"
				+ drPHONE+" varchar,"
				+ drDrType + " integer,"
				+ drDayYear + " integer,"
				+ drDayMonth + " integer,"
				+ drDayDay + " integer,"
				+ drDayType + " integer,"
				+ drSolarDay + " varchar,"
				+ drLunarDay + " varchar,"
				+ drRmDateType + " integer,"
				+ drDateOfNext + " integer,"
				+ drRemindType + " integer,"
				+ drADayOfRemind + " integer,"
				+ drRemindTime + " integer,"
				+ drRemindSound + " varchar,"
				+ drIsAppWidget + " integer,"
				+ drADayOfAppWidget + " integer,"
				+ drIsAutoSMS + " integer,"
				+ drSMSText + " varchar,"
				+ drRemark + " varchar,"
				+ drInitials + " varchar)");
		
		db.execSQL("create table if not exists "+Config_TABLENAME+" ("	//����execSQL����������
				+ cID + " integer primary key,"
				+ cConfigName + " varchar,"
				+ cConfigValue + " varchar)");
		
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (1,'IsPwd','0')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (2,'Pwd','')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (3,'IsRemind','1')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (4,'IsVibrate','1')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (5,'IsBell','1')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (6,'BellOfReminder','')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (7,'IsAutoSMS','1')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (8,'ReminderDrId','')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (9,'ReminderDateTime','')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (10,'BackUp','')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (11,'AfDays','1')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (12,'RemindTime','750')");
		db.execSQL("Insert into "+Config_TABLENAME+"("+cID+","+cConfigName+","+cConfigValue+") Values (13,'UseCount','0')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	//��дonUpgrade����
		
	}
	
}