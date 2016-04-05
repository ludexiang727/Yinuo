package com.yinuo.ui.component.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class Loading extends FrameLayout {

    private LayoutInflater mInflater;
    private ImageView mLoadingImg;
    private TextView mLoadingError;
    private AnimationDrawable mAnimationDrawable;

    public Loading(Context context) {
        this(context, null);
    }

    public Loading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Loading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.loading_layout, null);
        mLoadingImg = (ImageView) view.findViewById(R.id.loading_status);
        mLoadingError = (TextView) view.findViewById(R.id.loading_error);

        addView(view);
        hide();
        mAnimationDrawable = (AnimationDrawable) mLoadingImg.getBackground();
    }

    public void loading() {
        show();
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        if (mAnimationDrawable != null) {
            mAnimationDrawable.start();
        }
    }

    public void dismiss() {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        hide();
    }

    private void show() {
        setVisibility(View.VISIBLE);
    }

    private void hide() {
        setVisibility(View.GONE);
    }
}
