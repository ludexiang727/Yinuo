package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by gus on 16/4/23.
 */
public class InvestPageDataModel extends BaseObject {
    private int mInvestId;
    private String mInvestName;
    private int mInvestGender;
    private int mInvestValidate;
    private String mInvestNotice;
    private String mInvestDuty;
    private String mInvestImg;
    private String mInvestCompany;
    private String mInvestProvince;
    private String mInvestCity;

    public int getInvestId() {
        return mInvestId;
    }

    public void setInvestId(int investId) {
        this.mInvestId = investId;
    }

    public String getInvestName() {
        return mInvestName;
    }

    public void setInvestName(String investName) {
        this.mInvestName = investName;
    }

    public int getInvestGender() {
        return mInvestGender;
    }

    public void setInvestGender(int investGender) {
        this.mInvestGender = mInvestGender;
    }

    public int getInvestValidate() {
        return mInvestValidate;
    }

    public void setInvestValidate(int investValidate) {
        this.mInvestValidate = investValidate;
    }

    public String getInvestNotice() {
        return mInvestNotice;
    }

    public void setInvestNotice(String investNotice) {
        this.mInvestNotice = investNotice;
    }

    public String getInvestDuty() {
        return mInvestDuty;
    }

    public void setInvestDuty(String investDuty) {
        this.mInvestDuty = investDuty;
    }

    public String getInvestImg() {
        return mInvestImg;
    }

    public void setInvestImg(String investImg) {
        this.mInvestImg = investImg;
    }

    public String getInvestCompany() {
        return mInvestCompany;
    }

    public void setInvestCompany(String investCompany) {
        this.mInvestCompany = investCompany;
    }

    public String getInvestProvince() {
        return mInvestProvince;
    }

    public void setInvestProvince(String investProvince) {
        this.mInvestProvince = investProvince;
    }

    public String getInvestCity() {
        return mInvestCity;
    }

    public void setInvestCity(String investCity) {
        this.mInvestCity = investCity;
    }
}
