package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by ludexiang on 2016/5/3.
 */
public class AddressModel extends BaseObject {
    private double mLat;
    private double mLng;
    /** province name - 所属省名称 */
    private String mProvince;
    /** city name -- 城市名称 */
    private String mCityName;
    /** province id -- 所属省id */
    private int mProId;
    /** city id -- 城市id*/
    private int mCityId;
    /** chinese pinyin - 汉字拼音 */
    private String mCityPinYin;
    /** first charmeater - 城市名称首字母 */
    private String mCityFirstSpell;
    /** location address - 定位地点 */
    private String mAddress;
    private String mProMark;
    private int mProSort;
    /**1 -- hot city 0 -- normal*/
    private int mHotCity;

    private int mCityAreaId;
    private String mCityAreaName;
    private String mCityAreaPinYin;
    private String mCityAreaFirstSpell;


    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String city) {
        this.mCityName = city;
    }

    public int getCityId() {
        return mCityId;
    }

    public void setCityId(int cityId) {
        this.mCityId = cityId;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        this.mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        this.mLng = lng;
    }

    public int getProId() {
        return mProId;
    }

    public void setProId(int proId) {
        this.mProId = proId;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        this.mProvince = province;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getCityPinYin() {
        return mCityPinYin;
    }

    public void setCityPinYin(String cityPinYin) {
        this.mCityPinYin = cityPinYin;
    }

    public String getCityFirstSpell() {
        return mCityFirstSpell;
    }

    public void setCityFirstSpell(String cityFirstSpell) {
        this.mCityFirstSpell = cityFirstSpell;
    }

    public int getProSort() {
        return mProSort;
    }

    public void setProSort(int proSort) {
        this.mProSort = proSort;
    }

    public String getProMark() {
        return mProMark;
    }

    public void setProMark(String proMark) {
        this.mProMark = proMark;
    }

    public int getHotCity() {
        return mHotCity;
    }

    public void setHotCity(int hotCity) {
        this.mHotCity = hotCity;
    }

    public String getCityAreaFirstSpell() {
        return mCityAreaFirstSpell;
    }

    public void setCityAreaFirstSpell(String cityAreaFirstSpell) {
        this.mCityAreaFirstSpell = cityAreaFirstSpell;
    }

    public int getCityAreaId() {
        return mCityAreaId;
    }

    public void setCityAreaId(int cityAreaId) {
        this.mCityAreaId = cityAreaId;
    }

    public String getCityAreaName() {
        return mCityAreaName;
    }

    public void setCityAreaName(String cityAreaName) {
        this.mCityAreaName = cityAreaName;
    }

    public String getCityAreaPinYin() {
        return mCityAreaPinYin;
    }

    public void setCityAreaPinYin(String cityAreaPinYin) {
        this.mCityAreaPinYin = cityAreaPinYin;
    }
}
