package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.utils.ResUtils;

/**
 * Created by gus on 16/4/17.
 */
public class DiscoverNavView extends TextView implements View.OnClickListener {

    private int mNavViewPadding;

    public DiscoverNavView(Context context) {
        this(context, null);
    }

    public DiscoverNavView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscoverNavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNavViewPadding = ResUtils.getInt(context, R.dimen.discovery_page_nav_margin);
        setPadding(mNavViewPadding, mNavViewPadding, mNavViewPadding, mNavViewPadding);
        setOnClickListener(this);
    }


    public void setNavText(String navOptions, boolean colorState) {
        if (colorState) {
            setTextColor(Color.parseColor("#ccff4081"));
        } else {
            setTextColor(Color.parseColor("#ccffffff"));
        }

        setText(navOptions);
    }

    @Override
    public void onClick(View view) {

    }
}
