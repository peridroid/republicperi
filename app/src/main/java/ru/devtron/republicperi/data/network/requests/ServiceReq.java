package ru.devtron.republicperi.data.network.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.devtron.republicperi.data.network.response.Image;

public class ServiceReq {
    @SerializedName("images")
    List<Image> mImagesLocal;
    @SerializedName("images")
    List<Image> mImagesNetwork;

    public List<Image> getImagesLocal() {
        return mImagesLocal;
    }

    public List<Image> getImagesNetwork() {
        return mImagesNetwork;
    }
}
