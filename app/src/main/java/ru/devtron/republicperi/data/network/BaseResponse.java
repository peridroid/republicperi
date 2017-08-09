package ru.devtron.republicperi.data.network;

public class BaseResponse {
    public Integer id;
    public String name;

    BaseResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseResponse() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
