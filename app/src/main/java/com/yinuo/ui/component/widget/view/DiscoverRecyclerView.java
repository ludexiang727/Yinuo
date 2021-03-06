package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.yinuo.adapter.DiscoverRecyclerViewAdapter;
import com.yinuo.base.BaseObject;
import com.yinuo.ui.component.baseview.BaseRecyclerView;

import java.util.List;

/**
 * Created by gus on 16/4/16.
 */
public class DiscoverRecyclerView extends BaseRecyclerView {
    private DiscoverRecyclerViewAdapter mAdapter;

    public DiscoverRecyclerView(Context context) {
        this(context, null);
    }

    public DiscoverRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscoverRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mAdapter = new DiscoverRecyclerViewAdapter(context);

//        addItemDecoration(new RecyclerSpaceDecoration());
        setLayoutManager(new StaggeredGridLayoutManager(2, VERTICAL));
    }

    @Override
    public <T extends BaseObject> void bindRecycleView(List<T> lists) {
        mAdapter.loadData(lists);

        setAdapter(mAdapter);
    }

    @Override
    public DiscoverRecyclerViewAdapter getRecyclerAdapter() {
        return mAdapter;
    }
}
