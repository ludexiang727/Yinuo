package com.yinuo.ui.sub.loan;

import android.os.Bundle;
import android.view.View;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.net.base.NetBaseObject;

/**
 * Created by ludexiang on 2016/4/20.
 * balance rise for loan -- 余额升息
 */
public class LoanBalanceActivity extends BaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.loan_option_balance_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void loadView(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
