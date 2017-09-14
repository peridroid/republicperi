package ru.devtron.republicperi.ui.screen.main;


import java.util.List;

import retrofit2.Call;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.KeyValueStorage;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;
import ru.devtron.republicperi.ui.base.BaseModel;

public class MainModel extends BaseModel {

    Call<List<TourRes>> getNearestToursNetwork() {
        return CommonRepository.getInstance().getNearestToursNetwork();
    }

    Call<List<PlaceRes>> getNearestPlaceNetwork() {
        return CommonRepository.getInstance().getNearestPlacesNetwork();
    }

    List<PlaceRes> getNearestPlaceFromDb(){
        return CommonRepository.getInstance().getNearestPlacesDb();
    }

    Call<List<ServiceRes>> getServicesNetwork() {
        return CommonRepository.getInstance().getServicesNetwork();
    }

    void saveLastPlacesUpdate(String tag){
        KeyValueStorage.getInstance().saveLastPlacesUpdate(tag);
    }

}
