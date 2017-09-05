package ru.devtron.republicperi.data.network.response;


import com.google.gson.annotations.SerializedName;

public class UserRes {
	private String id;
	private String name;
	private String email;
	private String phone;
	@SerializedName("avatar_url")
	private String avatarUrl;

	public UserRes() {
	}

	public UserRes(String name, String email, String phone, String avatarUrl) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.avatarUrl = avatarUrl;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getPhone() {
		return phone;
	}
}
