package com.yinuo.net.response;

import com.yinuo.mode.PartnerRecyclerModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class NetPartnerPageObj extends NetBaseObject {
    private int mPartnerCount;
    private List<PartnerRecyclerModel> mModels;

    @Override
    protected void parse(JSONObject obj) {
        mModels = new ArrayList<PartnerRecyclerModel>();
        mPartnerCount = NetParseUtils.getInt(NetConstant.NET_JSON_PARTNER_FIELD_LISTS_COUNT, obj);
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_PARTNER_FIELD_LISTS, obj);

        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        if (array != null) {
            int length = array.length();
            for (int i = 0; i < length; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    int id = NetParseUtils.getInt(NetConstant.NET_JSON_PARTNER_LISTS_ID, obj);
                    String name = NetParseUtils.getString(NetConstant.NET_JSON_PARTNER_LISTS_NAME, obj);
                    String tel = NetParseUtils.getString(NetConstant.NET_JSON_PARTNER_LISTS_TEL, obj);
                    String imgurl = NetParseUtils.getString(NetConstant.NET_JSON_PARTNER_LISTS_IMG, obj);
                    int gender = NetParseUtils.getInt(NetConstant.NET_JSON_PARTNER_LISTS_GENDER, obj);

                    PartnerRecyclerModel model = new PartnerRecyclerModel();
                    model.setId(id);
                    model.setGender(gender);
                    model.setPartnerName(name);
                    model.setBannerOrImgURL(imgurl);
                    model.setPartnerTel(tel);
                    mModels.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<PartnerRecyclerModel> getModels() {
        return mModels;
    }

    public int getModelCount() {
        return mPartnerCount;
    }
}
