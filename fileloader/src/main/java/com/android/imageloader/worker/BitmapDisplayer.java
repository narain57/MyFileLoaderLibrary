package com.android.imageloader.worker;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.imageloader.model.FileToLoad;
import com.android.imageloader.utils.Utils;

import java.util.Map;

//Used to display bitmap in the UI thread
public class BitmapDisplayer implements Runnable
{
    private final Map<ImageView, String> views;
    Bitmap bitmap;
    FileToLoad fileToLoad;
    public BitmapDisplayer(Bitmap b, FileToLoad p,Map<ImageView, String> imageViews){
        bitmap=b;
        fileToLoad =p;
        this.views = imageViews;
    }
    public void run()
    {
        if(bitmap!=null)
            ((ImageView)fileToLoad.view).setImageBitmap(bitmap);
        else
            ((ImageView)fileToLoad.view).setImageResource(Utils.stub_id);
    }
}

