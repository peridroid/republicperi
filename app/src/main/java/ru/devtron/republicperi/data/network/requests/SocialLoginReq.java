package ru.devtron.republicperi.data.network.requests;

import com.google.gson.annotations.SerializedName;

public class SocialLoginReq {
	private String name;
	@SerializedName("avatar_url")
	private String avatarUrl;
	private String email;
	private String phone;

	public String getName() {
		return name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public SocialLoginReq(String name, String avatarUrl, String email, String phone) {
		this.name = name;
		this.avatarUrl = avatarUrl;
		this.email = email;
		this.phone = phone;
	}
}