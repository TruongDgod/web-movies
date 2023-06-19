/*******************************************************************************
 * Class        AppConst
 * Created date 2021/11/11
 * Lasted date
 * Author       VIDAL
 * Change log   2021/11/11 VIDAL Create New
 ******************************************************************************/
package com.bepay.application.constant;

public class AppConst {

    public static final String PHONE_NUMBER_REG = "^(257|\\+257|00257|0|)(31|61|62|63|64|65|66|67|68|69|71|72|76|79|75|78|21|22|23|24|25|26|27|28|29|77)([0-9]{6})$";
    public static final String PHONE_NUMBER_SPEC_REG = "[\\{\\}\\(\\) \\+]";
    public static final String BLANK = "";
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String TRUE = "true";
    public static final String FALSE = "false";


    public static final String MULTI_LANGUAGE = "Multi-language";

    public static final int INT_MULTI_LANGUAGE = 1;
    public static final int INT_NOT_MULTI_LANGUAGE = 0;
    public static final int NOTIFICATION_TYPE = 1;
    public static final int PROMOTION_TYPE = 2;

    public static final String CODE_SUCCESS = "200";
    public static final String CODE_BAD_REQUEST = "400";
    public static final Integer CODE_SESSION_TIMEOUT = 440;
    public static final String AML_REDIRECT="AML_REDIRECT";
    public static final String AML_CONTENTPATH="AML_CONTENTPATH";

    public static final String NEWS = "NEWS";

    public static final String GROUP_DATA_PACKAGE = "GROUP_DATA_PACKAGE";

    public static final String PROMOTION = "PROMOTION";
    public static final String TITLE = "TITLE";
    public static final String NAME = "NAME";

    public static final String SUB_TITLE = "SUB_TITLE";

    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String GROUP_DATA = "GROUP_DATA";
    public static final String BANNER = "BANNER";
    public static final String HEADER = "HEADER";
    public static final String AVATAR = "AVATAR";
    public static final String SHORT_CONTENT = "SHORT_CONTENT";
    public static final String FULL_CONTENT = "FULL_CONTENT";
    public static final String ALL = "ALL";

    public static final String GROUP_DATA_PACKAGE_IMG = "GROUP_DATA_PACKAGE";
    public static final String NEWS_SHORT_CONTENT = "news.shortcontent.";


    public static final String NEWS_FULL_CONTENT = "news.fullcontent.";
    public static final String NEWS_TITLE = "news.title.";
    public static final String GROUP_DATA_NAME = "group_data.name.";
    public static final String GROUP_DATA_PACKAGE_NAME = "group_data_package.name.";

    public static final String GROUP_DATA_PACKAGE_SHORT_NAME_STR = "SHORT_NAME";

    public static final String GROUP_DATA_PACKAGE_SHORT_NAME = "group_data_package.short_name.";
    public static final String GROUP_DATA_PACKAGE_TITLE = "group_data_package.title.";
    public static final String GROUP_DATA_PACKAGE_SUB_TITLE = "group_data_package.sub_title.";

    public static final String GROUP_DATA_PACKAGE_DESCRIPTION = "group_data_package.description.";

    public static final String DATE_FORMAT_DDMMYYYY = "dd/MM/yyyy";
    public static final String DATE_FORMAT_MMDDYYYY = "MM-dd-yyyy";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMMDD_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_FORMAT_MMDDYYYY_WITH_SLASH = "MM/dd/yyyy";

    public static final String SECRET = "U2VjcmV0S2V5VG9HZW5KV1Rz";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    /** Expire Time To Redis **/
    public static final long EXPIRED_TIME_REDIS = 60 * 60;

    public static final String ROLE_END_USER = "End User";
    public static final String ROLE_AGENT = "Agent & Merchant";

    public static final int CATEGORY_PROMOTION = 1;
    public static final int CATEGORY_ALL = 2;


    public static final int CMS_TEST_STATUS_COMPLETED = 3;




    /** Suffix Auth **/
    public static final String SUFFIX_AUTH = "userInfo";

    public static final int IS_DELETE_ENABLE = 0;
    public static final int IS_DELETE_DISENABLE = 1;



    public static final String NEWS_TABLE_NAME = "NEWS";
    public static final String SEQ_NEWS_NAME = "NEWS_SEQ";
    public static final Integer NEWS_RECORD_PER_PAGE = 20;
    public static final Integer NEWS_PAGE_START = 0;


    public static final String APP_CONFIG_TABLE_NAME = "APP_CONFIG";
    public static final String SEQ_APP_CONFIG_NAME = "APP_CONFIG_SEQ";

    public static final String CONFIG_APP_CONFIG_NAME = "APP_CONFIG_CONFIG";

    public static final Integer APP_CONFIG_RECORD_PER_PAGE = 20;
    public static final Integer APP_CONFIG_PAGE_START = 0;

    public static final String APP_IMAGE_TABLE_NAME = "APP_IMAGE";
    public static final String SEQ_APP_IMAGE_NAME = "APP_IMAGE_SEQ";

    public static final String CONFIG_APP_IMAGE_NAME = "APP_IMAGE_CONFIG";

    public static final Integer APP_IMAGE_PER_PAGE = 20;
    public static final Integer APP_IMAGE_PAGE_START = 0;

    public static final String FEATURE_CODE_TABLE_NAME = "FEATURE_CODE";



    /** SMS user segment entity **/
    public static final String SEGMENT_TABLE_NAME = "CMS_USER_SEGMENT";
    public static final String SEQ_USER_SEGMENT_SEQ_NAME = "CMS_USER_SEGMENT_SEQ";

    public static final String CONFIG_USER_SEGMENT_SEQ_NAME = "CMS_USER_SEGMENT_CONFIG";

    /** SMS user list entity **/
    public static final String USER_LIST_TABLE_NAME = "CMS_USER_LIST";
    public static final String SEQ_USER_LIST_NAME = "CMS_USER_LIST_SEQ";
    public static final String CONFIG_USER_LIST_NAME = "CMS_USER_LIST_CONFIG";



    /** SMS MESSAGE entity **/
    public static final String SCHEDULE_MESSAGE_TABLE_NAME = "CMS_SCHEDULE_MESSAGE";
    public static final String SEQ_SCHEDULE_MESSAGE_NAME = "CMS_SCHEDULE_MESSAGE_SEQ";
    public static final String CONFIG_SCHEDULE_MESSAGE_NAME = "CMS_SCHEDULE_MESSAGE_CONFIG";


    /** Notification log **/
    public static final String NOTIFICATION_LOG_TABLE_NAME = "NOTIFICATION_LOG";
    public static final String NOTIFICATION_LOG_SEQ = "NOTIFICATION_SEQ";



    /** CMS Message entity **/
    public static final String MESSAGE_TABLE_NAME = "CMS_MESSAGE";
    public static final String SEQ_MESSAGE_NAME = "CMS_MESSAGE_SEQ";

    public static final String CONFIG_MESSAGE_NAME = "CMS_MESSAGE_CONFIG";


    //* CMS User List **/
//    public static final String USER_LIST_TABLE_NAME = "CMS_USER_LIST";


    public static final String ACCOUNT_TABLE_NAME = "CMS_ACCOUNT";
    public static final String SEQ_ACCOUNT_NAME = "ACCOUNT_SEQ";

    public static final int STATUS_DRAFT = 1;
    public static final int STATUS_READY = 2;

    public static final int STATUS_COMPLETED = 4;


    // Group Data table
    public static final String GROUP_DATA_TABLE_NAME = "GROUP_DATA";
    public static final String SEQ_GROUP_DATA = "GROUP_DATA_SEQ";


    public static final int SAVE_DRAFT = 1;
    public static final int SAVE_PUBLISH = 0;


    // CMS ACTION FEATURE
    public static final String CMS_ACTION_FEATURE_TABLE_NAME = "CMS_ACTION_FEATURE";
    public static final String SEQ_CMS_ACTION_FEATURE = "CMS_ACTION_FEATURE_SEQ";





    // Group CMS Role
    public static final String CMS_ROLE_TABLE_NAME = "CMS_ROLES";
    public static final String SEQ_CMS_ROLE = "CMS_ROLE_SEQ";

    // Group data pakge
    public static final String GROUP_DATA_PACKAGE_TABLE_NAME = "GROUP_DATA_PACKAGE";
    public static final String SEQ_GROUP_DATA_PACKAGE = "GROUP_DATA_PACKAGE_SEQ";


    // file
    public static final String CMS_FILE_USER_LIST = "CMS_FILE_USER_LIST";

    public static final String CMS_FILE_USER_LIST_SEQ = "CMS_FILE_USER_LIST_SEQ";



    public static final int CMS_TARGET_TYPE_USER_LIST = 0;
    public static final int CMS_TARGET_TYPE_USER_SEGMENT = 1;

    public static final int CMS_SCHEDULE_STATUS_PUBLISH_NOW = 0;

    public static final int CMS_SCHEDULE_STATUS_SCHEDULE = 1;
    public static final int PROMOTION_CONTENT_TYPE_HTML = 1;
    public static final int STATUS_ON = 1;
    public static final int STATUS_OFF = 0;

    public static final String MESSAGE_STATUS_ON = "ON";
    public static final String MESSAGE_STATUS_OFF = "OFF";


    public static final String STATUS_NEWS_MESSAGE_ACTIVE = "Active";
    public static final String  STATUS_NEWS_MESSAGE_IN_ACTIVE = "Inactive";

    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_FR = "fr";
    public static final String LANGUAGE_RN = "rn";
    public static final String URL_IMAGE = "/news/";
    public static final String URL_IMAGE_DATA_PACKAGE = "/news/data-package/";

}
