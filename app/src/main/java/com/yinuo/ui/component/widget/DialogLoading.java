package com.yinuo.ui.component.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.utils.ResUtils;

/**
 * Created by ludexiang on 2016/4/26.
 */
public class DialogLoading extends Dialog {

    private LayoutInflater mInflater;
    private ImageView mDialogImg;
    private TextView mDialogTxt;
    private AnimationDrawable mAnimation;

    public DialogLoading(Context context, int theme) {
        super(context, theme);
        mInflater = LayoutInflater.from(context);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.dimAmount = 0.7f;
        params.width = ResUtils.getDimen(context, R.dimen.app_dialog_loading_wh);
        params.height = ResUtils.getDimen(context, R.dimen.app_dialog_loading_wh);
        window.setAttributes(params);
        setCancelable(false);
    }

    public void setViewLayout(int layout) {
        View view = mInflater.inflate(layout, null);
        mDialogImg = (ImageView) view.findViewById(R.id.dialog_loading_img);
        mDialogTxt = (TextView) view.findViewById(R.id.dialog_loading_txt);

        setContentView(view);
        setCanceledOnTouchOutside(false);
        mAnimation = (AnimationDrawable) mDialogImg.getBackground();
    }

    public void setDialogTxt(String txt) {
        mDialogTxt.setText(txt);
    }

    public void setDialogTxt(int resId) {
        mDialogTxt.setText(resId);
    }

    public void stopAnim() {
        if (mAnimation != null) {
            mAnimation.stop();
        }
        dismiss();
    }

    public void startAnim() {
        if (mAnimation != null && mAnimation.isRunning()) {
            mAnimation.stop();
        }

        if (mAnimation != null) {
            mAnimation.start();
        }
        show();
    }
}
