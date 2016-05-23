package com.yinuo.ui;

import android.os.Bundle;
import android.view.View;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;

/**
 * Created by ludexiang on 2016/5/23.
 */
public class MineActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.mine_page_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData() {
        dismissLoading();
    }

    @Override
    protected void loadView(View view) {

    }
}
