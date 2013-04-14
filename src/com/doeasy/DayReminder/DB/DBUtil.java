package com.doeasy.DayReminder.DB;

import android.content.Context;				
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
public class DBUtil extends SQLiteOpenHelper{
	
	public static final String drServiceClassName = "com.doeasy.DayReminder.DrService"; 
	public static final int lAlarmDialogId = 2009030814;	//数据库文件名称
	public static final int llAlarmAppWidgetId = 2003090817;	//数据库文件名称
	public static final int lsAlarmAppWidgetId = 2003090818;	//数据库文件名称
	public static final String DB_NAME = "DayDB.db";	//数据库文件名称
	public static final String Main_TABLENAME = "tblDayRem";		//提醒主表名
	public static final String drID="_id";						//ID
	public static final String drNAME="DrName";					//真实名称
	public static final String drPHOTO="DrPhoto";					//电话
	public static final String drPHONE="DrTelPhone";					//电话
	public static final String drDrType="DrType";				//类型
	public static final String drDayYear="DrdYear";					//年
	public static final String drDayMonth="DrdMonth";					//月
	public static final String drDayDay="DrdDay";					//日
	public static final String drDayType="DrdType";					//日期类型
	public static final String drSolarDay="DrSolarDay";					//日期类型
	public static final String drLunarDay="DrLunarDay";					//日期类型
	public static final String drRmDateType="DrRmDateType";	
	public static final String drDateOfNext="DateOfNext";					//下次日期
	public static final String drRemindType="DrRemindType";					//是否定时提醒
	public static final String drADayOfRemind="DrADaysOfRemind";					//提前提醒天数
	public static final String drRemindTime="DrRmTime";					//提醒时间
	public static final String drRemindSound="DrRmSound";					//下次日期
	public static final String drIsAutoSMS="DrIsAutoSMS";					//是否自动发送短信
	public static final String drIsAppWidget="DrIsAppWidget";
	public static final String drADayOfAppWidget="DrADayOfAppWidget";					//提前提醒天数
	public static final String drSMSText="DrSMSText";					//短信内容
	public static final String drRemark="DrRemark";					//下次日期
	public static final String drInitials="DrInitials";					//标识
	
	public static final String Config_TABLENAME = "tblConfig";		//配置表
	public static final String cID="_id";					//id
	public static final String cConfigName="ConfigName";					//配置名称
	public static final String cConfigValue="ConfigValue";					//配置值
	public static final String drRmDate="RmDate";					//提醒时间

	public DBUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);		//调用父类构造器
	}
	@Override
	public void onCreate(SQLiteDatabase db) {		//重写onCreate方法
		db.execSQL("create table if not exists "+Main_TABLENAME+" ("	//调用execSQL方法创建表
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
		
		db.execSQL("create table if not exists "+Config_TABLENAME+" ("	//调用execSQL方法创建表
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
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	//重写onUpgrade方法
		
	}
	
}