package ru.devtron.republicperi.data;

import retrofit2.Call;
import ru.devtron.republicperi.data.network.ServiceFactory;
import ru.devtron.republicperi.data.network.response.PlaceResponse;
import ru.devtron.republicperi.data.network.response.ServicesResponse;
import ru.devtron.republicperi.data.network.response.TourResponse;

public class CommonRepository {
    private static CommonRepository repository;
    public static CommonRepository getInstance() {
        if (repository == null) {
            repository = new CommonRepository();
        }
        return repository;
    }

    public Call<TourResponse> getNearestTours() {
        return ServiceFactory.getApiService().getTours();
    }

    public Call<PlaceResponse> getNearestPlaces() {
        return ServiceFactory.getApiService().getPlaces();
    }

    public Call<ServicesResponse> getServices() {
        return ServiceFactory.getApiService().getServices();
    }
}
