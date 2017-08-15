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
import ru.devtron.republicperi.data.network.response.BaseResponse;
import ru.devtron.republicperi.data.network.response.PlaceResponse;
import ru.devtron.republicperi.data.network.response.TourResponse;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private List<? extends BaseResponse> itemList;

    public ItemAdapter(List<? extends BaseResponse> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.titleTextView.setText(itemList.get(position).getTitle());
        if (itemList.get(position) instanceof PlaceResponse.PlaceRes) {
            PlaceResponse.PlaceRes item = (PlaceResponse.PlaceRes) itemList.get(position);
            if (!TextUtils.isEmpty(item.getImg())) {
                Picasso.with(holder.pictureImageView.getContext())
                        .load(item.getImg())
                        .resizeDimen(R.dimen.picture_width, R.dimen.picture_height)
                        .into(holder.pictureImageView);
            } else {
                holder.pictureImageView.setImageResource(R.drawable.ui_appbar_light);
            }

            holder.cityTextView.setText(item.getCity().name);
        } else if (itemList.get(position) instanceof TourResponse.TourRes) {
            TourResponse.TourRes item = (TourResponse.TourRes) itemList.get(position);
            if (item.getImages() != null && item.getImages().size() > 0) {
                Picasso.with(holder.pictureImageView.getContext())
                        .load(item.getImages().get(0).src)
                        .resizeDimen(R.dimen.picture_width, R.dimen.picture_height)
                        .into(holder.pictureImageView);
            } else {
                holder.pictureImageView.setImageResource(R.drawable.ui_appbar_light);
            }
            //holder.cityTextView.setText(item.ge);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView pictureImageView;
        TextView cityTextView;

        MyViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.name_tv);
            pictureImageView = view.findViewById(R.id.picture_iv);
            cityTextView = view.findViewById(R.id.city_tv);
        }
    }
}
