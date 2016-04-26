package com.yinuo.ui.sub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.net.base.NetBaseObject;

/**
 * Created by Administrator on 2016/4/26.
 */
public class BossOnlineAboutActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.boss_online_about_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadView(View view) {

    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
    }
}
