package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.yinuo.adapter.WorkspaceRecyclerViewAdapter;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.base.BaseObject;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkspaceRecyclerView extends BaseRecyclerView {

    private WorkspaceRecyclerViewAdapter mAdapter;

    public WorkspaceRecyclerView(Context context) {
        this(context, null);
    }

    public WorkspaceRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkspaceRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAdapter = new WorkspaceRecyclerViewAdapter(context);
        addItemDecoration(new RecyclerSpaceDecoration(context, RecyclerSpaceDecoration.VERTICAL_LIST));
        setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public <T extends BaseObject> void bindRecycleView(List<T> lists) {
        mAdapter.loadData(lists);
        setAdapter(mAdapter);
    }

    @Override
    public WorkspaceRecyclerViewAdapter getRecyclerAdapter() {
        return mAdapter;
    }
}
