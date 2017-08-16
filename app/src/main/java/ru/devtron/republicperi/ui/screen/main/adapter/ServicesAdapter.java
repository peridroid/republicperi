package ru.devtron.republicperi.ui.screen.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.network.response.ServiceRes;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    private List<ServiceRes> itemList;

    public ServicesAdapter(List<ServiceRes> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ServiceRes item = itemList.get(position);
        holder.titleTextView.setText(item.getTitle());
        if (!TextUtils.isEmpty(item.getImg())) {
            Picasso.with(holder.pictureImageView.getContext())
                    .load(item.getImg())
                    .resizeDimen(R.dimen.picture_width, R.dimen.picture_height)
                    .into(holder.pictureImageView);
        }
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
