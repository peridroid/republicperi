package ru.devtron.republicperi.data.network;

import android.support.annotation.DrawableRes;

public class ServicesResponse extends BaseResponse {
    @DrawableRes
    private int img;
    private int title;

    public ServicesResponse(Integer id, int name, @DrawableRes int img) {
        this.id = id;
        this.title = name;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public int getTitle() {
        return title;
    }
}
