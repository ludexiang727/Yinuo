package com.yinuo.ui.sub.invest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yinuo.Constants;
import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.mode.InvestWeChatModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetInvestWeChatObj;
import com.yinuo.ui.component.widget.view.InvestWeChatListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/5/4.
 */
public class InvestWeChatActivity extends BaseActivity implements View.OnClickListener {

    private int mPageIndex = 1;
    private final int PAGE_COUNT = 10;
    private UIHandler mHandler = new UIHandler();
    private int mBossId;

    private List<InvestWeChatModel> mModels = new ArrayList<InvestWeChatModel>();

    private InvestWeChatListView mListView;
    private TextView mSend;
    private EditText mMsgEdit;

    @Override
    protected int getContentLayout() {
        return R.layout.invest_wechat_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mBossId = intent.getIntExtra(Constants.INVEST_WECHAT_BOSS_ID, -1);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData() {
        NetRequest.getInstance().requestWeChatData(mPageIndex, PAGE_COUNT, mBossId, "", this);
    }

    @Override
    protected void loadView(View view) {
        mListView = (InvestWeChatListView) view.findViewById(android.R.id.list);
        mMsgEdit = (EditText) view.findViewById(R.id.invest_wechat_edit);
        mSend = (TextView) view.findViewById(R.id.invest_wechat_send);

        mListView.setItems(mModels);
        mSend.setOnClickListener(this);
        mMsgEdit.setOnClickListener(this);
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetInvestWeChatObj) {
            NetInvestWeChatObj weChat = (NetInvestWeChatObj) object;
            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.obj = weChat;
            msg.sendToTarget();
        }
    }

    private final class UIHandler extends Handler {
        private final int NOTIFY_SUCCESS = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    NetInvestWeChatObj obj = (NetInvestWeChatObj) msg.obj;
                    if (obj.getModels() != null) {
                        mModels.addAll(obj.getModels());
                    }
                    mListView.getInvestAdapter().notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invest_wechat_send: {
                break;
            }
            case R.id.invest_wechat_edit: {
//                mListView.scrollBottom();
                break;
            }
        }
    }
}
