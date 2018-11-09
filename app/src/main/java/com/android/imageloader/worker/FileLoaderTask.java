package com.android.imageloader.worker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.imageloader.cache.MemoryCache;
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
    private final Map<ImageView, String> views;
    private final FileType type;
    private FileToLoad fileToLoad;

    public FileLoaderTask(FileToLoad fileToLoad, MemoryCache memoryCache, MyDownLoader myImageDownLoader, Map<ImageView, String> map,FileType type){
        this.fileToLoad = fileToLoad;
        this.memoryCache = memoryCache;
        this.downloader = myImageDownLoader;
        this.views = map;
        this.type = type;
    }

    @Override
    public void run() {
        if(type.equals(FileType.IMAGE)){
            Bitmap bmp = downloader.getBitmap(fileToLoad.url);
            memoryCache.put(fileToLoad.url, bmp);
            if (imageViewReused(fileToLoad, views))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, fileToLoad, views);
            Activity a = (Activity) fileToLoad.view.getContext();
            a.runOnUiThread(bd);
        }else{
            String fileContent = null;
            try {
                fileContent = downloader.loadFile(fileToLoad.url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            memoryCache.put(fileToLoad.url, fileContent);
            TextDisplayerTask bd = new TextDisplayerTask(fileContent, fileToLoad);
            Activity a = (Activity) fileToLoad.view.getContext();
            a.runOnUiThread(bd);
        }
    }
}
