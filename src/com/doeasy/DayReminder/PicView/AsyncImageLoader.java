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
    	//����, ���ǿ���ͨ��ʹ�� Map ��containsKey() ����������Ƿ������Ƿ����, ���key����, ������Ѿ���ȡ��һ������, 
    	//��ôֱ�ӷ��ظ� key �� Map �е�ֵ. �����Ƿ�Ϊ null ��ֱ�ӷ���; ��� key ������, ��ȥ���ɻ��߻�ȡ����, �����뵽 Map ��, �����ظ�����.
    	// ����ʹ�� containsKey() ��������Ӧ����: 1. �������Է���ȡ�����ݿ���Ϊ��, ���Ҳ����б仯; 2. ��ȡ���ݱȽϺ�ʱ. ���������, ʹ�ø÷������Դ�󽵵�����, �ر�����ͬ�������.
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
	    options.inSampleSize = 1;   //width��hight��Ϊԭ����ʮ��һ
		if (imageUrl != null) {
			try
			{  
				if (URLUtil.isHttpUrl(imageUrl)) 
				{// ���Ϊ�����ַ��������url����ͼƬ  
					URL url = new URL(imageUrl);  
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
					conn.setDoInput(true);  
					conn.connect();  
					InputStream stream = conn.getInputStream();  
					bitmap = ImageUtil.DrBitmap(BitmapFactory.decodeStream(stream, null, options));
					stream.close();  
				} 
				else
				{// ���Ϊ�������ݣ�ֱ�ӽ���  
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
