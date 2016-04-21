package com.yinuo.mode;

import com.yinuo.base.BaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/21.
 */
public class BossOnlineDataModel extends BaseObject {
    private List<BossOnlineWorkModel> mWorkLists = new ArrayList<BossOnlineWorkModel>();
    private int mBossId;
    private String mBossImg;
    private String mBossTel;
    private int mBossValidate;
    private String mBossName;
    private String mBossDuty;
    private String mCompanyName;
    private String mCompanyLocation;
    private int mWorkTotal;

    public String getBossDuty() {
        return mBossDuty;
    }

    public void setBossDuty(String bossDuty) {
        this.mBossDuty = bossDuty;
    }

    public int getBossId() {
        return mBossId;
    }

    public void setBossId(int bossId) {
        this.mBossId = bossId;
    }

    public String getBossImg() {
        return mBossImg;
    }

    public void setBossImg(String bossImg) {
        this.mBossImg = bossImg;
    }

    public String getBossName() {
        return mBossName;
    }

    public void setBossName(String bossName) {
        this.mBossName = bossName;
    }

    public String getBossTel() {
        return mBossTel;
    }

    public void setBossTel(String bossTel) {
        this.mBossTel = bossTel;
    }

    public int getBossValidate() {
        return mBossValidate;
    }

    public void setBossValidate(int bossValidate) {
        this.mBossValidate = bossValidate;
    }

    public String getCompanyLocation() {
        return mCompanyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.mCompanyLocation = companyLocation;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        this.mCompanyName = companyName;
    }

    public List<BossOnlineWorkModel> getWorkLists() {
        return mWorkLists;
    }

    public void setWorkLists(List<BossOnlineWorkModel> workLists) {
        if (workLists != null) {
            this.mWorkLists.addAll(workLists);
        }
    }

    public int getWorkTotal() {
        return mWorkTotal;
    }

    public void setWorkTotal(int workTotal) {
        this.mWorkTotal = workTotal;
    }
}
