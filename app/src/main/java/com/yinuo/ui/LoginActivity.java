package com.yinuo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.ui.component.widget.view.common.FastClearEditLayout;
import com.yinuo.ui.component.widget.view.common.FlipperViewGroup;

/**
 * Created by ludexiang on 16/5/31.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mRight;
    private FlipperViewGroup mFlipperViewGroup;
    private FastClearEditLayout mAccountLayout;
    private FastClearEditLayout mPwdLayout;
    private FastClearEditLayout mCheckNumLayout;

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
        mCheckNumLayout = (FastClearEditLayout) view.findViewById(R.id.login_check_num);
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
                break;
            }
        }
    }

    private void showInputMethod(FastClearEditLayout layout) {
        EditText edit = layout.getEditText();
        edit.setFocusable(true);
        edit.requestFocus();
    }
}
