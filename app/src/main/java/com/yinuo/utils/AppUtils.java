package com.yinuo.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.View.MeasureSpec;

import java.lang.reflect.Field;

/**
 * Created by ludexiang on 2016/4/6.
 */
public class AppUtils {

    /**obtain screen width and height
     * screen[0] is width
     * screen[1] is height
     * for example huawei meta 7 include navigation
     * navigation is shown we can't monitor
     * so we obtain window change(height) base
     * 导航栏隐藏时 在onSizeChanged()
     * invoke ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(Rect)
     * 来获取屏幕高度
     */
    public static int[] obtainScreenWidthAndHeight(Context context) {
        int[] screen = new int[2];
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screen[0] = outMetrics.widthPixels;
        screen[1] = outMetrics.heightPixels;
//
        return screen;
    }

    /** obtain view location begin
     * 但是:有时不同得手机获取有可能包含状态栏有的手机不包含状态栏～～～坑啊
     * 所以:获取view的高度与屏幕的高度进行对比 == 没有包含状态栏 != 包含状态栏
     **/
    public static int measureView(View view) {
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
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
    /** obtain view location end */

    public static float obtainDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    /** obtain status height -- 获取状态栏高度 */
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;

        int x = 0, statusBarHeight = 38;//通常这个值会是38
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
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
