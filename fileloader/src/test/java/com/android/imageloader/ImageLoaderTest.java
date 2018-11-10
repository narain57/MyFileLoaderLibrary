package com.android.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import com.android.imageloader.cache.MemoryCache;
import com.android.imageloader.callback.FutureCallBack;
import com.android.imageloader.downloader.MyDownLoader;
import com.android.imageloader.utils.FileType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ImageLoaderTest {

    private Context instrumentationCtx;
    MemoryCache memoryCache=new MemoryCache();
    MyDownLoader downloader;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        downloader = new MyDownLoader(instrumentationCtx, memoryCache, new FutureCallBack<Object>() {
            @Override
            public void onCompleted(Object o) {

            }
        });

    }

    @Test
    public void loadfileTest() {
        String content = null;
        try {
             content = downloader.loadFile("http://pastebin.com/raw/wgkJgazE");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(content);
    }


    @Test
    public void getBitmapTest() {

        Bitmap bitmap = downloader.getBitmap("https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=64\\u0026w=64\\u0026s=ef631d113179b3137f911a05fea56d23");
        assertNotNull(bitmap);

    }
}