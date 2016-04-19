package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.yinuo.adapter.LoanGridViewAdapter;
import com.yinuo.mode.LoanGridViewModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/19.
 */
public class LoanGridView extends GridView {

    private LoanGridViewAdapter mAdapter;

    public LoanGridView(Context context) {
        this(context, null);
    }

    public LoanGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoanGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mAdapter = new LoanGridViewAdapter(context);
    }

    public void setOptions(List<LoanGridViewModel> modelList) {
        mAdapter.setItems(modelList);
        setAdapter(mAdapter);
    }

    public LoanGridViewAdapter getLoanAdapter() {
        return mAdapter;
    }
}
