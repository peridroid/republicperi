package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TourRes extends BaseResponse {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("time_end")
    @Expose
    private String timeEnd;
    @SerializedName("dots")
    @Expose
    private List<Dot> dots;
    @SerializedName("title_desc")
    @Expose
    private String titleDesc;
    @SerializedName("type_rests")
    @Expose
    private List<TypeRest> typeRests;
    @SerializedName("images")
    @Expose
    private List<Image> images;

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public List<Dot> getDots() {
        return dots;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public List<TypeRest> getTypeRests() {
        return typeRests;
    }

    public List<Image> getImages() {
        return images;
    }

    public class TypeRest {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String title;
        @SerializedName("ordering")
        @Expose
        public Integer ordering;

    }

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

    }

    public class Dot {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("lat")
        @Expose
        public Double lat;
        @SerializedName("lng")
        @Expose
        public Double lng;
        @SerializedName("city")
        @Expose
        public City city;

    }

    public class City {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("lat")
        @Expose
        public Double lat;
        @SerializedName("lng")
        @Expose
        public Double lng;
        @SerializedName("name")
        @Expose
        public String name;

    }
}