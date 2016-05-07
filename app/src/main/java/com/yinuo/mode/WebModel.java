package com.yinuo.mode;

import java.io.Serializable;

/**
 * Created by gus on 16/5/7.
 */
public class WebModel implements Serializable {
    private String mUrl;
    private boolean isSupportCatch = true;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public boolean isSupportCatch() {
        return isSupportCatch;
    }

    public void setSupportCatch(boolean supportCatch) {
        isSupportCatch = supportCatch;
    }
}
