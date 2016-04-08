package com.yinuo.net.response;

import android.util.Log;

import com.yinuo.mode.HomePageDataMode;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;
import com.yinuo.utils.AppUtils;
import com.yinuo.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/8.
 */
public class NetHomePageObj extends NetBaseObject {
    private List<HomePageDataMode> mModeLists;
    private int mDataCount;
    private List<String> mHomePageBanners;

    @Override
    protected void parse(JSONObject obj) {
        mModeLists = new ArrayList<HomePageDataMode>();
        mHomePageBanners = new ArrayList<String>();
        mDataCount = NetParseUtils.getInt(NetConstant.NET_JSON_FIELD_DATA_COUNT, obj);
        String banners = NetParseUtils.getString(NetConstant.NET_JSON_FIELD_PAGE_BANNERS, obj);
        for (String img : AppUtils.split(banners, ",")) {
            mHomePageBanners.add(img);
        }
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_FIELD_PAGE_LISTS, obj);
        parseArray(array);
    }

    public int getDataTotalCount() {
        return mDataCount;
    }

    public List<HomePageDataMode> getModeLists() {
        return mModeLists;
    }

    public List<String> getHomePageBanners() {
        return mHomePageBanners;
    }

    private void parseArray(JSONArray array) {
        if (array != null) {
            int len = array.length();
            for (int i = 0; i < len; ++i) {
                HomePageDataMode mode = new HomePageDataMode();
                try {
                    JSONObject innerObj = array.getJSONObject(i);
                    String img = NetParseUtils.getString(NetConstant.NET_JSON_HOME_CARD_IMG, innerObj);
                    String title = NetParseUtils.getString(NetConstant.NET_JSON_HOME_CARD_TITLE, innerObj);
                    String summary = NetParseUtils.getString(NetConstant.NET_JSON_HOME_CARD_SUMMARY, innerObj);
                    int attention = NetParseUtils.getInt(NetConstant.NET_JSON_HOME_CARD_ATTENTION, innerObj);
                    int collection = NetParseUtils.getInt(NetConstant.NET_JSON_HOME_CARD_COLLECTION, innerObj);
                    String tags = NetParseUtils.getString(NetConstant.NET_JSON_HOME_CARD_TAGS, innerObj);
                    mode.setAttention(attention);
                    mode.setCollectioned(collection);
                    mode.setImgURL(img);
                    mode.setSummary(summary);
                    mode.setTitle(title);
                    List<String> cardTags = new ArrayList<String>();
                    for(String tag : AppUtils.split(tags, ",")) {
                        cardTags.add(tag);
                    }
                    mode.setTags(cardTags);
                    mModeLists.add(mode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
