package com.doeasy.DayReminder.UserControl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

/**
 * һ�����Բ��� 
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class ViewScroll extends AbsoluteLayout
{
	
	private final int screenW=640;	//���õ���Ļ��
	private final int screenH=912;	//���õ���Ļ��   �ܸ߶�-����������ܸ߶�
	private int imgW;		//ͼƬԭʼ��
	private int imgH;		//ͼƬԭʼ��
	private TouchView tv;
	static final int BIGGER = 3;   
    static final int SMALLER = 4; 
    private int iRotatedAngle=0;
    private float scale = 0.06f;  
    private Bitmap bitmap=null;
    private Bitmap bit=null;
    private Context contex;
	public ViewScroll(Context context,Bitmap img,View topView)
	{
		super(context);
		try
		{
			contex=context;
			tv = new TouchView(context,screenW/2,screenH/2);
	        tv.setImageBitmap(img);
	        //Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
	        imgW = img.getWidth();
	        imgH = img.getHeight();
	        //����ȫ����ʾ��Ҫ�ĸ߶ȺͿ��
	        int layout_w=0;
	        int layout_h=0;
	        if((float)imgW/imgH>(float)screenW/screenH)
	        {
	        	layout_w=screenW;
	        	layout_h=(int)(((float)imgH/imgW)*screenW);
	        }
	        else
	        {
	        	layout_h=screenH;
	        	layout_w=(int)(((float)imgW/imgH)*screenH);
	        }
	        tv.setLayoutParams(new AbsoluteLayout.LayoutParams(layout_w,layout_h , layout_w==screenW?0:(screenW-layout_w)/2, layout_h==screenH?0:(screenH-layout_h)/2));
	        tv.setScaleType(ScaleType.FIT_XY);
	        this.addView(tv);
	        bitmap=img;
	        img=null;
		}
		catch(Exception ex)
		{
			Toast.makeText(context, "����ͼƬʧ�ܣ�"+ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	public void setScale(int _flag)
	{
		tv.setScale(scale,_flag);   
	}
	public void setRotate()
	{
		try
		{
			Matrix matrix = new Matrix(); 
			iRotatedAngle+=90;
			iRotatedAngle=iRotatedAngle%360;
			matrix.postRotate(iRotatedAngle);
			int w=1,h=1;
			int iflag=iRotatedAngle/90 % 2;
			if(iflag==0)
			{
				w=imgW;
				h=imgH;
			}
			else
			{
				w=imgH;
				h=imgW;
			}
			int layout_w=0;
	        int layout_h=0;
	        if((float)w/h>(float)screenW/screenH)
	        {
	        	layout_w=screenW;
	        	layout_h=(int)(((float)h/w)*screenW);
	        }
	        else
	        {
	        	layout_h=screenH;
	        	layout_w=(int)(((float)w/h)*screenH);
	        }
	        tv.setLayoutParams(new AbsoluteLayout.LayoutParams(layout_w,layout_h , layout_w==screenW?0:(screenW-layout_w)/2, layout_h==screenH?0:(screenH-layout_h)/2));
	        bit = Bitmap.createBitmap(bitmap, 0, 0,imgW,imgH, matrix, true);
			tv.setImageBitmap(bit);
			return;
		}
		catch(Exception ex)
		{
			Toast.makeText(contex, "ͼƬ��תʧ�ܣ�"+ex.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
	}
	public void RecycleImg()
	{
		if(bitmap!=null&&!bitmap.isRecycled())
		{
			bitmap.recycle();
		}
		if(bit!=null&&!bit.isRecycled())
		{
			bit.recycle();
		}
		bitmap=null;
		bit=null;
	}
}
