package com.doeasy.DayReminder.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doeasy.DayReminder.R;

public class ActivitySetPassWord extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	EditText etPwd1;
	EditText etPwd2;
	Button btnOK;
	Button btnCancel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_setpassword);
        etPwd1=(EditText)this.findViewById(R.id.etPwd1);
        etPwd2=(EditText)this.findViewById(R.id.etPwd2);
        
        btnOK=(Button)this.findViewById(R.id.btnOK);
		btnCancel=(Button)this.findViewById(R.id.btnCancel);
		
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
    }
    public void onClick(View v) 
	    {
	    	
			if(v == btnOK)
			{
				String sContent=getResources().getString(R.string.ss_ErrPwdDesp);
				try
				{
					String sPwd1=etPwd1.getText().toString();
					String sPwd2=etPwd2.getText().toString();
					if(!sPwd1.equals(sPwd2))
					{
						Toast.makeText(ActivitySetPassWord.this, sContent, Toast.LENGTH_SHORT).show();
						return;
					}
					((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
					Intent intented =getIntent();
					Bundle bred=new Bundle();
					bred.putString("PassWord", sPwd1);
					intented.putExtras(bred);
					setResult(RESULT_OK, intented);
					intented=null;
					bred=null;
					finish();
				}
				catch(java.lang.Exception e)
				{
					Toast.makeText(ActivitySetPassWord.this, sContent, Toast.LENGTH_SHORT).show();
				}
			}
			if(v == btnCancel)
			{
				ActivitySetPassWord.this.finish();
			}
		}
    
}