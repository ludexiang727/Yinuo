package com.yinuo.ui.component.widget.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by ludexiang on 2016/5/19.
 */
public class HeaderViewGroup extends RelativeLayout {
    public HeaderViewGroup(Context context) {
        this(context, null);
    }

    public HeaderViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                requestDisallowInterceptTouchEvent(false);
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
