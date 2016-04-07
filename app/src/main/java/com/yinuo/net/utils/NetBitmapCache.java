package com.yinuo.net.utils;

/**
 * Created by ludexiang on 2016/4/7.
 */
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class NetBitmapCache implements ImageCache {

    private LruCache<String, Bitmap> mCache;

    public NetBitmapCache() {
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = mCache.get(url);
        if (bitmap == null || bitmap.isRecycled())
            return null;
        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}
