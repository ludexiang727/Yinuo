package com.yinuo.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.yinuo.R;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.ui.component.widget.Loading;

/**
 * Created by ludexiang on 2016/4/28.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements IRequestListener {
    private int mLayoutId;
    private Loading mLoading;
    private ViewStub mViewStub;
    private RelativeLayout mContentParent;
    private UIHandler mHandler = new UIHandler();

    protected abstract int getContentLayout();
    protected abstract void loadData();
    protected abstract void loadView(View view);

    private final class UIHandler extends Handler {
        private static final int LOAD_DATA_SUCCESSFUL = 0x000;
        private static final int LOAD_DATA_FAILURE = 0x001;
        @Override
        public void handleMessage(Message msg) {
            mLoading.dismiss();
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_DATA_SUCCESSFUL: {
                    mContentParent.setVisibility(View.VISIBLE);
                    break;
                }
                case LOAD_DATA_FAILURE: {
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = getContentLayout();
        if (mLayoutId == 0) {
            throw new IllegalArgumentException("content layout id is Illegal");
        }
        setContentView(R.layout.app_base_activity_layout);
        mLoading = (Loading) findViewById(R.id.app_activity_loading);
        mViewStub = (ViewStub) findViewById(R.id.app_activity_viewstub);
        mContentParent = (RelativeLayout) findViewById(R.id.app_activity_content_parent);
        View view = LayoutInflater.from(this).inflate(mLayoutId, mContentParent, true);
        loadData();
        loadView(view);
        mLoading.loading();
    }

    @Override
    public void onFail(NetBaseObject object) {
//        mHandler.sendEmptyMessage(UIHandler.LOAD_DATA_FAILURE);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        mHandler.sendEmptyMessage(UIHandler.LOAD_DATA_SUCCESSFUL);
    }
}
