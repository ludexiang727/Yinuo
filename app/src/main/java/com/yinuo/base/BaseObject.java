package com.yinuo.base;

import java.io.Serializable;

/**
 * Created by ludexiang on 2016/4/7.
 */
public class BaseObject implements Serializable {
    /** appId -- app 对应的ID */
    protected int mAppId;
    /** banner url - each page banner url or image view url */
    protected String mBannerOrImgURL;
    /** app rank -- app 排行 */
    protected int mAppRank;

    public int getAppId() {
        return mAppId;
    }

    public void setmAppId(int appId) {
        this.mAppId = appId;
    }

    public String getBannerOrImgURL() {
        return mBannerOrImgURL;
    }

    public void setBannerOrImgURL(String bannerURL) {
        this.mBannerOrImgURL = bannerURL;
    }

    public void setAppRank(int rank) {
        mAppRank = rank;
    }

    public int getAppRank() {
        return mAppRank;
    }
}
