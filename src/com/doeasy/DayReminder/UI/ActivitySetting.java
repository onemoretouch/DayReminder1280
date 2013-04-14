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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.DrInfoDB;

@SuppressLint("SdCardPath")
public class ActivitySetting extends PreferenceActivity implements OnPreferenceChangeListener,   
OnPreferenceClickListener {
	
	final int DIALOG_REMINDTIME=304;				//ʱ��ѡ��Ի���id	
	final int DIALOG_UPDATEREMINDTIME=305;
	final int SetPwd_Request_Code=201;
	final int Bell_Request_Code=202;
	final int Restore_Request_Code=203;
	final int RestoreForM8_Request_Code=204;
	final String bakPath="/sdcard/DrInfo/Backup";
	private String sBell="/sdcard";
    SharedPreferences spSettings; 
    SharedPreferences.Editor spEditor ;
    Preference pfRemindTime;
    Preference pfPwd;
    ListPreference lpAfDays;
    ListPreference lpApDays;
    Preference pfBell;
    Preference pfUpdateRemind;
    Preference pfBackup;
    Preference pfRestore;
    //Preference pfRestoreForM8;
    
    int iAfDays=1;
    int iApDays=10;
    int rmTime = 750;
    String sPassWord="200938";
    
    clsCalendar cc=null;
	DrInfoDB dDb=null;
	
	@SuppressLint("WorldWriteableFiles")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.lo_setting);
		
		addPreferencesFromResource(R.xml.preference_setting);
		String prefsName = getPackageName() + "_preferences";  
		spSettings = getSharedPreferences (prefsName,Context.MODE_WORLD_WRITEABLE);
		spEditor= spSettings.edit();
		pfPwd=findPreference("pfPwd");
		pfPwd.setOnPreferenceClickListener(this);
		lpAfDays=(ListPreference)findPreference("lpAfDays");
		lpAfDays.setOnPreferenceChangeListener(this);
		lpApDays=(ListPreference)findPreference("lpApDays");
		lpApDays.setOnPreferenceChangeListener(this);
		pfRemindTime=findPreference("pfRemindTime");
		pfRemindTime.setOnPreferenceClickListener(this);
		pfBell=(Preference)findPreference("pfBell");
		pfBell.setOnPreferenceClickListener(this);
		
		pfUpdateRemind=findPreference("pfUpdateRemind");
		pfUpdateRemind.setOnPreferenceClickListener(this);
		pfBackup=findPreference("pfBackup");
		pfBackup.setOnPreferenceClickListener(this);
		pfRestore=findPreference("pfRestore");
		pfRestore.setOnPreferenceClickListener(this);
		//pfRestoreForM8=findPreference("pfRestoreForM8");
		//pfRestoreForM8.setOnPreferenceClickListener(this);
		InitPreference();
		dDb=new DrInfoDB(this);
	}
	private void InitPreference()
	{
		sPassWord=spSettings.getString("pfSetPwd", "200938"); 
		iAfDays=Integer.parseInt(lpAfDays.getValue());
		iApDays=Integer.parseInt(lpApDays.getValue());
		String sPwdSummary="";
		for(int i=0;i<sPassWord.length();i++)
		{
			sPwdSummary+="*";
		}	
		pfPwd.setSummary(sPwdSummary);
		lpAfDays.setSummary(String.format("提前%2d天", iAfDays));
		rmTime=spSettings.getInt("pfRemindTime", 750); 
		pfRemindTime.setSummary(String.format("%02d:%02d",rmTime/60,rmTime%60));
		String tBell=spSettings.getString("pfBell", "");
		if(!tBell.equals(""))
		{
			sBell=tBell;
		}
		pfBell.setSummary(sBell);
		
		lpApDays.setSummary(String.format("提前%2d天", iApDays));
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		String pfKey=preference.getKey();
		if(pfKey.equals("lpAfDays"))
		{
			lpAfDays.setValue(newValue.toString());
			spEditor.commit();
			iAfDays=Integer.parseInt(newValue.toString());
			lpAfDays.setSummary(String.format("提前%2d天", iAfDays));
		}
		if(pfKey.equals("lpApDays"))
		{
			lpApDays.setValue(newValue.toString());
			spEditor.commit();
			iApDays=Integer.parseInt(newValue.toString());
			lpApDays.setSummary(String.format("提前%2d天", iApDays));
		}
		return false;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onPreferenceClick(Preference preference) {
		String pfKey=preference.getKey();
		if(pfKey.equals("pfRemindTime"))
		{
			showDialog(DIALOG_REMINDTIME);
		}
		if(pfKey.equals("pfPwd"))
		{
			Intent intent = new Intent(ActivitySetting.this,ActivitySetPassWord.class);
			startActivityForResult(intent,SetPwd_Request_Code);
			intent=null;		
		}
		if(pfKey.equals("pfUpdateRemind"))
		{
			showCustomUpdateDialog();
			//showDialog(DIALOG_UPDATEREMINDTIME);
		}
		if(pfKey.equals("pfBell"))
		{
			/*
			ArrayList<String> filterList = new ArrayList<String>();
			filterList.add("mp3");
			filterList.add("wav");
			Intent intent = new Intent();
			intent.setAction("com.meizu.action.CHOOSE_SINGLE_FILE");
			intent.putExtra("init_directory", "/sdcard/Music");
			*/
			/*
			Intent intent = new Intent();

			intent.setType("sdcard/*");

			intent.setAction(Intent.ACTION_GET_CONTENT);
			*/
			try
		     {
				Intent intent= new Intent(this,ActivityBrowser.class);
				String sTmp="/sdcard";
				if(sBell.length()>0&&!sBell.equals(sTmp))
				{
					int iPos=sBell.lastIndexOf("/");
					sTmp=sBell.substring(0, iPos);
					
				}
				intent.putExtra("Sp",sTmp);
				intent.putExtra("Sd", false);
				intent.putExtra("FEName", ".mp3,.wav");
				startActivityForResult(intent,Bell_Request_Code);
			
		     
				//startActivityForResult(intent, Bell_Request_Code);
		     } 
		     catch (android.content.ActivityNotFoundException ex) 
		     {
				Toast.makeText(ActivitySetting.this, "选择文件失败："+ex.getMessage(), Toast.LENGTH_SHORT).show();
		     }	
		     
		}
		if(pfKey.equals("pfBackup"))
		{
			pfBackup.setSummary(getResources().getString(R.string.txt_Backuping));
			File destDir = new File(bakPath);
    		if (!destDir.exists())
    		{
    			destDir.mkdirs();
    		}
    		Date dt=new Date();
    		String sFi=String.format("DrInfo_%d%02d%02d%02d%02d%02d.xml", dt.getYear()+1900,dt.getMonth()+1,dt.getDate(),dt.getHours(),dt.getMinutes(),dt.getSeconds());
    		sFi=bakPath+"/"+sFi;
    		if(DrBackUp(sFi))
    		{
    			pfBackup.setSummary(sFi);
				dDb.updateConfig("Backup", sFi);
				Toast.makeText(ActivitySetting.this, "数据备份成功", Toast.LENGTH_SHORT).show();
    		}
		}
		if(pfKey.equals("pfRestore"))
		{
			/*
			ArrayList<String> filterList = new ArrayList<String>();
			filterList.add("xml");
			Intent intent = new Intent();
			intent.setAction("com.meizu.action.CHOOSE_SINGLE_FILE");
			intent.putExtra("init_directory", bakPath);
			intent.putStringArrayListExtra("file_filter", filterList);
			*/
			try
			{
				Intent intent= new Intent(this,ActivityBrowser.class);
				intent.putExtra("Sp",bakPath);
				intent.putExtra("Sd", true);
				intent.putExtra("FEName", ".xml");
				startActivityForResult(intent, Restore_Request_Code);
			} 
			catch (android.content.ActivityNotFoundException ex) 
			{
				Toast.makeText(ActivitySetting.this, "选择文件失败："+ex.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		if(pfKey.equals("pfRestoreForM8"))
		{
			ArrayList<String> filterList = new ArrayList<String>();
			filterList.add("csv");
			Intent intent = new Intent();
			intent.setAction("com.meizu.action.CHOOSE_SINGLE_FILE");
			intent.putExtra("init_directory", bakPath);
			intent.putStringArrayListExtra("file_filter", filterList);
			try
			{
				startActivityForResult(intent, RestoreForM8_Request_Code);
			} 
			catch (android.content.ActivityNotFoundException ex) 
			{
				Toast.makeText(ActivitySetting.this, "选择文件失败："+ex.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		return false; 
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch(id){			//�ԶԻ���ID�����ж�
		
		case DIALOG_REMINDTIME://���ʱ��Ի���Ĵ���
			
			dialog = new TimePickerDialog(				//����TimePickerDialog����
				this,
				new TimePickerDialog.OnTimeSetListener(){ //����OnTimeSetListener������
						@Override
						public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
							rmTime=hourOfDay*60+minute;
							spEditor.putInt("pfRemindTime", rmTime);
							spEditor.commit();
							pfRemindTime.setSummary(String.format("%02d:%02d",hourOfDay,minute));
						    //ctvRemindTime.setValue(String.format("%02d:%02d",hourOfDay,minute));
						}    				 
				 },
				 rmTime/60,		//���뵱ǰСʱ��
				 rmTime%60,			//���뵱ǰ������
				 true
			  );
  		  break;
		case DIALOG_UPDATEREMINDTIME:		//����ɾ��ȷ�϶Ի���
			Builder b = new AlertDialog.Builder(this);	
			b.setIcon(R.drawable.icon_warn);
			b.setMessage(R.string.dialog_setremindtime_message);		//���öԻ�������
			b.setPositiveButton(
					R.string.button_ok,
					new android.content.DialogInterface.OnClickListener() {				//����ȷ��ɾ��ť
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(dDb.updateDrInfo(iApDays,iAfDays,rmTime))
							{
								ReturnMain();
							}
						}
					});
			b.setNegativeButton(
					R.string.button_cancel,
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {}
					});
			dialog = b.create();
			b=null;
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
		((TextView) window.findViewById(R.id.dialog_message)).setText(R.string.dialog_setremindtime_message); 
		((Button) window.findViewById(R.id.dialog_ok)).setOnClickListener(new OnClickListener() {
			@Override                    
			public void onClick(View v) {                        
				// write your code to do things after users clicks OK                        
				if(dDb.updateDrInfo(iApDays,iAfDays,rmTime))
				{
					ReturnMain();
				}
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
	
	public void  onActivityResult(int requestCode, int resultCode, Intent data) {  

        super.onActivityResult(requestCode, resultCode, data); 
        //ȡ�ý��
        if(requestCode==SetPwd_Request_Code)
        {
        	
        	if(resultCode==RESULT_OK)
        	{
        		Bundle bred=data.getExtras();
        		if(bred!=null)
        		{
        			sPassWord=bred.getString("PassWord");
        			String sPwdSummary="";
        			for(int i=0;i<sPassWord.length();i++)
        			{
        				sPwdSummary+="*";
        			}
        			pfPwd.setSummary(sPwdSummary);
        			spEditor.putString("pfPwd", sPassWord);
					spEditor.commit();
        		}
        	}
        }
        if(requestCode==Bell_Request_Code)
        {
        	
        	if(resultCode==RESULT_OK)
        	{
        		Bundle b=data.getExtras();
        		sBell=b.getString("FC");
        		//String sFp=data.getData().getPath();
        		//sBell=sFp;
        		pfBell.setSummary(sBell);
        		spEditor.putString("pfBell", sBell);
				spEditor.commit();
        	}
        }
        if(requestCode==Restore_Request_Code)
        {
        	if(resultCode==RESULT_OK)
        	{
        		//String sFp=data.getData().getPath();
        		Bundle b=data.getExtras();
        		String sFp=b.getString("FC");
        		if(ReadXML(sFp))
        		{
        			dDb.updateNextDate();
        			ReturnMain();
        		}
        	}
        }
        if(requestCode==RestoreForM8_Request_Code)
        {
        	
        	if(resultCode==RESULT_OK)
        	{
        		String sFp=data.getData().getPath();
        		if(RestoreFromM8(sFp))
        		{
        			dDb.updateNextDate();
        			ReturnMain();
        		}
        	}
        }
	} 
	public boolean DrBackUp(String path)
	  {
		  try
		  {
			  File f = new File(path);
			  FileOutputStream fileOS=new FileOutputStream(f); 
			  fileOS.write(dDb.WriteXml().getBytes());
			  fileOS.close();
			  f=null;
		  }
		  catch(FileNotFoundException e)
		  {
			  Toast.makeText(this, "备份失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			  return false;
		  }
		  catch(IOException e)
		  {
			  Toast.makeText(this, "备份失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
			  return false;
		  }
		  return true;
	  }

	  private boolean ReadXML(String path)  
	  {  
		  DocumentBuilderFactory docBuilderFactory = null;  
		  DocumentBuilder docBuilder = null;  
		  Document doc = null;
		  boolean bIsSuccess=false;
		  String[] abName=new String[]{"DrId","DrType","DrPhoto","DrName","TelPhone","DrYear","DrMonth","DrDate","DayType","DrSolar","DrLunar","RmDateType","NextDate","IsAppWidget","ApDays","RemindType","AfDays","RemindTime","IsAutoSMS","SMSText","DrRemark"};
		  try 
		  {  
			  dDb.delAllDrInfo();
			  docBuilderFactory = DocumentBuilderFactory.newInstance();  
			  docBuilder = docBuilderFactory.newDocumentBuilder();  
			  File f = new File(path);
			  doc = docBuilder.parse(f);
			  //root element  
			  Element root = doc.getDocumentElement();  
			  NodeList nodeList = root.getElementsByTagName("DrInfo"); 
			  ContentValues values = new ContentValues();			//��ʶ;
			  String[] abValue=new String[21];
			  Node ndDr=null;
			  NamedNodeMap nnm=null;
			  for(int i =0;i< nodeList.getLength();i++)  
			  {  

				  ndDr = nodeList.item(i);  
				  nnm= ndDr.getAttributes();
				  //�ж����ϰ滹���°�
				  Node nTmp1= nnm.getNamedItem("IsRemind");
				  if(nTmp1!=null)
				  {
					  abName[11]="RemindType";
					  abName[15]="IsRemind";
				  }
				  //ȡ�����
				  for(int j=0;j<21;j++)
				  {
						Node nDrId= nnm.getNamedItem(abName[j]);
						if(nDrId==null)
						{
							abValue[j]="";
						}
						else
						{
							abValue[j]=nDrId.getNodeValue();
						}
				  }
				  Node nTmp2= nnm.getNamedItem("IsAppWidget");
				  if(nTmp2==null)
				  {
					  abValue[13]="1";
					  abValue[14]="30";
				  }
				  values.put(drDrType,Integer.parseInt(abValue[1]));
				  values.put(drPHOTO, abValue[2]);
				  values.put(drNAME, abValue[3]);
				  values.put(drPHONE, abValue[4]);
				  values.put(drDayYear, Integer.parseInt(abValue[5]));
				  values.put(drDayMonth, Integer.parseInt(abValue[6]));	
				  values.put(drDayDay, Integer.parseInt(abValue[7]));
				  values.put(drDayType, Integer.parseInt(abValue[8]));
				  values.put(drSolarDay, abValue[9]);
				  values.put(drLunarDay, abValue[10]);
				  values.put(drRmDateType, Integer.parseInt(abValue[11]));
				  values.put(drDateOfNext,abValue[12]);
				  values.put(drIsAppWidget, abValue[13]);
				  values.put(drADayOfAppWidget, abValue[14]);
				  values.put(drRemindType, Integer.parseInt(abValue[15]));
				  values.put(drADayOfRemind, Integer.parseInt(abValue[16]));
				  values.put(drRemindTime, Integer.parseInt(abValue[17]));
				  values.put(drIsAutoSMS, Integer.parseInt(abValue[18]));
				  values.put(drSMSText, abValue[19]);
				  values.put(drRemark, abValue[20]);
				  values.put(drInitials, "");
				  dDb.saveDrInfo(0, values);	//�������
				  values.clear();
			  } 
			  bIsSuccess=true;
			  Toast.makeText(this, "数据恢复成功", Toast.LENGTH_SHORT).show();
		  } 
		  catch (IOException e) 
		  {  
			  Toast.makeText(this, "恢复失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
		  } 
		  catch (SAXException e) 
		  {  
			  Toast.makeText(this, "恢复失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
		  } 
		  catch (ParserConfigurationException e) 
		  {  
			  Toast.makeText(this, "恢复失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
		  } 
		  finally 
		  {  
			  doc = null;  
			  docBuilder = null;  
			  docBuilderFactory = null;  
		  }  
		  return bIsSuccess;
	  } 
	  @SuppressWarnings("resource")
	private boolean RestoreFromM8(String path)
	  {
		  boolean bIsSuccess=false;
		  try 
		  {
			  
			  dDb.delAllDrInfo();
			  // ȡ���ı��ļ�
			  File file = new File(path);
			  FileInputStream fileIS = new FileInputStream(file);
		      InputStreamReader   fileISR   =   new   InputStreamReader(fileIS,"UTF-8");
		      BufferedReader br = new BufferedReader(fileISR);
		      String line=br.readLine();
		      cc=new clsCalendar();
		    //"1,樊相奎,1979,9,4,1,13980460308"
		      String[] DrValue=null;
		      int iGroupNo=0;
	    	  int iDrType;
	    	  ContentValues values = new ContentValues();			//��ʶ;
	    	  String sDrName="";
	    	  int iYear=0;
	    	  int iMonth=0;
	    	  int iDay=0;
	    	  int isl=0;
	    	  int iDayType=0;
	    	  String sTelPhone=null;
		      while ((line = br.readLine()) != null) 
		      {
		    	  DrValue=line.split(",");
		    	  iGroupNo=Integer.parseInt(DrValue[0]);
		    	  iDrType=0;
		    	  if(iGroupNo==-999)
		    	  {
		    		  continue;
		    	  }
		    	  else if(iGroupNo==-99)
		    	  {
		    		  iDrType=2;
		    	  }
		    	  else
		    	  {
		    		  iDrType=1;
		    	  }
		    	  sDrName=DrValue[1];
		    	  iYear=Integer.parseInt(DrValue[2]);
		    	  iMonth=Integer.parseInt(DrValue[3]);
		    	  iDay=Integer.parseInt(DrValue[4]);
		    	  isl=Integer.parseInt(DrValue[5]);
		    	  iDayType=0;
		    	  if(isl==1)
		    	  {
		    		  iDayType=2;
		    	  }
		    	  else
		    	  {
		    		  iDayType=1;
		    	  }
		    	  sTelPhone=DrValue[6];
		    	  
		    	  if(!cc.SetDay(iYear, iMonth, iDay, iDayType))
		    	  {
		    		  continue;
		    	  }
		    	  
				  values.put(drNAME, sDrName);
				  values.put(drPHOTO, "");
				  values.put(drPHONE, sTelPhone);
				  values.put(drDrType,iDrType);
				  values.put(drDayYear, iYear);
				  values.put(drDayMonth, iMonth);	
				  values.put(drDayDay, iDay);
				  values.put(drDayType, iDayType);
				  values.put(drSolarDay, cc.GetSolarString());
				  values.put(drLunarDay, cc.GetLunarString());
				  values.put(drRmDateType, iDayType);
				  values.put(drDateOfNext,cc.GetNextDateSolar(iDayType).getTime());
				  values.put(drIsAppWidget, 1);
				  values.put(drADayOfAppWidget, 30);
				  values.put(drRemindType, 1);
				  values.put(drADayOfRemind, 1);
				  values.put(drRemindTime, 750);
				  values.put(drIsAutoSMS, 0);
				  values.put(drSMSText, "");
				  values.put(drRemark, "");
				  values.put(drInitials, "");
				 boolean b= dDb.saveDrInfo(0, values);	//�������
				 if(b)
				 {
					 bIsSuccess = true;
				 }
				 values.clear();
		      }
		      values=null;
		      Toast.makeText(this, "数据恢复成功", Toast.LENGTH_SHORT).show();
		  }
		  catch(java.lang.Exception e)
		  {
			// 增加异常处理
			  Toast.makeText(this, "恢复失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
		  }
		  return bIsSuccess;
	  }
	  private void ReturnMain()
		{
			setResult(RESULT_OK);
			finish();
		}
	  public void onDestory()
	  {
		  	super.onDestroy();
			cc=null;
			dDb=null;
		  System.gc();
	  }
}