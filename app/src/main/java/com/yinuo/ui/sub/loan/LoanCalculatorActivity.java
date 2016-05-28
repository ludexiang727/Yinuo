package com.yinuo.ui.sub.loan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.mode.LoanCalculatorModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetLoanCalculatorObj;
import com.yinuo.ui.CityChoosePageActivity;
import com.yinuo.ui.component.widget.DialogLoading;
import com.yinuo.ui.component.widget.view.LoanCalculatorView;
import com.yinuo.utils.StringUtils;

import java.util.List;

/**
 * Created by ludexiang on 2016/4/20.
 * calculator for loan -- 计算器
 */
public class LoanCalculatorActivity extends BaseActivity implements View.OnClickListener {
    private EditText mLoanPriceEdit;
    private RelativeLayout mLoanLocationLayout;
    private TextView mAfterTax;
    private TextView mPreTax;
    private LoanCalculatorView mLoanCalculatorView;
    private UIHandler mHandler = new UIHandler();
    private DialogLoading mLoading;
    private double mInputPrice;

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
        mLoanLocationLayout = (RelativeLayout) view.findViewById(R.id.loan_calculator_location_layout);
        mAfterTax = (TextView) view.findViewById(R.id.loan_calculator_page_after_tax);
        mPreTax = (TextView) view.findViewById(R.id.loan_calculator_page_pre_tax);
        mLoanCalculatorView = (LoanCalculatorView) view.findViewById(R.id.loan_page_calculator_view);

        mLoanLocationLayout.setOnClickListener(this);
        mAfterTax.setOnClickListener(this);
        mPreTax.setOnClickListener(this);
        mLoanPriceEdit.requestFocus();
        mLoading = new DialogLoading(this, R.style.DialogTheme);
        mLoading.setViewLayout(R.layout.dialog_loading_layout);
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetLoanCalculatorObj) {
            NetLoanCalculatorObj obj = (NetLoanCalculatorObj) object;
            Message msg = mHandler.obtainMessage();
            msg.what = mHandler.NOTIFY_SUCCESS;
            msg.obj = obj;
            msg.sendToTarget();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_calculator_location_layout: {
                Intent intent = new Intent(this, CityChoosePageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.loan_calculator_page_after_tax: {
                calculatorAfterTax();
                break;
            }
            case R.id.loan_calculator_page_pre_tax: {
                calculatorPreTax();
                break;
            }
        }
    }

    private void calculatorAfterTax() {
        String price = mLoanPriceEdit.getText().toString();
        if (!StringUtils.isEmpty(price)) {
            mLoanCalculatorView.resetGuide();
            mInputPrice = Double.parseDouble(price);
            mLoading.startAnim();
            NetRequest.getInstance().requestLoanCalculator(1, mInputPrice, 0, this);
        }
    }

    private void calculatorPreTax() {
        String price = mLoanPriceEdit.getText().toString();
        if (!StringUtils.isEmpty(price)) {
            mLoanCalculatorView.resetGuide();
            mInputPrice = Double.parseDouble(price);
            mLoading.startAnim();
            NetRequest.getInstance().requestLoanCalculator(1, mInputPrice, 1, this);
        }
    }

    private final class UIHandler extends Handler {
        private final int NOTIFY_SUCCESS = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoading.stopAnim();
            switch (msg.what) {
                case NOTIFY_SUCCESS: {
                    NetLoanCalculatorObj obj = (NetLoanCalculatorObj) msg.obj;
                    double afterTax = obj.getAfterTax();
                    double preTax = obj.getPreTax();
                    double benefit = obj.getBenefit();
                    double individual = obj.getIndividual();
                    arcArea(obj.getLists(), afterTax, preTax, benefit, individual);
                    break;
                }
            }
        }
    }

    private void arcArea(List<LoanCalculatorModel> models, double...prices) {
        mLoanCalculatorView.setPrices(mInputPrice, prices);
        mLoanCalculatorView.setDetails(models);
    }
}
