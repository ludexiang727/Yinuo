package com.yinuo.ui.component.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.mode.LoanCalculatorModel;

import java.util.List;

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
        setOrientation(VERTICAL);
        mInflater = LayoutInflater.from(context);

        View view = mInflater.inflate(R.layout.loan_calculator_result_layout, this, true);
        mLoanGuide = (LoanCalculatorGuide) view.findViewById(R.id.loan_calculator_guide);
        mLoanDetailsLayout = (LinearLayout) view.findViewById(R.id.loan_calculator_result_list_layout);
        mAfterTaxPrice = (TextView) view.findViewById(R.id.loan_calculator_after_tax_price);
        mPreTaxPrice = (TextView) view.findViewById(R.id.loan_calculator_pre_tax_price);
        mBenefitPrice = (TextView) view.findViewById(R.id.loan_calculator_benefit_price);
        mIndividualPrice = (TextView) view.findViewById(R.id.loan_calculator_individual_price);
    }

    public void setPrices(double inputPrice, double...prices) {
        mAfterTaxPrice.setText(String.valueOf(prices[0]));
        mPreTaxPrice.setText(String.valueOf(prices[1]));
        mBenefitPrice.setText(String.valueOf(prices[2]));
        mIndividualPrice.setText(String.valueOf(prices[3]));

        long afterTax = Math.round((prices[0] / inputPrice) * 360);
        long preTax = Math.round((prices[1] / inputPrice) * 360);
        long benefit = Math.round((prices[2] / inputPrice) * 360);
        long individual = Math.round((prices[3] / inputPrice) * 360);
        mLoanGuide.setPrices(afterTax, preTax, benefit, individual);
    }

    public void setDetails(List<LoanCalculatorModel> models) {
        if (models == null || models.size() == 0) {
            return;
        }

        if (mLoanDetailsLayout.getChildCount() > 0) {
            mLoanDetailsLayout.removeAllViews();
        }
        for (int i = 0; i < models.size(); ++i) {
            LoanCalculatorModel model = models.get(i);
            View view = mInflater.inflate(R.layout.loan_calculator_details_layout, null);
            TextView name = (TextView) view.findViewById(R.id.loan_calculator_name);
            TextView person = (TextView) view.findViewById(R.id.loan_calculator_person);
            TextView company = (TextView) view.findViewById(R.id.loan_calculator_company);

            name.setText(model.getBenefitName());
            person.setText(model.getBenefitPerson());
            company.setText(model.getBenefitCompany());
            mLoanDetailsLayout.addView(view, i);
        }
    }

    public void resetGuide() {
        mLoanGuide.reset();
    }
}
