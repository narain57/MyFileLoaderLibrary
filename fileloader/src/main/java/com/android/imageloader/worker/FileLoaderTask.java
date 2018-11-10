package com.android.imageloader.worker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.imageloader.cache.MemoryCache;
import com.android.imageloader.callback.FutureCallBack;
import com.android.imageloader.downloader.MyDownLoader;
import com.android.imageloader.model.FileToLoad;
import com.android.imageloader.utils.FileType;
import com.android.imageloader.utils.Utils;

import java.io.IOException;
import java.util.Map;

import static com.android.imageloader.utils.Utils.imageViewReused;

public class FileLoaderTask implements Runnable {
    private final MemoryCache memoryCache;
    private final MyDownLoader downloader;
    private Map<ImageView, String> views;
    private final FileType type;
    private FutureCallBack<Object> callback;
    private FileToLoad fileToLoad;

    public FileLoaderTask(FileToLoad fileToLoad, MemoryCache memoryCache, MyDownLoader myImageDownLoader, Map<ImageView, String> map,FileType type){
        this.fileToLoad = fileToLoad;
        this.memoryCache = memoryCache;
        this.downloader = myImageDownLoader;
        this.views = map;
        this.type = type;
    }

    public FileLoaderTask(FileToLoad p, MemoryCache memoryCache, MyDownLoader myImageDownLoader, FutureCallBack<Object> callback, FileType type) {
        this.fileToLoad = p;
        this.memoryCache = memoryCache;
        this.downloader = myImageDownLoader;
        this.callback = callback;
        this.type = type;
    }

    @Override
    public void run() {
        if(type.equals(FileType.IMAGE)){
            Bitmap bmp = downloader.getBitmap(fileToLoad.url);
            memoryCache.put(fileToLoad.url, bmp);
            if(fileToLoad.view!=null) {
                BitmapDisplayer bd = new BitmapDisplayer(bmp, fileToLoad, views);
                Activity a = (Activity) fileToLoad.view.getContext();
                a.runOnUiThread(bd);
            }
        }else{
            String fileContent = null;
            try {
                fileContent = downloader.loadFile(fileToLoad.url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            memoryCache.put(fileToLoad.url, fileContent);
            if(fileToLoad.view !=null) {
                TextDisplayerTask bd = new TextDisplayerTask(fileContent, fileToLoad);
                Activity a = (Activity) fileToLoad.view.getContext();
                a.runOnUiThread(bd);
            }else{
                callback.onCompleted(fileContent);
            }
        }
    }
}
