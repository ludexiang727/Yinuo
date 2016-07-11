package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.yinuo.adapter.BossOnlineRecyclerViewAdapter;
import com.yinuo.base.BaseObject;
import com.yinuo.ui.component.baseview.BaseRecyclerView;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/21.
 */
public class BossOnlineRecyclerView extends BaseRecyclerView {
    private BossOnlineRecyclerViewAdapter mAdapter;

    public BossOnlineRecyclerView(Context context) {
        this(context, null);
    }

    public BossOnlineRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BossOnlineRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mAdapter = new BossOnlineRecyclerViewAdapter(context);
        setLayoutManager(new LinearLayoutManager(context));
        addItemDecoration(new RecyclerSpaceDecoration(context, RecyclerSpaceDecoration.VERTICAL_LIST));
    }

    @Override
    public <T extends BaseObject> void bindRecycleView(List<T> lists) {
        mAdapter.loadData(lists);

        setAdapter(mAdapter);
    }

    @Override
    public BossOnlineRecyclerViewAdapter getRecyclerAdapter() {
        return mAdapter;
    }
}
