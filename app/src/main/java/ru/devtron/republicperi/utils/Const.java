package ru.devtron.republicperi.utils;

public interface Const {
    String BASE_URL = "http://api.republic05.ru/v1/";
    String ETAG_HEADER = "If-None-Match";

    int PERMISSION_REQUEST_WRITE_EXTERNAL = 89;
    int PERMISSION_REQUEST_CAMERA = 87;
    int REQUEST_CAMERA_PICTURE = 99;
    int REQUEST_GALLERY_PICTURE = 88;
}
