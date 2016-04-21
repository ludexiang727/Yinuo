package com.yinuo.mode;

/**
 * Created by ludexiang on 2016/4/21.
 */
public class BossOnlineWorkModel {

    private int mWorkId;
    private String mWorkDuty;
    private int mWorkers;
    private String mWorkPublishTime;
    private String mWorkSalary;
    private int mWorkProperty;

    public String getWorkDuty() {
        return mWorkDuty;
    }

    public void setWorkDuty(String workDuty) {
        this.mWorkDuty = workDuty;
    }

    public int getWorkers() {
        return mWorkers;
    }

    public void setWorkers(int workers) {
        this.mWorkers = workers;
    }

    public int getWorkId() {
        return mWorkId;
    }

    public void setWorkId(int workId) {
        this.mWorkId = workId;
    }

    public int getWorkProperty() {
        return mWorkProperty;
    }

    public void setWorkProperty(int workProperty) {
        this.mWorkProperty = workProperty;
    }

    public String getWorkPublishTime() {
        return mWorkPublishTime;
    }

    public void setWorkPublishTime(String workPublishTime) {
        this.mWorkPublishTime = workPublishTime;
    }

    public String getWorkSalary() {
        return mWorkSalary;
    }

    public void setWorkSalary(String workSalary) {
        this.mWorkSalary = workSalary;
    }
}
