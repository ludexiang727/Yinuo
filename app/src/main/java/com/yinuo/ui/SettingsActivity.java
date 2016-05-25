package com.yinuo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;

/**
 * Created by ludexiang on 2016/5/23.
 */
public class SettingsActivity extends BaseActivity {
    private RelativeLayout mChangeAccount;
    private RelativeLayout mChangeTel;
    private RelativeLayout mChangePwd;
    private RelativeLayout mChangeGesture;
    private RelativeLayout mMsgNotify;
    private RelativeLayout mPrivateSetting;
    private RelativeLayout mClearMemory;
    private RelativeLayout mSuggestBack;
    private RelativeLayout mUsuallyQuestion;
    private RelativeLayout mVersionCheck;
    private TextView mCurrentVersion;
    private RelativeLayout mAboutYinuo;
    private TextView mSingOut;

    @Override
    protected int getContentLayout() {
        return R.layout.settings_page_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle(true);
        setMiddleTitle(R.string.setting_title);
    }

    @Override
    protected void loadView(View view) {
        mChangeAccount = (RelativeLayout) view.findViewById(R.id.setting_change_account_layout);
        mChangeTel = (RelativeLayout) view.findViewById(R.id.setting_change_tel_layout);
        mChangePwd = (RelativeLayout) view.findViewById(R.id.setting_change_pwd_layout);
        mChangeGesture = (RelativeLayout) view.findViewById(R.id.setting_change_gesture_layout);

        mMsgNotify = (RelativeLayout) view.findViewById(R.id.setting_notify_layout);
        mPrivateSetting = (RelativeLayout) view.findViewById(R.id.setting_private_layout);
        mClearMemory = (RelativeLayout) view.findViewById(R.id.setting_clear_memory_layout);
        mSuggestBack = (RelativeLayout) view.findViewById(R.id.setting_suggest_layout);
        mUsuallyQuestion = (RelativeLayout) view.findViewById(R.id.setting_usually_layout);
        mVersionCheck = (RelativeLayout) view.findViewById(R.id.setting_version_layout);
        mCurrentVersion = (TextView) view.findViewById(R.id.setting_current_version);
        mAboutYinuo = (RelativeLayout) view.findViewById(R.id.setting_about_yinuo_layout);
        mSingOut = (TextView) view.findViewById(R.id.settings_sign_out);
    }

    @Override
    protected void loadData() {
        dismissLoading();
    }

}
