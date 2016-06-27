package com.yinuo.mode;

import com.yinuo.base.BaseObject;

import java.util.List;

/**
 * Created by ludexiang on 16/6/27.
 */
public class NotifyMsgModel extends BaseObject {
    private List<NotifyMsg> mNotifyLists;
    private String mNotifyTime;

    public void setNotifyLists(List<NotifyMsg> lists) {
        mNotifyLists = lists;
    }

    public List<NotifyMsg> getList() {
        return mNotifyLists;
    }

    public String getNotifyTime() {
        return mNotifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.mNotifyTime = notifyTime;
    }

    public static final class NotifyMsg {
        private int mNotifyMsgId;
        private String mNotifyImg;
        private String mNotifyTitle;
        // redirect page url
        private String mNotifyUrl;

        public int getNotifyMsgId() {
            return mNotifyMsgId;
        }

        public void setNotifyMsgId(int notifyMsgId) {
            this.mNotifyMsgId = notifyMsgId;
        }

        public String getNotifyImg() {
            return mNotifyImg;
        }

        public void setNotifyImg(String notifyImg) {
            this.mNotifyImg = notifyImg;
        }

        public String getNotifyTitle() {
            return mNotifyTitle;
        }

        public void setNotifyTitle(String notifyTitle) {
            this.mNotifyTitle = notifyTitle;
        }

        public String getNotifyUrl() {
            return mNotifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.mNotifyUrl = notifyUrl;
        }

    }
}
