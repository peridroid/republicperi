package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.SerializedName;

public class ProfileRes {
    private Integer id;
    private String name;
    @SerializedName("sourname")
    private String surname;
    private String email;
    private String phone;
    private String img;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getImg() {
        return img;
    }
}
