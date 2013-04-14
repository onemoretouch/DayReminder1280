package com.doeasy.DayReminder.UI;

import java.io.File;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.UserControl.PartScreen;
import com.doeasy.DayReminder.UserControl.ViewScroll;

public class ActivityAvatarHandle extends Activity implements OnClickListener {
	
	ImageView ivAvator=null;
	ImageView ivPanel=null;
	@SuppressWarnings("unused")
	private RelativeLayout rlAvatarHandle;
	private LinearLayout.LayoutParams parm;
	private LinearLayout llPanel;
	private ViewScroll detail;
	int iFlag=0;
	final int iFile_Request_Code=401;
	final int iCamera_Request_Code=402;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.lo_avatarhandle);
        rlAvatarHandle=(RelativeLayout)this.findViewById(R.id.rlAvatarHandle);
        llPanel=(LinearLayout)this.findViewById(R.id.llPanel);
        View vOK=this.findViewById(R.id.btnOK);
        vOK.setOnClickListener(this);
        View vCancel=this.findViewById(R.id.btnCancel);
        vCancel.setOnClickListener(this);
        View vRotate=this.findViewById(R.id.btnRotate);
        vRotate.setOnClickListener(this);
        View vEnlarge=this.findViewById(R.id.btnEnlarge);
        vEnlarge.setOnClickListener(this);
        View vNarrow=this.findViewById(R.id.btnNarrow);
        vNarrow.setOnClickListener(this);
       
        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        
        if(b==null)
        {
        	iFlag=1;
        }
        else
        {
        	iFlag=i.getExtras().getInt("Flag");
        }
        if(iFlag==2)
		{
			Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(it, iCamera_Request_Code);
		}
		if(iFlag==1)
		{
			Intent localIntent1 = new Intent();
            localIntent1.setType("image/*");
            localIntent1.setAction("android.intent.action.GET_CONTENT");
            Intent localIntent2 = Intent.createChooser(localIntent1, "Select Picture");
            startActivityForResult(localIntent2, iFile_Request_Code);
		}
		
    }

	@SuppressLint({ "SdCardPath", "SdCardPath" })
	@SuppressWarnings("unused")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int vId=v.getId();
		if(vId==R.id.btnEnlarge)
		{
			if(detail!=null)
			{
				detail.setScale(3);
			}
		}
		if(vId==R.id.btnNarrow)
		{
			if(detail!=null)
			{
				detail.setScale(4);
			}
		}
		if(vId==R.id.btnRotate)
		{
			if(detail!=null)
			{
				detail.setRotate();
			}
		}
		if(vId==R.id.btnOK)
		{
			Date dt=new Date();
    		String sFi=String.format("DrAvatar_%d%02d%02d%02d%02d%02d.jpg", dt.getYear()+1900,dt.getMonth()+1,dt.getDate(),dt.getHours(),dt.getMinutes(),dt.getSeconds());
			if (android.os.Environment.MEDIA_MOUNTED != "mounted")
			{ 
				sFi="/data/data/"+getPackageName()+"/"+sFi;
	        } 
			else
			{ 
				File d=new File("/sdcard/DrInfo/Avator/");
				if(!d.exists())
				{
					d.mkdirs();
				}
				sFi="/sdcard/DrInfo/Avator/"+sFi;
	        } 
			if(PartScreen.Save(getWindow().getDecorView(),sFi))
			{
				Intent intented =getIntent();
				Bundle bred=new Bundle();
				bred.putString("File", sFi);
				intented.putExtras(bred);
				setResult(RESULT_OK,intented);
				finish();
			}
		}
	}
	public void  onActivityResult(int requestCode, int resultCode, Intent data) {  

        super.onActivityResult(requestCode, resultCode, data); 
        Bitmap bit=null;
        if(requestCode==iFile_Request_Code)
        {
        	if(resultCode==RESULT_OK)
        	{
        		Uri uri = data.getData();

                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                String sPath=cursor.getString(1);
                BitmapFactory.Options options=new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.outWidth=640;
				options.outHeight=912;
                bit =BitmapFactory.decodeFile(sPath,options);
        	}
        }
        if(requestCode==iCamera_Request_Code)
        {
        	
        	if(resultCode==RESULT_OK)
        	{
        		Bundle extras = data.getExtras();
        		bit = (Bitmap) extras.get("data");
        	}
        }
        if(bit!=null)
        {
        	if(detail!=null)
        	{
        		llPanel.removeView(detail);
        	}
        	
    		detail = new ViewScroll(ActivityAvatarHandle.this, bit,llPanel);
    		llPanel.addView(detail,parm);
    		bit=null;
        }
	}
	public void onDestory()
	  {
		  	super.onDestroy();
			llPanel=null;
			detail.RecycleImg();
			detail=null;
		    System.gc();
	  }
}
