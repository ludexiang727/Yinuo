package com.yinuo.net.response;

import com.yinuo.mode.BossOnlineDataModel;
import com.yinuo.mode.BossOnlineWorkModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/21.
 */
public class NetBossOnlinePageObj extends NetBaseObject {

    private List<BossOnlineDataModel> mModels;

    @Override
    protected void parse(JSONObject obj) {
        mModels = new ArrayList<BossOnlineDataModel>();
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_BOSS_ONLINE_FIELD_WORK_LISTS, obj);
        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        if (array != null) {
            int length = array.length();
            for (int i = 0; i < length; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    JSONObject bossObj = NetParseUtils.getObject(NetConstant.NET_JSON_BOSS_ONLINE_BOSS_INFO, obj);
                    int bossId = NetParseUtils.getInt(NetConstant.NET_JSON_BOSS_ONLINE_BOSS_ID, bossObj);
                    String bossImg = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_BOSS_IMG, bossObj);
                    String bossTel = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_BOSS_TEL, bossObj);
                    int bossValidate = NetParseUtils.getInt(NetConstant.NET_JSON_BOSS_ONLINE_BOSS_VALIDATE, bossObj);
                    String bossName = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_BOSS_NAME, bossObj);
                    String bossDuty = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_BOSS_DUTY, bossObj);
                    String companyName = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_COMPANY_NAME, bossObj);
                    String companyLocation = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_COMPANY_LOCATION, bossObj);
                    int workTotal = NetParseUtils.getInt(NetConstant.NET_JSON_BOSS_ONLINE_WORKS_TOTAL, bossObj);

                    JSONArray innerArray = NetParseUtils.getArray(NetConstant.NET_JSON_BOSS_ONLINE_WORK_WORKS, obj);
                    BossOnlineDataModel model = new BossOnlineDataModel();
                    model.setBossId(bossId);
                    model.setBossImg(bossImg);
                    model.setBossTel(bossTel);
                    model.setBossValidate(bossValidate);
                    model.setBossName(bossName);
                    model.setBossDuty(bossDuty);
                    model.setCompanyName(companyName);
                    model.setCompanyLocation(companyLocation);
                    List<BossOnlineWorkModel> works = parseInnerArray(innerArray);
                    if (works != null) {
                        model.setWorkLists(works);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<BossOnlineWorkModel> parseInnerArray(JSONArray array) {
        List<BossOnlineWorkModel> lists = new ArrayList<BossOnlineWorkModel>();
        if (array != null) {
            int length = array.length();
            for (int i = 0; i < length; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    int workId = NetParseUtils.getInt(NetConstant.NET_JSON_BOSS_ONLINE_WORK_ID, obj);
                    String workDuty = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_WORK_DUTY, obj);
                    int workers = NetParseUtils.getInt(NetConstant.NET_JSON_BOSS_ONLINE_WORK_DUTY, obj);
                    String workPublishTime = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_WORK_TIME, obj);
                    String workSalary = NetParseUtils.getString(NetConstant.NET_JSON_BOSS_ONLINE_WORK_SALARY, obj);
                    int workProperty = NetParseUtils.getInt(NetConstant.NET_JSON_BOSS_ONLINE_WORK_PROPERTY, obj);

                    BossOnlineWorkModel work = new BossOnlineWorkModel();
                    work.setWorkDuty(workDuty);
                    work.setWorkId(workId);
                    work.setWorkers(workers);
                    work.setWorkSalary(workSalary);
                    work.setWorkProperty(workProperty);
                    work.setWorkPublishTime(workPublishTime);

                    lists.add(work);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return lists;
    }

    public List<BossOnlineDataModel> getModels() {
        return mModels;
    }
}
