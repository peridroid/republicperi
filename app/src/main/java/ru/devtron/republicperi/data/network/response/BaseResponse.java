package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("title")
    @Expose
    public String title;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
