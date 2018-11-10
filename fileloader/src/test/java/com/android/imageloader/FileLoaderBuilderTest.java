package com.android.imageloader;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.imageloader.builder.FileLoaderBuilder;
import com.android.imageloader.downloader.MyDownLoader;
import com.android.imageloader.loader.MyFileLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class FileLoaderBuilderTest {

    private Context instrumentationCtx;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
    }

    @Test
    public void getLoaderTest(){
        MyFileLoader loader = new FileLoaderBuilder().setCacheLimit(Runtime.getRuntime().maxMemory() / 4).build();
        assertNotNull(loader);

    }

}
