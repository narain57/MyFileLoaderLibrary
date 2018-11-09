package com.android.imageloader.downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.android.imageloader.R;
import com.android.imageloader.cache.FileCache;
import com.android.imageloader.cache.MemoryCache;
import com.android.imageloader.model.FileToLoad;
import com.android.imageloader.utils.FileType;
import com.android.imageloader.utils.Utils;
import com.android.imageloader.worker.FileLoaderTask;

public class MyDownLoader {

    private final MemoryCache memoryCache;
    private final Map<ImageView, String> map;
    FileCache fileCache;
    ExecutorService executorService;

    public MyDownLoader(Context context, MemoryCache cache,Map<ImageView, String> map){
        executorService=Executors.newFixedThreadPool(5);
        fileCache=new FileCache(context);
        this.memoryCache = cache;
        this.map =map;
    }

    public void queue(String url, View view, FileType type)
    {
        FileToLoad p=new FileToLoad(url, view);
        executorService.submit(new FileLoaderTask(p,memoryCache,this, map,type));
    }

    public Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);

        //from SD cache
        Bitmap b = Utils.decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = Utils.decodeFile(f);
            return bitmap;
        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    public String loadFile(String url) throws IOException {

        File file= fileCache.getFile(url);
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes.toString();
    }
}
