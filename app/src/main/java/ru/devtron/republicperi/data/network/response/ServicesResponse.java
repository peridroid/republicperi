package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServicesResponse {

    public List<ServiceRes> services;

    public class ServiceRes extends BaseResponse {
        @SerializedName("img")
        @Expose
        public String img;

        public String getImg() {
            return img;
        }
    }
}
