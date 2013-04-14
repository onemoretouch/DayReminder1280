package com.doeasy.DayReminder.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;

public class ActivityDayDetails extends Activity  implements OnTouchListener{
	
	int iEdFlag=0;
	int iDrType=0;
	int iYear=2009;
	int iMonth=3;
	int iDay=8;
	int iDayType=1;
	
	ListView lvDate;	 					//声明ListView对象
	String [] DayDateName;		//声明用于存放联系人姓名的数组
	String [] DayDate;		//声明用于存放联系人姓名的数组
	clsCalendar cc=null;
	BaseAdapter baDay = new BaseAdapter(){
		@Override
		public int getCount() {
			if(DayDateName != null){		//如果姓名数组不为空
				return DayDateName.length;
			}
			else {
				return 0;					//如果姓名数组为空则返回0
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
			//LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			LayoutInflater inflater = getLayoutInflater();  
			view = inflater.inflate(R.layout.li_datedetails, null);
			TextView tvTitle=(TextView)view.findViewById(R.id.tvTitle);
			TextView tvDate=(TextView)view.findViewById(R.id.tvDate);
			tvTitle.setText(DayDateName[position]);
			tvDate.setText(DayDate[position]);
			return view;
		}
	};
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.lo_datedetails);
	    //���ؽ���ؼ�
	    TextView tvTile=(TextView)this.findViewById(R.id.tvTitle);
	    TextView tvSolarDay=(TextView)this.findViewById(R.id.tvSolarDay);
		TextView tvLunarDay=(TextView)this.findViewById(R.id.tvLunarDay);;
		ImageView ivSolar=(ImageView)this.findViewById(R.id.ivSolar);
		ImageView ivLunar=(ImageView)this.findViewById(R.id.ivLunar);
	    
	    lvDate=(ListView)this.findViewById(R.id.lvDate); 
	    lvDate.setDividerHeight(0);
	    lvDate.setAdapter(baDay);
        lvDate.setOnTouchListener(this);
        Bundle baed=getIntent().getExtras();
	    iEdFlag=baed.getInt("Flag");
	    if(iEdFlag==1)
	    {
	    	iDrType=baed.getInt("DrType");
	    	switch (iDrType) {
			case 1:
				tvTile.setText("生日详情");
				break;
			case 2:
				tvTile.setText("纪念日详情");
				break;
			default:
				tvTile.setText("节日详情");
				break;
			}
	    	iYear=baed.getInt("Year");
	    	iMonth=baed.getInt("Month");
	    	iDay=baed.getInt("Day");
	    	iDayType=baed.getInt("DayType");
	    	if(iDrType==1||iDrType==2)
	    	{
	    		if(iYear==0)
	    		{
	    			cc=new clsCalendar(iMonth,iDay,iDayType);
	    		}
	    		else
	    		{
	    			cc=new clsCalendar(iYear,iMonth,iDay,iDayType);
	    		}
		    	if(cc.GetStatus())
		    	{
		    		if(iYear==0)
		    		{
		    			switch (iDayType) {
						case 1:
							tvSolarDay.setText(cc.GetSolarMonthDayString());
			    			View llLunar=this.findViewById(R.id.llLunar);
				    		llLunar.setVisibility(View.GONE);
							break;
						case 2:
							tvLunarDay.setText(cc.GetLunarMonthDayString());
							View llSolar=this.findViewById(R.id.llSolar);
							llSolar.setVisibility(View.GONE);
							break;
						default:
							break;
						}
		    			
		    		}
		    		else
		    		{
		    			tvSolarDay.setText(cc.GetSolarString());
		    			tvLunarDay.setText(cc.GetLunarString());
		    		}
		    		if(iDrType==1)
		    		{
		    			if(iDayType==2&&iYear!=0)
		    			{
				    		switch(cc.GetHorescopeNum())
				    		{
					    		case 1:
					    			ivSolar.setImageResource(R.drawable.x1);
					    			break;
					    		case 2:
					    			ivSolar.setImageResource(R.drawable.x2);
					    			break;
					    		case 3:
					    			ivSolar.setImageResource(R.drawable.x3);
					    			break;
					    		case 4:
					    			ivSolar.setImageResource(R.drawable.x4);
					    			break;
					    		case 5:
					    			ivSolar.setImageResource(R.drawable.x5);
					    			break;
					    		case 6:
					    			ivSolar.setImageResource(R.drawable.x6);
					    			break;
					    		case 7:
					    			ivSolar.setImageResource(R.drawable.x7);
					    			break;
					    		case 8:
					    			ivSolar.setImageResource(R.drawable.x8);
					    			break;
					    		case 9:
					    			ivSolar.setImageResource(R.drawable.x9);
					    			break;
					    		case 10:
					    			ivSolar.setImageResource(R.drawable.x10);
					    			break;
					    		case 11:
					    			ivSolar.setImageResource(R.drawable.x11);
					    			break;
					    		case 12:
					    			ivSolar.setImageResource(R.drawable.x12);
					    			break;
				    		}
		    			}
			    		if(iYear!=0)
			    		{
				    		switch(cc.GetAnimalsYear())
				    		{
					    		case 1:
					    			ivLunar.setImageResource(R.drawable.s1);
					    			break;
					    		case 2:
					    			ivLunar.setImageResource(R.drawable.s2);
					    			break;
					    		case 3:
					    			ivLunar.setImageResource(R.drawable.s3);
					    			break;
					    		case 4:
					    			ivLunar.setImageResource(R.drawable.s4);
					    			break;
					    		case 5:
					    			ivLunar.setImageResource(R.drawable.s5);
					    			break;
					    		case 6:
					    			ivLunar.setImageResource(R.drawable.s6);
					    			break;
					    		case 7:
					    			ivLunar.setImageResource(R.drawable.s7);
					    			break;
					    		case 8:
					    			ivLunar.setImageResource(R.drawable.s8);
					    			break;
					    		case 9:
					    			ivLunar.setImageResource(R.drawable.s9);
					    			break;
					    		case 10:
					    			ivLunar.setImageResource(R.drawable.s10);
					    			break;
					    		case 11:
					    			ivLunar.setImageResource(R.drawable.s11);
					    			break;
					    		case 12:
					    			ivLunar.setImageResource(R.drawable.s12);
					    			break;
				    		}
			    		}
		    		}
		    		
		    		GetListItemData();
		    		baDay.notifyDataSetChanged();
		    	}
		    	else
		    	{
		    		cc=null;
		    	}
		    	super.onResume();
	    	}
	    	else
	    	{
	    		View llSolar=this.findViewById(R.id.llSolar);
	    		llSolar.setVisibility(View.GONE);
	    		View llLunar=this.findViewById(R.id.llLunar);
	    		llLunar.setVisibility(View.GONE);
	    		cc=new clsCalendar(iMonth,iDay,iDayType);
	    		GetListItemData();
	    		baDay.notifyDataSetChanged();
	    	}
	    }
	    else
	    {
	    	finish();
	    }
	}
    public void GetListItemData(){ 	
    	String[] sDdName = new String[6];			//创建存放姓名的String数组对象
    	String[] sDdate = new String[6];				//创建存放id的int数组对象
    	int i=0;
    	String sTemp="";
    	if(iDrType==1||iDrType==2)
    	{
	    	
	    	if(iYear!=0)
	    	{
	    		sTemp=cc.GetCurYearSolarString(1);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="今年公历";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}
		    	sTemp=cc.GetCurYearSolarString(2);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="今年农历";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}
	    	
		    	sTemp=cc.GetCurYearSolarString(3);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="今年闰月";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}

	    		sTemp=cc.GetNxtYearSolarString(1);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="明年公历";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}
		    	sTemp=cc.GetNxtYearSolarString(2);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="明年农历";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}
		    	sTemp=cc.GetNxtYearSolarString(3);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="明年闰月";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}
	    	}
	    	else
	    	{
	    		sTemp=cc.GetCurYearSolarString(iDayType);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="今　　年";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}
	    		sTemp=cc.GetNxtYearSolarString(iDayType);
		    	if(cc.GetStatus())
		    	{
		    		sDdName[i]="明　　年";
		    		sDdate[i]=sTemp;
		    		i++;
		    	}
	    	}
    	}
    	else
    	{
    		sTemp=cc.GetCurYearSolarString(iDayType);
	    	if(cc.GetStatus())
	    	{
	    		sDdName[i]="今　　年";
	    		sDdate[i]=sTemp;
	    		i++;
	    	}
    		sTemp=cc.GetNxtYearSolarString(iDayType);
	    	if(cc.GetStatus())
	    	{
	    		sDdName[i]="明　　年";
	    		sDdate[i]=sTemp;
	    		i++;
	    	}
    	}
    	DayDateName=new String[i];
    	DayDate=new String[i];
    	for(int j=0;j<i;j++)
    	{
    		DayDateName[j]=sDdName[j];
    		DayDate[j]=sDdate[j];
    	}
    	sDdName=null;
    	sDdate=null;
    	sTemp=null;
	}
    public void surfaceDestroyed()
	{
    	lvDate=null;	 					//声明ListView对象
    	DayDateName=null;					//声明用于存放联系人姓名的数组
    	DayDate=null;						//声明用于存放联系人姓名的数组
    	cc=null;
    	baDay=null;
    	System.gc();
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		//重写的onTouchEvent回调方法 
		if(v==lvDate)
		{
			switch(event.getAction())
			{
				//按下
				case MotionEvent.ACTION_DOWN:
					return super.onTouchEvent(event);
					//滑动
				case MotionEvent.ACTION_MOVE:
						
					break;
					//离开
				case MotionEvent.ACTION_UP:
					
					return super.onTouchEvent(event);
			}
		}
		//注意：返回值是false
		return false;
	}
}
