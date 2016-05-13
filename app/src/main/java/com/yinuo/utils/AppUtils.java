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

    public static float obtainDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    /**
     * Map a value within a given range to another range.
     * @param value the value to map
     * @param fromLow the low end of the range the value is within
     * @param fromHigh the high end of the range the value is within
     * @param toLow the low end of the range to map to
     * @param toHigh the high end of the range to map to
     * @return the mapped value
     */
    public static double mapValueFromRangeToRange(
            double value,
            double fromLow,
            double fromHigh,
            double toLow,
            double toHigh) {
        double fromRangeSize = fromHigh - fromLow;
        double toRangeSize = toHigh - toLow;
        double valueScale = (value - fromLow) / fromRangeSize;
        return toLow + (valueScale * toRangeSize);
    }
}
