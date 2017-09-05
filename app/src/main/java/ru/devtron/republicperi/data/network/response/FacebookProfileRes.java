package ru.devtron.republicperi.data.network.response;

import com.google.gson.annotations.SerializedName;

public class FacebookProfileRes {
	private String email;
	private FacebookPicture picture;
	@SerializedName("first_name")
	private String firstName;
	@SerializedName("last_name")
	private String lastName;
	private String id;

	private static class FacebookPicture {
		FacebookPictureData data;

		private static class FacebookPictureData {
			int height;
			@SerializedName("is_silhouette")
			boolean isSilhouette;
			String url;
			int width;
		}
	}

	public String getEmail() {
		return email;
	}

	public String getAvatar() {
		return picture.data.url;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}