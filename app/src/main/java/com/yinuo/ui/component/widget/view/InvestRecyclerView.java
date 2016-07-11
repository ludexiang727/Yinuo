package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.yinuo.adapter.InvestRecyclerViewAdapter;
import com.yinuo.base.BaseObject;
import com.yinuo.ui.component.baseview.BaseRecyclerView;

import java.util.List;

/**
 * Created by gus on 16/4/23.
 */
public class InvestRecyclerView extends BaseRecyclerView {

    private InvestRecyclerViewAdapter mAdapter;


    public InvestRecyclerView(Context context) {
        this(context, null);
    }

    public InvestRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InvestRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mAdapter = new InvestRecyclerViewAdapter(context);
        setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public InvestRecyclerViewAdapter getRecyclerAdapter() {
        return mAdapter;
    }

    public void isTopAddMargin(boolean add) {
        mAdapter.setAddTopMargin(add);
    }

    @Override
    public <T extends BaseObject> void bindRecycleView(List<T> lists) {
        mAdapter.loadData(lists);
        setAdapter(mAdapter);
    }
}
