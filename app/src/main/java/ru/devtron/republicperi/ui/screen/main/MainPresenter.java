package ru.devtron.republicperi.ui.screen.main;


import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.data.network.response.TourRes;
import ru.devtron.republicperi.ui.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainView> {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private static MainPresenter sMainPresenter = null;
    private MainModel mMainModel;

    private List<TourRes> mTourRes;
    private List<PlaceRes> mPlaceRes;
    private List<ServiceRes> mServiceRes;

    private MainPresenter() {
    }

    public static MainPresenter getInstance() {
        if (sMainPresenter == null) {
            sMainPresenter = new MainPresenter();
            Log.d(TAG, "getInstance() called");
        }
        return sMainPresenter;
    }

    @Override
    public void initView() {
        mMainModel = new MainModel();
        requestItemsFromNetwork();
    }

    private void requestItemsFromNetwork() {

        if (mTourRes == null || mTourRes.isEmpty())
            //first cardview
            mMainModel.getNearestToursNetwork().enqueue(new Callback<List<TourRes>>() {
                @Override
                public void onResponse(Call<List<TourRes>> call, Response<List<TourRes>> response) {
                    if (response.isSuccessful()) {
                        mTourRes = response.body();
                        getView().initRecyclerView(0, mTourRes);
                    }
                }

                @Override
                public void onFailure(Call<List<TourRes>> call, Throwable t) {
                }
            });
        else getView().initRecyclerView(0, mTourRes);

        if (mPlaceRes == null || mPlaceRes.isEmpty())
            //second cardview
            mMainModel.getNearestPlaceNetwork().enqueue(new Callback<List<PlaceRes>>() {
                @Override
                public void onResponse(Call<List<PlaceRes>> call, Response<List<PlaceRes>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 304) {
                            mPlaceRes = mMainModel.getNearestPlaceFromDb();
                        } else {
                            mPlaceRes = response.body();
                            mMainModel.saveLastPlacesUpdate(response.headers().get("ETag"));
                        }
                        getView().initRecyclerView(1, mPlaceRes);
                    }
                }

                @Override
                public void onFailure(Call<List<PlaceRes>> call, Throwable t) {

                }
            });
        else getView().initRecyclerView(1, mPlaceRes);

        if (mServiceRes == null || mServiceRes.isEmpty())
            //third cardview
            mMainModel.getServicesNetwork().enqueue(new Callback<List<ServiceRes>>() {
                @Override
                public void onResponse(Call<List<ServiceRes>> call, Response<List<ServiceRes>> response) {
                    if (response.isSuccessful()) {
                        if (mServiceRes == null) mServiceRes = response.body();
                        getView().initRecyclerView(2, mServiceRes);
                    }
                }

                @Override
                public void onFailure(Call<List<ServiceRes>> call, Throwable t) {

                }
            });
        else getView().initRecyclerView(2,mServiceRes);
    }

    public void destroy() {
        sMainPresenter = null;
    }
}
