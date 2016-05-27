package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by didi on 16/5/27.
 */
public class LoanCalculatorModel extends BaseObject {

    private String mBenefitName;
    private double mBenefitPerson;
    private double mBenefitCompany;

    public String getBenefitName() {
        return mBenefitName;
    }

    public void setBenefitName(String benefitName) {
        this.mBenefitName = benefitName;
    }

    public String getBenefitPerson() {
        return String.valueOf(mBenefitPerson);
    }

    public void setBenefitPerson(double benefitPerson) {
        this.mBenefitPerson = benefitPerson;
    }

    public String getBenefitCompany() {
        return String.valueOf(mBenefitCompany);
    }

    public void setBenefitCompany(double benefitCompany) {
        this.mBenefitCompany = benefitCompany;
    }
}
