package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.devtron.republicperi.data.entities.PlaceEntity;


public class PlaceRes extends BaseResponse {
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("warning")
    @Expose
    private String warning;
    @SerializedName("way")
    @Expose
    private String way;
    @SerializedName("secure")
    @Expose
    private String secure;

    public PlaceRes(PlaceEntity entity) {
        this.desc = entity.getDesc();
        this.img = entity.getImg();
        this.city = new City(entity.getCity());
        this.lat = entity.getLat();
        this.lng = entity.getLng();
        this.warning = entity.getWarning();
        this.way = entity.getWay();
        this.secure = entity.getSecure();
    }

    public String getDesc() {
        return desc;
    }

    public String getImg() {
        return img;
    }

    public City getCity() {
        return city;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getWarning() {
        return warning;
    }

    public String getWay() {
        return way;
    }

    public String getSecure() {
        return secure;
    }
}
