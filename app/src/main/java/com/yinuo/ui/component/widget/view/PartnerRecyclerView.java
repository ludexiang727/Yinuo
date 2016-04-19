package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import com.yinuo.adapter.PartnerRecyclerViewAdapter;
import com.yinuo.mode.PartnerRecyclerModel;
import com.yinuo.ui.component.widget.baseview.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class PartnerRecyclerView <E extends PartnerRecyclerModel> extends BaseRecyclerView {

    private PartnerRecyclerViewAdapter mAdapter;
    private List<E> mBindModels = new ArrayList<E>();

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

    public void bindDatas(List<E> datas) {
        if (datas != null) {
            mBindModels.addAll(datas);
        }
        mAdapter.loadData(mBindModels);
        setAdapter(mAdapter);
    }

    @Override
    public PartnerRecyclerViewAdapter getRecyclerAdapter() {
        return mAdapter;
    }
}
