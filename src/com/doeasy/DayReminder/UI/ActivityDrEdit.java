package com.doeasy.DayReminder.UI;

import static com.doeasy.DayReminder.DB.DBUtil.drADayOfAppWidget;
import static com.doeasy.DayReminder.DB.DBUtil.drADayOfRemind;
import static com.doeasy.DayReminder.DB.DBUtil.drDateOfNext;
import static com.doeasy.DayReminder.DB.DBUtil.drDayDay;
import static com.doeasy.DayReminder.DB.DBUtil.drDayMonth;
import static com.doeasy.DayReminder.DB.DBUtil.drDayType;
import static com.doeasy.DayReminder.DB.DBUtil.drDayYear;
import static com.doeasy.DayReminder.DB.DBUtil.drDrType;
import static com.doeasy.DayReminder.DB.DBUtil.drInitials;
import static com.doeasy.DayReminder.DB.DBUtil.drIsAppWidget;
import static com.doeasy.DayReminder.DB.DBUtil.drIsAutoSMS;
import static com.doeasy.DayReminder.DB.DBUtil.drLunarDay;
import static com.doeasy.DayReminder.DB.DBUtil.drNAME;
import static com.doeasy.DayReminder.DB.DBUtil.drPHONE;
import static com.doeasy.DayReminder.DB.DBUtil.drPHOTO;
import static com.doeasy.DayReminder.DB.DBUtil.drRemark;
import static com.doeasy.DayReminder.DB.DBUtil.drRemindTime;
import static com.doeasy.DayReminder.DB.DBUtil.drRemindType;
import static com.doeasy.DayReminder.DB.DBUtil.drRmDateType;
import static com.doeasy.DayReminder.DB.DBUtil.drSMSText;
import static com.doeasy.DayReminder.DB.DBUtil.drSolarDay;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.DrData;
import com.doeasy.DayReminder.DB.DrInfoDB;
//���������
@SuppressLint("WorldWriteableFiles")
public class ActivityDrEdit extends Activity implements OnClickListener{
	
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	@SuppressWarnings("unused")
	private int INTENT_TABVIDEO_PEQUESTCODE=101;
	private final int iAvator_Request_Code=101;
	final int DELETE_DIALOG = 0;		//确认删除对话框的ID
	final int AFDAYS_DIALOG = 1;			//声明列表对话框的id
	
	final int TIME_DIALOG=2;				//时间选择对话框id	
	final int EXIT_DIALOG=3;
	final int APDAYS_DIALOG=4;
	boolean bIsModi=false;
	
	 final int MENU_SELECTFILE=Menu.FIRST+1;//每个菜单项目的编号=======begin=========
	 final int MENU_TAKEPHOTO=Menu.FIRST+2;
	 final int MENU_CANCEL=Menu.FIRST+3;//每个菜单项目的编号=======end============

	    
	//定义界面中的控件
	LinearLayout 	llContent=null;

	ImageView drIv=null;
	
	Button btnContacts=null;
	Button btnDay=null;
	Button btnNextDate=null;
	Button btnAfDays=null;
	Button btnRemindTime=null;
	Button btnApDays=null;
	Button btnSave=null;
	Button btnCancel=null;
	
	CheckBox cbSolar=null;
	CheckBox cbLunar=null;
	CheckBox cbLunarLeap=null;
	CheckBox	cbAlarm=null;
	CheckBox	cbNotify=null;
	CheckBox	cbIsAutoSMS=null;
	CheckBox	cbIsAppWidget=null;
	
	EditText etDrName=null;
	EditText etTelPhone=null;
	EditText 	etRemark=null;
	EditText etSMSText=null;
	
	
	int iCt_Request_Code=201;
	int iEd_Request_Code=202;
	
	int iEdFlag=0;
	int iYear=2009;
	int iMonth=3;
	int iDay=8;
	int iDayType=1;
	int status = -1;				//0表示查看信息，1表示添加联系人，2表示修改联系人
	int iDrId=0;
	int iDrType=-1;
	
	final int MENU_DELETE = Menu.FIRST;			//声明菜单选行的ID
	
	int iRmDateType=0;
	int iRemindType=0;
	int iAfDays=1;
	int iApDays=10;
	int iRemindTime=750;
	int iIsAutoSMS=0;
	int iIsAppWidget=0;
	
	clsCalendar cc;
	ScrollView sv=null;
	String sLunar;
	String sSolar;
	Date dNextDate;
	String sDrIv="";
	BitmapFactory.Options options=null;
	Bitmap bit=null;
	int[] aiAfDays=null;
	//声明字符
	String sDrName="";
	String sDayName="";
	String sAfDays="";
	String sApDays="";
	String sRemindTime="";
	String sNextDate="";
	String sSmsText="";
	String sRemark="";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        InitApp initApp = new InitApp((Context)this);
		initApp.SetApp(0);
		pApplication=(LicenseAppliction)getApplicationContext();
		dbHelper = pApplication.getDataHelper();
        setContentView(R.layout.lo_dredit);
    	sAfDays=getResources().getString(R.string.txt_AfDays);
    	sApDays=getResources().getString(R.string.txt_ApDays);
    	sRemindTime=getResources().getString(R.string.txt_RemindTime);
    	sNextDate=getResources().getString(R.string.txt_NextDate);
    	aiAfDays=getResources().getIntArray(R.array.apdays_value);
	    options=new BitmapFactory.Options();
	    options.inPreferredConfig = Bitmap.Config.ARGB_4444;
	    options.inJustDecodeBounds = false;
	    options.inSampleSize = 2;   //width，hight设为原来的十分一
	    
	    //加载界面控件
	    llContent=(LinearLayout)this.findViewById(R.id.llContent);
	    
	    sv=(ScrollView)this.findViewById(R.id.sv);
	    sv.setSmoothScrollingEnabled(true);
	    
	    btnContacts=(Button)this.findViewById(R.id.btnContacts);
	    btnContacts.setOnClickListener(this);
	    
	    drIv=(ImageView)this.findViewById(R.id.drIv);
	    drIv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.add(0, MENU_SELECTFILE, 0, R.string.mnu_SelectFile);
	    		menu.add(0, MENU_TAKEPHOTO, 0, R.string.mnu_TakePhoto);
	    		//menu.add(0, MENU_CANCEL, 0, R.string.mnu_Cancel); 
			}                       
	    });
	    drIv.setOnClickListener(this);
	    
	    etDrName=(EditText)this.findViewById(R.id.etDrName);
	    etTelPhone=(EditText)this.findViewById(R.id.etTelPhone);
	    
	    btnDay=(Button)this.findViewById(R.id.btnDay);
	    btnDay.setOnClickListener(this);
	    
		cbSolar=(CheckBox)this.findViewById(R.id.cbSolar);
		cbSolar.setOnClickListener(this);
		//cbSolar.setOnCheckedChangeListener(this);
		cbLunar=(CheckBox)this.findViewById(R.id.cbLunar);
		cbLunar.setOnClickListener(this);
		//cbLunar.setOnCheckedChangeListener(this);
		cbLunarLeap=(CheckBox)this.findViewById(R.id.cbLunarLeap);
		cbLunarLeap.setOnClickListener(this);
		//cbLunarLeap.setOnCheckedChangeListener(this);
		
	    btnNextDate=(Button)this.findViewById(R.id.btnNextDate);
	    btnNextDate.setOnClickListener(this);
	    
	    cbAlarm=(CheckBox)this.findViewById(R.id.cbAlarm);
	    cbAlarm.setOnClickListener(this);
	    //cbAlarm.setOnCheckedChangeListener(this);

	    cbNotify=(CheckBox)this.findViewById(R.id.cbNotify);
	    cbNotify.setOnClickListener(this);
	    //cbNotify.setOnCheckedChangeListener(this);
	    
	    btnAfDays=(Button)this.findViewById(R.id.btnAfDays);
	    btnAfDays.setOnClickListener(this);
	    
	    btnRemindTime=(Button)this.findViewById(R.id.btnRemindTime);
	    btnRemindTime.setOnClickListener(this);
	    
	    cbIsAppWidget=(CheckBox)this.findViewById(R.id.cbIsAppWidget);
	    cbIsAppWidget.setOnClickListener(this);
	    
	    btnApDays=(Button)this.findViewById(R.id.btnApDays);
	    btnApDays.setOnClickListener(this);
	    
	    cbIsAutoSMS=(CheckBox)this.findViewById(R.id.cbIsAutoSMS);
	    cbIsAutoSMS.setOnClickListener(this);
	    //cbIsAutoSMS.setOnCheckedChangeListener(this);

		etSMSText=(EditText)this.findViewById(R.id.etSMSText);
		etSMSText.setEnabled(false);
		etRemark=(EditText)this.findViewById(R.id.etRemark);
		
		btnSave=(Button)this.findViewById(R.id.btnSave);
		btnCancel=(Button)this.findViewById(R.id.btnCancel);
		
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		cc=new clsCalendar();
		Intent intent = getIntent();
		try
		{
			iDrType = intent.getExtras().getInt("DrType");			//读取命令类型
			if((iDrType != 1)&&(iDrType !=2))
			{
				finish();
			}
			switch(iDrType)
			{
				case 1:
					//cetDrName.setTitle(getResources().getString(R.string.tvBdName));
					sDrName=getResources().getString(R.string.txt_BdName);
					sDayName=getResources().getString(R.string.txt_BdDay);
					break;
				case 2:
					//cetDrName.setTitle(getResources().getString(R.string.tvMdName));
					sDrName=getResources().getString(R.string.txt_MdName);
					sDayName=getResources().getString(R.string.txt_MdDay);
					break;
			}

			etDrName.setHint(sDrName);
			btnDay.setText(sDayName);
			
			iDrId = intent.getExtras().getInt("DrId");		//获得要显示的联系人的id
			//取得年月日数据
			if(iDrId!=0)
			{
				SetDrInfo();
			}
			else
			{
				SetDayInfo();
				SharedPreferences spSettings; 
		    	String prefsName = getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
				spSettings = getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
				iAfDays=Integer.parseInt(spSettings.getString("lpAfDays", "1"));
				iRemindTime=spSettings.getInt("pfRemindTime", 750);
				iApDays=Integer.parseInt(spSettings.getString("lpApDays","30"));
				spSettings=null;
				prefsName=null;
				btnAfDays.setText(String.format("%s提前%d天",sAfDays, iAfDays));
				btnApDays.setText(String.format("%s提前%d天",sApDays, iApDays));
				btnRemindTime.setText(String.format("%s%02d:%02d",sRemindTime, iRemindTime /60,iRemindTime % 60));
			}
			intent=null;
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(this, getResources().getString(R.string.err_operate), Toast.LENGTH_SHORT).show();
			finish();
		}
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		int Id=v.getId();
		if(Id == R.id.btnContacts)
		{
			if(pApplication.getLicenseReuslt()!=0)
			{
				Toast.makeText(this, pApplication.getNotFuncMessage(), Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intentct = new Intent(ActivityDrEdit.this,ActivityContacts.class);
			startActivityForResult(intentct,iCt_Request_Code);
			return;
		}
		
		if(Id == R.id.drIv)
		{
			/*
			if(laResult.getLicenseReuslt()!=0)
			{
				Toast.makeText(this, laResult.getNotFuncMessage(), Toast.LENGTH_SHORT).show();
				return;
			}
			Intent localIntent1 = new Intent();
            localIntent1.setType("image/*");
            localIntent1.setAction("android.intent.action.GET_CONTENT");
            Intent localIntent2 = Intent.createChooser(localIntent1, "Select Picture");
            startActivityForResult(localIntent2, INTENT_TABVIDEO_PEQUESTCODE);
            */
			v.showContextMenu();
		}
		if(Id == R.id.btnDay)
		{
			
			intent = new Intent(ActivityDrEdit.this,ActivityDayEdit.class);
			Bundle bsed=new Bundle();
			bsed.putInt("Flag", iEdFlag);
			bsed.putInt("Year", iYear);
			bsed.putInt("Month", iMonth);
			bsed.putInt("Day", iDay);
			bsed.putInt("DayType", iDayType);
			intent.putExtras(bsed);
			startActivityForResult(intent,iEd_Request_Code);
			intent=null;
			
			return;
		}
		if((Id == R.id.btnNextDate)&&(iEdFlag==1))
		{
			
			intent = new Intent(ActivityDrEdit.this,ActivityDayDetails.class);
			Bundle bdd=new Bundle();
			bdd.putInt("Flag", iEdFlag);
			bdd.putInt("DrType",iDrType);
			bdd.putInt("Year", iYear);
			bdd.putInt("Month", iMonth);
			bdd.putInt("Day", iDay);
			bdd.putInt("DayType", iDayType);
			intent.putExtras(bdd);
			startActivity(intent);
			intent=null;
			
		}
		if(Id == R.id.btnAfDays)
		{
			showDialog(AFDAYS_DIALOG);
		}
		if(Id == R.id.btnApDays)
		{
			showDialog(APDAYS_DIALOG);
		}
		if(Id == R.id.btnRemindTime)
		{
			showDialog(TIME_DIALOG);
		}
		if(Id == R.id.btnSave)
		{
			if(SaveDrInfo())
			{
				intent =getIntent();
				setResult(RESULT_OK, intent);
				intent=null;
				finish();
			}
		}
		if(Id == R.id.btnCancel)
		{
			
			String sTmp1=etRemark.getText().toString();
			if(!sTmp1.equals(sRemark))
	    	 {
	    		 bIsModi=true;
	    	 }
			String sTmp2=etSMSText.getText().toString();
			if(!sTmp2.equals(sSmsText))
	    	 {
	    		 bIsModi=true;
	    	 }
			if(bIsModi)
			{
				showCustomUpdateDialog();
				//showDialog(EXIT_DIALOG);
			}
			else
			{
				finish();
			}
		}
		if(v==cbSolar)
		{
			SetRmDateTypeByCheckBox(cbSolar.isChecked(),1);
		}
		if(v==cbLunar)
		{
			SetRmDateTypeByCheckBox(cbLunar.isChecked(),2);
		}
		if(v==cbLunarLeap)
		{
			SetRmDateTypeByCheckBox(cbLunarLeap.isChecked(),4);
		}
		if(v==cbAlarm)
		{
			SetRemindTypeByCheckBox(cbAlarm.isChecked(),1);
			return;
		}
		if(v==cbNotify)
		{
			SetRemindTypeByCheckBox(cbNotify.isChecked(),2);
			return;
		}
		if(v==cbIsAppWidget)
		{
			if(cbIsAppWidget.isChecked())
			{
				btnApDays.setEnabled(true);
				iIsAppWidget=1;
			}
			else
			{
				btnApDays.setEnabled(false);
				iIsAppWidget=0;
			}
			bIsModi=true;
			return;
		}
		if(v==cbIsAutoSMS)
		{
			SetIsAutoSMSByCheckBox(cbIsAutoSMS.isChecked());
			return;
		}
		
	}
	
	private void SetRmDateTypeByCheckBox(boolean Checked,int Value)
	{
		if(Checked)
		{
			iRmDateType+=Value;
		}
		else
		{
			iRmDateType-=Value;
		}
		if(iRmDateType==0)
		{
			iRemindType=0;
			btnNextDate.setText(sNextDate);
			cbIsAppWidget.setEnabled(false);
			cbIsAppWidget.setChecked(false);
			btnApDays.setEnabled(false);
			cbAlarm.setChecked(false);
			cbNotify.setChecked(false);
			cbAlarm.setEnabled(false);
			cbNotify.setEnabled(false);
			btnAfDays.setEnabled(false);
			btnRemindTime.setEnabled(false);
			btnNextDate.setEnabled(false);
			cbIsAutoSMS.setEnabled(false);
			cbIsAutoSMS.setChecked(false);
			etSMSText.setEnabled(false);
			etSMSText.setEnabled(false);
			return ;
		}
		if(iRemindType==0)
		{
			iRemindType=1;
			cbAlarm.setChecked(true);
		}
		if(iIsAppWidget==0)
		{
			iIsAppWidget=1;
			cbIsAppWidget.setChecked(true);
		}
		cbAlarm.setEnabled(true);
		cbNotify.setEnabled(true);
		btnAfDays.setEnabled(true);
		cbIsAppWidget.setEnabled(true);
		btnApDays.setEnabled(true);
		
		btnRemindTime.setEnabled(true);
		btnNextDate.setEnabled(true);
		cbIsAutoSMS.setEnabled(true);
		iIsAutoSMS=0;
		cbIsAutoSMS.setChecked(false);
		etSMSText.setEnabled(false);
		dNextDate=cc.GetNextDateSolar(iRmDateType);
		btnNextDate.setText(String.format("%s%d年%02d月%02d日　",sNextDate,dNextDate.getYear()+1900,dNextDate.getMonth()+1,dNextDate.getDate())+cc.GetCnWeekDay(dNextDate.getDay()));
		bIsModi=true;
	}
	private void SetRemindTypeByCheckBox(boolean Checked,int Value)
	{
		if(Checked)
		{
			iRemindType+=Value;
		}
		else
		{
			iRemindType-=Value;
		}
		if(iRemindType==0)
		{
			btnAfDays.setEnabled(false);
			btnRemindTime.setEnabled(false);
			btnNextDate.setEnabled(false);
			cbIsAutoSMS.setEnabled(false);
			cbIsAutoSMS.setChecked(false);
			etSMSText.setEnabled(false);
		}
		else
		{
			btnAfDays.setEnabled(true);
			btnRemindTime.setEnabled(true);
			btnNextDate.setEnabled(true);
			cbIsAutoSMS.setEnabled(true);
		}
		bIsModi=true;
	}
	private void SetIsAutoSMSByCheckBox(boolean Checked)
	{
		if(Checked)
		{
			iIsAutoSMS=1;
			etSMSText.setEnabled(true);
		}
		else
		{
			iIsAutoSMS=0;
			etSMSText.setEnabled(false);
		}
		bIsModi=true;
	}
	private void SetDayInfo()
	{
		if(iEdFlag==1)
		{
			if(iYear==0)
			{
				cc.SetDay(iMonth,iDay, iDayType);
				switch (iDayType) {
				case 1:
					sSolar=cc.GetSolarMonthDayString();
					cbSolar.setEnabled(true);
					cbLunar.setEnabled(false);
					break;
				case 2:
					sLunar=cc.GetLunarMonthDayString();
					cbSolar.setEnabled(false);
					cbLunar.setEnabled(true);
					break;
				default:
					cbSolar.setEnabled(false);
					cbLunar.setEnabled(false);
					break;
				}
				cbLunarLeap.setEnabled(false);
			}
			else
			{
				cc.SetDay(iYear,iMonth,iDay,iDayType);
				sLunar=cc.GetLunarString();
				sSolar=cc.GetSolarString();
				cbSolar.setEnabled(true);
				cbLunar.setEnabled(true);
				cbLunarLeap.setEnabled(true);
			}
			String sSrcDate=sNextDate;
			if(iDayType==1)
			{
				sSrcDate=sSolar;
				//sSrcDate+="\r\n";
				//sSrcDate+=sLunar;
			}
			else
			{
				sSrcDate=sLunar;
				//sSrcDate+="\r\n";
				//sSrcDate+=sSolar;
			}
			
			sSrcDate=sDayName+sSrcDate;
			btnDay.setText(sSrcDate);

			cbSolar.setChecked(false);
			cbLunar.setChecked(false);
			cbLunarLeap.setChecked(false);
			
			switch(iDayType)
			{
				case 1:
					cbLunar.setChecked(false);
					cbLunarLeap.setChecked(false);
					iRmDateType=1;
					cbSolar.setChecked(true);
					break;
				case 2:
					cbSolar.setChecked(false);
					cbLunarLeap.setChecked(false);
					iRmDateType=2;
					cbLunar.setChecked(true);
					break;
				case 3:
					cbSolar.setChecked(false);
					cbLunar.setChecked(false);
					iRmDateType=4;
					cbLunarLeap.setChecked(true);
					break;
				default:
					cbLunar.setChecked(false);
					cbLunarLeap.setChecked(false);
					iRmDateType=1;
					cbSolar.setChecked(true);
					break;
			}
			dNextDate=cc.GetNextDateSolar(iRmDateType);
			btnNextDate.setText(String.format("%s%d年%02d月%02d日　",sNextDate,dNextDate.getYear()+1900,dNextDate.getMonth()+1,dNextDate.getDate())+cc.GetCnWeekDay(dNextDate.getDay()));
			
			btnNextDate.setEnabled(true);
			cbSolar.setEnabled(true);
			iRemindType=1;
			cbAlarm.setChecked(true);
			cbIsAppWidget.setChecked(true);
			iIsAppWidget=1;
			btnApDays.setEnabled(true);
			cbAlarm.setEnabled(true);
			cbNotify.setEnabled(true);
			btnAfDays.setEnabled(true);
			btnRemindTime.setEnabled(true);
			cbIsAutoSMS.setEnabled(true);
			cbIsAppWidget.setEnabled(true);
			sSrcDate=null;
			bIsModi=true;
		}
		else
		{
			cbIsAppWidget.setChecked(false);
			cbIsAppWidget.setEnabled(false);
			btnApDays.setEnabled(false);
			iIsAppWidget=0;
			btnNextDate.setEnabled(false);
			cbSolar.setEnabled(false);
			cbLunar.setEnabled(false);
			cbLunarLeap.setEnabled(false);
			cbAlarm.setEnabled(false);
			cbNotify.setEnabled(false);
			btnAfDays.setEnabled(false);
			btnRemindTime.setEnabled(false);
			cbIsAutoSMS.setEnabled(false);
			etSMSText.setEnabled(false);
		}
		
	}

	private void SetDrInfo()
	{
		DrData dDrInfo=dbHelper.getDrInfo(iDrId);
    	if(dDrInfo.iDrId>0)
    	{
    		String sDesp="";
    		sDrIv=dDrInfo.sPhoto;
    		iYear=dDrInfo.iYear;
    		if(iYear==0)
    		{
    			switch (iDayType) {
				case 1:
					cbSolar.setEnabled(true);
					cbLunar.setEnabled(false);
					break;
				case 2:
					cbSolar.setEnabled(false);
					cbLunar.setEnabled(true);
					break;
				default:
					cbSolar.setEnabled(false);
					cbLunar.setEnabled(false);
					break;
				}
    			
    			cbLunarLeap.setEnabled(false);
    		}
    		iMonth=dDrInfo.iMonth;
    		iDay=dDrInfo.iDay;
    		iDayType=dDrInfo.iDayType;
    		iDrType=dDrInfo.iDrType;
    		sLunar=dDrInfo.sLunar;
    		sSolar=dDrInfo.sSolar;
    		if(iYear==0)
    		{
    			cc.SetDay(iMonth, iDay, iDayType);
    		}
    		else
    		{
    			cc.SetDay(iYear,iMonth, iDay, iDayType);
    		}
    			switch(iDrType)
			{
				case 1:
					//cetDrName.setTitle(getResources().getString(R.string.tvBdName));
					sDrName=getResources().getString(R.string.txt_BdName);
					sDayName=getResources().getString(R.string.txt_BdDay);
					sDesp=sDayName;
					break;
				case 2:
					//cetDrName.setTitle(getResources().getString(R.string.tvMdName));
					sDrName=getResources().getString(R.string.txt_MdName);
					sDayName=getResources().getString(R.string.txt_MdDay);
					sDesp=sDayName;
					break;
			}
    		btnDay.setText(sDayName);
    		if(cc.GetStatus())
    		{
    			iEdFlag=1;
    		}
    		//cetDrName.setValue(dDrInfo.sDrName);
    		//cetTelPhone.setValue(dDrInfo.sTelPhone);
    		etDrName.setText(dDrInfo.sDrName);
    		etTelPhone.setText(dDrInfo.sTelPhone);
    		
    		if(sDrIv.length()>0)
    		{
    			//Uri uriImage=Uri.parse(sDrIv);
    			drIv.setImageURI(Uri.parse(sDrIv));
    			/*
    			File fi=new File(sDrIv);
    			if(fi.exists())
    			{
    				
    				 options.inSampleSize=(int)(fi.length()/1024.0/200);
    				 bit =ImageUtil.getRoundedCornerBitmap(BitmapFactory.decodeFile(sDrIv,options),20);
		    		 //bit =BitmapFactory.decodeFile(sDrIv,options);
		             drIv.setImageBitmap(bit);
    			}
    			fi=null;
    			*/
    		}
    		
    		if(dDrInfo.iDayType==1)
    		{
    			sDesp+=dDrInfo.sSolar;
    		}
    		else
    		{
    			sDesp+=dDrInfo.sLunar;
    		}
    		
    		btnDay.setText(sDesp);
    		dNextDate=new Date(dDrInfo.lNextDate);  		
    		
    		iRmDateType =dDrInfo.iRmDateType;
	    	if(iRmDateType==0)
    		{
	    		cbIsAppWidget.setEnabled(false);
	    		btnApDays.setEnabled(false);
	    		btnNextDate.setEnabled(false);
    			cbAlarm.setEnabled(false); 
    			cbNotify.setEnabled(false);
    			btnAfDays.setEnabled(false); 
    			btnRemindTime.setEnabled(false); 
    			cbIsAutoSMS.setEnabled(false); 
    			etSMSText.setEnabled(false); 
    		}
    		else
    		{
    			cbSolar.setChecked(false);
    			cbLunar.setChecked(false);
    			cbLunarLeap.setChecked(false);
    			if(iRmDateType % 2==1)
    			{
    				cbSolar.setChecked(true);
    			}
    			if(((int)(iRmDateType / 2))%2==1)
    			{
    				cbLunar.setChecked(true);
    			}
    			if(iRmDateType / 4 == 1)
    			{
    				cbLunarLeap.setChecked(true);
    			}
    		}
	    	btnNextDate.setText(String.format("%s%d年%02d月%02d日　",sNextDate,dNextDate.getYear()+1900,dNextDate.getMonth()+1,dNextDate.getDate())+cc.GetCnWeekDay(dNextDate.getDay()));
	    	
	    	iIsAppWidget=dDrInfo.iIsAppWidget;
	    	iApDays=dDrInfo.iApDays;
	    	if(iIsAppWidget==1)
	    	{
	    		cbIsAppWidget.setChecked(true);
	    		btnApDays.setEnabled(true);
	    		btnApDays.setText(String.format("%s提前%d天",sApDays, iApDays));
	    	}
	    	else
	    	{
	    		cbIsAppWidget.setChecked(false);
	    		btnApDays.setEnabled(false);
	    		btnApDays.setText(sApDays);
	    	}
	    	iRemindType = dDrInfo.iRemindType;
	    	if(iRemindType==0)
	    	{
	    		btnAfDays.setEnabled(false); 
	    		btnRemindTime.setEnabled(false); 
	    		cbIsAutoSMS.setEnabled(false); 
	    		etSMSText.setEnabled(false); 
	    	}
	    	else
	    	{
		    	if(iRemindType % 2==1)
	    		{
	    			cbAlarm.setChecked(true);
	    		}
	    		if(iRemindType / 2==1)
	    		{
	    			cbNotify.setChecked(true);
	    		}
	    	}
    		iAfDays=dDrInfo.iAfDays;
    		btnAfDays.setText(String.format("%s提前%d天",sAfDays, iAfDays));
    		iRemindTime=dDrInfo.iRemindTime;
    		
	    	btnRemindTime.setText(String.format("%s%02d:%02d", sRemindTime,iRemindTime /60,iRemindTime % 60));
	    	iIsAutoSMS=dDrInfo.iIsAutoSMS;
	    	etSMSText.setText(dDrInfo.sSMSText);
	    	

	    	if(iIsAutoSMS==1)
	    	{
	    		cbIsAutoSMS.setChecked(true);
	    		etSMSText.setEnabled(true); 
	    	}
	    	else
	    	{
	    		cbIsAutoSMS.setChecked(false);
	    		etSMSText.setEnabled(false); 
	    	}
	    	sSmsText=dDrInfo.sSMSText;
	    	etSMSText.setText(sSmsText);
	    	sRemark=dDrInfo.sRemark;
	    	etRemark.setText(sRemark);
	    	bIsModi=false;
    	}
    	dDrInfo=null;
	}
	/** 
	* ���� Activity �յ���������ݽ�� 
	*/  
	public void  onActivityResult(int requestCode, int resultCode, Intent data) {  

	        super.onActivityResult(requestCode, resultCode, data); 
	        //ȡ�ý��
	        if(requestCode==iEd_Request_Code)
	        {
	        	
	        	if(resultCode==RESULT_OK)
	        	{
	        		Bundle bred=data.getExtras();
	        		if(bred!=null)
	        		{
	        			iEdFlag=bred.getInt("Flag");
	        			iYear=bred.getInt("Year");
	        			iMonth=bred.getInt("Month");
	        			iDay=bred.getInt("Day");
	        			iDayType=bred.getInt("DayType");
	        			SetDayInfo();
	        		}
	        		bred=null;
	        		bIsModi=true;
	        	}
	        }
	        if(requestCode==iCt_Request_Code)
	        {
	        	if(resultCode==RESULT_OK)
	        	{
	        		Bundle bred=data.getExtras();
	        		if(bred!=null)
	        		{
	        			etDrName.setText(bred.getString("RealName"));
	        			etTelPhone.setText(bred.getString("TelPhone"));
	        		}
	        		bred=null;
	        		bIsModi=true;
	        	}
	        }
	        if(requestCode==iAvator_Request_Code)
	        {
	        	if(resultCode==RESULT_OK)
	        	{
	        		Bundle b=data.getExtras();
	        		sDrIv=b.getString("File");
	        		drIv.setImageURI(Uri.parse(sDrIv));
	        	}
	        }
	        /*
	        if(requestCode==INTENT_TABVIDEO_PEQUESTCODE)
	        {
	        	
	        	if(resultCode==RESULT_OK)
	        	{
	        		Uri uri = data.getData();
	                //ȡ�ÈDƬ֮uri�ᣬ���Mһ��͸�^ContentResolver��ȡ��ԓ�DƬuri��Ԕ����λ�YӍ��

	                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);

	                cursor.moveToFirst();
	                sDrIv=cursor.getString(1);//ȡ�ÈDƬuri�еę�λ�YӍ
	                
       		     	bit =ImageUtil.getRoundedCornerBitmap(BitmapFactory.decodeFile(sDrIv,options),10);
       		     	drIv.setImageBitmap(bit);
	        		//drIv.setImageURI(uri);
	        		//sDrIv=uri.toString();
	        		//etTelPhone.setText(sDrIv);
	        	}
	        }
	        */
	} 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(0, MENU_DELETE, 0, R.string.menu_delete)
			.setIcon(R.drawable.icon_delete);			//��ӡ���ӡ��˵�ѡ��
		//menu.add(0, MENU_EDIT, 0, R.string.menu_edit)
		//	.setIcon(R.drawable.edit);		//��ӡ�ɾ��˵�ѡ��
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){		//�жϰ��µĲ˵�ѡ��

		case MENU_DELETE:				//������ɾ��ѡ��
			showDialog(DELETE_DIALOG);	//��ʾȷ��ɾ��Ի���
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override  //�˵���ѡ��״̬�仯��Ļص�����
    public boolean onContextItemSelected(MenuItem mi){	
    	Intent intentct = new Intent(ActivityDrEdit.this,ActivityAvatarHandle.class);
		Bundle b=new Bundle();
		switch(mi.getItemId()){
		
		  case MENU_SELECTFILE:
			  	b.putInt("Flag", 1);
				break;
		  case MENU_TAKEPHOTO:
			  b.putInt("Flag", 2);
			  break;
		  case MENU_CANCEL:
			  	return true;
		}    	
		intentct.putExtras(b);
		startActivityForResult(intentct,iAvator_Request_Code);
		return super.onContextItemSelected(mi);
    }
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch(id){//�ԶԻ���ID�����ж�
		case DELETE_DIALOG:		//����ɾ��ȷ�϶Ի���
			Builder a = new AlertDialog.Builder(this);	
			a.setMessage(R.string.dialog_delete_message);		//���öԻ�������
			a.setPositiveButton(
					R.string.button_ok,
					new android.content.DialogInterface.OnClickListener() {				//����ȷ��ɾ��ť
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(DeleteDrInfo())
							{
								Intent intent =getIntent();
								setResult(RESULT_OK, intent);
								finish();
								intent=null;
							}
						}
					});
			a.setNegativeButton(
					R.string.button_cancel,
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
			dialog = a.create();
			a=null;
			break;
		case AFDAYS_DIALOG:
			Builder b = new AlertDialog.Builder(this);		//创建Builder对象
			b.setIcon(R.drawable.icon_warn);					//设置图标
			b.setTitle("提前天数");						//设置标题
			b.setSingleChoiceItems(										//设置列表中的各个属性
					R.array.afdays, 
					iAfDays,//字符串数组
					new DialogInterface.OnClickListener() {	//为列表设置OnClickListener监听器
						@Override
						public void onClick(DialogInterface dialog, int which) {
							btnAfDays.setText(String.format("%s提前%d天",sAfDays, aiAfDays[which]));
							iAfDays=aiAfDays[which];
							dialog.cancel();
						}
					});
			dialog=b.create();							//生成Dialog对象
			b=null;
			break;
		case APDAYS_DIALOG:
			Builder c = new AlertDialog.Builder(this);		//创建Builder对象
			c.setIcon(R.drawable.icon_warn);					//设置图标
			c.setTitle("提前天数");						//设置标题
			c.setSingleChoiceItems(										//设置列表中的各个属性
					R.array.afdays, 
					iAfDays,//字符串数组
					new DialogInterface.OnClickListener() {	//为列表设置OnClickListener监听器
						@Override
						public void onClick(DialogInterface dialog, int which) {
							btnApDays.setText(String.format("%s提前%d天",sApDays, aiAfDays[which]));
							iApDays=aiAfDays[which];
							dialog.cancel();
						}
					});
			dialog=c.create();							//生成Dialog对象
			b=null;
			break;
		case TIME_DIALOG://���ʱ��Ի���Ĵ���
			dialog = new TimePickerDialog(				//����TimePickerDialog����
				this,
				new TimePickerDialog.OnTimeSetListener(){ //����OnTimeSetListener������
						@Override
						public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
							btnRemindTime.setText(String.format("%s%d:%02d",sRemindTime, hourOfDay,minute));
							iRemindTime=hourOfDay*60+minute;
						}    				 
				 },
				 iRemindTime / 60,		//���뵱ǰСʱ��
				 iRemindTime % 60,			//���뵱ǰ������
				 true
			  ); 
  		  break;
		case EXIT_DIALOG:
			Builder d = new AlertDialog.Builder(this);	
			d.setMessage(R.string.dialog_cancelupdate_message);		//���öԻ�������
			d.setPositiveButton(
					R.string.button_ok,
					new android.content.DialogInterface.OnClickListener() {				//����ȷ��ɾ��ť
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			d.setNegativeButton(
					R.string.button_cancel,
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
			dialog = d.create();
			d=null;
			break;
		}
		return dialog;
	}
	private void showCustomUpdateDialog()
	{
		//final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.setContentView(R.layout.lo_confirm_dialog);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.lo_confirm_dialog);
		((TextView) window.findViewById(R.id.dialog_message)).setText(R.string.dialog_cancelupdate_message); 
		((Button) window.findViewById(R.id.dialog_ok)).setOnClickListener(new OnClickListener() {
			@Override                    
			public void onClick(View v) {                        
				// write your code to do things after users clicks OK                        
				finish();
				dialog.dismiss();
				}                
			}); 
		((Button) window.findViewById(R.id.dialog_cancel)).setOnClickListener(new OnClickListener() {
			@Override                    
			public void onClick(View v) {                        
				// write your code to do things after users clicks OK                        
				dialog.dismiss();
				}                
			}); 
		
	}
	private boolean DeleteDrInfo()
	{
		try
		{
			boolean b=dbHelper.delDrInfo(iDrId);
			if(b)
			{
				
				return true;
			}
			else
			{
				Toast.makeText(this, "失败：未找到对应数据", Toast.LENGTH_SHORT).show();
				return  false;
			}
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(this, "失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
    @Override
    //�����˳���
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
    	 String sTmp=etRemark.getText().toString();
    	 if(!sTmp.equals(sTmp))
    	 {
    		 bIsModi=true;
    	 }
    	 if(bIsModi)
			{
				//showDialog(EXIT_DIALOG);
    		 	showCustomUpdateDialog();
				return true;
			}    	 
     }
     return super.onKeyDown(keyCode, event);
    }
	private boolean SaveDrInfo()
	{
		String sDrName=etDrName.getText().toString().trim();
		String sTelPhone=etTelPhone.getText().toString().trim();
		
		if(sDrName.length()==0)
		{
			Toast.makeText(this, "失败:必须输入姓名/名称！", Toast.LENGTH_LONG).show();
			return false;
		}
		if(iEdFlag==0)
		{
			Toast.makeText(this, "失败:必须输入日期！", Toast.LENGTH_LONG).show();
			return false;
		}
		if(sTelPhone.length()>0)
		{
			if (!PhoneNumberUtils.isGlobalPhoneNumber(sTelPhone))
			{
				Toast.makeText(this, "失败:电话号码格式不正确！", Toast.LENGTH_LONG).show();
				return false;
			}
		}
		if(sTelPhone.length()==0)
		{
			iIsAutoSMS=0;
		}
		try
		{
			ContentValues values = new ContentValues();			//��ʶ
			values.put(drNAME, sDrName);
			values.put(drPHOTO, sDrIv);
			values.put(drPHONE, sTelPhone);
			values.put(drDrType, iDrType);
			values.put(drDayYear, iYear);
			values.put(drDayMonth, iMonth);	
			values.put(drDayDay, iDay);
			values.put(drDayType, iDayType);
			values.put(drSolarDay, sSolar);
			values.put(drLunarDay, sLunar);
			values.put(drRmDateType, iRmDateType);
			values.put(drDateOfNext, dNextDate.getTime());
			values.put(drIsAppWidget, iIsAppWidget);
			values.put(drADayOfAppWidget, iApDays);
			values.put(drRemindType, iRemindType);
			values.put(drADayOfRemind, iAfDays);
			values.put(drRemindTime, iRemindTime);
			values.put(drIsAutoSMS, iIsAutoSMS);
			String sSms=etSMSText.getText().toString().trim();
			if(iIsAutoSMS==1&&sSms.length()==0)
			{
				Toast.makeText(this, "失败:必须输入短信内容！", Toast.LENGTH_LONG).show();
				return false;
			}
			values.put(drSMSText, sSms);
			values.put(drRemark, etRemark.getText().toString().trim());
			values.put(drInitials, "");
			boolean b=dbHelper.saveDrInfo(iDrId, values);
			values.clear();
			values=null;
			if(b)
			{
				Toast.makeText(this, getResources().getString(R.string.suc_save), Toast.LENGTH_LONG).show();
				return  true;
			}
			else
			{
				Toast.makeText(this, getResources().getString(R.string.err_save), Toast.LENGTH_LONG).show();
				return  false;
			}
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(ActivityDrEdit.this, "失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
	}
	protected void onDestroy()
	{
		super.onDestroy();
		
		llContent=null;
		btnContacts=null;
		btnDay=null;
		btnNextDate=null;
		btnAfDays=null;
		btnRemindTime=null;
		etDrName=null;
		etTelPhone=null;
		
		cbAlarm=null;
		cbNotify=null;
		cbIsAutoSMS=null;
		
		
		etSMSText=null;
		
		
		cbSolar=null;
		cbLunar=null;
		cbLunarLeap=null;
		
		btnSave=null;
		btnCancel=null;
		dbHelper=null;
		cc=null;
		sv=null;
		sLunar=null;
		sSolar=null;
		dNextDate=null;
		options=null;
    	if(bit!=null&&!bit.isRecycled())
    	{
    		bit.recycle();
    	}
    	bit=null;
		System.gc();
	}
	
	
}