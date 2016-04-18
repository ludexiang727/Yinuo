package com.yinuo.net.request;

import com.squareup.okhttp.FormEncodingBuilder;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.response.NetDiscoveryPageObj;
import com.yinuo.net.response.NetHomePageDetailsObj;
import com.yinuo.net.response.NetHomePageObj;
import com.yinuo.net.response.NetLoginObj;
import com.yinuo.net.response.NetPartnerPageObj;
import com.yinuo.net.response.NetRegisterObj;
import com.yinuo.net.utils.NetConstant;

/**
 * Created by ludexiang on 2016/4/8.
 */
public class NetRequest <T extends NetBaseObject> {
    private static NetRequest mInstance;

    private NetRequest() {

    }

    public static NetRequest getInstance() {
        return NetRequestFactory.create();
    }

    public static class NetRequestFactory {
        public static NetRequest create() {
            if (mInstance == null) {
                mInstance = new NetRequest();
            }
            return mInstance;
        }
    }

    private FormEncodingBuilder build() {
        /** upload common params */
        FormEncodingBuilder build = new FormEncodingBuilder();
        return build;
    }

    /** sign in -- 登录 **/
    public void requestLogin(String userName, String password, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_LOGIN_PARAM_USERNAME, userName);
        build.add(NetConstant.NET_REQUEST_LOGIN_PARAM_PASSWORD, password);
        OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_SIGN_IN, build.build(), listener, new NetLoginObj());
    }

    /** sign up -- 注册 **/
    public void requestRegister(String userName, String password, String code, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_REGISTER_PARAM_USERNAME, userName);
        build.add(NetConstant.NET_REQUEST_REGISTER_PARAM_PASSWORD, password);
        build.add(NetConstant.NET_REQUEST_REGISTER_PARAM_CODE, code);
        OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_SIGN_UP, build.build(), listener, new NetRegisterObj());
    }

    /** home page data -- 首页数据 分页 及 加载更多*/
    public void requestHomePageData(int pageIndex, int pageCount, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_HOMEPAGE_PARAM_INDEX, String.valueOf(pageIndex));
        build.add(NetConstant.NET_REQUEST_HOMEPAGE_PARAM_COUNT, String.valueOf(pageCount));
        OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_HOME_PAGE, build.build(), listener, new NetHomePageObj());
    }

    /** home page data -- app 数据详情页 */
    public void requestAppDetails(int appId, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_HOMEPAGE_DETAILS_APPID, String.valueOf(appId));
        OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_HOME_PAGE_DETAILS, build.build(), listener, new NetHomePageDetailsObj());
    }

    /** discovery page data - 发现页面 数据请求 **/
    public void requestDiscoveryPageData(int propertyId, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_DISCOVERYPAGE_PARAM_PROPERTYID, String.valueOf(propertyId));
        OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_DISCOVERY_PAGE, build.build(), listener, new NetDiscoveryPageObj());
    }

    /** partner page data - 合伙人页面 数据请求
     *  limit -- just female or male
     *  condition -- invest or skill 0 -- skill 1 -- invest partner
     * **/
    public void requestPartnerPageData(int pageIndex, int pageCount, int limit, int condition, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_INDEX, String.valueOf(pageIndex));
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_COUNT, String.valueOf(pageCount));
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_LIMIT, String.valueOf(limit));
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_CONDITION, String.valueOf(condition));
        OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_PARTNER_PAGE, build.build(), listener, new NetPartnerPageObj());
    }
}
