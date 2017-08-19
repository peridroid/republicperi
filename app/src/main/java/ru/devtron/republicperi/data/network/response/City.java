package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.devtron.republicperi.data.entities.CityEntity;

public class City {
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lng")
    @Expose
    public Double lng;
    @SerializedName("name")
    @Expose
    public String name;

    public City (CityEntity entity) {
        id = entity.getRemoteId();
        lat = entity.getLat();
        lng = entity.getLng();
        name = entity.getName();
    }
}
