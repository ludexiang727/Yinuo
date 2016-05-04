package com.yinuo.net.response;

import com.yinuo.mode.InvestWeChatModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/5/4.
 */
public class NetInvestWeChatObj extends NetBaseObject {
    private List<InvestWeChatModel> mModels;

    @Override
    protected void parse(JSONObject obj) {
        mModels = new ArrayList<InvestWeChatModel>();
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_INVEST_WECHAT_LISTS, obj);
        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        if (null != array) {
            int len = array.length();
            for (int i = 0; i < len; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    String time = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_WECHAT_TIME, obj);
                    int type = NetParseUtils.getInt(NetConstant.NET_JSON_INVEST_WECHAT_MSG_TYPE, obj);
                    String imgURL = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_WECHAT_HEADER, obj);
                    String msgBody = NetParseUtils.getString(NetConstant.NET_JSON_INVEST_WECHAT_MSG_BODY, obj);

                    InvestWeChatModel model = new InvestWeChatModel();
                    model.setHeaderImg(imgURL);
                    model.setType(type);
                    model.setMessage(msgBody);
                    model.setMsgTime(time);
                    mModels.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public List<InvestWeChatModel> getModels() {
        return mModels;
    }
}
