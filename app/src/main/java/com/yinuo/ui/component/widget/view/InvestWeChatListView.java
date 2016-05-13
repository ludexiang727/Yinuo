package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;

import com.yinuo.adapter.InvestListViewAdapter;
import com.yinuo.mode.InvestWeChatModel;
import com.yinuo.ui.component.widget.baseview.BaseBezierRefreshListView;

import java.util.List;

/**
 * Created by ludexiang on 2016/5/4.
 */
public class InvestWeChatListView extends BaseBezierRefreshListView {

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
        setAdapter(mAdapter);
    }

    public void setItems(List<InvestWeChatModel> models) {
        mAdapter.setList(models);
    }

    public InvestListViewAdapter getInvestAdapter() {
        return mAdapter;
    }

}
