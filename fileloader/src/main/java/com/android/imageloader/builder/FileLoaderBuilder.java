package com.android.imageloader.builder;

import android.content.Context;
import android.view.View;

import com.android.imageloader.loader.MyFileLoader;

public class FileLoaderBuilder {

    // optional parameters
    public Context context;
    public String url;
    public View view;
    public long cacheLimit;


    public FileLoaderBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public FileLoaderBuilder setCacheLimit(long value) {
        this.cacheLimit = value;
        return this;
    }

    public FileLoaderBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public FileLoaderBuilder setView(View view) {
        this.view = view;
        return this;
    }

    public MyFileLoader build(){
        return new MyFileLoader(this);
    }

}
