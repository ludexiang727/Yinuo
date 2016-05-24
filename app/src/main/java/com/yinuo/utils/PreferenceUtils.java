package com.yinuo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yinuo.Constants;
import com.yinuo.base.BaseApplication;

/**
 * Created by gus on 16/5/7.
 */
public final class PreferenceUtils {
    private SharedPreferences mSharePrefer;
    private SharedPreferences.Editor mEditor;
    private static PreferenceUtils mPreference;

    private PreferenceUtils() {
        mSharePrefer = BaseApplication.getInstance().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mSharePrefer.edit();
    }

    private static final class PreferenceFactory {
        public static PreferenceUtils create() {
            if (mPreference == null) {
                mPreference = new PreferenceUtils();
            }
            return mPreference;
        }
    }

    public static PreferenceUtils getInstance() {
        return PreferenceFactory.create();
    }

    /** save config info begin **/
    public void setConfigAdImgUrl(String imgUrl) {
        mEditor.putString("config_ad_img_url", imgUrl);
        mEditor.commit();
    }

    public String getConfigAdImgUrl() {
        return mSharePrefer.getString("config_ad_img_url", "");
    }

    public void setConfigAdEntranceUrl(String entranceUrl) {
        mEditor.putString("config_ad_entrance_url", entranceUrl);
        mEditor.commit();
    }

    public String getConfigAdEntranceUrl() {
        return mSharePrefer.getString("config_ad_entrance_url", "");
    }

    public void setConfigVersion(int version) {
        mEditor.putInt("app_config_version", version);
        mEditor.commit();
    }

    public int getConfigVersion() {
        return mSharePrefer.getInt("app_config_version", 0);
    }
    /** save config info end **/

    public void setAccount(String userAccount) {
        mEditor.putString("user_account", userAccount);
        mEditor.commit();
    }

    public String getAccount() {
        return mSharePrefer.getString("user_account", "");
    }
}
