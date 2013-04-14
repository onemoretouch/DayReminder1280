package com.doeasy.DayReminder.UI;

import java.util.Date;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.doeasy.DayReminder.InitApp;
import com.doeasy.DayReminder.LicenseAppliction;
import com.doeasy.DayReminder.R;
import com.doeasy.DayReminder.R.color;
import com.doeasy.DayReminder.R.drawable;
import com.doeasy.DayReminder.R.layout;
import com.doeasy.DayReminder.clsCalendar;
import com.doeasy.DayReminder.DB.DBUtil;
import com.doeasy.DayReminder.DB.DrInfoDB;
import com.doeasy.DayReminder.PicView.AsyncImageLoader;
import com.doeasy.DayReminder.PicView.DrawableImageLoader;
import com.doeasy.DayReminder.Reminder.SetReminder;
import com.doeasy.DayReminder.UserControl.Panel;
import com.doeasy.DayReminder.UserControl.Panel.OnPanelListener;


public class ActivityMain extends Activity implements OnSeekBarChangeListener,OnClickListener
{
	private DrawableImageLoader dImageLoader = null;
	
	Panel pnFunc=null;
	LicenseAppliction pApplication=null;
	DrInfoDB dbHelper;
	clsCalendar cc;
	Date dToday;
	Button btnAll=null;
	Button btnBir=null;
	Button btnMem=null;
	Button btnHol=null;
	Button btnCal=null;
	//Button btnFunc=null;
	Button btnDel=null;
	Button btnSedAll=null;
	Button btnSearch=null;
	EditText etKey=null;
	View llBottom=null;
	SeekBar sbMonth=null;
	private String[][] DrInfo;
	boolean[] bChecked;
	private boolean bDisplayPop=false;
	private boolean bIsSedAll=false;
	int iChecked=0;
	int iCDrType=0;
	final int MENU_BDADD = Menu.FIRST;			//声明菜单选行的ID
	final int MENU_MDADD = Menu.FIRST+1;			//声明菜单选行的ID
	final int MENU_HDADD = Menu.FIRST+2;			//声明菜单选行的ID
	final int MENU_CALENDAR = Menu.FIRST+3;			//声明菜单选行的ID
	final int MENU_SET = Menu.FIRST+4;			//声明菜单选行的ID
	final int MENU_ABOUT = Menu.FIRST+5;			//声明菜单选行的ID
	final int MENU_EXIT = Menu.FIRST+6;			//声明菜单选行的ID
	final int MENU_DELETE = Menu.FIRST+7;		//声明菜单项的编号
	final int DIALOG_EXIT = 0;		//确认删除对话框的ID
	final int DIALOG_POPUPWINDOW = 1;
	final	 int DIALOG_DELETE=2;
	TextView tvTitle=null;
	TextView tvTitle1=null;
	ListView lvDrInfo=null;	 					//声明ListView对象
	ScrollView sv=null;
	int iModi_Request_Code=101;
	int iSet_Request_Code=102;
	private TextView overlay;
	
	InputMethodManager imm;
	private Animation animationTranslate;
	
	private boolean bSeekBarClick=false;
	private static class ViewCache
	{
		TextView tvDrName;
		TextView tvDrDesp;
		TextView tvNextDate;
		TextView tvAgoDays;
		TextView tvAftDays;
		ImageView drIv;
		CheckBox cb;
	}
	
	private LayoutInflater mInflater;// =  (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	BaseAdapter baDrInfo = new BaseAdapter(){
		
		@Override
		public int getCount() {
			if(DrInfo != null){		//如果姓名数组不为空
				return DrInfo[0].length;
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
			ViewCache holder; // 该容器用来存放控件对象
			 if (convertView == null) {
				 convertView = mInflater.inflate(R.layout.li_main, null);
				 holder = new ViewCache();
				 holder.tvDrName = (TextView) convertView.findViewById(R.id.tvDrName);
				 holder.tvDrDesp = (TextView) convertView.findViewById(R.id.tvDrDesp);
				 holder.tvNextDate = (TextView) convertView.findViewById(R.id.tvNextDate);
				 holder.tvAgoDays = (TextView) convertView.findViewById(R.id.tvAgoDays);
				 holder.tvAftDays = (TextView) convertView.findViewById(R.id.tvAftDays);
				 holder.drIv = (ImageView) convertView.findViewById(R.id.drIv);
				 //drIv = (ImageView) convertView.findViewById(R.id.drIv);
				 holder.cb = (CheckBox) convertView.findViewById(R.id.cbSed);
				 convertView.setTag(holder); // 将存放对象的容器存进视图对象中
			 } else {
				 holder = (ViewCache) convertView.getTag(); // 直接拿出控件对象
				 //drIv = (ImageView) convertView.findViewById(R.id.drIv);
			 }
			 holder.tvDrName.setText(DrInfo[1][position]);
			 holder.tvDrDesp.setText(DrInfo[2][position]);
			 holder.drIv.setImageResource(R.drawable.defaulttx);
			if(DrInfo[8][position].equals("3"))
			{
				holder.tvAgoDays.setVisibility(View.GONE);
				//holder.drIv.setTag(30000+position);
				holder.drIv.setImageResource(R.drawable.defaulthd);
				//drIv.setImageResource(R.drawable.defaulthd);
			}
			else
			{
				holder.tvAgoDays.setText(DrInfo[3][position]);
				holder.tvAgoDays.setVisibility(View.VISIBLE);
				//drIv.setTag(DrInfo[7][position]);
				//new AsyncViewTask().execute(drIv);//异步加载图片  
				//drIv.setDrawingCacheEnabled(true);
				setViewImage(holder.drIv,DrInfo[7][position]);
				holder.drIv.setDrawingCacheEnabled(true);
			}
			
			int iAfDays=Integer.parseInt(DrInfo[5][position]);
			holder.tvAftDays.setText(String.valueOf(iAfDays));
			
			if(iAfDays==0)
			{
				holder.tvAftDays.setTextColor(getResources().getColor(color.mlt1));
				holder.tvAftDays.setText("今天");
			}
			else if(iAfDays<10)
			{
				holder.tvAftDays.setTextColor(getResources().getColor(color.mlt2));
			}
			else if(iAfDays<30)
			{
				holder.tvAftDays.setTextColor(getResources().getColor(color.mlt3));
			}
			else
			{
				holder.tvAftDays.setTextColor(getResources().getColor(color.mlt4));
			}    		
       	
			holder.tvNextDate.setText(DrInfo[4][position]);
			
			convertView.setId(20000+position);
			convertView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					try {
						int ipos=v.getId()-20000;
						int iDrType=Integer.parseInt(DrInfo[8][ipos]);
						Intent intent=null;
						if(iDrType==3)
						{
							intent= new Intent(ActivityMain.this,ActivityHDrInfo.class);
						}
						else
						{
							intent= new Intent(ActivityMain.this,ActivityDrEdit.class);
							intent.putExtra("DrType", iDrType);
						}
						intent.putExtra("DrId", Integer.parseInt(DrInfo[0][ipos]));
						startActivityForResult(intent,iModi_Request_Code);
						ClosePanel();
						intent=null;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
			
			
			holder.cb.setChecked(bChecked[position]);
			holder.cb.setId(position);
			holder.cb.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						int position=v.getId();
		               	bChecked[position]=!bChecked[position];
		               	if(bChecked[position])
		               	{
		               		iChecked++;
		               	}
		               	else
		               	{
		               		iChecked--;
		               	}
		               	if(iChecked>0)
		               	{
		               		ShowBottom();
		               	}
		               	if(iChecked<=0)
		               	{
		               		HideBottom();
		               	}
		               	btnDel.setText(String.format("删除(%d)",iChecked));
		               	if(iChecked==lvDrInfo.getCount())
		               	{
		               		btnSedAll.setText(R.string.button_nosedall);
		               		bIsSedAll=true;
		               	}
		               	else
		               	{
		               		btnSedAll.setText(R.string.button_sedall);
		               		bIsSedAll=false;
		               	}
					} catch (Exception e) {
						// TODO: handle exception
					}
	               }	
				});
	           convertView.setOnTouchListener(touchListener);
	           return convertView;
		}
	};

	public void setViewImage(final ImageView v, String url) 
    {
		//如果只是单纯的把图片显示，而不进行缓存。直接用下面的方法拿到URL的Bitmap就行显示就OK
//      Bitmap bitmap = WebImageBuilder.returnBitMap(url);
//      ((ImageView) v).setImageBitmap(bitmap);
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		asyncImageLoader.loadDrawable(url, new AsyncImageLoader.ImageCallback() 
        {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) 
            {
                if(imageDrawable!=null && imageDrawable.getIntrinsicWidth()>0 ) 
                {
                    v.setImageDrawable(imageDrawable);
                }
                else
                {
                	v.setImageResource(R.drawable.defaulttx);
                }
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        InitApp initApp = new InitApp(this);
		initApp.SetApp(0);
		pApplication=(LicenseAppliction)getApplicationContext();
		dbHelper = pApplication.getDataHelper();
		dImageLoader = new DrawableImageLoader(this,2);
        setContentView(layout.lo_main);
        
        imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInflater =  (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        this.overlay = (TextView) View.inflate(this, R.layout.overlay, null);
		getWindowManager()
				.addView(
						overlay,
						new WindowManager.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT,
								-240,
								-400,
								WindowManager.LayoutParams.TYPE_APPLICATION,
								WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
										| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
								PixelFormat.TRANSLUCENT));
		
        llBottom=this.findViewById(R.id.llBottom);
		llBottom.setVisibility(View.GONE);
        
        tvTitle=(TextView)this.findViewById(R.id.tvTitle);
        tvTitle.setOnClickListener(this);
        tvTitle1=(TextView)this.findViewById(R.id.tvTitle1);
        tvTitle1.setOnClickListener(this);
        View v=this.findViewById(R.id.rlTmp);
        v.setBackgroundDrawable(dImageLoader.LoadImage(drawable.bg_main));
        //btnFunc=(Button)this.findViewById(R.id.btnFunc);
        //btnFunc.setOnClickListener(this);
        pnFunc=(Panel)this.findViewById(R.id.pnFunc);
        pnFunc.setOnPanelListener(new OnPanelListener(){

			@Override
			public void onPanelClosed(Panel panel) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPanelOpened(Panel panel) {
				// TODO Auto-generated method stub
				for(int i=0;i<bChecked.length;i++)
	    		 {
	    			 bChecked[i]=false;
	    		 }
	    		 baDrInfo.notifyDataSetChanged();
				HideBottom();
			}
        	
        });
        lvDrInfo=(ListView)this.findViewById(R.id.lvDrInfo); 
        lvDrInfo.setDividerHeight(0);
        lvDrInfo.setAdapter(baDrInfo);
        lvDrInfo.setOnTouchListener(touchListener);
       
        btnAll=(Button)this.findViewById(R.id.btnAll);
        btnAll.setBackgroundResource(R.drawable.bg_mainbutton_down);
        btnBir=(Button)this.findViewById(R.id.btnBir);
		btnMem=(Button)this.findViewById(R.id.btnMem);
		btnHol=(Button)this.findViewById(R.id.btnHol);
		btnCal=(Button)this.findViewById(R.id.btnCal);
		btnSedAll=(Button)this.findViewById(R.id.btnSedAll);
		btnDel=(Button)this.findViewById(R.id.btnDel);
		btnSearch=(Button)this.findViewById(R.id.btnSearch);
		btnAll.setOnClickListener(this);
		btnBir.setOnClickListener(this);
		btnMem.setOnClickListener(this);
		btnHol.setOnClickListener(this);
		btnCal.setOnClickListener(this);
		etKey=(EditText)this.findViewById(R.id.etKey);
		btnSearch.setOnClickListener(this);
		btnSedAll.setOnClickListener(this);
		btnDel.setOnClickListener(this);
		sbMonth=(SeekBar)this.findViewById(R.id.sbMonth);
		sbMonth.setOnSeekBarChangeListener(this);
		sbMonth.setOnTouchListener(new OnTouchListener(){
			@SuppressWarnings("unused")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
		    	float x=event.getX();
				float y=event.getY();
				if (event.getAction() == MotionEvent.ACTION_DOWN&&!bSeekBarClick) 
				{
					overlay.setVisibility(View.VISIBLE);
					bSeekBarClick=true;
					int iMonth=(int)(x/53.3);
					sbMonth.setProgress(iMonth);
					//llContainer.setBackgroundResource(R.drawable.bglisted100);
					return false;
				}
				if (event.getAction() == MotionEvent.ACTION_UP&&bSeekBarClick) 
				{
					bSeekBarClick=false;
				}
				return false;
			}
		});
		try
		{
			//SetSeekBarBackground();
			cc=new clsCalendar();
			dToday=new Date();
			dToday.setHours(0);
			dToday.setMinutes(0);
			dToday.setSeconds(0);
			Date dNow=new Date();
	        cc=new clsCalendar(dNow.getYear()+1900,dNow.getMonth()+1,dNow.getDate(),1);
	        //String sTmp=cc.GetSolarString()+" ("+cc.GetLunarShortString()+")";
	        tvTitle.setText(cc.GetSolarString());
	        tvTitle1.setText(" ("+cc.GetLunarShortString()+")");
			GetDrInfo(0,"");
			iCDrType=0;
			sbMonth.setProgress(dToday.getMonth());
			overlay.setVisibility(View.GONE);
			lvDrInfo.setSelection(0);
			setUpdatedDialogAlarm();
	    	//sTmp=null;
	    	int iUseCount=dbHelper.getUseCount();
	    	if(iUseCount==0)
	    	{
	    		Intent intent= new Intent(ActivityMain.this,ActivitySetting.class);
	    		startActivityForResult(intent,iSet_Request_Code);
	    		Toast.makeText(this, "第一次使用本软件，请设置闹铃铃声，否则可能会导致闹铃无法提醒。", Toast.LENGTH_LONG).show();
	    	}
	    	iUseCount++;
	    	if(!dbHelper.updateConfig("UseCount", String.valueOf(iUseCount)))
	    	{
	    		dbHelper.AddConfig(13, "UseCount", String.valueOf(iUseCount));
	    	}
		}
		catch(java.lang.Exception e)
		{
			Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
			return;
		}
    }

    public void onProgressChanged(SeekBar mSeekBar01, int progress, boolean fromTouch) {
    		try
    		{
	    		int iMonth=mSeekBar01.getProgress()+1;
	    		//overlay.setVisibility(View.VISIBLE);
	    		overlay.setText(String.valueOf(iMonth));
		    	int iPos=0;
		    	boolean bFound=false;
		    	for(int i=0;i<DrInfo[6].length;i++)
		    	{
		    		if(Integer.parseInt(DrInfo[6][i])==iMonth)
		    		{
		    			iPos=i;
		    			bFound=true;
		    			break;
		    		}
		    	}
		    	if(bFound)
		    	{
		    		lvDrInfo.setSelection(iPos);
		    	}
    		}
    		catch(java.lang.Exception e)
    		{
    			return;
    		}
    	}
		public void onStartTrackingTouch(SeekBar arg0) 
		{
			// TODO Auto-generated method stub
			overlay.setVisibility(View.VISIBLE);
			/*
			try
			{
				int iMonth=arg0.getKeyProgressIncrement();
		    	int iPos=0;
		    	boolean bFound=false;
		    	for(int i=0;i<DrInfo[6].length;i++)
		    	{
		    		if(Integer.parseInt(DrInfo[6][i])==iMonth)
		    		{
		    			iPos=i;
		    			bFound=true;
		    			break;
		    		}
		    	}
		    	if(bFound)
		    	{
		    		lvDrInfo.setSelection(iPos);
		    	}
			}
			catch(java.lang.Exception e)
			{
				return;
			}
			*/
		}
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			//overlay.setVisibility(View.GONE);
			
			if(!bSeekBarClick);
			{
				overlay.setVisibility(View.GONE);
				ClosePanel();
			}
		}
    
	    public void onClick(View v) 
	    {
	    	ClosePanel();
	    	/*
	    	if(v == tvTitle)
			{
	    		Intent intent= new Intent(ActivityMain.this,ActivityCalendar.class);
				startActivity(intent);
				intent=null;
				return;
			}
			*/
	    	if(v==tvTitle||v==tvTitle1)
	    	{
	    		if(pnFunc!=null)
		    	{
		    		pnFunc.setOpen(!pnFunc.isOpen(), true);
		    		//HideBottom();
		    	}
	    	}
			if(v == btnAll)
			{
				getDrInfoByType(0);
				return;
			}
			if(v == btnBir)
			{
				getDrInfoByType(1);
				return;
			}
			if(v == btnMem)
			{
				getDrInfoByType(2);
				return;
			}
			
			if(v == btnHol)
			{
				getDrInfoByType(3);
				return;
			}
			if(v==btnCal)
			{
				Intent intent= new Intent(ActivityMain.this,ActivityCalendar.class);
				startActivity(intent);
				intent=null;
				return;
			}
			if(v==btnSearch)
			{
				String sTmp=etKey.getText().toString();
				if(sTmp.length()==0)
				{
					Toast.makeText(this,getResources().getString(R.string.err_search_null), Toast.LENGTH_SHORT).show();
					return;
				}
				GetDrInfo(-1,etKey.getText().toString());
			}
			if(v == btnSedAll)
			{
				
				for(int i=0;i<bChecked.length;i++)
	    		 {
	    			 bChecked[i]=!bIsSedAll;
	    		 }
	    		 baDrInfo.notifyDataSetChanged();
	    		 if(!bIsSedAll)
	    		 {
		    		 iChecked=bChecked.length;
		    		 bDisplayPop=true;
		    		 bIsSedAll=true;
		    		 btnSedAll.setText(R.string.button_nosedall);
	    		 }
	    		 else
	    		 {
	    			 HideBottom();
	    		 }
	    		 btnDel.setText(String.format("删除(%d)",iChecked));
			}
			if(v == btnDel)
			{
				//showDialog(DIALOG_DELETE);
				showCustomDeleteDialog();
			}
		}
	    private void getDrInfoByType(int idrType)
	    {
	    	switch(idrType)
			{
	    	case 0:
				GetDrInfo(0,"");
				btnAll.setBackgroundResource(R.drawable.bg_mainbutton_down);
				btnBir.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnMem.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnHol.setBackgroundResource(R.drawable.bg_mainbutton_up);
				break;
	    	case 1:
				GetDrInfo(1,"");
				btnAll.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnBir.setBackgroundResource(R.drawable.bg_mainbutton_down);
				btnMem.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnHol.setBackgroundResource(R.drawable.bg_mainbutton_up);
				break;
	    	case 2:
				GetDrInfo(2,"");
				btnAll.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnBir.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnMem.setBackgroundResource(R.drawable.bg_mainbutton_down);
				btnHol.setBackgroundResource(R.drawable.bg_mainbutton_up);
				break;
	    	case 3:
				GetDrInfo(3,"");
				btnAll.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnBir.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnMem.setBackgroundResource(R.drawable.bg_mainbutton_up);
				btnHol.setBackgroundResource(R.drawable.bg_mainbutton_down);
			}
	    }
	    
	    private View.OnTouchListener touchListener = new View.OnTouchListener()
	    {
			float StartX=0.0f;
			float StartY=0.0f;
			float EndX=0.0f;
			float EndY=0.0f;
			int iMoveCount=0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ClosePanel();
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
					try
					{
						if(iMoveCount==1&&(float)Math.abs(EndX-StartX)/Math.abs(EndY-StartY)>1)
						{
							iMoveCount++;
							if(EndX<StartX)
							{
								iCDrType++;
								if(iCDrType==4)
								{
									iCDrType=3;
									Intent intent= new Intent(ActivityMain.this,ActivityCalendar.class);
									startActivity(intent);
									intent=null;
								}
								else
								{
									animationFromRightToLeftShowListView();
									getDrInfoByType(iCDrType);
									
								}
							}
							if(EndX>StartX)
							{
								iMoveCount++;
								if(iCDrType==0)
								{
									return false;
								}
								iCDrType--;
								if(iCDrType<0)
								{
									iCDrType=0;
								}
								animationFromLeftToRightShowListView();
								getDrInfoByType(iCDrType);
							}
						}
					}
					catch(Exception ex)
					{
						return false;
					}
					return false;
				}
				if (event.getAction() == MotionEvent.ACTION_MOVE&&iMoveCount==0) 
				{
					try
					{
						if(Math.abs(x-StartX)>20)
						{
							EndX=x;
							EndY=y;
							iMoveCount++;
						}
						/*
						if(EndX-StartX<-60)
						{
							iCDrType++;
							if(iCDrType==4)
							{
								iCDrType=3;
								Intent intent= new Intent(ActivityMain.this,ActivityCalendar.class);
								startActivity(intent);
								intent=null;
							}
							else
							{
								getDrInfoByType(iCDrType);
							}
							
							return false;
						}
						if(EndX-StartX>60)
						{
							if(iCDrType==0)
							{
								return false;
							}
							iCDrType--;
							if(iCDrType<0)
							{
								iCDrType=0;
							}
							getDrInfoByType(iCDrType);
							iMoveCount++;
							return false;
						}
						*/
						return false;
					}
					catch(Exception ex)
					{
						return false;
					}
				}
				return false;
			}
		};
		
	    private void ClosePanel()
	    {
	    	imm.hideSoftInputFromWindow(etKey.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    	if(pnFunc!=null&&pnFunc.isOpen())
	    	{
	    		pnFunc.setOpen(false, true);
	    	}
	    }
	private void deleteDrInfo()
	{
		for(int i=0;i<bChecked.length;i++)
		{
			if(bChecked[i])
			{
				try
				{
					dbHelper.delDrInfo(Integer.parseInt(DrInfo[0][i]));
				}
				catch(java.lang.Exception e)
				{
					;
				}
			}
		}
    	GetDrInfo(iCDrType,"");
    	setUpdatedDialogAlarm();
    	HideBottom();
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_BDADD, Menu.NONE, R.string.menu_bdadd).setIcon(R.drawable.icon_bday);			//添加“添加”菜单选项
		menu.add(0, MENU_MDADD, Menu.NONE, R.string.menu_mdadd).setIcon(R.drawable.icon_mday);			//添加“添加”菜单选项
		menu.add(0, MENU_HDADD, Menu.NONE, R.string.menu_hdadd).setIcon(R.drawable.icon_hday);			//添加“添加”菜单选项
		menu.add(0, MENU_CALENDAR, Menu.NONE, R.string.menu_calendar).setIcon(R.drawable.icon_calendar);			//添加“添加”菜单选项
		menu.add(0, MENU_SET, Menu.NONE, R.string.menu_set).setIcon(R.drawable.icon_setting);			//添加“添加”菜单选项
		menu.add(0, MENU_ABOUT, Menu.NONE, R.string.menu_about).setIcon(R.drawable.icon_about);			//添加“添加”菜单选项
		//menu.add(0, MENU_EXIT, Menu.NONE, R.string.menu_exit).setIcon(R.drawable.icon_exit);		//添加“删除”菜单选项
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent=null;
		switch(item.getItemId()){		//判断按下的菜单选项
		case MENU_BDADD:				//按下添加按钮
			intent= new Intent(ActivityMain.this,ActivityDrEdit.class);
			intent.putExtra("DrType", 1);
			intent.putExtra("DrId", 0);
			startActivityForResult(intent,iModi_Request_Code);
			break;
		case MENU_MDADD:				//按下添加按钮
			intent= new Intent(ActivityMain.this,ActivityDrEdit.class);
			intent.putExtra("DrType", 2);
			intent.putExtra("DrId", 0);
			startActivityForResult(intent,iModi_Request_Code);
			break;
		case MENU_HDADD:				//按下添加按钮
			if(pApplication.getLicenseReuslt()!=0)
			{
				Toast.makeText(this, pApplication.getNotFuncMessage(), Toast.LENGTH_SHORT).show();
				break;
			}
			intent= new Intent(ActivityMain.this,ActivityHolidays.class);
			startActivityForResult(intent,iModi_Request_Code);
			break;
		case MENU_CALENDAR:
			intent= new Intent(ActivityMain.this,ActivityCalendar.class);
			startActivity(intent);
			break;
		case MENU_SET:					//按下添加按钮
			intent= new Intent(ActivityMain.this,ActivitySetting.class);
			startActivityForResult(intent,iSet_Request_Code);
			break;
		case MENU_ABOUT:				//按下添加按钮
			intent= new Intent(ActivityMain.this,ActivityAbout.class);
			startActivity(intent);
			break;
		case MENU_EXIT:					//按下了删除选项
			//showDialog(DIALOG_EXIT);	//显示确认删除对话框
			//this.finish();
			ExitDayReminder();
			break;
		}
		intent=null;
		return super.onOptionsItemSelected(item);
	}

	public void  onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);
        
      //取得结果
        if(requestCode==iSet_Request_Code)
        {
        	setUpdatedDialogAlarm();
        	if(resultCode==RESULT_OK)
        	{
        		GetDrInfo(iCDrType,etKey.getText().toString());
        		baDrInfo.notifyDataSetChanged();
        	}
        }
        if(requestCode==iModi_Request_Code)
        {
        	
        	if(resultCode==RESULT_OK)
        	{
        		GetDrInfo(iCDrType,etKey.getText().toString());
        		baDrInfo.notifyDataSetChanged();
        		setUpdatedDialogAlarm();
        	}
        }
	} 
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch(id){			//对对话框ID进行判断
		case DIALOG_EXIT:		//创建删除确认对话框
			Builder b = new AlertDialog.Builder(this);	
			b.setMessage(R.string.dialog_exit_message);		//设置对话框内容
			b.setPositiveButton(
					R.string.button_ok,
					new android.content.DialogInterface.OnClickListener() {				//点下确认删除按钮
						@Override
						public void onClick(DialogInterface dialog, int which) {
							android.os.Process.killProcess(android.os.Process.myPid());
							System.exit(0);
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
		case DIALOG_DELETE:		//创建删除确认对话框
			/*
			Builder d = new AlertDialog.Builder(this);	
			d.setMessage(R.string.dialog_deletesed_message);		//设置对话框内容
			d.setPositiveButton(
					R.string.button_ok,
					new android.content.DialogInterface.OnClickListener() {				//点下确认删除按钮
						@Override
						public void onClick(DialogInterface dialog, int which) {
							deleteDrInfo();
						}
					});
			d.setNegativeButton(
					R.string.button_cancel,
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {}
					});
			dialog = d.create();
			*/
			
			
			break;
		}
		return dialog;
	}	
	
	private void showCustomDeleteDialog()
	{
		//final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.setContentView(R.layout.lo_confirm_dialog);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.lo_confirm_dialog);
		((TextView) window.findViewById(R.id.dialog_message)).setText(R.string.dialog_deletesed_message); 
		((Button) window.findViewById(R.id.dialog_ok)).setOnClickListener(new OnClickListener() {
			@Override                    
			public void onClick(View v) {                        
				// write your code to do things after users clicks OK                        
				deleteDrInfo(); 
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
    private void GetDrInfo(int DrType,String KeyValue)
    {
    	try
    	{
    		ClosePanel();
    		HideBottom();
	    	iCDrType=DrType;
	    	DrInfo=dbHelper.getDrMainList(DrType,KeyValue);
	    	if(DrInfo==null)
	    	{
	    		return;
	    	}
	    	int iCount=DrInfo[0].length;

	    	bChecked=new boolean[iCount];
	    	baDrInfo.notifyDataSetChanged();
    	}
    	catch(java.lang.Exception e)
		{
    		Toast.makeText(ActivityMain.this, "取得数据："+e.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
    }
    
    private void setUpdatedDialogAlarm()
    {
    	try
    	{
	    	SetReminder sr=new SetReminder(this);
    		sr.setAllRemind();
    	}
    	catch(java.lang.Exception e)
		{
    		Toast.makeText(this, "更新提醒闹钟失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
    }

    @Override
    //�����˳���
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
    	 if(bDisplayPop)
    	 {
    		 for(int i=0;i<bChecked.length;i++)
    		 {
    			 bChecked[i]=false;
    		 }
    		 baDrInfo.notifyDataSetChanged();
    		 HideBottom();
    		 return true;
    	 }
    	 else if(pnFunc!=null&&pnFunc.isOpen())
    	 {
    		 pnFunc.setOpen(false, true);
    		 return true;
    	 }
    	 else
    	 {
    		 ExitDayReminder();
    	 }
     }
     return super.onKeyDown(keyCode, event);
    }
    /*
    private void animationHideListView()
    {
    	animationTranslate= AnimationUtils.loadAnimation(this, R.anim.hide_alpha);
    	lvDrInfo.startAnimation(animationTranslate);
    }
    */
    private void animationFromLeftToRightShowListView()
    {
    	animationTranslate= AnimationUtils.loadAnimation(this, R.anim.ltr_show_alpha_translate);
    	lvDrInfo.startAnimation(animationTranslate);
    }
    private void animationFromRightToLeftShowListView()
    {
    	animationTranslate= AnimationUtils.loadAnimation(this, R.anim.rtl_show_alpha_translate);
    	lvDrInfo.startAnimation(animationTranslate);
    }
    private void ShowBottom()
    {
    	if(!bDisplayPop)
    	{
    		ClosePanel();
	    	llBottom.setVisibility(View.VISIBLE);
	    	animationTranslate=new TranslateAnimation(0f, 0f, 48.0f,0.0f);
	    	animationTranslate.setDuration(200);
	    	llBottom.setAnimation(animationTranslate);
			bDisplayPop=true;
    	}
    }
    private void HideBottom()
    {
    	if(bDisplayPop)
    	{
	    	animationTranslate=new TranslateAnimation(0f, 0f, 0.0f,48.0f);
	    	animationTranslate.setDuration(200);
	    	llBottom.setAnimation(animationTranslate);
	    	llBottom.setVisibility(View.GONE);
	    	iChecked=0;
			bDisplayPop=false;
			bIsSedAll=false;
			btnSedAll.setText(R.string.button_sedall);
    	}
    }
    
    private void ExitDayReminder()
    {
    	//int sdk_Version = android.os.Build.VERSION.SDK_INT; 
    	ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
    	am.restartPackage(getPackageName());
    	finish();
    	/*
    	if (sdk_Version >= 8) 
    	{            
    		//Intent startMain = new Intent(Intent.ACTION_MAIN);
    		//Intent startMain = new Intent(this, ActivityAlarmDialog.class);
    		//startMain.addCategory(Intent.CATEGORY_HOME);            
    		//startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);            
    		//startActivity(startMain);
    		//System.exit(0); 
    		
    		finish();
    		android.os.Process.killProcess(android.os.Process.myPid());
    	} 
    	else if (sdk_Version < 8) 
    	{   
    		
    		finish();
    		android.os.Process.killProcess(android.os.Process.myPid());
    		//ActivityManager activityMgr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);            
    		//activityMgr.restartPackage(getPackageName());        
    	}
    	*/
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper=null;
    	cc=null;
    	dToday=null;
    	btnAll=null;
    	btnBir=null;
    	btnMem=null;
    	btnHol=null;
    	/*
    	sDrId=null;
    	sDrName=null;
    	sDrDes=null;
    	sDrNextDate=null;
    	sAftDays=null;
    	sNdMonth=null;
    	*/
    	DrInfo=null;
    	bChecked=null;
    	
    	tvTitle=null;
    	lvDrInfo=null;	 					//����ListVifew����
    	sv=null;
    	baDrInfo=null;

    	System.gc();
    	System.exit(0);
      }
    
}