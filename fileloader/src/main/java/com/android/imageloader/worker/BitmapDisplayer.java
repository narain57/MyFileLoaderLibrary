package com.android.imageloader.worker;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.imageloader.R;
import com.android.imageloader.callback.FutureCallBack;
import com.android.imageloader.model.FileToLoad;
import com.android.imageloader.utils.Utils;

import java.util.Map;

//Used to display bitmap in the UI thread
public class BitmapDisplayer implements Runnable
{
    private final FutureCallBack callback;
    Bitmap bitmap;
    FileToLoad fileToLoad;
    public BitmapDisplayer(Bitmap b, FileToLoad p, FutureCallBack<Object> callback){
        bitmap=b;
        fileToLoad =p;
        this.callback = callback;
    }
    public void run()
    {
        if(Thread.interrupted())
            return;

        if(bitmap!=null)
            ((ImageView)fileToLoad.view).setImageBitmap(bitmap);
        else
            ((ImageView)fileToLoad.view).setImageResource(R.drawable.defaultimage);

        callback.onCompleted(bitmap);
    }
}

