package com.doeasy.DayReminder.PicView;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.doeasy.DayReminder.R;


public class AsyncViewTask extends AsyncTask<View, Void, Bitmap>
{  

	private ImageView mView;  

	private HashMap<String, SoftReference<Bitmap>> imageCache;  

	public AsyncViewTask(Context mContext, String mobileinfo) 
	{  

		imageCache = new HashMap<String, SoftReference<Bitmap>>();  

	} 
	public AsyncViewTask() 
	{  

		imageCache = new HashMap<String, SoftReference<Bitmap>>();  

	}
	protected Bitmap doInBackground(View... views) 
	{  
		try
		{ 
			Bitmap bitmap = null;  
			BitmapFactory.Options options=new BitmapFactory.Options();
		    options.inJustDecodeBounds = false;
		    options.inSampleSize = 4;   //width，hight设为原来的十分一
			View view = views[0];  
			this.mView = (ImageView)view;  
			if (view.getTag() != null&&view.getTag().toString().length()>0) {
				
				if (imageCache.containsKey(view.getTag())) {  
					SoftReference<Bitmap> cache = imageCache.get(view.getTag().toString());  
					bitmap = cache.get();  
					if (bitmap != null) {
						return bitmap;  
					} 
				}
			 	
				if (URLUtil.isHttpUrl(view.getTag().toString())) 
				{// 如果为网络地址。则连接url下载图片  
					URL url = new URL(view.getTag().toString());  
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
					conn.setDoInput(true);  
					conn.connect();  
					InputStream stream = conn.getInputStream();  
					bitmap = ImageUtil.DrBitmap(BitmapFactory.decodeStream(stream, null, options));
					stream.close();  
				} 
				else
				{// 如果为本地数据，直接解析  
					//drawable = Drawable.createFromPath(view.getTag().toString());  			
					bitmap=ImageUtil.DrBitmap(BitmapFactory.decodeFile(view.getTag().toString(),options));
				}  
				imageCache.put(view.getTag().toString(), new SoftReference<Bitmap>(bitmap));
				options=null;
				return bitmap;  
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{  
			return null;  
		}    
		
	}  
	protected void onPostExecute(Bitmap bitmap) {  

		if (bitmap != null) {  
			//this.mView.setBackgroundDrawable(drawable); 
			this.mView.setImageBitmap(bitmap);
			this.mView = null;  
		} 
		else
		{
			this.mView.setImageResource(R.drawable.defaulttx);
			this.mView=null;
		}
	}  
} 
