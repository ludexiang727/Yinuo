package com.yinuo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.mode.NotifyMsgModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetNotifyMsgObj;
import com.yinuo.ui.component.widget.DialogLoading;
import com.yinuo.ui.component.widget.view.NotifyMsgListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 16/6/27.
 */
public class NotifyMsgActivity extends BaseActivity implements View.OnClickListener {
    private TextView mNotifyChange;
    private NotifyMsgListView mListView;
    private UIHandler mHandler = new UIHandler();
    private List<NotifyMsgModel> mItems = new ArrayList<NotifyMsgModel>();
    private DialogLoading mDialogLoading;

    @Override
    protected int getContentLayout() {
        return R.layout.app_notify_msg_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle(true);
        setMiddleTitle(R.string.mine_news);
    }

    @Override
    protected void loadData() {
        NetRequest.getInstance().requestNotifyMsg(0, this);
    }

    @Override
    protected void loadView(View view) {
        mListView = (NotifyMsgListView) view.findViewById(R.id.notify_list_view);
        mListView.setData(mItems);
        mNotifyChange = (TextView) view.findViewById(R.id.app_notify_change);
        mNotifyChange.setOnClickListener(this);
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetNotifyMsgObj) {
            NetNotifyMsgObj notify = (NetNotifyMsgObj) object;
            Message msg = mHandler.obtainMessage();
            msg.obj = notify;
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.sendToTarget();
        }
    }

    private class UIHandler extends Handler {
        private final int NOTIFY_SUCCESS = 0x000;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    if (mDialogLoading != null) {
                        mDialogLoading.stopAnim();
                        mDialogLoading = null;
                    }
                    NetNotifyMsgObj notify = (NetNotifyMsgObj) msg.obj;
                    if (notify != null && notify.getData() != null) {
                        mItems.add(notify.getData());
                        mListView.getNotifyAdapter().notifyDataSetChanged();
                    }
                    break;
                }
            }
        }
    }

    private void changeNotifyMsg() {
        mDialogLoading = new DialogLoading(this, R.style.DialogTheme);
        mDialogLoading.setViewLayout(R.layout.dialog_loading_layout);
        mDialogLoading.setDialogTxt(R.string.app_loading_data);
        mDialogLoading.startAnim();
        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_notify_change: {
                changeNotifyMsg();
                break;
            }
        }
    }
}
