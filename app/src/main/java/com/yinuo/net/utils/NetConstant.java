package com.yinuo.net.utils;

/**
 * Created by ludexiang on 2016/4/8.
 */
public class NetConstant {
    /***************** request interface begin -- 请求接口名称 *********************/
    public static final String REQUEST_URL = "http://www.baidu.com/";
    public static final String REQUEST_URL_SIGN_IN = "pSignIn";
    public static final String REQUEST_URL_SIGN_UP = "PSignUp";
    public static final String REQUEST_URL_HOME_PAGE = "pGetHomePage";
    public static final String REQUEST_URL_HOME_PAGE_DETAILS = "pGetHomeDetails";
    /***************** request interface end *********************/

    /*************  json parse begin field -- 接口response json field **********************/
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
    public static final String NET_REQUEST_HOMEPAGE_DETAILS_APPID = "home_page_details_appid";

    // json
    public static final String NET_JSON_FIELD_DATA_COUNT = "home_page_data_count";
    public static final String NET_JSON_FIELD_PAGE_BANNERS = "home_page_banners";
    public static final String NET_JSON_HOME_BANNER_IMG_URL = "banners_image_url";
    public static final String NET_JSON_HOME_BANNER_REDIRECT_URL = "banners_redirect_url";
    public static final String NET_JSON_FIELD_PAGE_LISTS = "home_page_lists";
    public static final String NET_JSON_HOME_CARD_IMG = "card_img";
    public static final String NET_JSON_HOME_CARD_TITLE = "card_title";
    public static final String NET_JSON_HOME_CARD_SUMMARY = "card_summary";
    public static final String NET_JSON_HOME_CARD_ATTENTION = "card_attention";
    public static final String NET_JSON_HOME_CARD_COLLECTION = "card_collection";
    public static final String NET_JSON_HOME_CARD_TAGS = "card_tags";
    /**home page data end*/

    /** home page details json begin */
    public static final String NET_JSON_HOME_DETAILS_IMGS = "home_details_imgs";
    /** home page details json end*/


    /******************  json parse end field **********************/
}
