package com.yinuo.net.response;

import com.yinuo.mode.HomeItemDetailBannerMode;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;
import com.yinuo.utils.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/12.
 */
public class NetHomePageDetailsObj extends NetBaseObject {

    private List<HomeItemDetailBannerMode> mAppDetailsImgs;

    @Override
    protected void parse(JSONObject obj) {
        mAppDetailsImgs = new ArrayList<HomeItemDetailBannerMode>();
        String images = NetParseUtils.getString(NetConstant.NET_JSON_APP_DETAILS_IMGS, obj);
        String[] imgArray = AppUtils.split(images, ",");
        for (String img : imgArray) {
            HomeItemDetailBannerMode mode = new HomeItemDetailBannerMode();
            mode.setBannerOrImgURL(img);
            mAppDetailsImgs.add(mode);
        }
    }

    public List<HomeItemDetailBannerMode> getDetailsImgs() {
        return mAppDetailsImgs;
    }
}
