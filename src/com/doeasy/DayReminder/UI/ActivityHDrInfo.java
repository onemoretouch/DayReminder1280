package com.doeasy.DayReminder.UI;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.*;
import static com.doeasy.DayReminder.DB.DBUtil.*;
public class ActivityHDrInfo extends Activity   implements OnClickListener{
	
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	clsCalendar cc=null;
	boolean bIsModi=false;
	//定义界面中的控件
	TextView 	tvHdName=null;
	TextView 	tvHdDesp=null;
	Button btnNextDate=null;
	Button btnAfDays=null;
	Button btnRemindTime=null;
	Button btnApDays=null;
	
	EditText 	etRemark=null;
	
	CheckBox	cbIsAppWidget=null;
	CheckBox	cbAlarm=null;
	CheckBox	cbNotify=null;
	
	//0表示查看信息，1表示添加联系人，2表示修改联系人
	int status = -1;
	int iDrId=0;
	int iDrType=-1;
	int iYear=2001;
	int iMonth=1;
	int iDay=1;
	int iDayType=0;
	int iRemindTime=0;
	int iRemindType=0;
	int iAfDays=0;
	int iApDays=10;
	int iIsAppWidget=0;
	int[] aiAfDays=null;
	Date dNextDate;
	final int MENU_DELETE = Menu.FIRST;			//声明菜单选行的ID
	final int MENU_EDIT = Menu.FIRST+1;		//声明菜单项的编号
	
	String sRemark="";
	final int DELETE_DIALOG = 0;		//确认删除对话框的ID
	final int AFDAYS_DIALOG = 1;			//声明列表对话框的id
	final int TIME_DIALOG=2;				//时间选择对话框id	
	final int EXIT_DIALOG=3;
	final int APDAYS_DIALOG=4;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    InitApp initApp = new InitApp(this);
		initApp.SetApp(0);
		pApplication=(LicenseAppliction)getApplicationContext();
		dbHelper = pApplication.getDataHelper();
		
	    setContentView(R.layout.lo_hdrinfo);
	    
	  
	 
	    tvHdName=(TextView)this.findViewById(R.id.tvHdName);
	    
	    tvHdDesp=(TextView)this.findViewById(R.id.tvHdDesp);
	    
	    btnNextDate=(Button)this.findViewById(R.id.btnNextDate);
	    btnNextDate.setOnClickListener(this);
	    
	    cbIsAppWidget=(CheckBox)this.findViewById(R.id.cbIsAppWidget);
	    cbIsAppWidget.setOnClickListener(this);
	    
	    btnApDays=(Button)this.findViewById(R.id.btnApDays);
	    btnApDays.setOnClickListener(this);
	    
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
	    
	    etRemark=(EditText)this.findViewById(R.id.etRemark);
	    aiAfDays=getResources().getIntArray(R.array.apdays_value);
		View btnSave=this.findViewById(R.id.btnSave);
		View btnCancel=this.findViewById(R.id.btnCancel);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		cc=new clsCalendar();
		Intent intent = getIntent();
		iDrId = intent.getExtras().getInt("DrId");		//获得要显示的联系人的id
		intent=null;
		if(iDrId!=0)
		{
			SetDrInfo();
			//取得年月日数据
		}
		else
		{	
			finish();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		int Id=v.getId();
		
		if((Id == R.id.btnNextDate))
		{
			intent = new Intent(ActivityHDrInfo.this,ActivityDayDetails.class);
			Bundle bdd=new Bundle();
			bdd.putInt("Flag", 1);
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
			String sTmp=etRemark.getText().toString();
			if(!sTmp.equals(sRemark))
	    	 {
	    		 bIsModi=true;
	    	 }
			if(bIsModi)
			{
				//showDialog(EXIT_DIALOG);
				showCustomUpdateDialog();
			}
			else
			{
				finish();
			}
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
			return;
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
		}
		else
		{
			btnAfDays.setEnabled(true);
			btnRemindTime.setEnabled(true);
		}
		bIsModi=true;
	}
	
	private void SetDrInfo()
	{
		DrData dDrInfo=dbHelper.getDrInfo(iDrId);
    	if(dDrInfo.iDrId>0)
    	{
    		String sDesp="";
    		iYear=dDrInfo.iYear;
    		iMonth=dDrInfo.iMonth;
    		iDay=dDrInfo.iDay;
    		iDayType=dDrInfo.iDayType;
    		iDrType=dDrInfo.iDrType;
 
    		tvHdName.setText(dDrInfo.sDrName);
    		if(iDayType==1||iDayType==4||iDayType==5)
	   		{
				sDesp=dDrInfo.sSolar;
	   		}
	   		else
	   		{
	   			sDesp=dDrInfo.sLunar;
	   		}
    			
    		tvHdDesp.setText(sDesp);
    		dNextDate=new Date(dDrInfo.lNextDate); 
    		btnNextDate.setText(String.format("%s%d年%02d月%02d日　",getResources().getString(R.string.txt_NextDate),dNextDate.getYear()+1900,dNextDate.getMonth()+1,dNextDate.getDate())+cc.GetCnWeekDay(dNextDate.getDay()));
    		
    		iIsAppWidget=dDrInfo.iIsAppWidget;
	    	iApDays=dDrInfo.iApDays;
	    	if(iIsAppWidget==1)
	    	{
	    		cbIsAppWidget.setChecked(true);
	    		btnApDays.setEnabled(true);
	    		btnApDays.setText(String.format("%s提前%d天",getResources().getString(R.string.txt_ApDays), iApDays));
	    	}
	    	else
	    	{
	    		cbIsAppWidget.setChecked(false);
	    		btnApDays.setEnabled(false);
	    		btnApDays.setText(getResources().getString(R.string.txt_ApDays));
	    	}
	    	
    		iRemindType=dDrInfo.iRemindType;
    		if(iRemindType==0)
    		{
    			btnRemindTime.setEnabled(false);
    			btnNextDate.setEnabled(false);
    		}
    		if(iRemindType % 2==1)
    		{
    			cbAlarm.setChecked(true);
    		}
    		if(((int)(iRemindType / 2))%2==1)
    		{
    			cbNotify.setChecked(true);
    		}
    		iAfDays=dDrInfo.iAfDays;
    		btnAfDays.setText(String.format("%s提前%d天",getResources().getString(R.string.txt_AfDays) ,iAfDays));
    		iRemindTime=dDrInfo.iRemindTime;
	    	btnRemindTime.setText(String.format("%s%02d:%02d", getResources().getString(R.string.txt_RemindTime),iRemindTime /60,iRemindTime % 60));

	    	sRemark=dDrInfo.sRemark;
	    	etRemark.setText(sRemark);
	    	bIsModi=false;
    	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(0, MENU_DELETE, 0, R.string.menu_delete)
			.setIcon(R.drawable.icon_delete);			//添加“添加”菜单选项
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){		//判断按下的菜单选项
		case MENU_DELETE:				//按下了删除选项
			showDialog(DELETE_DIALOG);	//显示确认删除对话框
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch(id){			//对对话框ID进行判断
		case DELETE_DIALOG:		//创建删除确认对话框
			Builder b = new AlertDialog.Builder(this);	
			b.setMessage(R.string.dialog_delete_message);		//设置对话框内容
			b.setPositiveButton(
					R.string.button_ok,
					new android.content.DialogInterface.OnClickListener() {				//点下确认删除按钮
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
			b.setNegativeButton(
					R.string.button_cancel,
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {	
						}
					});
			dialog = b.create();
			b=null;
			break;
		case TIME_DIALOG://生成时间对话框的代码
				dialog = new TimePickerDialog(				//创建TimePickerDialog对象
					this,
					new TimePickerDialog.OnTimeSetListener(){ //创建OnTimeSetListener监听器
							@Override
							public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
								iRemindTime=hourOfDay*60+minute;
								btnRemindTime.setText(String.format("%s%02d:%02d",getResources().getString(R.string.txt_RemindTime),hourOfDay,minute));
								bIsModi=true;
							}    				 
					 },
					 iRemindTime / 60,		//传入当前小时数
					 iRemindTime % 60,			//传入当前分钟数
					 true
				  );
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
							btnApDays.setText(String.format("%s提前%d天",getResources().getString(R.string.txt_ApDays),aiAfDays[which]));
							iApDays=aiAfDays[which];
							dialog.cancel();
						}
					});
			dialog=c.create();							//生成Dialog对象
			b=null;
			break;
			case AFDAYS_DIALOG:
				Builder d = new AlertDialog.Builder(this);		//创建Builder对象
				//d.setIcon(R.drawable.icon);					//设置图标
				d.setTitle("提前天数");						//设置标题
				d.setSingleChoiceItems(										//设置列表中的各个属性
						R.array.afdays, 
						iAfDays,//字符串数组
						new DialogInterface.OnClickListener() {	//为列表设置OnClickListener监听器
							@Override
							public void onClick(DialogInterface dialog, int which) {
								btnAfDays.setText(String.format("%s提前%d天", getResources().getString(R.string.txt_AfDays),aiAfDays[which]));
								iAfDays=aiAfDays[which];
								bIsModi=true;
								dialog.cancel();
							}
						});
				dialog=d.create();							//生成Dialog对象
				d=null;
				break;
			case EXIT_DIALOG:
				Builder l = new AlertDialog.Builder(this);	
				l.setMessage(R.string.dialog_cancelupdate_message);		//设置对话框内容
				l.setPositiveButton(
						R.string.button_ok,
						new android.content.DialogInterface.OnClickListener() {				//点下确认删除按钮
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						});
				
				l.setNegativeButton(
						R.string.button_cancel,
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});	
				dialog = l.create();
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
	private boolean SaveDrInfo()
	{
		
		try
		{
			ContentValues values = new ContentValues();			//��ʶ
			values.put(drIsAppWidget, iIsAppWidget);
			values.put(drADayOfAppWidget, iApDays);
			values.put(drRemindType, iRemindType);
			values.put(drADayOfRemind, iAfDays);
			values.put(drRemindTime, iRemindTime);
			values.put("DrRemark", etRemark.getText().toString());
			boolean b=dbHelper.updateDrInfo(iDrId, values);
			values.clear();
			values=null;
			if(b)
			{
				Toast.makeText(this, "更新提醒成功！", Toast.LENGTH_LONG).show();
				return  true;
			}
			else
			{
				Toast.makeText(this, "更新提醒失败！", Toast.LENGTH_LONG).show();
				return  false;
			}
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(ActivityHDrInfo.this, "失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return  false;
		}
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
    	 if(!sTmp.equals(sRemark))
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
	protected void onDestroy()
	{
		super.onDestroy();
		dbHelper=null;
		cc=null;
		
		tvHdName=null;
		tvHdDesp=null;
		btnNextDate=null;
		btnAfDays=null;
		btnRemindTime=null;
		
		etRemark=null;
		
		cbAlarm=null;
		cbNotify=null;
		dNextDate=null;

		System.gc();
	}
}
