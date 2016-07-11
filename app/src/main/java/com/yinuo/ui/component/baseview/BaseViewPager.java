package com.yinuo.ui.component.baseview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by gus on 16/4/23.
 */
public class BaseViewPager extends ViewPager {

    public BaseViewPager(Context context) {
        this(context, null);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
