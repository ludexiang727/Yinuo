package com.yinuo.net.response;

import com.yinuo.mode.HomePageBannersModel;
import com.yinuo.mode.HomePageDataModel;
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
    private List<HomePageDataModel> mModeLists;
    private int mDataCount;
    private List<HomePageBannersModel> mHomePageBanners;

    @Override
    protected void parse(JSONObject obj) {
        mModeLists = new ArrayList<HomePageDataModel>();
        mHomePageBanners = new ArrayList<HomePageBannersModel>();
        mDataCount = NetParseUtils.getInt(NetConstant.NET_JSON_HOME_FIELD_DATA_COUNT, obj);
        JSONArray banners = NetParseUtils.getArray(NetConstant.NET_JSON_HOME_FIELD_PAGE_BANNERS, obj);
        parseBannersArray(banners);
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_HOME_FIELD_PAGE_LISTS, obj);
        parseCardArray(array);
    }

    public int getDataTotalCount() {
        return mDataCount;
    }

    public List<HomePageDataModel> getModeLists() {
        return mModeLists;
    }

    public List<HomePageBannersModel> getHomePageBanners() {
        return mHomePageBanners;
    }

    private void parseCardArray(JSONArray array) {
        if (array != null) {
            int len = array.length();
            for (int i = 0; i < len; ++i) {
                HomePageDataModel mode = new HomePageDataModel();
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
                    for(String tag : StringUtils.split(tags, ",")) {
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

    private void parseBannersArray(JSONArray array) {
        if (array != null) {
            int len = array.length();
            for (int i = 0; i < len; ++i) {
                HomePageBannersModel banners = new HomePageBannersModel();
                try {
                    JSONObject obj = array.getJSONObject(i);
                    String url = NetParseUtils.getString(NetConstant.NET_JSON_HOME_BANNER_IMG_URL, obj);
                    String redirect = NetParseUtils.getString(NetConstant.NET_JSON_HOME_BANNER_REDIRECT_URL, obj);
                    banners.setBannerOrImgURL(url);
                    banners.setRedirectURL(redirect);
                    mHomePageBanners.add(banners);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
