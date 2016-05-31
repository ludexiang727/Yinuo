package com.yinuo.ui.sub.loan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.utils.ResUtils;
import com.yinuo.utils.StringUtils;

/**
 * Created by ludexiang on 2016/4/20.
 * extremity loan for loan -- 极速贷款
 */
public class LoanExtremityActivity extends BaseActivity {
    private ImageView mTitleBackground;
    private ImageView mTitleBack;
    private TextView mExtremityLimit;


    @Override
    protected int getTitleLayout() {
        return R.layout.loan_extremity_title_layout;
    }

    @Override
    protected void initTitleView(View titleView) {
        mTitleBackground = (ImageView) titleView.findViewById(R.id.loan_extremity_title_background);
        mTitleBack = (ImageView) titleView.findViewById(R.id.loan_extremity_title_back);
        mExtremityLimit = (TextView) titleView.findViewById(R.id.loan_extremity_limit);
        mExtremityLimit.setText(StringUtils.richString(ResUtils.getString(this, R.string.loan_extremity_limit)));
    }

    @Override
    protected int getContentLayout() {
        return R.layout.loan_option_extremity_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle(true);
    }

    @Override
    protected void loadView(View view) {

    }

    @Override
    protected void loadData() {
        dismissLoading();
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
