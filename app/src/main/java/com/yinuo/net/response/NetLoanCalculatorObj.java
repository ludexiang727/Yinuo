package com.yinuo.net.response;

import com.yinuo.mode.LoanCalculatorModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by didi on 16/5/27.
 */
public class NetLoanCalculatorObj extends NetBaseObject {
    private List<LoanCalculatorModel> mLists;
    private double mAfterTax;
    private double mPreTax;
    private double mBenefit;
    private double mIndividual;

    @Override
    protected void parse(JSONObject obj) {
        mLists = new ArrayList<LoanCalculatorModel>();
        mAfterTax = NetParseUtils.getDouble(NetConstant.NET_JSON_LOAN_CALCULATOR_AFTER_TAX, obj);
        mPreTax = NetParseUtils.getDouble(NetConstant.NET_JSON_LOAN_CALCULATOR_PRE_TAX, obj);
        mBenefit = NetParseUtils.getDouble(NetConstant.NET_JSON_LOAN_CALCULATOR_BENEFIT, obj);
        mIndividual = NetParseUtils.getDouble(NetConstant.NET_JSON_LOAN_CALCULATOR_INDIVIDUAL, obj);
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_LOAN_CALCULATOR_BENEFIT_LISTS, obj);
        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        int len = array.length();
        if (len == 0) {
            return;
        }
        for (int i = 0; i < len; ++i) {
            try {
                JSONObject obj = array.getJSONObject(i);
                LoanCalculatorModel model = new LoanCalculatorModel();
                String name = NetParseUtils.getString(NetConstant.NET_JSON_LOAN_CALCULATOR_BENEFIT_NAME, obj);
                double person = NetParseUtils.getDouble(NetConstant.NET_JSON_LOAN_CALCULATOR_BENEFIT_PERSON, obj);
                double company = NetParseUtils.getDouble(NetConstant.NET_JSON_LOAN_CALCULATOR_BENEFIT_COMPANY, obj);
                model.setBenefitName(name);
                model.setBenefitPerson(person);
                model.setBenefitCompany(company);
                mLists.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public List<LoanCalculatorModel> getLists() {
        return mLists;
    }

    public double getAfterTax() {
        return mAfterTax;
    }


    public double getPreTax() {
        return mPreTax;
    }


    public double getBenefit() {
        return mBenefit;
    }


    public double getIndividual() {
        return mIndividual;
    }

}
