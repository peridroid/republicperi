package ru.devtron.republicperi.ui.screen.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.network.response.BaseResponse;
import ru.devtron.republicperi.data.network.response.ServiceRes;
import ru.devtron.republicperi.ui.base.BaseActivity;
import ru.devtron.republicperi.ui.screen.main.adapter.ItemAdapter;
import ru.devtron.republicperi.ui.screen.main.adapter.ServicesAdapter;
import ru.devtron.republicperi.ui.screen.tour.TourFragment;

public class MainFragment extends Fragment implements MainView {

    public static final String TAG = "MainFragmentTag";

    List<RecyclerView> mRecyclerViewArrayList = new ArrayList<>();
    LinearLayout cardHolderLinear;
    int[] headings = new int[]{R.string.card_nearest_tours_heading, R.string.card_showplace_heading, R.string.menu_services};

    private MainPresenter mMainPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter = MainPresenter.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cardHolderLinear = view.findViewById(R.id.card_holder_ll);

        generateCardViews();

        mMainPresenter.takeView(this);
        mMainPresenter.initView();

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

    @Override
    public void initRecyclerView(int position, List<? extends BaseResponse> response) {
        RecyclerView recyclerView = mRecyclerViewArrayList.get(position);
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (position == 2) {
            ServicesAdapter adapter = new ServicesAdapter((List<ServiceRes>) response);
            recyclerView.setAdapter(adapter);
        } else {
            ItemAdapter adapter = new ItemAdapter(response);
            recyclerView.setAdapter(adapter);
            setItemClick(adapter, position);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private void setItemClick(final ItemAdapter adapter, final int pos) {
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(BaseResponse model, ImageView pictureImageView) {
                ((BaseActivity) getActivity()).setFragment(TourFragment
                        .newInstance(model, ViewCompat.getTransitionName(pictureImageView)), pictureImageView);
            }
        });
    }

    private void setOnMoreBtnClick(final Button cardMoreBtn, final int position) {
        cardMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMainPresenter.dropView();
        if (isRemoving()) {
            Log.d(TAG, "onDestroyView() called");
            mMainPresenter.destroy();
        }
    }
}
