package com.doeasy.DayReminder.PicView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
/**
 * 
 * DrawableImageLoader--<b>圖像加載</b>-- 
 * 
 * @author fanxk
 * @version 1.45 2011/11/20
 * 
 */
public class DrawableImageLoader {

	private Context mContext;
	private HashMap<String, WeakReference<Bitmap>> imageCache;
	private BitmapFactory.Options options;
	List<Integer> ResourIDS;
	public DrawableImageLoader(Context context,int ImageQ) {
		this.mContext = context;
		ResourIDS=new ArrayList<Integer>();
		imageCache = new HashMap<String, WeakReference<Bitmap>>();
		options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = 1; // width��hight��Ϊԭ����ʮ��һ
		switch (ImageQ) {
		case 0:
			options.inPreferredConfig = Bitmap.Config.ARGB_4444;
			break;
		case 1:
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			break;
		case 2:
			options.inPreferredConfig = Bitmap.Config.ALPHA_8;
			break;
		case 3:
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			break;
		default:
			break;
		}
			
		
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inDither = false;
	}
/**
 * LoadImage：加載圖�?
 * @param ResourceID
 * @return
 */
	public BitmapDrawable LoadImage(int ResourceID) {
		try {
			if (imageCache.containsKey(String.valueOf(ResourceID))) {
				WeakReference<Bitmap> cache = imageCache.get(String
						.valueOf(ResourceID));
				Bitmap bitmap = cache.get();
				if (bitmap != null) {
					return new BitmapDrawable(bitmap);
				}
			}

			InputStream is = mContext.getResources()
					.openRawResource(ResourceID);
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
			if (bitmap != null) {
				imageCache.put(String.valueOf(ResourceID),new WeakReference<Bitmap>(bitmap));
				ResourIDS.add(ResourceID);
				is.close();
				is = null;
				System.gc();
				return new BitmapDrawable(bitmap);
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	/**
	 * Recycle:釋放图像资源
	 */
	public void Recycle() {
		int len=ResourIDS.size();
		int ResourceID=0;
		for(int i=0;i<len;i++)
		{
			ResourceID=ResourIDS.get(i);
			if (imageCache.containsKey(String.valueOf(ResourceID))) {
				WeakReference<Bitmap> cache = imageCache.get(String
						.valueOf(ResourceID));
				Bitmap bitmap = cache.get();
				if (bitmap != null) {
					bitmap.recycle();
				}
			}
		}
		mContext=null;
		imageCache=null;
		options=null;
		ResourIDS=null;
	}
}
