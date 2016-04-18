package com.yinuo.ui.component.widget.view;

import android.content.Context;

import com.yinuo.adapter.WorkspaceRecyclerViewAdapter;
import com.yinuo.adapter.base.BaseRecyclerAdapter;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;

/**
 * Created by ludexiang on 2016/4/18.
 */
public class WorkspaceRecyclerView extends BaseRecyclerView {
    private WorkspaceRecyclerViewAdapter mAdapter;

    public WorkspaceRecyclerView(Context context) {
        super(context);
    }

    @Override
    protected <T extends BaseRecyclerAdapter> T getRecyclerAdapter() {
        return null;
    }
}
