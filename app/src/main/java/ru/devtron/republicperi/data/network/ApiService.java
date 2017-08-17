package ru.devtron.republicperi.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.devtron.republicperi.data.network.requests.LoginReq;
import ru.devtron.republicperi.data.network.response.AccessToken;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ProfileRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;

public interface ApiService {
	@GET("tours")
	Call<List<TourRes>> getTours();
	@GET("category/services")
	Call<List<ServiceRes>> getServices();
	@GET("places")
	Call<List<PlaceRes>> getPlaces();
	@POST("login")
	Call<AccessToken> loginUser(@Body LoginReq login);
	@GET("me")
	Call<ProfileRes> getUserInfo();
}
