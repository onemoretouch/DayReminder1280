package com.doeasy.DayReminder.UserControl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.View;


public class PartScreen {
	private static final String TAG="PartScreen"; 
	private static Bitmap bitmap=null;
	private static void getScreen(View v){ 
        v.setDrawingCacheEnabled(true); 
        v.buildDrawingCache(); 
        bitmap = ThumbnailUtils.extractThumbnail(Bitmap.createBitmap(v.getDrawingCache(),80,240, 480, 480),240,240,0); 
        //ThumbnailUtils.extractThumbnail(source, width, height, options);
        v.destroyDrawingCache(); 
        //return bitmap; 
    } 
      
    private static boolean savePic(String filename){ 
        FileOutputStream fileOutputStream = null; 
        try {
        	File f=new File(filename);
        	if(!f.exists())
        	{
        		f.createNewFile();
        	}
            fileOutputStream = new FileOutputStream(f); 
            if (fileOutputStream != null) { 
            	bitmap.compress(Bitmap.CompressFormat.JPEG, 96, fileOutputStream); 
                fileOutputStream.flush(); 
                fileOutputStream.close(); 
            } 
            return true;
        } 
        catch (FileNotFoundException e)
        { 
            Log.d(TAG, "Exception:FileNotFoundException"); 
            e.printStackTrace(); 
            return true;
        } 
        catch (IOException e) 
        { 
            Log.d(TAG, "IOException:IOException"); 
            e.printStackTrace(); 
            return true;
        } 
    }
    public static boolean Save(View v,String fileName)
    { 
    	try
    	{
    		getScreen(v);
    		boolean b=savePic(fileName); 
    		if(!bitmap.isRecycled())
    		{
    			bitmap.recycle();
    		}
    		bitmap=null;
    		return b;
    	}
    	catch (Exception e) { 
            return false;
        } 
    } 

}
