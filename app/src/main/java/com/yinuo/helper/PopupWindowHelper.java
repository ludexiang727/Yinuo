package com.yinuo.helper;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by ludexiang on 2016/5/16.
 */
public class PopupWindowHelper {
    private PopupWindow mPopupWindow;
    private static PopupWindowHelper sPopupHelper;
    private int mPopWidth;
    private int mPopHeight;
    private int mPopAnimation = -1;

    private PopupWindowHelper() {
    }

    public static PopupWindowHelper getInstance() {
        return PopupFactory.create();
    }

    private static final class PopupFactory {
        private static PopupWindowHelper create() {
            if (sPopupHelper == null) {
                sPopupHelper = new PopupWindowHelper();
            }
            return sPopupHelper;
        }
    }

    public void setPopWH(int width, int height) {
        mPopWidth = width;
        mPopHeight = height;
    }

    public void setPopAnimation(int popAnimation) {
        mPopAnimation = popAnimation;
    }

    public void showPopInLocation(View contentView, View dropView, int gravity, int x, int y) {
        if (mPopWidth == 0 || mPopHeight == 0) {
            mPopWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            mPopHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        mPopupWindow = new PopupWindow(contentView, mPopWidth, mPopHeight);
        mPopupWindow.setAnimationStyle(mPopAnimation);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        /** popup window showAtLocation 一定要在上面三个设置的下方 否则 setOutsideTouchable(true)不起作用*/
        mPopupWindow.showAtLocation(dropView, gravity, x, y);
        mPopupWindow.update();
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        if (mPopupWindow != null) {
            return mPopupWindow.isShowing();
        }
        return false;
    }

    public void updatePopHeight(int height) {
        if (mPopupWindow != null) {
            mPopupWindow.update(mPopWidth, height);
        }
    }
}
