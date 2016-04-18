package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by Administrator on 2016/4/18.
 */
public class PartnerRecyclerModel extends BaseObject {
    private int mId;
    private int mGender;
    private String mPartnerName;
    private String mPartnerTel;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        this.mGender = gender;
    }

    public String getPartnerName() {
        return mPartnerName;
    }

    public void setPartnerName(String partnerName) {
        this.mPartnerName = partnerName;
    }

    public String getPartnerTel() {
        return mPartnerTel;
    }

    public void setPartnerTel(String partnerTel) {
        this.mPartnerTel = partnerTel;
    }
}
