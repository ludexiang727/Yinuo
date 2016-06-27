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
    private List<NotifyMsgModel> mModelLists;
    private String mMsgTime;

    @Override
    protected void parse(JSONObject obj) {
        super.parse(obj);
        mModelLists = new ArrayList<NotifyMsgModel>();
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_NOTIFY_LISTS, obj);
        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        if (array == null) {
            return;
        }
        int length = array.length();
        for (int i = 0; i < length; ++i) {
            NotifyMsgModel model = new NotifyMsgModel();
            try {
                JSONObject obj = array.getJSONObject(i);
                mMsgTime = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_TIME, obj);
                model.setNotifyTime(mMsgTime);

                JSONArray innerArray = NetParseUtils.getArray(NetConstant.NET_JSON_NOTIFY_MSG_LISTS, obj);
                List<NotifyMsgModel.NotifyMsg> data = new ArrayList<NotifyMsgModel.NotifyMsg>();
                for (int j = 0; j < innerArray.length(); ++j) {
                    JSONObject innerObj = innerArray.getJSONObject(j);
                    NotifyMsgModel.NotifyMsg modelMsg = new NotifyMsgModel.NotifyMsg();
                    int id = NetParseUtils.getInt(NetConstant.NET_JSON_NOTIFY_MSG_ID, innerObj);
                    String imgUrl = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_IMG, innerObj);
                    String msgTitle = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_TITLE, innerObj);
                    String redirect = NetParseUtils.getString(NetConstant.NET_JSON_NOTIFY_MSG_REDIRECT, innerObj);

                    modelMsg.setNotifyMsgId(id);
                    modelMsg.setNotifyImg(imgUrl);
                    modelMsg.setNotifyTitle(msgTitle);
                    modelMsg.setNotifyUrl(redirect);

                    data.add(modelMsg);
                }
                model.setNotifyLists(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mModelLists.add(model);
        }

    }

    public List<NotifyMsgModel> getData() {
        return mModelLists;
    }
}
