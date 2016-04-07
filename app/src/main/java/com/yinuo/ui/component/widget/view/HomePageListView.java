package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class HomePageListView extends ListView {
    public HomePageListView(Context context) {
        this(context, null);
    }

    public HomePageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
