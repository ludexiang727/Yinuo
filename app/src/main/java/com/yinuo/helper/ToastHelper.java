package com.yinuo.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yinuo.R;
import com.yinuo.utils.ResUtils;

/**
 * Created by ludexiang on 2016/5/3.
 */
public class ToastHelper {

    private Context mContext;
    private static ToastHelper sHelper;
    private int mMarginBottom;
    private int mMarginTop;

    public enum ToastLocation {
        TOP, CENTER, BOTTOM
    }

    private ToastHelper(Context context) {
        mContext = context;
        mMarginBottom = ResUtils.getDimen(mContext, R.dimen.app_toast_margin_bottom);
        mMarginTop = ResUtils.getDimen(mContext, R.dimen.app_toast_margin_top);
    }

    public static ToastHelper getInstance(Context context) {
        return Factory.create(context);
    }

    private static final class Factory {
        public static ToastHelper create(Context context) {
            if (sHelper == null) {
                synchronized (Factory.class) {
                    sHelper = new ToastHelper(context);
                }
            }
            return sHelper;
        }
    }

    public void showLongToast(String msg, ToastLocation location) {
        Toast toast = createToast(location);
        toast.setView(loadView(0, msg));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void showLongToast(int msgId, ToastLocation location) {
        Toast toast = createToast(location);
        toast.setView(loadView(0, msgId));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void showShortToast(String msg, ToastLocation location) {
        Toast toast = createToast(location);
        toast.setView(loadView(0, msg));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showShortToast(int msgId, ToastLocation location) {
        Toast toast = createToast(location);
        toast.setView(loadView(0, msgId));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showLongToastWithIcon(int resId, String msg, ToastLocation location) {
        Toast toast = createToast(location);
        toast.setView(loadView(resId, msg));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void showShortToastWithIcon(int resId, String msg, ToastLocation location) {
        Toast toast = createToast(location);
        toast.setView(loadView(resId, msg));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private Toast createToast(ToastLocation loc) {
        Toast toast = new Toast(mContext);
        if (loc == ToastLocation.TOP) {
            toast.setGravity(Gravity.TOP, 0, mMarginTop);
        } else if (loc == ToastLocation.CENTER) {
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else if (loc == ToastLocation.BOTTOM) {
            toast.setGravity(Gravity.BOTTOM, 0, mMarginBottom);
        }
        return toast;
    }

    private View loadView(int resId, String info) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.app_toast_layout, null);
        ImageView img = (ImageView) view.findViewById(R.id.app_toast_img);
        TextView msg = (TextView) view.findViewById(R.id.app_toast_msg);
        msg.setText(info);
        img.setImageResource(resId);
        return view;
    }

    private View loadView(int resId, int msgId) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.app_toast_layout, null);
        ImageView img = (ImageView) view.findViewById(R.id.app_toast_img);
        TextView msg = (TextView) view.findViewById(R.id.app_toast_msg);
        msg.setText(msgId);
        img.setImageResource(resId);
        return view;
    }
}
