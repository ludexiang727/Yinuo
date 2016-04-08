package com.yinuo.net.utils;

/**
 * Created by ludexiang on 2016/4/8.
 */
public class NetConstant {
    public static final String REQUEST_URL = "http://www.baidu.com/";
    public static final String REQUEST_URL_SIGN_IN = "sign_in";
    public static final String REQUEST_URL_SIGN_UP = "sign_up";
    public static final String REQUEST_URL_HOME_PAGE = "home_page";

    /******************  json parse begin field **********************/
    /** base begin*/
    public static final String NET_JSON_BASE_ERRNO = "errno";
    public static final String NET_JSON_BASE_ERRMSG = "errmsg";
    /** base end */

    /** sign in / out begin */
    public static final String NET_REQUEST_LOGIN_PARAM_USERNAME = "login_uname";
    public static final String NET_REQUEST_LOGIN_PARAM_PASSWORD = "login_pwd";
    public static final String NET_REQUEST_REGISTER_PARAM_USERNAME = "register_uname";
    public static final String NET_REQUEST_REGISTER_PARAM_PASSWORD = "register_pwd";
    public static final String NET_REQUEST_REGISTER_PARAM_CODE = "register_code";
    /** sign in / out end */

    /**home page data begin */
    public static final String NET_REQUEST_HOMEPAGE_PARAM_INDEX = "home_page_index";
    public static final String NET_REQUEST_HOMEPAGE_PARAM_COUNT = "home_page_count";

    // json
    public static final String NET_JSON_FIELD_DATA_COUNT = "home_page_data_count";
    public static final String NET_JSON_FIELD_PAGE_BANNERS = "home_page_banners";
    public static final String NET_JSON_FIELD_PAGE_LISTS = "home_page_lists";
    public static final String NET_JSON_HOME_CARD_IMG = "card_img";
    public static final String NET_JSON_HOME_CARD_TITLE = "card_title";
    public static final String NET_JSON_HOME_CARD_SUMMARY = "card_summary";
    public static final String NET_JSON_HOME_CARD_ATTENTION = "card_attention";
    public static final String NET_JSON_HOME_CARD_COLLECTION = "card_collection";
    public static final String NET_JSON_HOME_CARD_TAGS = "card_tags";
    /**home page data end*/


    /******************  json parse end field **********************/
}
