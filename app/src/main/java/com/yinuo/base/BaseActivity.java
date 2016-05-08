package com.yinuo.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.ui.component.widget.Loading;
import com.yinuo.utils.ResUtils;

/**
 * Created by Administrator on 2016/4/9.
 */
public abstract class BaseActivity extends Activity implements IRequestListener {
    private int mLayoutId;
    private Loading mLoading;
    private ViewStub mViewStub;
    private RelativeLayout mContentParent;
    private UIHandler mHandler = new UIHandler();
    private RelativeLayout mTitleParent;
    private ImageView mTitleLeft;
    private LinearLayout mTitleRight;
    private TextView mTitleMiddle;

    private int mTitleRightChildLeftMargin;

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
        mTitleRightChildLeftMargin = ResUtils.getDimen(this, R.dimen.app_title_right_child_margin);
        setContentView(R.layout.app_base_activity_layout);
        mTitleParent = (RelativeLayout) findViewById(R.id.app_activity_title_layout);
        mTitleLeft = (ImageView) findViewById(R.id.app_activity_title_left);
        mTitleRight = (LinearLayout) findViewById(R.id.app_activity_title_right);
        mTitleMiddle = (TextView) findViewById(R.id.app_activity_title_middle);
        mLoading = (Loading) findViewById(R.id.app_activity_loading);
        mViewStub = (ViewStub) findViewById(R.id.app_activity_viewstub);
        mContentParent = (RelativeLayout) findViewById(R.id.app_activity_content_parent);
        View view = LayoutInflater.from(this).inflate(mLayoutId, mContentParent, true);
        mLoading.loading();
        loadData();
        loadView(view);
    }

    @Override
    public void onFail(NetBaseObject object) {
//        mHandler.sendEmptyMessage(UIHandler.LOAD_DATA_FAILURE);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        mHandler.sendEmptyMessage(UIHandler.LOAD_DATA_SUCCESSFUL);
    }

    protected void showTitle(boolean isShow) {
        mTitleParent.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    protected void setMiddleTitle(String title) {
        mTitleMiddle.setText(title);
    }

    protected void setMiddleTitle(int id) {
        mTitleMiddle.setText(id);
    }

    protected void setLeftImg(int resId) {
        mTitleLeft.setImageResource(resId);
    }

    protected void setRightChild(View... vs) {
        if (mTitleRight != null && mTitleRight.getChildCount() > 0) {
            mTitleRight.removeAllViews();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = mTitleRightChildLeftMargin;
        for (int i = 0; i < vs.length; ++i) {
            mTitleRight.addView(vs[i], i, params);
        }
    }

    public void dismissLoading() {
        mLoading.dismiss();
        mContentParent.setVisibility(View.VISIBLE);
    }
}
