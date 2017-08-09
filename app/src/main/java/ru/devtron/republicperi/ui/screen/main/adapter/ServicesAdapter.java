package ru.devtron.republicperi.ui.screen.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.network.BaseResponse;
import ru.devtron.republicperi.data.network.ServicesResponse;

import static android.content.ContentValues.TAG;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    private List<ServicesResponse> itemList;

    public ServicesAdapter(List<? extends BaseResponse> itemList) {
        this.itemList = (List<ServicesResponse>) itemList;
        Log.d(TAG, "ServicesAdapter: " + this.itemList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ServicesResponse item = itemList.get(position);
        holder.titleTextView.setText(item.getTitle());
        //holder.pictureImageView.setImageResource(item.getImg());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView pictureImageView;

        MyViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.name_tv);
            pictureImageView = v.findViewById(R.id.picture_iv);
        }
    }
}
