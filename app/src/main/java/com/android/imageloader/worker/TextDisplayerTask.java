package com.android.imageloader.worker;

import android.text.TextUtils;
import android.widget.TextView;
import com.android.imageloader.model.FileToLoad;

//Used to display text in the UI thread
public class TextDisplayerTask implements Runnable
{
    String content;
    FileToLoad fileToLoad;
    public TextDisplayerTask(String content, FileToLoad p){
        this.content=content;
        fileToLoad =p;
    }
    public void run()
    {
        if(!TextUtils.isEmpty(content))
            ((TextView)fileToLoad.view).setText(content);
        else
            ((TextView)fileToLoad.view).setText("No content available");
    }
}


