package com.yinuo.net.base;

import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;
import com.yinuo.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ludexiang on 2016/4/5.
 */
public abstract class NetBaseObject {
    protected int mErrNo = -1;
    protected String mErrMsg = "";

    public NetBaseObject() {

    }

    public void parse(String data) {
        if (StringUtils.isEmpty(data)) {
            return;
        }
        try {
            JSONObject object = new JSONObject(data);
            mErrNo = NetParseUtils.getInt(NetConstant.NET_JSON_BASE_ERRNO, object);
            if (mErrNo != 0) {
                mErrMsg = NetParseUtils.getString(NetConstant.NET_JSON_BASE_ERRMSG, object);
                return;
            }
            parse(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected abstract void parse(JSONObject obj);
}
