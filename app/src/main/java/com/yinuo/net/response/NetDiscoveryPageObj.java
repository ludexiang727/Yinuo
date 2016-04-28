package com.yinuo.net.response;

import com.yinuo.mode.DiscoveryRecycleModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;
import com.yinuo.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gus on 16/4/17.
 */
public class NetDiscoveryPageObj extends NetBaseObject {
    private List<DiscoveryRecycleModel> mDiscoveryRecycleData;
    private String[] mDiscoveryNavigation;
    private int mDiscoveryNavDefault = -1;

    @Override
    protected void parse(JSONObject obj) {
        mDiscoveryRecycleData = new ArrayList<DiscoveryRecycleModel>();
        JSONObject navObj = NetParseUtils.getObject(NetConstant.NET_JSON_DISCOVER_FIELD_NAVIGATION, obj);
        if (navObj != null) {
            mDiscoveryNavDefault = NetParseUtils.getInt(NetConstant.NET_JSON_DISCOVER_NAVIGATION_DEFAULT, navObj);
            String scroll = NetParseUtils.getString(NetConstant.NET_JSON_DISCOVER_NAVIGATION_SCROLL_BAR, navObj);
            mDiscoveryNavigation = StringUtils.split(scroll, ",");
        }
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_DISCOVER_FIELD_RECYCLE_LISTS, obj);
        parseArray(array);
    }

    public int getDiscoveryNavDefault() {
        return mDiscoveryNavDefault;
    }

    public List<DiscoveryRecycleModel> getDiscoveryLists() {
        return mDiscoveryRecycleData;
    }

    public String[] getDiscoveryNavScrollView() {
        return mDiscoveryNavigation;
    }

    private void parseArray(JSONArray array) {
        if (array == null || array.length() == 0) {
            return;
        }

        int length = array.length();
        for (int i = 0; i < length; ++i) {
            try {
                JSONObject obj = array.getJSONObject(i);
                String title = NetParseUtils.getString(NetConstant.NET_JSON_DISCOVER_LISTS_TITLE, obj);
                int rank = NetParseUtils.getInt(NetConstant.NET_JSON_DISCOVER_LISTS_RANK, obj);
                String property = NetParseUtils.getString(NetConstant.NET_JSON_DISCOVER_LISTS_PROPERTY, obj);
                int attention = NetParseUtils.getInt(NetConstant.NET_JSON_DISCOVER_LISTS_ATTENTION, obj);
                String imgUrl = NetParseUtils.getString(NetConstant.NET_JSON_DISCOVER_LISTS_IMG, obj);
                String summary = NetParseUtils.getString(NetConstant.NET_JSON_DISCOVER_LISTS_SUMMARY, obj);

                DiscoveryRecycleModel model = new DiscoveryRecycleModel();
                model.setTitle(title);
                model.setAttention(attention);
                model.setProperty(property);
                model.setAppRank(rank);
                model.setBannerOrImgURL(imgUrl);
                model.setSummary(summary);

                mDiscoveryRecycleData.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
