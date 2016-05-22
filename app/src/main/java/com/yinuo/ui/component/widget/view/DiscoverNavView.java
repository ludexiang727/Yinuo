package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.utils.ResUtils;

/**
 * Created by gus on 16/4/17.
 */
public class DiscoverNavView extends TextView {

    private int mNavViewPaddingTB;
    private int mNavViewPaddingLR;

    public DiscoverNavView(Context context) {
        this(context, null);
    }

    public DiscoverNavView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscoverNavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNavViewPaddingTB = ResUtils.getInt(context, R.dimen.discovery_page_nav_margin_tb);
        mNavViewPaddingLR = ResUtils.getInt(context, R.dimen.discovery_page_nav_margin_lr);
        setPadding(mNavViewPaddingLR, mNavViewPaddingTB, mNavViewPaddingLR, mNavViewPaddingTB);
        setClickable(true);
        setGravity(Gravity.CENTER);
    }


    public void setNavText(String navOptions) {
        setText(navOptions);
    }

    public void setNavBackground(boolean isSelected) {
        if (isSelected) {
            setTextColor(Color.WHITE);
            setBackgroundResource(R.drawable.discover_navigate_background);
        } else {
            setTextColor(Color.parseColor("#CC909090"));
            setBackgroundResource(0);
        }
    }

}
