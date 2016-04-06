package com.yinuo.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by ludexiang on 2016/4/6.
 */
public class AppUtils {

    /**obtain screen width and height
     * screen[0] is width
     * screen[1] is height
     */
    public static int[] obtainScreenWidthAndHeight(Context context) {
        int[] screen = new int[2];
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screen[0] = outMetrics.widthPixels;
        screen[1] = outMetrics.heightPixels;
        return screen;
    }
}
