package ru.devtron.republicperi.data.network.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VkProfileRes {

	private List<Response> response = null;

	public String getFullName() {
		return response.get(0).lastName + " " + response.get(0).firstName;
	}

	public String getFirstName() {
		return response.get(0).firstName;
	}

	public String getLastName() {
		return response.get(0).lastName;
	}

	public String getAvatar() {
		return response.get(0).photo;
	}

	public String getPhone() {
		return response.get(0).mobilePhone;
	}

	private static class Response {
		@SerializedName("uid")
		int id;
		@SerializedName("first_name")
		String firstName;
		@SerializedName("last_name")
		String lastName;
		@SerializedName("photo_200")
		String photo;
		@SerializedName("mobile_phone")
		String mobilePhone;
		@SerializedName("home_phone")
		String homePhone;
	}
}