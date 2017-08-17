package ru.devtron.republicperi.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;

interface ApiService {
	@GET("tours")
	Call<List<TourRes>> getTours();
	@GET("category/services")
	Call<List<ServiceRes>> getServices();
	@GET("places")
	Call<List<PlaceRes>> getPlaces();


}
