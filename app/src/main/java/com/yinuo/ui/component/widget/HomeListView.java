package com.yinuo.ui.component.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomeListView extends ListView {
    public HomeListView(Context context) {
        this(context, null);
    }

    public HomeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
