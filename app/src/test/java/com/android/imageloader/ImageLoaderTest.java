package com.android.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import com.android.imageloader.cache.MemoryCache;
import com.android.imageloader.downloader.MyDownLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    MemoryCache memoryCache=new MemoryCache();
    MyDownLoader downloader;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        downloader = new MyDownLoader(instrumentationCtx,memoryCache,imageViews);

    }

    @Test
    public void getBitmapTest() {

        Bitmap bitmap = downloader.getBitmap("");
        assertNotNull(bitmap);

    }
}