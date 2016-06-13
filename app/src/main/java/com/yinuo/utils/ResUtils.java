package com.yinuo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/4/9.
 */
public class ResUtils {
    public static String getString(Context context, int strId) {
        return context.getString(strId);
    }

    public static int getInt(Context context, int intId) {
        return context.getResources().getDimensionPixelOffset(intId);
    }

    public static int getColor(Context context, int clrId) {
        return context.getResources().getColor(clrId);
    }

    public static int getDimen(Context context, int dimenId) {
        return context.getResources().getDimensionPixelOffset(dimenId);
    }

    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }
}
