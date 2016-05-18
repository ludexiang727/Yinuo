package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by ludexiang on 2016/5/18.
 * tenement other condition model - 租房和旺铺条件Model
 */
public class WorkspaceTenementConditionModel extends BaseObject {
    private int mTenementConditionId = -1; // no limit
    // house tenement price -- 房屋租金范围
    private String mTenementPriceCope;
    // house size -- 房型 1室or...
    private String mTenementHouseSize;
    // house owner -- 房屋整租or合租
    private String mTenementHouseStyle;
    // house owner -- 房屋归属者 经济or个人
    private String mTenementHouseOwner;
    // house decorate -- 房子的装修
    private String mTenementHouseDecorate;
    // house sort -- 根据条件获取到的房屋排序
    private String mTenementHouseSort;
    // house area -- 房屋面积
    private String mTenementHouseArea;
    // house day price -- 房子日租金
    private String mTenementHouseDayPrice;

    public int getTenementConditionId() {
        return mTenementConditionId;
    }

    public void setTenementConditionId(int tenementConditionId) {
        this.mTenementConditionId = tenementConditionId;
    }

    public String getTenementHouseArea() {
        return mTenementHouseArea;
    }

    public void setTenementHouseArea(String tenementHouseArea) {
        this.mTenementHouseArea = tenementHouseArea;
    }

    public String getTenementHouseDayPrice() {
        return mTenementHouseDayPrice;
    }

    public void setTenementHouseDayPrice(String tenementHouseDayPrice) {
        this.mTenementHouseDayPrice = tenementHouseDayPrice;
    }

    public String getTenementHouseDecorate() {
        return mTenementHouseDecorate;
    }

    public void setTenementHouseDecorate(String tenementHouseDecorate) {
        this.mTenementHouseDecorate = tenementHouseDecorate;
    }

    public String getTenementHouseOwner() {
        return mTenementHouseOwner;
    }

    public void setTenementHouseOwner(String tenementHouseOwner) {
        this.mTenementHouseOwner = tenementHouseOwner;
    }

    public String getTenementHouseSize() {
        return mTenementHouseSize;
    }

    public void setTenementHouseSize(String tenementHouseSize) {
        this.mTenementHouseSize = tenementHouseSize;
    }

    public String getTenementHouseSort() {
        return mTenementHouseSort;
    }

    public void setTenementHouseSort(String tenementHouseSort) {
        this.mTenementHouseSort = tenementHouseSort;
    }

    public String getTenementHouseStyle() {
        return mTenementHouseStyle;
    }

    public void setTenementHouseStyle(String tenementHouseStyle) {
        this.mTenementHouseStyle = tenementHouseStyle;
    }

    public String getTenementPriceCope() {
        return mTenementPriceCope;
    }

    public void setTenementPriceCope(String tenementPriceCope) {
        this.mTenementPriceCope = tenementPriceCope;
    }
}
