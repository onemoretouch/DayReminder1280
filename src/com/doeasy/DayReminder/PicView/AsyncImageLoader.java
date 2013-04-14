package com.doeasy.DayReminder.PicView;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.webkit.URLUtil;

public class AsyncImageLoader {
    private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

    public Drawable loadDrawable(final String imageUrl,final ImageCallback callback) {
    	//这里, 我们可以通过使用 Map 的containsKey() 方法来检测是否数据是否存在, 如果key存在, 则表明已经获取过一次数据, 
    	//那么直接返回该 key 在 Map 中的值. 不管是否为 null 都直接返回; 如果 key 不存在, 则去生成或者获取数据, 并放入到 Map 中, 并返回该数据.
    	// 这里使用 containsKey() 来检测可以应用于: 1. 从其他对方获取的数据可能为空, 并且不会有变化; 2. 获取数据比较耗时. 这个场景下, 使用该方法可以大大降低消耗, 特别是在同步情况下.
    	if(imageUrl.length()==0)
    	{
    		return null;
    	}
    	
    	if (imageCache.containsKey(imageUrl))
    	{
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            if (softReference.get() != null) 
            {
                return softReference.get();
            }
        }
        
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                callback.imageLoaded((Drawable) msg.obj, imageUrl);
            }
        };
        //load data
        new Thread() {
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                handler.sendMessage(handler.obtainMessage(0, drawable));
            };
        }.start();
        
        return null;
    }
    /*
    protected Drawable loadImageFromUrl(String imageUrl) {
        try {
            return Drawable.createFromStream(new URL(imageUrl).openStream(),
                    "src");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
    protected Drawable loadImageFromUrl(String imageUrl)
    {
    	Bitmap bitmap = null;  
		BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
	    options.inSampleSize = 1;   //width，hight设为原来的十分一
		if (imageUrl != null) {
			try
			{  
				if (URLUtil.isHttpUrl(imageUrl)) 
				{// 如果为网络地址。则连接url下载图片  
					URL url = new URL(imageUrl);  
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
					bitmap=ImageUtil.DrBitmap(BitmapFactory.decodeFile(imageUrl,options));
				}  
				options=null;
				return new BitmapDrawable(bitmap) ;  
			} 
			catch (Exception e) 
			{  
				return null;  
			}
		}
		else
		{
			return null;  
		}
    }
    //call back interface
    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable, String imageUrl);
    }
    
}
