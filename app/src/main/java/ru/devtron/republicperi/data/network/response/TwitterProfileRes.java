package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.SerializedName;

public class TwitterProfileRes {
	@SerializedName("id_str")
	private String id;
	private String name;
	@SerializedName("profile_image_url")
	private String avatar;
	private String email;

	public TwitterProfileRes(String id, String name, String avatar, String email) {
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}
}