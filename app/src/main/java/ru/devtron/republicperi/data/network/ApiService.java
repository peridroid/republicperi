package ru.devtron.republicperi.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.devtron.republicperi.data.network.response.PlaceResponse;
import ru.devtron.republicperi.data.network.response.ServicesResponse;
import ru.devtron.republicperi.data.network.response.TourResponse;

public interface ApiService {
	@GET("tours")
	Call<TourResponse> getTours();
	@GET("category/services")
	Call<ServicesResponse> getServices();
	@GET("places")
	Call<PlaceResponse> getPlaces();
}
