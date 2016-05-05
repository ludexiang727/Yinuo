package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.yinuo.adapter.InvestListViewAdapter;
import com.yinuo.mode.InvestWeChatModel;

import java.util.List;

/**
 * Created by ludexiang on 2016/5/4.
 */
public class InvestWeChatListView extends ListView {

    private InvestListViewAdapter mAdapter;

    public InvestWeChatListView(Context context) {
        this(context, null);
    }

    public InvestWeChatListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InvestWeChatListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAdapter = new InvestListViewAdapter(context);
    }

    public void setItems(List<InvestWeChatModel> models) {
        mAdapter.setItems(models);
        setAdapter(mAdapter);
    }

    public InvestListViewAdapter getInvestAdapter() {
        return mAdapter;
    }

}
