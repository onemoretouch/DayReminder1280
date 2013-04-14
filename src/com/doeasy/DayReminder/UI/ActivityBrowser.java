package com.doeasy.DayReminder.UI;

import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.doeasy.DayReminder.R;

public class ActivityBrowser extends Activity {
    /** Called when the activity is first created. */
	ListView lvCatalog=null;
	ListView lvFile=null;
	String[] catalogName=null;
	String[] fileName=null;
	
	String FileExtentName;
	private int iSedType=0;
	int[] fileType=null;
	private int iSed=-1;
	private int iBcCount=0;
	Button btnOK=null;
	Button btnCancel=null;
	private final String rootPath="/mnt/";
	private boolean IsSpecifiedDirectory=false;
	BaseAdapter baCatalog = new BaseAdapter(){
		@Override
		public int getCount() {
			if(catalogName != null){		//����������鲻Ϊ��
				return catalogName.length;
			}
			else {
				return 0;					//�����������Ϊ���򷵻�0
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
			view = inflater.inflate(R.layout.li_catalog, null);
			//RelativeLayout rlc=(RelativeLayout)view.findViewById(R.id.rlC);
			TextView tvCName=(TextView)view.findViewById(R.id.tvCName);
			if(position==iBcCount-1)
			{
				if(position==0)
				{
					tvCName.setBackgroundResource(R.drawable.bcsded);
					tvCName.setText("sdcard");
				}
				else
				{
					tvCName.setBackgroundResource(R.drawable.bcned);
					tvCName.setText(catalogName[position]);
				}
			}
			else
			{
				if(position==0)
				{
					tvCName.setBackgroundResource(R.drawable.xml_btn_sd);
					tvCName.setText("sdcard");
				}
				else
				{
					tvCName.setBackgroundResource(R.drawable.xml_btn_catalog);
					tvCName.setText(catalogName[position]);
				}
			}
			return view;
		}
	};
	
	BaseAdapter baFile = new BaseAdapter(){
		private int iPosition=-1;
		@Override
		public int getCount() {
			if(fileName != null){		//����������鲻Ϊ��
				return fileName.length;
			}
			else {
				return 0;					//�����������Ϊ���򷵻�0
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
			iPosition=position;
			View view = null;
			//LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			LayoutInflater inflater = getLayoutInflater();  
			view = inflater.inflate(R.layout.li_file, null);
			//view.setBackgroundResource(R.drawable.xmlist90);
			ImageView ivIcon=(ImageView)view.findViewById(R.id.ivIcon);
			if(fileType[position]==0)
			{
				ivIcon.setBackgroundResource(R.drawable.catalog);
			}
			else
			{
				ivIcon.setBackgroundResource(R.drawable.file);
			}
			
			TextView tvFName=(TextView)view.findViewById(R.id.tvFName);
			tvFName.setText(String.valueOf(fileName[position]));
			
			RadioButton rbSed=(RadioButton)view.findViewById(R.id.rbSed);
			
			if(iSedType!=fileType[position])
			{
				rbSed.setVisibility(View.GONE);
			}
			rbSed.setId(position); 
			
			view.setId(20000+position);
			view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int id=v.getId()-20000;
					if(!IsSpecifiedDirectory)
	        		{
						TextView tv=(TextView)v.findViewById(R.id.tvFName);
						if(fileType[id]==0)
						{
							String str=rootPath;
			        		for(int i=0;i<catalogName.length;i++)
			        		{
			        			str+=catalogName[i]+"/";
			        		}
			        		str+=tv.getText().toString();
			        		GetFiles(str,iPosition);
						}
						else {
							if(iSed != -1)
							{
	                            RadioButton tempButton = (RadioButton)ActivityBrowser.this.findViewById(iSed);
	                            if(tempButton != null)
	                            {
	                               tempButton.setChecked(false);
	                            }
	                        }
							
							RadioButton rButton = (RadioButton)ActivityBrowser.this.findViewById(id);
							rButton.setChecked(true);
							iSed=id;
						}
	        		}
					else
					{
						if(iSed != -1)
						{
                            RadioButton tempButton = (RadioButton)ActivityBrowser.this.findViewById(iSed);
                            if(tempButton != null)
                            {
                               tempButton.setChecked(false);
                            }
                        }
						
						RadioButton rButton = (RadioButton)ActivityBrowser.this.findViewById(id);
						rButton.setChecked(true);
						iSed=id;
					}
				}
			});
			
			 
			rbSed.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    
                    if(isChecked){
                        
                        if(iSed != -1){
                            RadioButton tempButton = (RadioButton)ActivityBrowser.this.findViewById(iSed);
                            if(tempButton != null){
                               tempButton.setChecked(false);
                            }
                        }
                        
                        iSed = buttonView.getId();
                        
                    }
                }
            });
			
            if(iSed == position){
            	rbSed.setChecked(true);
            }
			return view;
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lo_browser);
        lvCatalog=(ListView)this.findViewById(R.id.lvCatalog);
        lvCatalog.setDividerHeight(0);
        lvCatalog.setAdapter(baCatalog);
        lvCatalog.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvCatalog.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				//���÷���ֵ
        		if(!IsSpecifiedDirectory)
        		{
	        		TextView tv=(TextView)view.findViewById(R.id.tvCName);
	        		if(position==0)
	    			{
	        			tv.setBackgroundResource(R.drawable.bcsded);
	    			}
	    			else
	    			{
	    				tv.setBackgroundResource(R.drawable.bcned);
	    			}
	        		String str=rootPath;
	        		for(int i=0;i<=position;i++)
	        		{
	        			str+=catalogName[i]+"/";
	        		}
	        		GetFiles(str,-1);
        		}
			}
		});
        lvCatalog.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) 
			{
				if(!IsSpecifiedDirectory)
        		{
					// TODO Auto-generated method stub
					TextView tv=(TextView)view.findViewById(R.id.tvCName);
	        		if(position==0)
	    			{
	        			tv.setBackgroundResource(R.drawable.bcsded);
	    			}
	    			else
	    			{
	    				tv.setBackgroundResource(R.drawable.bcned);
	    			}
        		}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        lvFile=(ListView)this.findViewById(R.id.lvFileList);
        lvFile.setDividerHeight(0);
        lvFile.setAdapter(baFile);
        lvFile.setItemChecked(0, true);
        
        //lvFile.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvFile.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				//���÷���ֵ
        		if(!IsSpecifiedDirectory)
        		{
	        		String str=rootPath;
	        		for(int i=0;i<catalogName.length;i++)
	        		{
	        			str+=catalogName[i]+"/";
	        		}
	        		str+=fileName[position];
	        		view.setBackgroundColor(4);
	        		GetFiles(str,position);
        		}
			}
		});
        btnOK=(Button)this.findViewById(R.id.btnOK);
		btnCancel=(Button)this.findViewById(R.id.btnCancel);
		btnOK.setOnClickListener(buttonListener);
		btnCancel.setOnClickListener(buttonListener);
        FileExtentName=null;
        iSedType=0;
        String sTmp="/";
        Bundle baed=getIntent().getExtras();
        if(baed!=null)
        {
        	String path=baed.getString("Sp");
        	if(path!=null)
        	{
        		sTmp=path;
        		File destDir = new File(path);
        		if (!destDir.exists())
        		{
        			destDir.mkdirs();
        		}
        	}
        	boolean b=baed.getBoolean("Sd");
        	if(b)
        	{
        		IsSpecifiedDirectory=true;
        	}
        	else
        	{
        		IsSpecifiedDirectory=false;
        	}
	        String str=baed.getString("FEName");
	        if(str!=null)
	        {
	        	FileExtentName=str;
	        	iSedType=1;
	        }
	        
        }
        GetFiles(sTmp,-1);
        baCatalog.notifyDataSetChanged();
        baFile.notifyDataSetChanged();
    }
    private Button.OnClickListener buttonListener = new Button.OnClickListener()
    {
	    public void onClick(View v) 
	    {
	    	
			if(v == btnOK)
			{
				if(iSed==-1)
				{
					Toast.makeText(ActivityBrowser.this, "必须选中文件", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String sSed=rootPath;
					for(int i=0;i<catalogName.length;i++)
					{
						sSed+=catalogName[i]+"/";
					}
					sSed+=fileName[iSed];
					Intent intented =getIntent();
					Bundle bred=new Bundle();
					bred.putString("FC", sSed);
					intented.putExtras(bred);
					setResult(RESULT_OK, intented);
					intented=null;
					bred=null;
					finish();
				}
			}
			if(v == btnCancel)
			{
				finish();
			}
		}
	};
    private void GetFiles( String _FilePath,int iPostion)
    {
    	File filePath =new File(_FilePath);
    	if(!filePath.exists())
    	{
    		filePath=new File("/mnt/sdcard/");
    		_FilePath=filePath.getPath();
    	}
	    if(filePath.isDirectory() &&filePath.canRead())
	    {
	    	_FilePath=filePath.getPath();
	    	iSed=-1;
	    	catalogName=null;
		    fileName=null;
		    fileType=null;
		    _FilePath=_FilePath.replace(rootPath, "");
		    if(_FilePath.subSequence(0, 1).equals("/"))
		    {
		    	_FilePath=_FilePath.substring(1,_FilePath.length());
		    }
		    String[] sCTemp=_FilePath.split("/");
		    int iCount=sCTemp.length;
		    if(iCount==0)
		    {
		    	iCount++;
		    }
		    catalogName=new String[iCount];
		    for(int i=0;i<iCount;i++)
		    {
		    	catalogName[i]=sCTemp[i];
		    }
		    iBcCount=iCount;
		    File[] files = filePath.listFiles();
		    iCount=files.length;
		    int iFcCount=0;
		    int iFfCount=0;
		    String[] sFCTemp=new String[iCount];
		    String[] sFFTemp=new String[iCount];
		    String sString="";
		    if(files != null)
		    {
		    	for(int i=0; i < iCount; i++)
		    	{
		    		if(!files[i].isDirectory() && files[i].canRead())
		    		{ 
		    			sString=files[i].getName();
		    			if(FileExtentName!=null)
		    			{
		    				int iPos=sString.lastIndexOf(".");
		    				if(iPos!=-1)
		    				{
			    				String sFeName=sString.substring(iPos);
			    				if(FileExtentName.indexOf(sFeName)!=-1)
			    				{
			    					sFFTemp[iFfCount]=sString.trim();
			    					iFfCount++;
			    				}
		    				}
		    			}
		    		}
		    		else if(files[i].isDirectory() && files[i].canRead())
		    		{
		    			sFCTemp[iFcCount]=files[i].getName().trim();
		    			iFcCount++;
		    		}
		    	}
		    }
		    fileName=new String[iFcCount+iFfCount];
		    fileType=new int[iFcCount+iFfCount];
		    
		    String sTmp="";
		    int iPos=0;
		    for(int i=0;i<iFcCount;i++)
		    {
		    	iPos=i;
		    	sTmp=sFCTemp[iPos];
		    	for(int j=i+1;j<iFcCount;j++)
		    	{
		    		if(sFCTemp[j].compareTo(sTmp)<0)
		    		{
		    			iPos=j;
		    			sTmp=sFCTemp[j];
		    		}
		    	}
		    	sFCTemp[iPos]=sFCTemp[i];
		    	fileName[i]=sTmp;
		    	fileType[i]=0;
		    }
		    
		    for(int i=0;i<iFfCount;i++)
		    {
		    	iPos=i;
		    	sTmp=sFFTemp[iPos];
		    	for(int j=i+1;j<iFfCount;j++)
		    	{
		    		if(sFFTemp[j].compareTo(sTmp)<0)
		    		{
		    			iPos=j;
		    			sTmp=sFFTemp[j];
		    		}
		    	}
		    	sFFTemp[iPos]=sFFTemp[i];
		    	fileName[iFcCount+i]=sTmp;
		    	fileType[iFcCount+i]=1;
		    }
		    sTmp=null;
		    sFCTemp=null;
		    sFFTemp=null;
		    sCTemp=null;
		    baCatalog.notifyDataSetChanged();
		    lvCatalog.setItemChecked(lvCatalog.getCount()-1, true);
	        baFile.notifyDataSetChanged();
	    }
	    filePath=null;
	}
    protected void onDestroy()
	{
		super.onDestroy();
		lvCatalog=null;
		lvFile=null;
		catalogName=null;
		fileName=null;
		fileType=null;
		
		btnOK=null;
		btnCancel=null;
		
		baCatalog=null;
		baFile=null;
		
		System.gc();
	}
}