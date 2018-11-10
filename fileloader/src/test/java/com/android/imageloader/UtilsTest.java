package com.android.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.imageloader.cache.FileCache;
import com.android.imageloader.callback.FutureCallBack;
import com.android.imageloader.downloader.MyDownLoader;
import com.android.imageloader.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    private Context instrumentationCtx;
    FileCache cache;
    String url = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=64\\u0026w=64\\u0026s=ef631d113179b3137f911a05fea56d23";
    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        cache = new FileCache(instrumentationCtx);

    }


    @Test
    public void decodeFileTest(){
        Bitmap bmp = Utils.decodeFile(cache.getFile(url));
        assertNotNull(bmp);
    }
}
