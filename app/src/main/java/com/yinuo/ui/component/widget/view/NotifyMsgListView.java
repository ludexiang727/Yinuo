package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.yinuo.adapter.NotifyMsgListAdapter;
import com.yinuo.mode.NotifyMsgModel;

import java.util.List;

/**
 * Created by ludexiang on 16/6/27.
 */
public class NotifyMsgListView extends ListView {

    private NotifyMsgListAdapter mNotifyAdapter;

    public NotifyMsgListView(Context context) {
        this(context, null);
    }

    public NotifyMsgListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotifyMsgListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mNotifyAdapter = new NotifyMsgListAdapter(context);
    }

    public void setData(List<NotifyMsgModel> items) {
        mNotifyAdapter.setList(items);
        setAdapter(mNotifyAdapter);
    }

    public NotifyMsgListAdapter getNotifyAdapter() {
        return mNotifyAdapter;
    }
}
