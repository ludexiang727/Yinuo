package com.yinuo.net.base;

import org.json.JSONObject;

/**
 * Created by ludexiang on 2016/4/5.
 */
public abstract class NetBaseObject {
    protected int mErrNo;
    protected String mErrMsg;

    public NetBaseObject() {

    }

    public abstract void parse(JSONObject obj);
}
