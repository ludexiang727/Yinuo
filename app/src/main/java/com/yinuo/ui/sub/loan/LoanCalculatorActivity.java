package com.yinuo.ui.sub.loan;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.ui.component.widget.view.LoanCalculatorView;

/**
 * Created by ludexiang on 2016/4/20.
 * calculator for loan -- 计算器
 */
public class LoanCalculatorActivity extends BaseActivity implements View.OnClickListener {
    private EditText mLoanPriceEdit;
    private TextView mAfterTax;
    private TextView mPreTax;
    private LoanCalculatorView mLoanCalculatorView;


    @Override
    protected int getContentLayout() {
        return R.layout.loan_option_calculator_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle(true);
        setMiddleTitle(R.string.loan_page_calculator_title);
    }

    @Override
    protected void loadData() {
        dismissLoading();
    }

    @Override
    protected void loadView(View view) {
        mLoanPriceEdit = (EditText) view.findViewById(R.id.loan_calculator_page_input_price);
        mAfterTax = (TextView) view.findViewById(R.id.loan_calculator_page_after_tax);
        mPreTax = (TextView) view.findViewById(R.id.loan_calculator_page_pre_tax);
        mLoanCalculatorView = (LoanCalculatorView) view.findViewById(R.id.loan_page_calculator_view);

        mAfterTax.setOnClickListener(this);
        mPreTax.setOnClickListener(this);
        mLoanPriceEdit.requestFocus();
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
            case R.id.loan_calculator_page_after_tax: {
                break;
            }
            case R.id.loan_calculator_page_pre_tax: {
                break;
            }
        }
    }
}
