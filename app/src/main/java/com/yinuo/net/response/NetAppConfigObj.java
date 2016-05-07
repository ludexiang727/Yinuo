package com.yinuo.net.response;

import android.util.Log;

import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;
import com.yinuo.utils.PreferenceUtils;

import org.json.JSONObject;

/**
 * Created by gus on 16/5/7.
 */
public class NetAppConfigObj extends NetBaseObject {

    @Override
    protected void parse(JSONObject obj) {
        int appConfigVersion = NetParseUtils.getInt(NetConstant.NET_JSON_APP_CONFIG_VERSION, obj);
        PreferenceUtils.getInstance().setConfigVersion(appConfigVersion);
        if (obj.has("app_splash_ad")) {
            JSONObject adObj = obj.optJSONObject("app_splash_ad");
            PreferenceUtils.getInstance().setConfigAdImgUrl(adObj.optString("splash_ad_img_url"));
            PreferenceUtils.getInstance().setConfigAdEntranceUrl(adObj.optString("splash_ad_entrance_url"));
        }
    }
}
