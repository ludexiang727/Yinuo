package com.yinuo.base;

/**
 * Created by ludexiang on 2016/4/7.
 */
public class BaseObject {
    protected String mBannerURL;
    protected int mRank;

    public String getBannerURL() {
        return mBannerURL;
    }

    public void setBannerURL(String bannerURL) {
        this.mBannerURL = bannerURL;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public int getRank() {
        return mRank;
    }
}
