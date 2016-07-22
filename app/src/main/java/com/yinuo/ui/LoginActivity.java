package com.yinuo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.ui.component.widget.common.FastClearEditLayout;
import com.yinuo.ui.component.widget.common.FlipperViewGroup;
import com.yinuo.utils.ResUtils;

/**
 * Created by ludexiang on 16/5/31.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, FastClearEditLayout.ITextWatcherListener {
    private final int UPDATE_MILL = 0x001;
    private int COUNT_DOWN = 60;
    private boolean isCountDown = true;
    private ImageView mBack;
    private TextView mRight;
    private FlipperViewGroup mFlipperViewGroup;
    private FastClearEditLayout mAccountLayout;
    private FastClearEditLayout mPwdLayout;
    private FastClearEditLayout mTelLayout;
    private FastClearEditLayout mCheckNumLayout;
    private TextView mConfirm;
    private TextView mObtainCheckNum;
    private TextView mForgetPwd;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_MILL: {
                    mObtainCheckNum.setText(String.format(ResUtils.getString(LoginActivity.this, R.string.app_login_obtain_check_num_count_down), msg.arg1));
                    if (msg.arg1 == 0) {
                        mObtainCheckNum.setEnabled(true);
                        mObtainCheckNum.setText(ResUtils.getString(LoginActivity.this, R.string.app_login_obtain_check_num));
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected int getTitleLayout() {
        return R.layout.app_login_title_layout;
    }

    @Override
    protected void initTitleView(View titleView) {
        mBack = (ImageView) titleView.findViewById(R.id.app_login_back);
        mRight = (TextView) titleView.findViewById(R.id.app_login_right);

        mBack.setOnClickListener(this);
        mRight.setOnClickListener(this);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.app_login_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle(true);
    }

    @Override
    protected void loadView(View view) {
        mFlipperViewGroup = (FlipperViewGroup) view.findViewById(R.id.login_flipper_view_group);
        mAccountLayout = (FastClearEditLayout) view.findViewById(R.id.login_account);
        mPwdLayout = (FastClearEditLayout) view.findViewById(R.id.login_pwd);
        mTelLayout = (FastClearEditLayout) view.findViewById(R.id.login_tel);
        mCheckNumLayout = (FastClearEditLayout) view.findViewById(R.id.login_check_num);
        mObtainCheckNum = (TextView) view.findViewById(R.id.login_obtain_check_num);
        mForgetPwd = (TextView) view.findViewById(R.id.app_login_forget_pwd);
        mConfirm = (TextView) view.findViewById(R.id.app_login_confirm);

        mTelLayout.setTextWatcherListener(this);
        mObtainCheckNum.setOnClickListener(this);
        mForgetPwd.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        dismissLoading();
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_login_back: {
                break;
            }
            case R.id.app_login_right: {
                // enter register page
                break;
            }
            case R.id.login_obtain_check_num: {
                // obtain check number
                countDown();
                mObtainCheckNum.setEnabled(false);
                break;
            }
            case R.id.app_login_forget_pwd: {
                // forget password
                break;
            }
            case R.id.app_login_confirm: {
                isCountDown = false;
                break;
            }
        }
    }

    private void showInputMethod(FastClearEditLayout layout) {
        EditText edit = layout.getEditText();
        edit.setFocusable(true);
        edit.requestFocus();
    }

    @Override
    public void onTextWatcher(CharSequence s) {
        if (s == null || s.length() < 11) {
            // is tel phone number
            mObtainCheckNum.setEnabled(false);
            return;
        }
        mObtainCheckNum.setEnabled(true);
    }

    private void countDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    Message msg = mHandler.obtainMessage();
                    msg.arg1 = COUNT_DOWN;
                    msg.what = UPDATE_MILL;
                    msg.sendToTarget();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    COUNT_DOWN--;
                } while (COUNT_DOWN >= 0 && isCountDown);
                COUNT_DOWN = 60;
            }
        }).start();
    }
}
