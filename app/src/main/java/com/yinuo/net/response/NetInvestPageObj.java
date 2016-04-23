package com.yinuo.net.response;

import com.yinuo.mode.InvestPageDataModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gus on 16/4/23.
 */
public class NetInvestPageObj extends NetBaseObject {
    private List<InvestPageDataModel> mModels;
    private int mCount;

    @Override
    protected void parse(JSONObject obj) {
        mModels = new ArrayList<InvestPageDataModel>();
        mCount = NetParseUtils.getInt(NetConstant.NET_JSON_INVEST_FIELD_LISTS_COUNT, obj);
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_INVEST_FIELD_LISTS, obj);
        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        if (null != array) {
            int length = array.length();
            for (int i = 0; i < length; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    int investId = NetParseUtils.getInt(NetConstant.NET_JSON_INVEST_LISTS_ID, obj);
                    String investImg = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_LISTS_IMG, obj);
                    int investValidate = NetParseUtils.getInt(NetConstant.NET_JSON_INVEST_LISTS_VALIDATE, obj);
                    String investName = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_LISTS_NAME, obj);
                    String investDuty = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_LISTS_DUTY, obj);
                    String investProvince = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_LISTS_PROVINCE, obj);
                    String investCity = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_LISTS_CITY, obj);
                    String investCompany = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_LISTS_COMPANY, obj);
                    String investNotice = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_LISTS_NOTICE, obj);

                    InvestPageDataModel model = new InvestPageDataModel();
                    model.setInvestId(investId);
                    model.setInvestImg(investImg);
                    model.setInvestName(investName);
                    model.setInvestDuty(investDuty);
                    model.setInvestProvince(investProvince);
                    model.setInvestCity(investCity);
                    model.setInvestCompany(investCompany);
                    model.setInvestNotice(investNotice);
                    model.setInvestValidate(investValidate);
                    mModels.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<InvestPageDataModel> getModels() {
        return mModels;
    }

    public int getCount() {
        return mCount;
    }
}
