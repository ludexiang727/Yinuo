package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by ludexiang on 2016/4/19.
 */
public class LoanGridViewModel extends BaseObject {
    private int mItemId;
    private String mItemImgUrl;
    private String mItemOption;
    private String mItemHot;
    private String mItemLocation;

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int itemId) {
        this.mItemId = itemId;
    }

    public String getItemImgUrl() {
        return mItemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.mItemImgUrl = itemImgUrl;
    }

    public String getItemOption() {
        return mItemOption;
    }

    public void setItemOption(String itemOption) {
        this.mItemOption = itemOption;
    }

    public String getItemHot() {
        return mItemHot;
    }

    public void setItemHot(String itemHot) {
        this.mItemHot = itemHot;
    }

    public String getItemLocation() {
        return mItemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.mItemLocation = itemLocation;
    }

    @Override
    public String toString() {
        return "LoanGridViewModel{" +
                "mItemHot='" + mItemHot + '\'' +
                ", mItemId=" + mItemId +
                ", mItemImgUrl='" + mItemImgUrl + '\'' +
                ", mItemOption='" + mItemOption + '\'' +
                ", mItemLocation='" + mItemLocation + '\'' +
                '}';
    }
}
