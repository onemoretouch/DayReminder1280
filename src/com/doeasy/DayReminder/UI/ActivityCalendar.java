package com.doeasy.DayReminder.UI;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LSDate;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.R.color;
import com.doeasy.DayReminder.SimpleData;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.DrInfoDB;

public class ActivityCalendar extends Activity {
    /** Called when the activity is first created. */
	
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	
	Date dNow=null;
	long[] dtMd=null;
	String[] sDrMdName=null;
	String[] sMdName=null;
	LSDate[] lsdLunar=null;
	boolean[] bIsCurrMonth=null;
	private int	iSedCalendarItem=-1;
	int[] iIsMyD=null;

	GridView gvCalendar=null;
	//ListView lvDrInfo=null;
	TextView tvMp=null;
	TextView tvMed=null;
	TextView tvMn=null;
	
	TextView tvSolar=null;
	TextView tvDay=null;
	TextView tvLunar1=null;
	TextView tvLunar2=null;
	TextView tvRemindText=null;
	LinearLayout llCalendar=null;
	
	int iCurrMonth=0;
	int iCurrYear=1900;
	int iCurrDay=0;
	
	long lcNow=0;
	Date dTmp=null;
	clsCalendar cc=null;
	private Animation animationTranslate;
	BaseAdapter baCalendar = new BaseAdapter(){
		@Override
		public int getCount() {
			if(dtMd != null){		
				return dtMd.length;
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
			view = inflater.inflate(R.layout.li_calendar, null);
			TextView tvMdName=(TextView)view.findViewById(R.id.tvMdInfo);
			tvMdName.setText(sMdName[position]);
			tvMdName.setId(position);
			if(bIsCurrMonth[position])
			{
				tvMdName.setTextColor(color.gray);
				if(Math.abs(dtMd[position]-lcNow)<24*60*60*1000)
				{
					tvMdName.setBackgroundResource(R.drawable.bg_ccld_sed);
					iSedCalendarItem=position;
				}
				else
				{
					dTmp=new Date(dtMd[position]);
					if(!(dNow.getYear()==dTmp.getYear()&&dNow.getMonth()==dTmp.getMonth()))
					{
						if(dTmp.getDate()==1)
						{
							iSedCalendarItem=position;
							tvMdName.setBackgroundResource(R.drawable.bg_cld_sed);
						}
					}
				}
			}
			//view.setBackgroundResource(R.drawable.bgclded);
			ImageView ivAllow=(ImageView)view.findViewById(R.id.ivAllow);
			switch(iIsMyD[position])
			{
			case -1:
				ivAllow.setBackgroundResource(R.drawable.dbm);
				break;
			case 0:
				break;
			case 1:
				ivAllow.setBackgroundResource(R.drawable.dbh);
				break;
			default:
				ivAllow.setBackgroundResource(R.drawable.dbmh);
				break;
			}
			/*
			view.setId(10000+position);
			view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int iPosition=v.getId()-10000;
					if(bIsCurrMonth[iPosition])
					{
						if(iSedCalendarItem!=-1)
						{
							TextView tvLSed=(TextView)ActivityCalendar.this.findViewById(iSedCalendarItem);
							if(Math.abs(dtMd[iSedCalendarItem]-lcNow)<24*60*60*1000)
							{
								tvLSed.setBackgroundResource(R.drawable.xml_bg_ccld);
							}
							else
							{
								tvLSed.setBackgroundResource(R.drawable.xml_bg_cld);
							}
						}
						iSedCalendarItem=iPosition;
						TextView tvCSed=(TextView)ActivityCalendar.this.findViewById(iSedCalendarItem);
						if(Math.abs(dtMd[iPosition]-lcNow)<24*60*60*100)
						{
							tvCSed.setBackgroundResource(R.drawable.bg_ccld_sed);
						}
						else
						{
							tvCSed.setBackgroundResource(R.drawable.bg_cld_sed);
						}
						SetRemindText(iPosition);
						
					}
				}
				
			});
			*/
			return view;
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        InitApp initApp = new InitApp((Context)this);
		initApp.SetApp(0);
		pApplication=(LicenseAppliction)getApplicationContext();
		dbHelper = pApplication.getDataHelper();
        setContentView(R.layout.lo_calendar);
        tvMp=(TextView)this.findViewById(R.id.tvMp);
        tvMed=(TextView)this.findViewById(R.id.tvMed);
        tvMn=(TextView)this.findViewById(R.id.tvMn);
        tvMp.setOnClickListener(clickListener);
        tvMed.setOnClickListener(clickListener);
        tvMn.setOnClickListener(clickListener);
        
        tvSolar=(TextView)this.findViewById(R.id.tvSolar);
    	tvDay=(TextView)this.findViewById(R.id.tvDay);
    	tvLunar1=(TextView)this.findViewById(R.id.tvLunar1);
    	tvLunar2=(TextView)this.findViewById(R.id.tvLunar2);
    	tvRemindText=(TextView)this.findViewById(R.id.tvRemindText);
    	
        gvCalendar=(GridView)this.findViewById(R.id.gvCalendar);
        gvCalendar.setAdapter(baCalendar);
        gvCalendar.setOnTouchListener(TouchLight);
       
        gvCalendar.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(bIsCurrMonth[arg2])
				{
					if(iSedCalendarItem!=-1)
					{
						TextView tvLSed=(TextView)ActivityCalendar.this.findViewById(iSedCalendarItem);
						if(Math.abs(dtMd[iSedCalendarItem]-lcNow)<24*60*60*1000)
						{
							tvLSed.setBackgroundResource(R.drawable.xml_bg_ccld);
						}
						else
						{
							tvLSed.setBackgroundResource(R.drawable.xml_bg_cld);
						}
					}
					iSedCalendarItem=arg2;
					TextView tvCSed=(TextView)ActivityCalendar.this.findViewById(iSedCalendarItem);
					if(Math.abs(dtMd[arg2]-lcNow)<24*60*60*100)
					{
						tvCSed.setBackgroundResource(R.drawable.bg_ccld_sed);
					}
					else
					{
						tvCSed.setBackgroundResource(R.drawable.bg_cld_sed);
					}
					SetRemindText(arg2);
					
				}
			}
        });
        llCalendar=(LinearLayout)this.findViewById(R.id.llCalendar);
        dNow=new Date();
        iCurrYear=dNow.getYear()+1900;
        iCurrMonth=dNow.getMonth()+1;
        iCurrDay=dNow.getDate();
        
        Date dt=new Date(iCurrYear-1900,iCurrMonth-1,iCurrDay,0,0,0);
        lcNow=dt.getTime();
        dt=null;
        int iPrevMonth=iCurrMonth-1;
    	if(iPrevMonth==0)
    	{
    		iPrevMonth=12;
    	}
    	int iNextMonth=iCurrMonth+1;
    	if(iNextMonth==13)
    	{
    		iNextMonth=1;
    	}
    	tvMp.setText(String.valueOf(iPrevMonth)+"月");
    	tvMed.setText(String.format("%d年%d月", iCurrYear,iCurrMonth));
    	tvMn.setText(String.valueOf(iNextMonth)+"月");
        cc=new clsCalendar();
        GetMdInfo();
    }
    public final OnTouchListener TouchLight = new OnTouchListener() {
		float StartX=0.0f;
		@SuppressWarnings("unused")
		float StartY=0.0f;
		float EndX=0.0f;
		@SuppressWarnings("unused")
		float EndY=0.0f;
		int iMoveCount=0;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			float x=event.getX();
			float y=event.getY();
			if (event.getAction() == MotionEvent.ACTION_DOWN) 
			{
				StartX=x;
				StartY=y;
				iMoveCount=0;
				//llContainer.setBackgroundResource(R.drawable.bglisted100);
				return false;
			} 
			if (event.getAction() == MotionEvent.ACTION_UP) 
			{		
				return false;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE&&iMoveCount==0) 
			{
				EndX=x;
				EndY=y;
				if(EndX-StartX>20)
				{
					//animationHideListView();
					SetCalendarMonth(-1);
					animationFromLeftToRightShowListView();
					iMoveCount++;
					return true;
				}
				if(EndX-StartX<-20)
				{
					//animationHideListView();
					SetCalendarMonth(1);
					animationFromRightToLeftShowListView();
					iMoveCount++;
					return true;
				}
			}
			return false;
		}
	};
    private View.OnClickListener clickListener = new View.OnClickListener()
    {
	    public void onClick(View v) 
	    {
	    	dtMd=null;
	    	sMdName=null;
	    	bIsCurrMonth=null;
	    	if(v==tvMp)
	    	{
	    		//animationHideListView();
	    		SetCalendarMonth(-1);
	    		animationFromLeftToRightShowListView();
	    	}
	    	if(v==tvMn)
	    	{
	    		//animationHideListView();
	    		SetCalendarMonth(1);
	    		animationFromRightToLeftShowListView();
	    	}
	    	if(v==tvMed)
	    	{
	    		SetCalendarMonth(0);
	    	}
	    	
	    }
    };
    private void SetCalendarMonth(int iMnp)
    {
    	dtMd=null;
    	sMdName=null;
    	bIsCurrMonth=null;
    	if(iMnp==-1)
    	{
    		iCurrMonth--;
    		if(iCurrMonth==0)
    		{
    			iCurrMonth=12;
    			iCurrYear--;
    		}
    	}
    	if(iMnp==1)
    	{
    		iCurrMonth++;
    		if(iCurrMonth==13)
    		{
    			iCurrMonth=1;
    			iCurrYear++;
    		}
    	}
    	if(iMnp==0)
    	{
    		iCurrYear=dNow.getYear()+1900;
            iCurrMonth=dNow.getMonth()+1;
            iCurrDay=dNow.getDate();
    	}
    	int iPrevMonth=iCurrMonth-1;
    	if(iPrevMonth==0)
    	{
    		iPrevMonth=12;
    	}
    	int iNextMonth=iCurrMonth+1;
    	if(iNextMonth==13)
    	{
    		iNextMonth=1;
    	}
    	tvMp.setText(String.valueOf(iPrevMonth)+"月");
    	tvMed.setText(String.format("%d年%d月", iCurrYear,iCurrMonth));
    	tvMn.setText(String.valueOf(iNextMonth)+"月");
    	if((iCurrYear==dNow.getYear()+1900)&&(iCurrMonth==dNow.getMonth()+1))
    	{
    		tvMed.setBackgroundResource(R.drawable.bg_ccms);
    	}
    	else
    	{
    		tvMed.setBackgroundResource(R.drawable.bg_cms);
    	}
    	if(iCurrYear==dNow.getYear()+1900&&iCurrMonth==dNow.getMonth()+1)
    	{
    		 iCurrDay=dNow.getDate();
    	}
    	else
    	{ 
    		 iCurrDay=1;
    	}
    	GetMdInfo();
    }
    /*
    private void animationHideListView()
    {
    	animationTranslate= AnimationUtils.loadAnimation(this, R.anim.hide_alpha);
    	gvCalendar.startAnimation(animationTranslate);
    	llCalendar.startAnimation(animationTranslate);
    }
    */
    private void animationFromLeftToRightShowListView()
    {
    	animationTranslate= AnimationUtils.loadAnimation(this, R.anim.ltr_show_alpha_translate);
    	gvCalendar.startAnimation(animationTranslate);
    	llCalendar.startAnimation(animationTranslate);
    }
    private void animationFromRightToLeftShowListView()
    {
    	animationTranslate= AnimationUtils.loadAnimation(this, R.anim.rtl_show_alpha_translate);
    	gvCalendar.startAnimation(animationTranslate);
    	llCalendar.startAnimation(animationTranslate);
    }
    private void GetMdInfo()
    {
    	try
    	{
	    	int imDays=cc.SolarMonthDays(iCurrYear, iCurrMonth);
	    	Date dtFirstDay=new Date(iCurrYear-1900,iCurrMonth-1,1,0,0,0);
	
	    	Date dtLastDay=new Date(iCurrYear-1900,iCurrMonth-1,imDays,23,59,59);
	    	
	    	int ifWeekDay=dtFirstDay.getDay();
	    	int ilWeekDay=dtLastDay.getDay();
	    	
	    	Date dtBeginDay=new Date(dtFirstDay.getTime()-ifWeekDay*24*60*60*1000);
	    	Date dtEndDay=new Date(dtLastDay.getTime()+(6-ilWeekDay)*24*60*60*1000);
	    	int iShowDays=(int)((dtEndDay.getTime()-dtBeginDay.getTime())/(24*60*60*1000))+1;
	    	
	    	dtMd=new long[iShowDays];
	    	sMdName=new String[iShowDays];
	    	sDrMdName=new String[iShowDays];
	    	iIsMyD=new int[iShowDays];
	    	bIsCurrMonth=new boolean[iShowDays];
	    	lsdLunar=new LSDate[iShowDays];
	    	
	    	//long[] DrMd=new long[iShowDays];
	    	
	    	
	    	
	    	int iPosDrBegin=0;
	    	int iOnePos=-1;
	    	Date dtTmp=null;
	    	LSDate lsdTmp=null;
	    	String sLunarCnDayName="";
	    	String sSolarHoliday="";
	    	String sLunarHoliday="";
	    	String sWeekHoliday="";
	    	String sSolarTerm="";
	    	for(int i=0;i<iShowDays;i++)
	    	{
	    		dtTmp=new Date(dtBeginDay.getTime()+i*24*60*60*1000l);
	    		lsdTmp=cc.GetLunarLSDateBySolarDate(dtTmp);
	    		lsdLunar[i]=new LSDate(lsdTmp.Year,lsdTmp.Month,lsdTmp.Day,lsdTmp.IsLeap);
	    		sLunarCnDayName=cc.GetLunarCnDayName(lsdTmp.Month, lsdTmp.Day, lsdTmp.IsLeap);
	    		sMdName[i]=sLunarCnDayName;
	    		bIsCurrMonth[i]=false;
	    		iIsMyD[i]=0;
	    		sDrMdName[i]="";
	    		if(dtTmp.getMonth()==(iCurrMonth-1))
	    		{
	    			bIsCurrMonth[i]=true;
	    			
	    			if(dtTmp.getDate()==iCurrDay)
	    			{
	    				iOnePos=i;
	    			}
		    		sSolarHoliday=cc.GetSoloarHoliday(String.format("%02d%02d", iCurrMonth,dtTmp.getDate()));
		    		
		    		
		    		
		    		if(!lsdTmp.IsLeap)
		    		{
		    			sLunarHoliday=cc.GetLunarHoliday(String.format("%02d%02d", lsdTmp.Month,lsdTmp.Day));
		    		}
		    	  	
		    	  	sWeekHoliday=cc.GetWeekHoliday(iCurrMonth, dtTmp.getDate(), dtTmp.getDay(), ifWeekDay, ilWeekDay, imDays);
		    	  	
		    	  	sSolarTerm=cc.GetSoralTerm(dtTmp);
		    	  	
	//	    	  	String sDrName="";
		    		//ȡ���������
		    	  	
		    	  	if(sSolarTerm.length()>0)
		    		{
		    			iIsMyD[i]++;
		    			sDrMdName[i]+=sSolarTerm+"\r\n";
		    			sMdName[i]=sSolarTerm;
		    		}
		    	  	
		    	  	if(sWeekHoliday.length()>0)
		    		{
		    			iIsMyD[i]++;
		    			sDrMdName[i]+=sWeekHoliday+"\r\n";
		    			sMdName[i]=sWeekHoliday;
		    		}
		    		
		    		if(sSolarHoliday.length()>0)
		    		{
		    			iIsMyD[i]++;
		    			sDrMdName[i]+=sSolarHoliday+"\r\n";
		    			sMdName[i]=sSolarHoliday;
		    		}
		    		if(sLunarHoliday.length()>0)
		    		{
		    			iIsMyD[i]++;
		    			sDrMdName[i]+=sLunarHoliday+"\r\n";
		    			sMdName[i]=sLunarHoliday;
		    		}
		    		
		    		List<SimpleData> sDataList=dbHelper.getSimpleDatas(dtFirstDay, dtLastDay);
		    		String sTmp1="";
		    		String sTmp2="";
		    		int icCount=sDataList.size();
		    		for(;iPosDrBegin<icCount;iPosDrBegin++)
		    		{
		    			SimpleData simpleData=sDataList.get(iPosDrBegin);
		    			if(simpleData.getNext().getDate()!=dtTmp.getDate())
		    			{
		    				break;
		    			}
		    			if(simpleData.getNext().getDate()==dtTmp.getDate())
		    			{
		    				sTmp1+=simpleData.getName()+",";
		    				sTmp2+=simpleData.getName();
		    				if(simpleData.getDrType()==1)
		    				{
		    					sTmp2+="的生日\r\n";
		    				}
		    				iPosDrBegin++;
		    			}
		    		}
		    		
		    		if(sTmp1.length()>0)
		    		{
		    			sTmp1=sTmp1.substring(0,sTmp1.length()-1);
		    			sDrMdName[i]+=sTmp2;
		    			iIsMyD[i]=-1;
		    			sMdName[i]=sTmp1;
		    		}
	    		}
	    		dtMd[i]=dtTmp.getTime();
	    		sMdName[i]=String.valueOf(dtTmp.getDate())+"\r\n"+sMdName[i];
	    	}
	    	baCalendar.notifyDataSetChanged();
	    	if(iOnePos!=-1)
	    	{
	    		SetRemindText(iOnePos);
	    	}
	    	
	    	dtTmp=null;
	    	
	    	sSolarHoliday=null;
    		sLunarHoliday=null;
    		sWeekHoliday=null;
    		sSolarTerm=null;
    		
	    	dtFirstDay=null;
	    	dtLastDay=null;
	    	dtBeginDay=null;
	    	dtEndDay=null;
	    	
    	}
		catch(java.lang.Exception e)
		{
			Toast.makeText(this, "错误："+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
    }
    private void SetRemindText(int i)
    {
    	try
    	{
	    	Date dt=new Date(dtMd[i]);
	    	tvSolar.setText(String.format("%d年%02d月%02d日　%s", dt.getYear()+1900,dt.getMonth()+1,dt.getDate(),cc.GetCnWeekDay(dt.getDay())));
	    	tvDay.setText(String.valueOf(dt.getDate()));
	    	tvLunar1.setText("农历　"+cc.GetLunarCnMonthDayName(lsdLunar[i].Month,lsdLunar[i].Day,lsdLunar[i].IsLeap));
	    	tvLunar2.setText(cc.GetGanZhi(dt));
	    	String sTmp=sDrMdName[i];
	    	if(sTmp.length()==0)
	    	{
	    		sTmp="��";
	    	}
	    	tvRemindText.setText(sDrMdName[i]);
    	}
		catch(java.lang.Exception e)
		{
			Toast.makeText(this, "错误："+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
    }
    protected void onDestroy()
	{
		super.onDestroy();
		
		dNow=null;
		dtMd=null;
		sDrMdName=null;
		sMdName=null;
		lsdLunar=null;
		bIsCurrMonth=null;
		iIsMyD=null;

		gvCalendar=null;
		tvMp=null;
		tvMed=null;
		tvMn=null;
		
		tvSolar=null;
		tvDay=null;
		tvLunar1=null;
		tvLunar2=null;
		tvRemindText=null;
		cc=null;
		baCalendar=null;
		System.gc();
	}
}