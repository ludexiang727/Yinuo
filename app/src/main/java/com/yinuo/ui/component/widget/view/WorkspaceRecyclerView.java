package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;

import com.yinuo.adapter.WorkspaceRecyclerViewAdapter;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;

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
        mAdapter = new WorkspaceRecyclerViewAdapter();
    }

    @Override
    public WorkspaceRecyclerViewAdapter getRecyclerAdapter() {
        return mAdapter;
    }
}