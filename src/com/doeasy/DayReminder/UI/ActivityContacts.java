package com.doeasy.DayReminder.UI;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doeasy.DayReminder.R;

public class ActivityContacts extends Activity {


	String[] contactsName;		//声明用于存放联系人姓名的数组
	String[] contactsPhone;		//声明用于存放联系人电话的数组
	
	int[] groupsId;
	String[] groupsTitle;
	Spinner sp;
	
	Button btnSearch=null;
	EditText etCName=null;
	ListView lv;	 					//声明ListView对象
	ContentResolver cr ;
	
	BaseAdapter baContacts = new BaseAdapter(){
		@Override
		public int getCount() {
			if(contactsName != null){		//如果姓名数组不为空
				return contactsName.length;
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
			LayoutInflater inflater = getLayoutInflater();  
			view = inflater.inflate(R.layout.li_contact, null);
			TextView tvRName=(TextView)view.findViewById(R.id.tvRealName);
			TextView tvTelPhone=(TextView)view.findViewById(R.id.tvTelPhone);
			tvRName.setText(contactsName[position]);
			tvTelPhone.setText(contactsPhone[position]);
			return view;
		}
	};
	
	BaseAdapter baGroups = new BaseAdapter(){
		
		@Override
		public int getCount() {
			if(groupsId != null){		
				return groupsId.length;
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
			//LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			LayoutInflater inflater = getLayoutInflater();  
			view = inflater.inflate(R.layout.li_spinner, null);
			TextView tvGroupId=(TextView)view.findViewById(R.id.tvspId);
			tvGroupId.setVisibility(View.GONE);
			TextView tvGroupName=(TextView)view.findViewById(R.id.tvspName);
			tvGroupId.setText(String.valueOf(groupsId[position]));
			tvGroupName.setText(groupsTitle[position]);
			return view;
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lo_contacts);
        
        cr = getContentResolver(); 
        
        sp=(Spinner)this.findViewById(R.id.spGroupName);
        sp.setAdapter(baGroups);
        
        sp.setOnItemSelectedListener(
           new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				TextView tvgId=(TextView)arg1.findViewById(R.id.tvspId);
				QueryContactsInfo(Integer.parseInt(tvgId.getText().toString()));
				baContacts.notifyDataSetChanged();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }        	   
           }
        );
        etCName=(EditText)this.findViewById(R.id.etCName);
        btnSearch=(Button)this.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(buttonListener);
        lv=(ListView)this.findViewById(R.id.lvContacts); 
        lv.setDividerHeight(0);
        lv.setAdapter(baContacts);
        lv.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
        		//设置返回值
        		Intent intented =getIntent();
				Bundle bred=new Bundle();
				bred.putString("RealName", contactsName[position]);
				bred.putString("TelPhone", contactsPhone[position]);
				intented.putExtras(bred);
				setResult(RESULT_OK, intented);
				finish();
			}
		});
    }
    @Override
	protected void onResume() {
    	QueryGroupsInfo();
    	baGroups.notifyDataSetChanged();
    	QueryContactsInfo(0);
		baContacts.notifyDataSetChanged();
		super.onResume();
	}
    private Button.OnClickListener buttonListener = new Button.OnClickListener()
    {
	    public void onClick(View v) 
	    {
	    	
			if(v == btnSearch)
			{
				QueryContactsInfo(-9999);
				baContacts.notifyDataSetChanged();
			}
		}
	};
    private void QueryGroupsInfo(){
    	     
	    String[] GROUPS_PROJECTION = new String[] { "_id","title"};//
        Cursor cgroups = cr.query(ContactsContract.Groups.CONTENT_URI, GROUPS_PROJECTION,null, null, "_id ASC");
    	int count=cgroups.getCount()+1;
    	groupsId=new int[count];
    	groupsTitle=new String[count];
    	int i=1;
    	groupsId[0]=0;
    	groupsTitle[0]="全部";
    	int iId=-1;
    	String gTitle="";
    	while(cgroups.moveToNext()) {
    		iId=cgroups.getInt(0);
    		gTitle=cgroups.getString(1);
    		if(gTitle!=null)
    		{
    			groupsId[i]=iId;
    			groupsTitle[i]=gTitle;
    			i++;
    		}
    		gTitle=null;
    	}
    	gTitle=null;
    	cgroups.close();
    	GROUPS_PROJECTION=null;
    	cgroups=null;
    }
    
	private void QueryContactsInfo(int GroupId) {  
        int i=0;   
	    String[] PHONES_PROJECTION = new String[] { "_id","display_name","data1","data3"};//
        Cursor cphone;
       if(GroupId==-9999)
       {
    	   String scName=etCName.getText().toString().trim();
    	   if(scName.length()==0)
    	   {
    		   Toast.makeText(ActivityContacts.this, "请输入联系人姓名", Toast.LENGTH_SHORT).show();
    		   return;
    	   }
    	    cphone=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION,"display_name like '%"+scName+"%'", null, "_id ASC");
	       	int iCount=cphone.getCount();
	   	    contactsName=new String[iCount];
	   	    contactsPhone=new String[iCount];
	   	    String strName="";
	   	    String strPhone="";
	   	    while(cphone.moveToNext()) {
	   		    strName = cphone.getString(1);
	   		    strPhone = cphone.getString(2);
	   	     	contactsName[i]=strName;
	   	       	contactsPhone[i]=strPhone;
	   		    i++;
	   	    }
	   	    strName=null;
		    strPhone=null;
		    cphone.close();
       }
       else if(GroupId==0)
        {
        	cphone=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION,null, null, "_id ASC");
        	int iCount=cphone.getCount();
    	    contactsName=new String[iCount];
    	    contactsPhone=new String[iCount];
    	    String strName="";
    	    String strPhone="";
    	    while(cphone.moveToNext()) {
    		    strName = cphone.getString(1);
    		    strPhone = cphone.getString(2);
    	     	contactsName[i]=strName;
    	       	contactsPhone[i]=strPhone;
    		    i++;
    	    }
    	    strName=null;
		    strPhone=null;
    	    cphone.close();
        }
        else
        {
        	String[] RAW_PROJECTION = new String[]{ ContactsContract.Data.RAW_CONTACT_ID,};
        	String RAW_GROUP_WHERE = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID +"=?" +" and " + ContactsContract.Data.MIMETYPE+ "=" + "'" + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "'" ;
        	Cursor cmMRawIds =cr.query(ContactsContract.Data.CONTENT_URI, RAW_PROJECTION,RAW_GROUP_WHERE,  new String[]{""+String.valueOf(GroupId)},  "data1 asc"); 
        	int imCount=cmMRawIds.getCount();
        	String[] cName=new String[imCount*3];
        	String[] cPhone=new String[imCount*3];
        	i=0;
        	String RAW_PHONE_WHERE = ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"=?" +" and " + ContactsContract.Data.MIMETYPE+ "=" + "'" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'" ;
        	String strName="";
    	    String strPhone="";
    	    String cContactId="";
        	while(cmMRawIds.moveToNext()) {
        		cContactId=String.valueOf(cmMRawIds.getInt(0));
        		cphone=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION,RAW_PHONE_WHERE, new String[]{""+cContactId}, "_id ASC");
        		while(cphone.moveToNext()) {
        		    strName = cphone.getString(1);
        		    strPhone = cphone.getString(2);
        		    cName[i]=strName;
        		    cPhone[i]=strPhone;  
        		    i++;
        	    }
        	    cphone.close();
        	}
        	strName=null;
		    strPhone=null;
        	cmMRawIds.close();
        	contactsName=new String[i];
     	    contactsPhone=new String[i];
        	for(int j=0;j<i;j++)
        	{
        		contactsName[j]=cName[j];
    	       	contactsPhone[j]=cPhone[j];
        	}
        	cName=null;
        	cPhone=null;
	    }
        cphone=null;
        PHONES_PROJECTION=null;
    }
	protected void onDestroy()
	{
		super.onDestroy();
		contactsName=null;		//�������ڴ����ϵ�����������
		contactsPhone=null;	//�������ڴ����ϵ�˵绰������
		
		groupsId=null;
		groupsTitle=null;
		
		sp=null;
		lv=null;	 					//����ListView����
		cr=null;
		
		baContacts=null;
		baGroups=null;
		System.gc();
	}
}