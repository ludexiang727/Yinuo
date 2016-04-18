package com.yinuo.mode;

import com.yinuo.base.BaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/7.
 */
public class HomePageDataModel extends BaseObject {
    private String mImgURL;
    /** 1 collectioned 0 uncollecion*/
    private int mCollectioned;
    /** how many people attention */
    private int mAttention;
    private String mTitle;
    private List<String> mTags = new ArrayList<String>();
    private String mSummary;

    public int getAttention() {
        return mAttention;
    }

    public void setAttention(int attention) {
        this.mAttention = attention;
    }

    public int getCollectioned() {
        return mCollectioned;
    }

    public void setCollectioned(int collection) {
        this.mCollectioned = collection;
    }

    public String getImgURL() {
        return mImgURL;
    }

    public void setImgURL(String imgURL) {
        this.mImgURL = imgURL;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        this.mSummary = summary;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        this.mTags = tags;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public String toString() {
        return "HomePageDataModel{" +
                "mAttention=" + mAttention +
                ", mImgURL='" + mImgURL + '\'' +
                ", mCollectioned=" + mCollectioned +
                ", mTitle='" + mTitle + '\'' +
                ", mTags=" + mTags +
                ", mSummary='" + mSummary + '\'' +
                '}';
    }
}
