package com.doeasy.DayReminder.UI;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;

public class ActivityDayEdit extends Activity implements OnClickListener {
	EditText etYear=null;
	EditText etMonth=null;
	EditText etDay=null;
	
	RadioGroup rgDayType=null;
	RadioButton rbSolar=null;
	RadioButton rbLunar=null;
	RadioButton rbLunarLeap=null;
	
	int iEdFlag=0;
	int iYear=0;
	int iMonth=0;
	int iDay=0;
	int iDayType=0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    setContentView(R.layout.lo_dayedit);
	    
	    etYear=(EditText)this.findViewById(R.id.etYear);
	    ///etYear.setOnClickListener(this);
	    etYear.addTextChangedListener(EtYearWatcher);
	    //InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
	    //imm.showSoftInput(etYear, 0);
	    etMonth=(EditText)this.findViewById(R.id.etMonth);
	    etMonth.addTextChangedListener(EtMonthWatcher);
	    etDay=(EditText)this.findViewById(R.id.etDay);
	    etDay.addTextChangedListener(EtDayWatcher);
	    rgDayType=(RadioGroup)this.findViewById(R.id.rgDayType);
	    rgDayType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
	    	 // 参数checkedId代表事件源radioButton的id，通过这个比较来判断事件源  
            @Override  
            public void onCheckedChanged(RadioGroup group, int checkedId) {  
                switch (checkedId)
                {
                case R.id.rbSolar:
                	iDayType=1;
                	break;
                case R.id.rbLunar:
                	iDayType=2;
                	break;
                case R.id.rbLunarLeap:
                	iDayType=3;
                	break;
                default:
                	iDayType=0;
                	break;
                }
            }  
        });  
	    
        rbSolar=(RadioButton)findViewById(R.id.rbSolar);
        rbLunar=(RadioButton)findViewById(R.id.rbLunar);
        rbLunarLeap=(RadioButton)findViewById(R.id.rbLunarLeap);
        
        Bundle baed=getIntent().getExtras();
        rbSolar.setEnabled(true);
        rbSolar.setChecked(true);
        rbLunar.setEnabled(true);
        rbLunarLeap.setEnabled(false);
        if(baed!=null)
        {
        	iEdFlag=baed.getInt("Flag");
        }
        else
        {
        	iEdFlag=0;
        }
	    if(iEdFlag==1)
	    {
	    	iYear=baed.getInt("Year");
	    	if(iYear!=0)
	    	{
	    		etYear.setText(String.valueOf(iYear));
	    		//rbLunar.setEnabled(true);
	    	    rbLunarLeap.setEnabled(true);
	    	}
	    	etMonth.setText(String.valueOf(baed.getInt("Month")));
	    	etDay.setText(String.valueOf(baed.getInt("Day")));
	    	switch(baed.getInt("DayType"))
	    	{
		    	case 1:
		    		rbSolar.setChecked(true);
		    		break;
		    	case 2:
		    		rbLunar.setChecked(true);
		    		break;
		    	case 3:
		    		rbLunarLeap.setChecked(true);
		    		break;
	    	}
	    }
	    else
	    {
	    	etYear.getEditableText().clear();
	    	etMonth.getEditableText().clear();
	    	etDay.getEditableText().clear();
	    }
	    baed=null;
        View btnOK=this.findViewById(R.id.btnOK);
		View btnCancel=this.findViewById(R.id.btnCancel);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		//InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		//imm.toggleSoftInputFromWindow(etYear.getWindowToken(), 0, 0);   
		//etYear.requestFocus();
		//etYear.selectAll();
	}
	protected void onStart()
	  { 
		  
		  etYear.requestFocus();
		  InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		  //imm.toggleSoftInputFromWindow(etYear.getWindowToken(), 0, 0);   
		  imm.showSoftInput(etYear, 0);
		  //etYear.requestFocus();
		  super.onStart();
	  }
	protected void onResume()
	  {
		  etYear.requestFocus();
		  InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		  //imm.toggleSoftInputFromWindow(etYear.getWindowToken(), 0, 0);   
		  imm.showSoftInput(etYear, 0);
		  super.onResume();
		  //StartAlarm();
	  }
	private TextWatcher EtDayWatcher = new TextWatcher() {
        @SuppressWarnings("unused")
		CharSequence temp = null;
        public void afterTextChanged(Editable s) {
          
            String t=etDay.getText().toString();
        	if(s.length()>0)
        	{
	        	int i=Integer.parseInt(t);
	        	if(i>31)
	        	{
	        		etDay.setSelection(0, 2);
	        	}
	        	else if(s.length()==2&&i==0)
	        	{
	        		etDay.setSelection(0, 2);
	        	}
        	}
        	t=null;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
        }

        public void onTextChanged(CharSequence s, int start, int before,int count) {
        	temp = s;
        }
    };
	private TextWatcher EtMonthWatcher = new TextWatcher() {
        @SuppressWarnings("unused")
		CharSequence temp = null;
        public void afterTextChanged(Editable s) {
        	
            String t=etMonth.getText().toString();
        	if(s.length()>0)
        	{
	        	int i=Integer.parseInt(t);
	        	if(i>=2&&i<=12)
	        	{
	        		etDay.requestFocus();
	        		//etDay.selectAll();
	        	}
	        	else if(i>12)
	        	{
	        		etMonth.setSelection(0, 2);
	        	}
	        	else if(s.length()==2&&i==0)
	        	{
	        		etMonth.setSelection(0, 2);
	        	}
        	}
        	t=null;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        	
        }

        public void onTextChanged(CharSequence s, int start, int before,int count) {
        	temp = s;
        }
    };
	private TextWatcher EtYearWatcher = new TextWatcher() {
        @SuppressWarnings("unused")
		CharSequence temp = null;
        public void afterTextChanged(Editable s) {
        	
        	String t=etYear.getText().toString();
        	if(s.length()>0)
        	{
	        	int i=Integer.parseInt(t);
	        	if(i>=1900&&i<=2050)
	        	{
	        		etMonth.requestFocus();
	        		rgDayType.setEnabled(true);
	        		rbLunar.setEnabled(true);
		    	    rbLunarLeap.setEnabled(true);
	        	}
	        	else if(i>2050)
	        	{
	        		etYear.setSelection(0, 4);
	        		rbSolar.setChecked(true);
	        		rbLunar.setEnabled(true);
		    	    rbLunarLeap.setEnabled(false);
	        	}
	        	else if(s.length()==4&&i<1900)
	        	{
	        		etYear.setSelection(0, 4);
	        		rbSolar.setChecked(true);
	        		rbLunar.setEnabled(true);
		    	    rbLunarLeap.setEnabled(false);
	        	}
	        	else
	        	{
	        		rbSolar.setChecked(true);
	        		rbLunar.setEnabled(true);
		    	    rbLunarLeap.setEnabled(false);
	        	}
        	}
        	else
        	{
        		rbSolar.setChecked(true);
        		rbLunar.setEnabled(true);
	    	    rbLunarLeap.setEnabled(false);
        	}
        	t=null;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
        }

        public void onTextChanged(CharSequence s, int start, int before,int count) {
        	temp = s;
        }
    };
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	int Id=v.getId();
    	if(Id == R.id.btnOK)
		{
			String sContent=getResources().getString(R.string.err_dateformart);
			try
			{
				String sYear=etYear.getText().toString().trim();
				String sMonth=etMonth.getText().toString().trim();
				String sDay=etDay.getText().toString().trim();
				if(sYear.length()==0)
				{
					sYear="0";
				}
				iYear=Integer.parseInt(sYear);
				iMonth=Integer.parseInt(sMonth);
				iDay=Integer.parseInt(sDay);
				clsCalendar cc;
				if(iYear==0)
				{
					cc=new clsCalendar(2000,iMonth,iDay,iDayType);
				}
				else
				{
					cc=new clsCalendar(iYear,iMonth,iDay,iDayType);
				}
				if(!cc.GetStatus())
				{
					Toast.makeText(ActivityDayEdit.this, sContent, Toast.LENGTH_SHORT).show();
					return;
				}
				Date dNow=new Date();
				Date dt=cc.GetSolar();
				if(dt.after(dNow))
				{
					Toast.makeText(ActivityDayEdit.this, getResources().getString(R.string.err_date_future), Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intented =getIntent();
				Bundle bred=new Bundle();
				iEdFlag=1;
				bred.putInt("Flag", iEdFlag);
				bred.putInt("Year", iYear);
				bred.putInt("Month", iMonth);
				bred.putInt("Day", iDay);
				bred.putInt("DayType", iDayType);
				intented.putExtras(bred);
				setResult(RESULT_OK, intented);
				intented=null;
				bred=null;
				cc=null;
				sContent=null;
				sYear=null;
				sMonth=null;
				sDay=null;
				finish();
			}
			catch(java.lang.Exception e)
			{
				Toast.makeText(ActivityDayEdit.this, sContent, Toast.LENGTH_SHORT).show();
			}
		}
		if(Id==R.id.btnCancel)
		{
			setResult(RESULT_CANCELED);
			finish();
		}
		if(Id==R.id.etYear)
		{
			etMonth.setSelected(false);
			etMonth.setSelection(0, 0);
			etDay.setSelected(false);
			etDay.setSelection(0, 0);
		}
		if(Id==R.id.etMonth)
		{
			etYear.setSelection(0, 0);
			etDay.setSelection(0, 0);
		}
		if(Id==R.id.etDay)
		{
			etYear.setSelection(0, 0);
			etMonth.setSelection(0, 0);
		}
	}
	
	protected void onDestroy()
	{
		super.onDestroy();
		etYear=null;
		etMonth=null;
		etDay=null;
		rgDayType=null;
		
		System.gc();
	}
}
