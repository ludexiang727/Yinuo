package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by ludexiang on 2016/5/4.
 */
public class InvestWeChatModel extends BaseObject {
    private String mHeaderImg;
    /** left - right style left --> 0 right --> 1 */
    private int mType;
    private String mMessage;
    private String mMsgTime;

    public String getHeaderImg() {
        return mHeaderImg;
    }

    public void setHeaderImg(String headerImg) {
        this.mHeaderImg = headerImg;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getMsgTime() {
        return mMsgTime;
    }

    public void setMsgTime(String msgTime) {
        this.mMsgTime = msgTime;
    }
}
