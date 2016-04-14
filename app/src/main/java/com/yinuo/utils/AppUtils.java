package com.yinuo.utils;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;

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

    public static String[] split(String banners, String split) {
        if (StringUtils.isEmpty(banners)) {
            return new String[0];
        }

        return banners.split(split);
    }

    /** include status bar */
    public static int[] viewOnLocationInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    public static int[] viewOnLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }
}
