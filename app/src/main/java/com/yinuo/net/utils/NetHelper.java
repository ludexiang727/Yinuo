package com.yinuo.net.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gus on 16/4/23.
 * check net work
 */
public class NetHelper {

    private static NetHelper sHelper;
    private static Context sContext;

    private NetHelper(Context context) {
        sContext = context;
    }

    public static NetHelper getInstance(Context context) {
        return NetWorkFactory.getInstance(context);
    }

    private static class NetWorkFactory {
        public static NetHelper getInstance(Context context) {
            if (sHelper == null) {
                sHelper = new NetHelper(context);
            }
            return sHelper;
        }
    }

    public static boolean isNetworkConnected() {
        if (sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected() {
        if (sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected() {
        if (sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType() {
        if (sContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
