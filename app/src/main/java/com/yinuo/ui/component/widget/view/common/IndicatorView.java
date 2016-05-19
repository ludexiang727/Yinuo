package com.yinuo.ui.component.widget.view.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yinuo.R;


/**
 * Created by ludexiang on 16-4-5.
 */
public class IndicatorView extends LinearLayout {
    private int[] mDotViews = new int[]{R.drawable.app_header_dot_normal, R.drawable.app_header_dot_selected};
    private int mPadding;
    private Resources mResources;
    private int mCurrent;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mResources = getResources();

        setOrientation(HORIZONTAL);

        mPadding = mResources.getDimensionPixelOffset(R.dimen.app_header_indicator_padding);
    }

    public void snapToScreen(int which) {
        if (mCurrent < getChildCount()) {
            ((ImageView) getChildAt(mCurrent)).setImageResource(mDotViews[0]);
            ((ImageView) getChildAt(which)).setImageResource(mDotViews[1]);
            mCurrent = which;
        }
    }

    public void addDotView(int addSize) {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

        for (int i = 0; i < addSize; i++) {
            ImageView dotView = new ImageView(getContext());
            dotView.setImageResource(mDotViews[0]);
            dotView.setScaleType(ImageView.ScaleType.FIT_XY);
            dotView.setPadding(mPadding, 0, mPadding, mPadding);
            addView(dotView, i, params);
            bringToFront();
        }
    }
}
