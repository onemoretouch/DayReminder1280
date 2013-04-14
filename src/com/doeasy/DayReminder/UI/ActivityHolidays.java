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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.DrInfoDB;

public class ActivityHolidays extends Activity implements OnClickListener{

	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	int[][] HDrInfo;
	int[] hId;
	String[] hName;
	long[] lNextDate;
	String[] hDesp;
	boolean[] hChecked;
	//MyOpenHelper myHelper;		
	int[] holidayId;
	String[] holidayName;	
	String[] holidayDesp;	
	String[] holidayDate;
	boolean[] holidayChecked;
	
	int shCount;
	int lhCount;
	int whCount;
	int stCount=24;
	int iDisplay=0;

	Button btnSh=null;
	Button btnLh=null;
	Button btnWh=null;
	Button btnSt=null;
	
	ListView lv;	 				
	clsCalendar cc;
	BaseAdapter baHolidays = new BaseAdapter(){
		@Override
		public int getCount() {
			if(holidayId != null){		
				return holidayId.length;
			}
			else {
				return 0;					
			}
		}
		@Override
		public Object getItem(int arg0) {
			return null;
		}
		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			LayoutInflater inflater = getLayoutInflater();  
			view = inflater.inflate(R.layout.li_holiday, null);
			TextView tvHName=(TextView)view.findViewById(R.id.tvHName);
			TextView tvHDesp=(TextView)view.findViewById(R.id.tvHDesp);
			TextView tvHDate=(TextView)view.findViewById(R.id.tvHDate);
			CheckBox cbSed=(CheckBox)view.findViewById(R.id.cbSed);
			tvHName.setText(holidayName[position]);
			tvHDesp.setText(holidayDesp[position]);
			tvHDate.setText(holidayDate[position]);
			cbSed.setChecked(holidayChecked[position]);
			return view;
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        InitApp initApp = new InitApp(this);
		initApp.SetApp(0);
		pApplication=(LicenseAppliction)getApplicationContext();
		dbHelper = pApplication.getDataHelper();
        setContentView(R.layout.lo_holidays);
        
        cc=new clsCalendar();
        shCount=cc.GetFtvLength(1);
        lhCount=cc.GetFtvLength(2);
        whCount=cc.GetFtvLength(4);
        int rowsCount=shCount+lhCount+whCount+stCount;
        hId=new int[rowsCount];
    	hName=new String[rowsCount];

    	lNextDate=new long[rowsCount];
    	hChecked=new boolean[rowsCount];
    	hDesp=new String[rowsCount];
    	
    	lv=(ListView)this.findViewById(R.id.lvHolidays);
    	lv.setDividerHeight(0);
        lv.setAdapter(baHolidays);
        lv.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				//���÷���ֵ
        		holidayChecked[position]=!holidayChecked[position];
        		int iBegin=0;
            	switch(iDisplay)
            	{
        	   		case 1:
        	   			iBegin=0;
        	   			break;
        	   		case 2:
        	   			iBegin=shCount;
        	   			break;
        	   		case 4:
        	   			iBegin=shCount+lhCount;
        	   			break;
        	   		case 5:
        	   			iBegin=shCount+lhCount+whCount;
        	   			break;
        	   		default:
        	   			iBegin=0;
        	   			break;
            	}
            	hChecked[iBegin+position]=holidayChecked[position];
            	baHolidays.notifyDataSetChanged();
			}
		});

    	btnSh=(Button)this.findViewById(R.id.btnSh);
    	btnLh=(Button)this.findViewById(R.id.btnLh);
    	btnWh=(Button)this.findViewById(R.id.btnWh);
    	btnSt=(Button)this.findViewById(R.id.btnSt);
    	
        View btnOK=this.findViewById(R.id.btnOK);
        View btnCancel=this.findViewById(R.id.btnCancel);
		
        btnSh.setBackgroundResource(R.drawable.btn_sd_sed);
        
		btnSh.setOnClickListener(this);
		btnLh.setOnClickListener(this);
		btnWh.setOnClickListener(this);
		btnSt.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		GetAllHolidays();
    	baHolidays.notifyDataSetChanged();
    }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	int Id=v.getId();
		if(Id == R.id.btnSh)
		{
			QueryHolidaysInfo(1);
			baHolidays.notifyDataSetChanged();
			btnSh.setBackgroundResource(R.drawable.btn_sd_sed);
			btnLh.setBackgroundResource(R.drawable.btn_ld);
			btnWh.setBackgroundResource(R.drawable.btn_wd);
			btnSt.setBackgroundResource(R.drawable.btn_td);
			return;
		}
		if(Id == R.id.btnLh)
		{
			QueryHolidaysInfo(2);
			baHolidays.notifyDataSetChanged();
			btnSh.setBackgroundResource(R.drawable.btn_sd);
			btnLh.setBackgroundResource(R.drawable.btn_ld_sed);
			btnWh.setBackgroundResource(R.drawable.btn_wd);
			btnSt.setBackgroundResource(R.drawable.btn_td);
		}
		if(Id == R.id.btnWh)
		{
			QueryHolidaysInfo(4);
			baHolidays.notifyDataSetChanged();
			btnSh.setBackgroundResource(R.drawable.btn_sd);
			btnLh.setBackgroundResource(R.drawable.btn_ld);
			btnWh.setBackgroundResource(R.drawable.btn_wd_sed);
			btnSt.setBackgroundResource(R.drawable.btn_td);
		}
		
		if(Id == R.id.btnSt)
		{
			QueryHolidaysInfo(5);
			baHolidays.notifyDataSetChanged();
			btnSh.setBackgroundResource(R.drawable.btn_sd);
			btnLh.setBackgroundResource(R.drawable.btn_ld);
			btnWh.setBackgroundResource(R.drawable.btn_wd);
			btnSt.setBackgroundResource(R.drawable.btn_td_sed);
		}
		if(Id == R.id.btnOK)
		{
			if(SaveDrInfo())
			{
				Intent intented =getIntent();
				setResult(RESULT_OK, intented);
				finish();
			}
		}
		if(Id == R.id.btnCancel)
		{
			finish();
		}
	}
    
    private void GetAllHolidays()
    {
    	int iMonth=0;
    	int iDay=0;
    	Date dSolar;
    	int iRows=0;
    	int i=0;
    	boolean bIsSch=false;
    	holidayId=new int[shCount];		
    	holidayName=new String[shCount];		
    	holidayDesp=new String[shCount];	
    	holidayDate=new String[shCount];	
    	holidayChecked=new boolean[shCount];
    	
    	HDrInfo=dbHelper.getHolidayDrList();
    	if(HDrInfo[0].length>0)
    	{
    		bIsSch=true;
    	}
    	else
    	{
    		bIsSch=false;
    	}
    	int cHIdPos=0;
    	boolean bIsSed=false;
    	for(i=0;i<shCount;i++)
    	{
    		bIsSed=false;
    		iMonth=cc.GetFtvMonth(1, i);
    		iDay=cc.GetFtvDay(1, i);
    		cc.SetDay(iMonth,iDay, 1);
    		dSolar=cc.GetNextDateSolar(1);
    		
    		hId[i]=cc.GetFtvId(1, i);
        	hName[i]=cc.GetFtvName(1, i);
        	hDesp[i]=cc.getFtvDesp(1,i);
        	/*
        	hDrType[i]=3;
        	hYear[i]=2001;
        	hMonth[i]=iMonth;
        	hDay[i]=iDay;
        	hDayType[i]=1;
        	hRemindType[i]=1;
        	hNextDate[i]=dSolar;
        	*/
        	lNextDate[i]=dSolar.getTime();
        	if(bIsSch&&HDrInfo[1][cHIdPos]==hId[i])
        	{
        		bIsSed=true;
        		cHIdPos++;
        		if(cHIdPos==HDrInfo[1].length)
        		{
        			bIsSch=false;
        		}
        	}
        	hChecked[i]=bIsSed;
        	
    		holidayId[i]=hId[i];
    		holidayName[i]="　　"+hName[i];
    		holidayDesp[i]="　"+hDesp[i];
    		holidayDate[i]=String.format("　临近：%d年%02d月%02d日　",dSolar.getYear()+1900,dSolar.getMonth()+1,dSolar.getDate())+cc.GetCnWeekDay(dSolar.getDay());
    		holidayChecked[i]=bIsSed;
    	}
    	iDisplay=1;
    	iRows+=i;
    	for(i=0;i<lhCount;i++)
    	{
    		bIsSed=false;
    		iMonth=cc.GetFtvMonth(2, i);
    		iDay=cc.GetFtvDay(2, i);
    		cc.SetDay(iMonth,iDay, 2);
    		dSolar=cc.GetNextDateSolar(2);
    		lNextDate[iRows+i]=dSolar.getTime();
    		hId[iRows+i]=cc.GetFtvId(2, i);
        	hName[iRows+i]=cc.GetFtvName(2, i);
        	hDesp[iRows+i]=cc.getFtvDesp(2,i);
        	/*
        	hDrType[iRows+i]=3;
        	hYear[iRows+i]=2001;
        	hMonth[iRows+i]=iMonth;
        	hDay[iRows+i]=iDay;
        	hDayType[iRows+i]=2;
        	hRemindType[iRows+i]=2;
        	hNextDate[iRows+i]=dSolar;
        	*/
        	if(bIsSch&&HDrInfo[1][cHIdPos]==hId[iRows+i])
        	{
        		bIsSed=true;
        		cHIdPos++;
        		if(cHIdPos==HDrInfo[1].length)
        		{
        			bIsSch=false;
        		}
        	}
        	hChecked[iRows+i]=bIsSed;
    	}
    	iRows+=i;
    	for(i=0;i<whCount;i++)
    	{
    		bIsSed=false;
    		iMonth=cc.GetFtvMonth(4, i);
    		iDay=cc.GetFtvDay(4, i);
    		cc.SetDay(iMonth,iDay, 4);
    		dSolar=cc.GetNextDateSolar(8);
    		lNextDate[iRows+i]=dSolar.getTime();
    		hId[iRows+i]=cc.GetFtvId(4, i);
        	hName[iRows+i]=cc.GetFtvName(4, i);
        	hDesp[iRows+i]=cc.getFtvDesp(4,i);
        	/*
        	hDrType[iRows+i]=3;
        	hYear[iRows+i]=2010;
        	hMonth[iRows+i]=iMonth;
        	hDay[iRows+i]=iDay;
        	hDayType[iRows+i]=4;
        	hRemindType[iRows+i]=8;
        	hNextDate[iRows+i]=dSolar;
        	*/
        	if(bIsSch&&HDrInfo[1][cHIdPos]==hId[iRows+i])
        	{
        		bIsSed=true;
        		cHIdPos++;
        		if(cHIdPos==HDrInfo[1].length)
        		{
        			bIsSch=false;
        		}
        	}
        	hChecked[iRows+i]=bIsSed;
    	}
    	iRows+=i;
    	for(i=0;i<stCount;i++)
    	{
    		bIsSed=false;
    		iMonth=(int)((i+2)/2);
    		iDay=i+1;
    		cc.SetDay(iMonth,iDay, 5);
    		dSolar=cc.GetNextDateSolar(16);
    		lNextDate[iRows+i]=dSolar.getTime();
    		hId[iRows+i]=cc.GetSoralTermId(i);
        	hName[iRows+i]=cc.GetSoralTermCnName(i);
        	hDesp[iRows+i]=cc.GetSoralTermCnDesp(i);
        	/*
        	hDrType[iRows+i]=3;
        	hYear[iRows+i]=2000;
        	hMonth[iRows+i]=iMonth;
        	hDay[iRows+i]=iDay;
        	hDayType[iRows+i]=5;
        	hRemindType[iRows+i]=16;
        	hNextDate[iRows+i]=dSolar;
        	*/
        	if(bIsSch&&HDrInfo[1][cHIdPos]==hId[iRows+i])
        	{
        		bIsSed=true;
        		cHIdPos++;
        		if(cHIdPos==HDrInfo[1].length)
        		{
        			bIsSch=false;
        		}
        	}
        	hChecked[iRows+i]=bIsSed;
    	}
    	dSolar=null;
    }
    private void QueryHolidaysInfo(int hType){
    	
    	int iCount=0;
    	int iBegin=0;
    	iDisplay=hType;
    	switch(hType)
    	{
	   		case 1:
	   			iCount=shCount;
	   			iBegin=0;
	   			break;
	   		case 2:
	   			iCount=lhCount;
	   			iBegin=shCount;
	   			break;
	   		case 4:
	   			iCount=whCount;
	   			iBegin=shCount+lhCount;
	   			break;
	   		case 5:
	   			iCount=stCount;
	   			iBegin=shCount+lhCount+whCount;
	   			break;
	   		default:
	   			iCount=0;
	   			iBegin=0;
	   			break;
    	}
    	holidayId=new int[iCount];		//声明用于存放联系人姓名的数组
    	holidayName=new String[iCount];		//声明用于存放联系人姓名的数组
    	holidayDesp=new String[iCount];	//声明用于存放联系人电话的数组
    	holidayDate=new String[iCount];	//声明用于存放联系人电话的数组
    	holidayChecked=new boolean[iCount];
    	Date dt=new Date();
    	for(int i=0;i<iCount;i++)
    	{
    		holidayId[i]=hId[iBegin+i];
    		holidayName[i]="　　"+hName[iBegin+i];
    		holidayDesp[i]="　"+hDesp[iBegin+i];
    		dt.setTime(lNextDate[iBegin+i]);
    		holidayDate[i]=String.format("　临近：%d年%02d月%02d日　",dt.getYear()+1900,dt.getMonth()+1,dt.getDate())+cc.GetCnWeekDay(dt.getDay());
    		holidayChecked[i]=hChecked[iBegin+i];
    	}
    }
    @SuppressLint("WorldWriteableFiles")
	private boolean SaveDrInfo()
	{
    	int iCount=shCount+lhCount+whCount+stCount;
    	boolean bIsSch=false;
    	if(HDrInfo[1]==null)
    	{
    		bIsSch=false;
    	}
    	else
    	{
    		if(HDrInfo[1].length>0) 
    		{
    			bIsSch=true;
    		}
    	}
    	int cHIdPos=0;
    	int iMonth;
    	int iDay;
    	int iDayType;
    	int iRmDateType;
    	SharedPreferences spSettings; 
    	String prefsName = getPackageName() + "_preferences";   //[PACKAGE_NAME]_preferences  
		spSettings = getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
		int iAfDays=Integer.parseInt(spSettings.getString("lpAfDays", "1"));
		int iRemindTime=spSettings.getInt("pfRemindTime", 750);
		spSettings=null;
		prefsName=null;
    	ContentValues values;			//��ʶ
    	for(int i=0;i<iCount;i++)
    	{
    		if(bIsSch)
    		{
	    		if(HDrInfo[1][cHIdPos]==hId[i])
				{
	    			if(hChecked[i])
	    			{
	    				HDrInfo[1][cHIdPos]=0;
	    			}
	    			else
	    			{
	    				dbHelper.delDrInfo(HDrInfo[0][cHIdPos]);
	    			}
					cHIdPos++;
					if(HDrInfo[1].length==cHIdPos)
					{
						bIsSch=false;
					}
					continue;
				}
    		}
    		if(hChecked[i])
    		{
    			iDayType=(int)(hId[i]/10000);
    			iMonth=(int)((hId[i] % 10000)/100);
    			iDay=(int)(hId[i] % 100);
    			
    			try
    			{
    				/*
    				switch(iDayType)
    	    		{
    					case 1:
    						iRemindType=1;
    						break;
    					case 2:
    						iRemindType=2;
    						break;
    					case 4:
    						iRemindType=8;
    						break;
    		    		case 5:
    		    			iRemindType=16;
    		    			break;
    		    		default:
    		    			iRemindType=0;
    		    			break;
    	    		}
					*/
    				values = new ContentValues();
    				iRmDateType=(int)Math.pow(2, iDayType-1);
					values.put(drNAME, hName[i]);
					values.put(drPHOTO, "");
					values.put(drPHONE, "");
					values.put(drDrType, 3);
					values.put(drDayYear, 2001);
					values.put(drDayMonth, iMonth);	
					values.put(drDayDay, iDay);
					values.put(drDayType, iDayType);
					if(iDayType==2)
					{
						values.put(drSolarDay, "");
						values.put(drLunarDay, hDesp[i]);
					}
					else
					{
						values.put(drSolarDay, hDesp[i]);
						values.put(drLunarDay, "");
					}
					values.put(drRmDateType,iRmDateType);
					values.put(drDateOfNext, lNextDate[i]);
					
					values.put(drIsAppWidget, 1);
					values.put(drADayOfAppWidget, 30);
					  
					values.put(drRemindType, 2);
						
					values.put(drADayOfRemind,iAfDays);
					values.put(drRemindTime, iRemindTime);
					values.put(drIsAutoSMS, 0);
					values.put(drSMSText, "");
		
					values.put(drRemark, "");
					values.put(drInitials, "");
					
					boolean b=dbHelper.saveDrInfo(0, values);

					values.clear();
					values=null;
					if(!b)
					{
						Toast.makeText(this, "保存节日失败！", Toast.LENGTH_LONG).show();
						return  false;
					}
				}
				catch(java.lang.Exception e)
				{
					Toast.makeText(this, "保存节日失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
					return  false;
				}
			}
    		values=null;
    	}
    	if(bIsSch)
    	{
	    	iCount=HDrInfo[1].length;
	    	for(int i=0;i<iCount;i++)
	    	{
	    		if(HDrInfo[1][i]==0)
	    		{
	    			continue;
	    		}
	    		try
	    		{
	    			dbHelper.delDrInfo(HDrInfo[0][i]);
	    		}
	    		catch(java.lang.Exception e)
				{
	    			Toast.makeText(this, "失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
					return  false;
				}
	    	}
    	}
    	Toast.makeText(this, "保存节日提醒成功！", Toast.LENGTH_LONG).show();
		return  true;
	}
    protected void onDestroy()
    {
    	super.onDestroy();
    	holidayId=null;
    	holidayName=null;
    	holidayDesp=null;
    	holidayDate=null;
    	holidayChecked=null;
    	hId=null;
    	hName=null;
    	/*
    	hDrType=null;
    	hYear=null;
    	hMonth=null;
    	hDay=null;
    	hDayType=null;
    	hRemindType=null;
    	hNextDate=null;
    	*/
    	dbHelper=null;
    	lNextDate=null;
    	hDesp=null;
    	hChecked=null;
    	cc=null;
    	btnSh=null;
    	btnLh=null;
    	btnWh=null;
    	btnSt=null;

    	baHolidays=null;
    	lv=null;
    	System.gc();
    }
}