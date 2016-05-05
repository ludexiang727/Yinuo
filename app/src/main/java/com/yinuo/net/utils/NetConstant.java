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
    public static final String REQUEST_URL_INVEST_PAGE = "pGetInvestPage";
    public static final String REQUEST_URL_INVEST_WECHAT = "pWechat";
    public static final String REQUEST_URL_INVEST_WECHAT_SEND = "pWechatSendMsg";
    public static final String REQUEST_URL_LOAN_PAGE = "pGetLoanPage";
    public static final String REQUEST_URL_WORKSPACE_PAGE = "pGetWorkspacePage";
    public static final String REQUEST_URL_BOSS_ONLINE_PAGE = "pGetBossOnlinePage";
    public static final String REQUEST_URL_BOSS_ONLINE_ABOUT_PAGE = "pGetBossOnlineAboutPage";
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

    /** invest page data being */
    public static final String NET_REQUEST_INVEST_PARAM_INDEX = "invest_page_index";
    public static final String NET_REQUEST_INVEST_PARAM_COUNT = "invest_page_count";
    public static final String NET_REQUEST_INVEST_WECHAT_TIME = "invest_wechat_time";
    public static final String NET_REQUEST_INVEST_WECHAT_BOSS_ID = "invest_wechat_boss_id";
    public static final String NET_REQUEST_INVEST_WECHAT_SEND_MSG = "invest_wechat_send_msg";
    public static final String NET_REQUEST_INVEST_WECHAT_SEND_TIME = "invest_wechat_send_time";

    // json
    public static final String NET_JSON_INVEST_FIELD_LISTS_COUNT = "invest_count";
    public static final String NET_JSON_INVEST_FIELD_LISTS = "invest_lists";
    public static final String NET_JSON_INVEST_LISTS_ID = "invest_list_id";
    public static final String NET_JSON_INVEST_LISTS_NAME = "invest_list_name";
    public static final String NET_JSON_INVEST_LISTS_CARD = "invest_list_card";
    public static final String NET_JSON_INVEST_LISTS_GENDER = "invest_list_gender";
    public static final String NET_JSON_INVEST_LISTS_VALIDATE = "invest_list_validate";
    public static final String NET_JSON_INVEST_LISTS_IMG = "invest_list_img";
    public static final String NET_JSON_INVEST_LISTS_DUTY = "invest_list_duty";
    // may be empty
    public static final String NET_JSON_INVEST_LISTS_COMPANY = "invest_list_company";
    public static final String NET_JSON_INVEST_LISTS_NOTICE = "invest_list_notice";
    public static final String NET_JSON_INVEST_LISTS_PROVINCE = "invest_list_province";
    public static final String NET_JSON_INVEST_LISTS_CITY = "invest_list_city";


    // wechat
    public static final String NET_JSON_INVEST_WECHAT_LISTS = "invest_wechat_lists";
    public static final String NET_JSON_INVEST_WECHAT_TIME = "wechat_time";
    public static final String NET_JSON_INVEST_WECHAT_HEADER = "wechat_header_url";
    public static final String NET_JSON_INVEST_WECHAT_MSG_BODY = "wechat_msg_body";
    public static final String NET_JSON_INVEST_WECHAT_MSG_TYPE = "wechat_msg_type";
    /** invest page data end*/

    /** loan page data begin */
    public static final String NET_REQUEST_LOAN_PARAM_OPTION_ID = "loan_option_id";
    public static final String NET_REQUEST_LOAN_PARAM_OPTION_LOCATION = "loan_option_location";

    // json
    public static final String NET_JSON_LOAN_FIELD_OPTIONS_LISTS = "loan_options_list";
    public static final String NET_JSON_LOAN_OPTIONS_ID = "loan_option_id";
    public static final String NET_JSON_LOAN_OPTIONS_IMG = "loan_option_img";
    public static final String NET_JSON_LOAN_OPTIONS_TXT = "loan_option_txt";
    public static final String NET_JSON_LOAN_OPTIONS_HOT = "loan_option_hot";
    public static final String NET_JSON_LOAN_OPTIONS_LOCATION = "loan_option_location";
    /** loan page data end */

    /** workspace page data begin */
    public static final String NET_REQUEST_WORKSPACE_PARAM_INDEX = "workspace_page_index";
    public static final String NET_REQUEST_WORKSPACE_PARAM_COUNT = "workspace_page_count";

    // json
    public static final String NET_JSON_WORKSPACE_FIELD_OPTIONS_LISTS = "workspace_option_lists";
    public static final String NET_JSON_WORKSPACE_OPTIONS_IMG = "workspace_option_img";
    public static final String NET_JSON_WORKSPACE_OPTIONS_TXT = "workspace_option_txt";
    public static final String NET_JSON_WORKSPACE_OPTIONS_ID = "workspace_option_id";
    public static final String NET_JSON_WORKSPACE_FIELD_LIST_LISTS = "workspace_page_lists";
    public static final String NET_JSON_WORKSPACE_LIST_ID = "workspace_list_id";
    public static final String NET_JSON_WORKSPACE_LIST_IMG = "workspace_list_img";
    public static final String NET_JSON_WORKSPACE_LIST_TITLE = "workspace_list_title";
    public static final String NET_JSON_WORKSPACE_LIST_TAGS = "workspace_list_tags";
    public static final String NET_JSON_WORKSPACE_LIST_LOCATION = "workspace_list_location";
    public static final String NET_JSON_WORKSPACE_LIST_EXTRA = "workspace_list_extra";
    public static final String NET_JSON_WORKSPACE_LIST_PRICE = "workspace_list_price";
    /** workspace page data end*/

    /** boss online page data begin */
    public static final String NET_REQUEST_BOSS_ONLINE_PARAM_INDEX = "boss_online_page_index";
    public static final String NET_REQUEST_BOSS_ONLINE_PARAM_COUNT = "boss_online_page_count";

    // json
    public static final String NET_JSON_BOSS_ONLINE_FIELD_WORK_LISTS = "boss_online_work_lists";

    public static final String NET_JSON_BOSS_ONLINE_BOSS_INFO = "boss_online_boss_info";
    public static final String NET_JSON_BOSS_ONLINE_BOSS_ID = "boss_online_boss_id";
    public static final String NET_JSON_BOSS_ONLINE_BOSS_IMG = "boss_online_boss_img";
    public static final String NET_JSON_BOSS_ONLINE_BOSS_TEL = "boss_online_boss_tel";
    public static final String NET_JSON_BOSS_ONLINE_BOSS_VALIDATE = "boss_online_boss_validate";
    public static final String NET_JSON_BOSS_ONLINE_BOSS_NAME = "boss_online_boss_name";
    public static final String NET_JSON_BOSS_ONLINE_BOSS_DUTY = "boss_online_boss_duty";
    public static final String NET_JSON_BOSS_ONLINE_COMPANY_ID = "boss_online_company_id";
    public static final String NET_JSON_BOSS_ONLINE_COMPANY_NAME= "boss_online_company_name";
    public static final String NET_JSON_BOSS_ONLINE_COMPANY_LOCATION = "boss_online_company_location";
    public static final String NET_JSON_BOSS_ONLINE_COMPANY_ABOUT = "boss_online_company_about";
    // 大于3条 有更多
    public static final String NET_JSON_BOSS_ONLINE_WORKS_TOTAL = "boss_online_works_total";

    public static final String NET_JSON_BOSS_ONLINE_WORK_WORKS = "boss_online_works";
    public static final String NET_JSON_BOSS_ONLINE_WORK_ID = "boss_online_work_id";
    // work duty -- 招聘职位
    public static final String NET_JSON_BOSS_ONLINE_WORK_DUTY = "boss_online_work_duty";
    // work duty -- 招聘人数
    public static final String NET_JSON_BOSS_ONLINE_WORKS_NUMBER = "boss_online_work_numbers";
    // publish time -- 发布时间
    public static final String NET_JSON_BOSS_ONLINE_WORK_TIME = "boss_online_publish_time";
    // work salary -- 薪资
    public static final String NET_JSON_BOSS_ONLINE_WORK_SALARY = "boss_online_work_salary";
    // work property -- 工作属性 - 兼、全职
    public static final String NET_JSON_BOSS_ONLINE_WORK_PROPERTY = "boss_online_work_property";
    /** boss online page data end*/

    /** boss online about page data begin */
    // param
    public static final String NET_REQUEST_BOSS_ONLINE_ABOUT_PARAM_COMPANY_ID = "boss_online_about_company_id";

    // json
    /** boss online about page data end */
    /******************  json parse end field **********************/
}
