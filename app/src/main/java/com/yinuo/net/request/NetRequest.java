package com.yinuo.net.request;

import com.squareup.okhttp.FormEncodingBuilder;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.response.NetBossOnlineAboutPageObj;
import com.yinuo.net.response.NetBossOnlinePageObj;
import com.yinuo.net.response.NetDiscoveryPageObj;
import com.yinuo.net.response.NetHomePageDetailsObj;
import com.yinuo.net.response.NetHomePageObj;
import com.yinuo.net.response.NetInvestPageObj;
import com.yinuo.net.response.NetLoanPageObj;
import com.yinuo.net.response.NetLoginObj;
import com.yinuo.net.response.NetPartnerPageObj;
import com.yinuo.net.response.NetRegisterObj;
import com.yinuo.net.response.NetWorkspacePageObj;
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
    public int requestLogin(String userName, String password, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_LOGIN_PARAM_USERNAME, userName);
        build.add(NetConstant.NET_REQUEST_LOGIN_PARAM_PASSWORD, password);
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_SIGN_IN,
                build.build(), listener, new NetLoginObj());
        return requestCode;
    }

    /** sign up -- 注册 **/
    public int requestRegister(String userName, String password, String code, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_REGISTER_PARAM_USERNAME, userName);
        build.add(NetConstant.NET_REQUEST_REGISTER_PARAM_PASSWORD, password);
        build.add(NetConstant.NET_REQUEST_REGISTER_PARAM_CODE, code);
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_SIGN_UP,
                build.build(), listener, new NetRegisterObj());
        return requestCode;
    }

    /** home page data -- 首页数据 分页 及 加载更多*/
    public int requestHomePageData(int pageIndex, int pageCount, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_HOMEPAGE_PARAM_INDEX, String.valueOf(pageIndex));
        build.add(NetConstant.NET_REQUEST_HOMEPAGE_PARAM_COUNT, String.valueOf(pageCount));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_HOME_PAGE,
                build.build(), listener, new NetHomePageObj());
        return requestCode;
    }

    /** home page data -- app 数据详情页 */
    public int requestAppDetails(int appId, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_HOMEPAGE_DETAILS_APPID, String.valueOf(appId));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_HOME_PAGE_DETAILS,
                build.build(), listener, new NetHomePageDetailsObj());
        return requestCode;
    }

    /** discovery page data - 发现页面 数据请求 **/
    public int requestDiscoveryPageData(int propertyId, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_DISCOVERYPAGE_PARAM_PROPERTYID, String.valueOf(propertyId));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_DISCOVERY_PAGE,
                build.build(), listener, new NetDiscoveryPageObj());
        return requestCode;
    }

    /** partner page data - 合伙人页面 数据请求
     *  limit -- just female or male
     *  condition -- invest or skill 0 -- skill 1 -- invest partner
     * **/
    public int requestPartnerPageData(int pageIndex, int pageCount, int limit, int condition, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_INDEX, String.valueOf(pageIndex));
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_COUNT, String.valueOf(pageCount));
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_LIMIT, String.valueOf(limit));
        build.add(NetConstant.NET_REQUEST_PARTNER_PARAM_CONDITION, String.valueOf(condition));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_PARTNER_PAGE,
                build.build(), listener, new NetPartnerPageObj());
        return requestCode;
    }

    /** invest page data - 找投资页面 数据请求*/
    public int requestInvestPageData(int pageIndex, int pageCount, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_INVEST_PARAM_INDEX, String.valueOf(pageIndex));
        build.add(NetConstant.NET_REQUEST_INVEST_PARAM_COUNT, String.valueOf(pageCount));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_INVEST_PAGE,
                build.build(), listener, new NetInvestPageObj());
        return requestCode;
    }

    /** loan page data -- 小额贷款页面 */
    public int requestLoanPageData(int optionId, int optionLocation, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_LOAN_PARAM_OPTION_ID, String.valueOf(optionId));
        build.add(NetConstant.NET_REQUEST_LOAN_PARAM_OPTION_LOCATION, String.valueOf(optionLocation));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_LOAN_PAGE,
                build.build(), listener, new NetLoanPageObj());
        return requestCode;
    }

    /** workspace page data -- 工作间页面 */
    public int requestWorkspacePageData(int pageIndex, int pageCount, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_WORKSPACE_PARAM_INDEX, String.valueOf(pageIndex));
        build.add(NetConstant.NET_REQUEST_WORKSPACE_PARAM_COUNT, String.valueOf(pageCount));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_WORKSPACE_PAGE,
                build.build(), listener, new NetWorkspacePageObj());
        return requestCode;
    }

    /** boss online page data -- 伯乐在线 页面 */
    public int requestBossOnlinePageData(int pageIndex, int pageCount, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_BOSS_ONLINE_PARAM_INDEX, String.valueOf(pageIndex));
        build.add(NetConstant.NET_REQUEST_BOSS_ONLINE_PARAM_COUNT, String.valueOf(pageCount));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_BOSS_ONLINE_PAGE,
                build.build(), listener, new NetBossOnlinePageObj());
        return requestCode;
    }

    /** boss online page about page -- 伯乐在线关于界面 **/
    public int requestBossOnlineAboutPageData(int companyId, IRequestListener<T> listener) {
        FormEncodingBuilder build = build();
        build.add(NetConstant.NET_REQUEST_BOSS_ONLINE_ABOUT_PARAM_COMPANY_ID, String.valueOf(companyId));
        int requestCode = OkHttpRequest.getInstance().httpPostRequest(NetConstant.REQUEST_URL_BOSS_ONLINE_ABOUT_PAGE,
                build.build(), listener, new NetBossOnlineAboutPageObj());
        return requestCode;
    }

    public void cancelRequest(int key) {
        OkHttpRequest.getInstance().cancelRequest(key);
    }
}
