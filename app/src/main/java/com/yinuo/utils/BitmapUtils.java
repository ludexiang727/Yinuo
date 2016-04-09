package com.yinuo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by ludexiang on 2016/4/6.
 * BitmapUtils used by ImageView
 * for example : circle round etc.
 */
public class BitmapUtils {

    /**
     * obtain circle bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap circularBitmap(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecodeBounds if set to true return null(no bitmap) but
        // return out (bitmap.width and bitmap height)
        options.inJustDecodeBounds = true;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        BitmapFactory.

        return null;
    }
}
