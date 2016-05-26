package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;

/**
 * Created by didi on 16/5/26.
 */
public class LoanCalculatorView extends LinearLayout {

    private LayoutInflater mInflater;
    private LoanCalculatorGuide mLoanGuide;
    private LinearLayout mLoanDetailsLayout;
    private TextView mAfterTaxPrice;
    private TextView mPreTaxPrice;
    private TextView mBenefitPrice;
    private TextView mIndividualPrice;

    public LoanCalculatorView(Context context) {
        this(context, null);
    }

    public LoanCalculatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoanCalculatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        mInflater = LayoutInflater.from(context);

        View view = mInflater.inflate(R.layout.loan_calculator_result_layout, this, true);
        mLoanGuide = (LoanCalculatorGuide) view.findViewById(R.id.loan_calculator_guide);
        mLoanDetailsLayout = (LinearLayout) view.findViewById(R.id.loan_calculator_result_list_layout);
        mAfterTaxPrice = (TextView) view.findViewById(R.id.loan_calculator_after_tax_price);
        mPreTaxPrice = (TextView) view.findViewById(R.id.loan_calculator_pre_tax_price);
        mBenefitPrice = (TextView) view.findViewById(R.id.loan_calculator_benefit_price);
        mIndividualPrice = (TextView) view.findViewById(R.id.loan_calculator_individual_price);
    }

    public void setPrices() {

    }
}
