package com.android.imageloader.model;

import android.view.View;
import android.widget.ImageView;

public class FileToLoad
{
    public String url;
    public View view;
    public FileToLoad(String u, View i){
        url=u;
        view=i;
    }
}
