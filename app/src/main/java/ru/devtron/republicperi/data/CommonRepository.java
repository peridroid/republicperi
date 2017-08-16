package ru.devtron.republicperi.data;

import java.util.List;

import retrofit2.Call;
import ru.devtron.republicperi.data.network.ServiceFactory;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;

public class CommonRepository {
    private static CommonRepository repository;
    public static CommonRepository getInstance() {
        if (repository == null) {
            repository = new CommonRepository();
        }
        return repository;
    }

    public Call<List<TourRes>> getNearestTours() {
        return ServiceFactory.getApiService().getTours();
    }

    public Call<List<PlaceRes>> getNearestPlaces() {
        return ServiceFactory.getApiService().getPlaces();
    }

    public Call<List<ServiceRes>> getServices() {
        return ServiceFactory.getApiService().getServices();
    }
}
