package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;

import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.base.BaseObject;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/21.
 */
public class BossOnlineRecyclerView extends BaseRecyclerView {
    public BossOnlineRecyclerView(Context context) {
        this(context, null);
    }

    public BossOnlineRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BossOnlineRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public <T extends BaseObject> void bindRecycleView(List<T> lists) {

    }

    @Override
    public <T extends BaseRecyclerAdapter> T getRecyclerAdapter() {
        return null;
    }
}
