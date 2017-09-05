package ru.devtron.republicperi.data.network.requests;

public class SignUpReq {
    private String email;
    private String name;
    private String sourname;
    private String phone;
    private String password;

    public SignUpReq(String email, String name, String sourname, String phone, String password) {
        this.email = email;
        this.name = name;
        this.sourname = sourname;
        this.phone = phone;
        this.password = password;
    }
}
