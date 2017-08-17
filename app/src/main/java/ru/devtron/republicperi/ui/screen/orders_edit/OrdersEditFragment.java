package ru.devtron.republicperi.ui.screen.orders_edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.ui.screen.main.adapter.ItemAdapter;
import ru.devtron.republicperi.ui.screen.orders_edit.adapter.OrderAdapter;
import ru.devtron.republicperi.utils.OrderStatus;

/**
 * Created by Dalalai on 16-08-2017.
 */

public class OrdersEditFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected OrderAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_orders, container, false);

        mRecyclerView = rootView.findViewById(R.id.edit_orders_recycler);

        ArrayList<OrderAdapter.ItemParams> itemParamses = new ArrayList<>(3);
        itemParamses.add(new OrderAdapter.ItemParams("Южные красоты Дагестана", OrderStatus.COMPLETED, getContext(), ContextCompat.getColor(getContext(), R.color.redFooter)));
        itemParamses.add(new OrderAdapter.ItemParams("Южные красоты Дагестана", OrderStatus.COMPLETED, getContext(), ContextCompat.getColor(getContext(), R.color.blueFooter)));
        itemParamses.add(new OrderAdapter.ItemParams("Высокогорный тур", OrderStatus.ACTIVE, getContext(), ContextCompat.getColor(getContext(), R.color.orangeFooter)));

        mAdapter = new OrderAdapter(itemParamses);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }


}
