package com.android.imageloader.cache;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {

    private static final String TAG = "MemoryCache";
    private Map<String, Object> cache=Collections.synchronizedMap(
            new LinkedHashMap<String, Object>(10,1.5f,true));//Last argument true for LRU ordering
    private long size=0;//current allocated size
    private long limit=1000000;//max memory in bytes

    public MemoryCache(long limit){
        //use 25% of available heap size
        setLimit(limit);
    }

    public void setLimit(long new_limit){
        limit=new_limit;
        Log.i(TAG, "MemoryCache will use up to "+limit/1024./1024.+"MB");
    }

    public Object get(String id){
        try{
            if(!cache.containsKey(id))
                return null;
            return cache.get(id);
        }catch(NullPointerException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void put(String id, Object content){
        try{
            if(cache.containsKey(id))
                size-=getSizeInBytes(cache.get(id));
            cache.put(id, content);
            size+=getSizeInBytes(content);
            checkSize();
        }catch(Throwable th){
            th.printStackTrace();
        }
    }

    private void checkSize() {
        Log.i(TAG, "cache size="+size+" length="+cache.size());
        if(size>limit){
            Iterator<Entry<String, Object>> iter=cache.entrySet().iterator();//least recently accessed item will be the first one iterated
            while(iter.hasNext()){
                Entry<String, Object> entry=iter.next();
                size-=getSizeInBytes(entry.getValue());
                iter.remove();
                if(size<=limit)
                    break;
            }
            Log.i(TAG, "Clean cache. New size "+cache.size());
        }
    }

    public void clear() {
        try{
            cache.clear();
            size=0;
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }

    long getSizeInBytes(Object content) {
        if(content==null)
            return 0;
        if(content instanceof Bitmap)
            return ((Bitmap)content).getRowBytes() * ((Bitmap)content).getHeight();
        return ((String)content).getBytes().length;
    }
}