package com.yinuo.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ludexiang on 2016/5/6.
 */
public class UiThreadHandler {
    private static Handler sUiHandler = new Handler(Looper.getMainLooper());
    private static Object sToken = new Object();

    private UiThreadHandler() {
    }

    public static final boolean post(Runnable r) {
        return sUiHandler == null?false:sUiHandler.post(r);
    }

    public static final boolean postDelayed(Runnable r, long delayMillis) {
        return sUiHandler == null?false:sUiHandler.postDelayed(r, delayMillis);
    }

    public static final Handler getsUiHandler() {
        return sUiHandler;
    }

    public static final boolean postOnceDelayed(Runnable r, long delayMillis) {
        if(sUiHandler == null) {
            return false;
        } else {
            sUiHandler.removeCallbacks(r, sToken);
            return sUiHandler.postDelayed(r, delayMillis);
        }
    }

    public static void removeCallbacks(Runnable runnable) {
        if(sUiHandler != null) {
            sUiHandler.removeCallbacks(runnable);
        }
    }
}
