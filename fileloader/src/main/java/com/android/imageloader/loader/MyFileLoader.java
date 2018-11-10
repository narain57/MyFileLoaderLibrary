package com.android.imageloader.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.imageloader.builder.FileLoaderBuilder;
import com.android.imageloader.cache.MemoryCache;
import com.android.imageloader.callback.FutureCallBack;
import com.android.imageloader.downloader.MyDownLoader;
import com.android.imageloader.utils.FileType;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class MyFileLoader {

    private final Context context;
    private Map<ImageView, String> map=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private Map<String, MyDownLoader> downloaderMap=Collections.synchronizedMap(new WeakHashMap<String, MyDownLoader>());

    MemoryCache memoryCache = new MemoryCache();

    public MyFileLoader(FileLoaderBuilder fileLoaderBuilder) {
        this.context = fileLoaderBuilder.context;
        memoryCache.setLimit(fileLoaderBuilder.cacheLimit);
    }

    public void load(FileType type,String url,View view,FutureCallBack<Object> callBack)
    {
        //If type is a Image then view needs to be ImageView
        if(type.equals(FileType.IMAGE)) {
            Bitmap bitmap = (Bitmap) memoryCache.get(url);
            if (bitmap != null) {
                if (view != null)
                    ((ImageView) view).setImageBitmap(bitmap);
                else
                    callBack.onCompleted(bitmap);
            }
            else {
                MyDownLoader downloader = new MyDownLoader(context, memoryCache, map);
                downloaderMap.put(url,downloader);
                downloader.queue(url, view,type);
            }
        } else {
            String content = (String)memoryCache.get(url);
            if (!TextUtils.isEmpty(content)) {
                if (view != null)
                    ((TextView) view).setText(content);
                else
                    callBack.onCompleted(content);
            }
            else {
                MyDownLoader downloader = new MyDownLoader(context, memoryCache,callBack);
                downloaderMap.put(url,downloader);
                downloader.queue(url, view,type);
            }
        }
    }

    public void cancel(String imageUrl){
        downloaderMap.get(imageUrl).cancelTask(imageUrl);
    }
}
