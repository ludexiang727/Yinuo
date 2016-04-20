package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import com.yinuo.adapter.PartnerRecyclerViewAdapter;
import com.yinuo.base.BaseObject;
import com.yinuo.mode.PartnerRecyclerModel;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class PartnerRecyclerView extends BaseRecyclerView {

    private PartnerRecyclerViewAdapter mAdapter;

    public PartnerRecyclerView(Context context) {
        this(context, null);
    }

    public PartnerRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartnerRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mAdapter = new PartnerRecyclerViewAdapter(context);
        setLayoutManager(new GridLayoutManager(context, 3));
    }

    @Override
    public <T extends BaseObject> void bindRecycleView(List<T> lists) {
        mAdapter.loadData(lists);
        setAdapter(mAdapter);
    }

    @Override
    public PartnerRecyclerViewAdapter getRecyclerAdapter() {
        return mAdapter;
    }
}
