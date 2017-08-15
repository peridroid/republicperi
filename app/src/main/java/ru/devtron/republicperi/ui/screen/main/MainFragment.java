package ru.devtron.republicperi.ui.screen.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.network.response.BaseResponse;
import ru.devtron.republicperi.data.network.response.PlaceResponse;
import ru.devtron.republicperi.data.network.response.ServicesResponse;
import ru.devtron.republicperi.data.network.response.TourResponse;
import ru.devtron.republicperi.ui.screen.main.adapter.ItemAdapter;
import ru.devtron.republicperi.ui.screen.main.adapter.ServicesAdapter;

public class MainFragment extends Fragment {
    List<RecyclerView> mRecyclerViewArrayList = new ArrayList<>();
    LinearLayout cardHolderLinear;
    int[] headings = new int[]{R.string.card_nearest_tours_heading, R.string.card_showplace_heading, R.string.menu_services};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cardHolderLinear = view.findViewById(R.id.card_holder_ll);

        generateCardViews();
        requestItemsFromNetwork();
        return view;
    }

    private void generateCardViews() {
        for (int position = 0; position < 3; position++) {
            CardView cardItem = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.cardview, cardHolderLinear, false);
            TextView cardHeading = cardItem.findViewById(R.id.card_heading);
            Button cardMoreBtn = cardItem.findViewById(R.id.more_btn);
            RecyclerView placesRecyclerView = cardItem.findViewById(R.id.recycler_view);

            cardHeading.setText(headings[position]);
            setOnMoreBtnClick(cardMoreBtn, position);

            mRecyclerViewArrayList.add(placesRecyclerView);
            cardHolderLinear.addView(cardItem);
        }
    }

    private void initRecyclerView(int position, List<? extends BaseResponse> response) {
        RecyclerView recyclerView = mRecyclerViewArrayList.get(position);
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (position == 2) {
            ServicesAdapter adapter = new ServicesAdapter((List<ServicesResponse.ServiceRes>) response);
            recyclerView.setAdapter(adapter);
        } else {
            ItemAdapter adapter = new ItemAdapter(response);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setOnMoreBtnClick(Button cardMoreBtn, int position) {
        cardMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void requestItemsFromNetwork() {
        //first cardview
        Call<TourResponse> callTours = CommonRepository.getInstance().getNearestTours();
        callTours.enqueue(new Callback<TourResponse>() {
            @Override
            public void onResponse(Call<TourResponse> call, Response<TourResponse> response) {
                if (response.isSuccessful()) {
                    initRecyclerView(0, response.body().getTours());
                }
            }

            @Override
            public void onFailure(Call<TourResponse> call, Throwable t) {

            }
        });

        //second cardview
        Call<PlaceResponse> showPlacesCall = CommonRepository.getInstance().getNearestPlaces();
        showPlacesCall.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if (response.isSuccessful()) {
                    initRecyclerView(1, response.body().getPlaces());
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {

            }
        });

        //third cardview
        Call<ServicesResponse> servicesCall = CommonRepository.getInstance().getServices();
        servicesCall.enqueue(new Callback<ServicesResponse>() {
            @Override
            public void onResponse(Call<ServicesResponse> call, Response<ServicesResponse> response) {
                if (response.isSuccessful()) {
                    initRecyclerView(2, response.body().services);
                }
            }

            @Override
            public void onFailure(Call<ServicesResponse> call, Throwable t) {

            }
        });
    }
}
