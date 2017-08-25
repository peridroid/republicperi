package ru.devtron.republicperi.data.network.response;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("id_tour")
    @Expose
    public Integer idTour;
    @SerializedName("src")
    @Expose
    public String src;


    private Uri imageUri;

    public Image(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
