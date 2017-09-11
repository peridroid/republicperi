package ru.devtron.republicperi.utils;

public interface Const {
    String BASE_URL = "http://api.republic05.ru/v1/";
    String ETAG_HEADER = "If-None-Match";

    boolean isBackendReadyForSocialAuth = false;

    String VK_BASE_URL = "https://api.vk.com/method/";
    String FACEBOOK_BASE_URL = "https://graph.facebook.com/v2.8/";
    String TWITTER_BASE_URL = "https://api.twitter.com/1.1/";

    //network
    int MAX_CONNECTION_TIMEOUT = 10000;
    int MAX_READ_TIMEOUT = 5000;
    int MAX_WRITE_TIMEOUT = 12000;
    int RETRY_REQUEST_BASE_DELAY = 500;
    int RETRY_REQUEST_COUNT = 5;

    int PERMISSION_REQUEST_WRITE_EXTERNAL = 89;
    int PERMISSION_REQUEST_CAMERA = 87;
    int REQUEST_CAMERA_PICTURE = 99;
    int REQUEST_GALLERY_PICTURE = 88;

    //region ==================== Notifications ====================
    int PUSH_REQ_CODE = 535;
    int NOTIFICATION_MANAGER_ID = 7;
    CharSequence NOTIFICATION_URGENT_TOUR_TYPE = "NOTIFICATION_URGENT_TOUR_TYPE";
    CharSequence NOTIFICATION_ACTION_TYPE = "NOTIFICATION_ACTION_TYPE";
    //endregion
}
