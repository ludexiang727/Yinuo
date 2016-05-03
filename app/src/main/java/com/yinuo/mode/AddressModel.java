package com.yinuo.mode;

/**
 * Created by ludexiang on 2016/5/3.
 */
public class AddressModel {
    private double mLat;
    private double mLng;
    private String mProvince;
    /** city name */
    private String mCity;
    /** province id */
    private int mProId;
    /** city id */
    private int mCityId;

    private String mAddress;

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
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
}
