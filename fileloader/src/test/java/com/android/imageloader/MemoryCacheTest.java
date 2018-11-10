package com.android.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.imageloader.cache.MemoryCache;
import com.android.imageloader.callback.FutureCallBack;
import com.android.imageloader.downloader.MyDownLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MemoryCacheTest {

    private Context instrumentationCtx;
    MemoryCache cache;
    private MyDownLoader downloader;
    String url="https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=64\\u0026w=64\\u0026s=ef631d113179b3137f911a05fea56d23";

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        cache = new MemoryCache();
        cache.setLimit(Runtime.getRuntime().maxMemory()/4);
        downloader = new MyDownLoader(instrumentationCtx, cache, new FutureCallBack<Object>() {
            @Override
            public void onCompleted(Object o) {

            }
        });

    }

    @Test
    public void getCacheTest(){
        cache.put(url,downloader.getBitmap(url));
         Bitmap bmp = (Bitmap)cache.get(url);
        assertNotNull(bmp);
        areBitmapsEqual(bmp,downloader.getBitmap(url));

    }

    @Test
    private boolean areBitmapsEqual(Bitmap bitmap1, Bitmap bitmap2) {
        // compare two bitmaps by their bytes
        byte[] array1 = BitmapToByteArray(bitmap1);
        byte[] array2 = BitmapToByteArray(bitmap2);
        if (java.util.Arrays.equals(array1, array2)) {
            return true;
        }
        return false;
    }

    private static byte[] BitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] result = bos.toByteArray();
        return result;
    }


}
