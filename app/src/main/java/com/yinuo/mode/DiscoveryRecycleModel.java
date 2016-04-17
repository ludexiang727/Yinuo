package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by gus on 16/4/16.
 */
public class DiscoveryRecycleModel extends BaseObject {
    /** title -- 标题 */
    private String mTitle;
    /** produce such as game app etc -- 属性 例如 游戏 应用等等 */
    private String mProperty;
    /** introduce -- 简介 */
    private String mSummary;
    /** attention -- 关注度 */
    private int mAttention;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getAttention() {
        return mAttention;
    }

    public void setAttention(int attention) {
        this.mAttention = attention;
    }

    public String getProperty() {
        return mProperty;
    }

    public void setProperty(String property) {
        this.mProperty = property;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        this.mSummary = summary;
    }

    @Override
    public String toString() {
        return "DiscoveryRecycleModel{" +
                "mProperty=" + mProperty +
                ", mSummary='" + mSummary + '\'' +
                '}';
    }
}
