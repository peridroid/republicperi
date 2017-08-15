package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceResponse {
    List<PlaceRes> places;

    public List<PlaceRes> getPlaces() {
        return places;
    }

    public class PlaceRes extends BaseResponse {
        @SerializedName("desc")
        @Expose
        public String desc;
        @SerializedName("img")
        @Expose
        public String img;
        @SerializedName("city")
        @Expose
        public TourResponse.TourRes.City city;
        @SerializedName("lat")
        @Expose
        public Double lat;
        @SerializedName("lng")
        @Expose
        public Double lng;
        @SerializedName("warning")
        @Expose
        public String warning;
        @SerializedName("way")
        @Expose
        public String way;
        @SerializedName("secure")
        @Expose
        public String secure;

        public String getDesc() {
            return desc;
        }

        public String getImg() {
            return img;
        }

        public TourResponse.TourRes.City getCity() {
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
}
