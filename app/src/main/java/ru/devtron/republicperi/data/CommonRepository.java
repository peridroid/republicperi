package ru.devtron.republicperi.data;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import ru.devtron.republicperi.App;
import ru.devtron.republicperi.data.entities.DaoSession;
import ru.devtron.republicperi.data.entities.PlaceEntity;
import ru.devtron.republicperi.data.entities.PlaceEntityDao;
import ru.devtron.republicperi.data.network.ApiService;
import ru.devtron.republicperi.data.network.PicassoCache;
import ru.devtron.republicperi.data.network.ServiceFactory;
import ru.devtron.republicperi.data.network.requests.LoginReq;
import ru.devtron.republicperi.data.network.response.AccessToken;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ProfileRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;

public class CommonRepository {
    private static CommonRepository repository;
    private Picasso mPicasso;
    private DaoSession mDaoSession;
    private ApiService mApiService;

    private CommonRepository() {
        mPicasso = new PicassoCache(App.getContext()).getPicassoInstance();
        mDaoSession = App.getDaoSession();
        mApiService = ServiceFactory.getApiService();
    }

    public static CommonRepository getInstance() {
        if (repository == null) {
            repository = new CommonRepository();
        }
        return repository;
    }

    public Picasso getPicasso() {
        return mPicasso;
    }

    public Call<List<TourRes>> getNearestToursNetwork() {
        return mApiService.getTours();
    }

    public Call<List<PlaceRes>> getNearestPlacesNetwork() {
        return mApiService.getPlaces(KeyValueStorage.getInstance().getPlacesLastUpdate());
    }

    public List<PlaceRes> getNearestPlacesDb() {
        List<PlaceEntity> placeEntities = new ArrayList<>();
        try {
            placeEntities = App.getDaoSession().queryBuilder(PlaceEntity.class)
                    .orderDesc(PlaceEntityDao.Properties.RemoteId)
                    .build()
                    .list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        List<PlaceRes> placeResList = new ArrayList<>();
        for (PlaceEntity placeEntity : placeEntities) {
            placeResList.add(new PlaceRes(placeEntity));
        }
        return placeResList;
    }

    public Call<List<ServiceRes>> getServicesNetwork() {
        return mApiService.getServices();
    }

    public Call<AccessToken> loginUser(LoginReq loginReq) {
        return mApiService.loginUser(loginReq);
    }

    public Call<ProfileRes> getUserInfo() {
        return mApiService.getUserInfo();
    }

}
