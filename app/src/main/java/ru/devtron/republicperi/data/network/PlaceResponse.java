package ru.devtron.republicperi.data.network;

public class PlaceResponse extends BaseResponse{
    private String place;
    private String img;

    public PlaceResponse(Integer id, String name, String place, String img) {
        super(id, name);
        this.place = place;
        this.img = img;
    }

    public String getPlace() {
        return place;
    }

    public String getImg() {
        return img;
    }
}
