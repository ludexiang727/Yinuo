package com.yinuo.ui.sub.loan;

import android.os.Bundle;
import android.view.View;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.net.base.NetBaseObject;

/**
 * Created by ludexiang on 16/5/30.
 */
public class LoanInsuranceActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.loan_insurance_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadView(View view) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }
}
