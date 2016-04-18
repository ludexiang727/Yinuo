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
    public static final String REQUEST_URL_DISCOVERY_PAGE = "pGetDiscoveryPage";
    public static final String REQUEST_URL_PARTNER_PAGE = "pGetPartnerPage";
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

    /** app details page begin **/
    public static final String NET_JSON_APP_DETAILS_IMGS = "app_details_imgs";
    /** app details page end*/

    /**home page data begin */
    public static final String NET_REQUEST_HOMEPAGE_PARAM_INDEX = "home_page_index";
    public static final String NET_REQUEST_HOMEPAGE_PARAM_COUNT = "home_page_count";
    public static final String NET_REQUEST_HOMEPAGE_DETAILS_APPID = "home_page_details_appid";

    // json
    public static final String NET_JSON_HOME_FIELD_DATA_COUNT = "home_page_data_count";
    public static final String NET_JSON_HOME_FIELD_PAGE_BANNERS = "home_page_banners";
    public static final String NET_JSON_HOME_BANNER_IMG_URL = "banners_image_url";
    public static final String NET_JSON_HOME_BANNER_REDIRECT_URL = "banners_redirect_url";
    public static final String NET_JSON_HOME_FIELD_PAGE_LISTS = "home_page_lists";
    public static final String NET_JSON_HOME_CARD_IMG = "card_img";
    public static final String NET_JSON_HOME_CARD_TITLE = "card_title";
    public static final String NET_JSON_HOME_CARD_SUMMARY = "card_summary";
    public static final String NET_JSON_HOME_CARD_ATTENTION = "card_attention";
    public static final String NET_JSON_HOME_CARD_COLLECTION = "card_collection";
    public static final String NET_JSON_HOME_CARD_TAGS = "card_tags";
    /**home page data end*/

    /** discovery page data begin **/
    public static final String NET_REQUEST_DISCOVERYPAGE_PARAM_PROPERTYID = "discovery_page_properyid";

    // json
    public static final String NET_JSON_DISCOVER_FIELD_NAVIGATION = "discovery_navigation";
    public static final String NET_JSON_DISCOVER_NAVIGATION_DEFAULT = "discovery_nav_default";
    public static final String NET_JSON_DISCOVER_NAVIGATION_SCROLL_BAR = "discovery_nav_scroll_bar";
    public static final String NET_JSON_DISCOVER_FIELD_RECYCLE_LISTS = "discovery_lists";
    public static final String NET_JSON_DISCOVER_LISTS_TITLE = "discovery_recycle_title";
    public static final String NET_JSON_DISCOVER_LISTS_IMG = "discovery_recycle_img_url";
    public static final String NET_JSON_DISCOVER_LISTS_RANK = "discovery_recycle_rank";
    public static final String NET_JSON_DISCOVER_LISTS_PROPERTY = "discovery_recycle_property";
    public static final String NET_JSON_DISCOVER_LISTS_SUMMARY = "discovery_recycle_summary";
    public static final String NET_JSON_DISCOVER_LISTS_ATTENTION = "discovery_recycle_attention";
    /** discovery page data end */

    /** partner page data begin */
    public static final String NET_REQUEST_PARTNER_PARAM_INDEX = "partner_page_index";
    public static final String NET_REQUEST_PARTNER_PARAM_COUNT = "partner_page_count";
    public static final String NET_REQUEST_PARTNER_PARAM_LIMIT = "partner_page_limit";
    public static final String NET_REQUEST_PARTNER_PARAM_CONDITION = "partner_page_condition";

    // json
    public static final String NET_JSON_PARTNER_FIELD_LISTS_COUNT = "partner_count";
    public static final String NET_JSON_PARTNER_FIELD_LISTS = "partner_lists";
    public static final String NET_JSON_PARTNER_LISTS_ID = "partner_id";
    public static final String NET_JSON_PARTNER_LISTS_NAME = "partner_name";
    public static final String NET_JSON_PARTNER_LISTS_TEL = "partner_tel";
    public static final String NET_JSON_PARTNER_LISTS_GENDER = "partner_gender";
    public static final String NET_JSON_PARTNER_LISTS_IMG = "partner_img";
    /** partner page data end*/

    /******************  json parse end field **********************/
}
