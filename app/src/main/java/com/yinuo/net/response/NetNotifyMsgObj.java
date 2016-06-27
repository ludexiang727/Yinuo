package com.yinuo.net.response;

import com.yinuo.mode.NotifyMsgModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 16/6/27.
 */
public class NetNotifyMsgObj extends NetBaseObject {
    private NotifyMsgModel mModel;
    private String mMsgTime;

    @Override
    protected void parse(JSONObject obj) {
        super.parse(obj);
        mModel = new NotifyMsgModel();
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_NOTIFY_LISTS, obj);
        mMsgTime = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_TIME, obj);
        mModel.setNotifyTime(mMsgTime);
        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        if (array == null) {
            return;
        }
        List<NotifyMsgModel.NotifyMsg> data = new ArrayList<NotifyMsgModel.NotifyMsg>();
        int length = array.length();
        for (int i = 0; i < length; ++i) {
            try {
                JSONObject obj = array.getJSONObject(i);
                NotifyMsgModel.NotifyMsg model = new NotifyMsgModel.NotifyMsg();
                int id = NetParseUtils.getInt(NetConstant.NET_JSON_NOTIFY_MSG_ID, obj);
                String imgUrl = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_IMG, obj);
                String msgTitle = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_TITLE, obj);
                String redirect = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_REDIRECT, obj);

                model.setNotifyMsgId(id);
                model.setNotifyImg(imgUrl);
                model.setNotifyTitle(msgTitle);
                model.setNotifyUrl(redirect);

                data.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mModel.setNotifyLists(data);
    }

    public NotifyMsgModel getData() {
        return mModel;
    }
}
