package com.android.imageloader.builder;

import android.content.Context;
import android.view.View;

import com.android.imageloader.loader.MyFileLoader;

public class FileLoaderBuilder {

    // optional parameters
    public Context context;
    public long cacheLimit;


    public FileLoaderBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public FileLoaderBuilder setCacheLimit(long value) {
        this.cacheLimit = value;
        return this;
    }

    public MyFileLoader build(){
        return new MyFileLoader(this);
    }

}
