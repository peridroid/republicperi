package ru.devtron.republicperi.ui.screen.main.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.network.response.BaseResponse;
import ru.devtron.republicperi.data.network.response.PlaceRes;
import ru.devtron.republicperi.data.network.response.TourRes;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private List<? extends BaseResponse> itemList;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(BaseResponse position, ImageView pictureImageView);
    }

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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.titleTextView.setText(itemList.get(position).getTitle());
        //указываем transition name
        ViewCompat.setTransitionName(holder.pictureImageView, itemList.get(position).getTitle() + position);
        if (itemList.get(position) instanceof PlaceRes) {
            PlaceRes item = (PlaceRes) itemList.get(position);
            if (!TextUtils.isEmpty(item.getImg())) {
                CommonRepository.getInstance().getPicasso()
                        .load(item.getImg())
                        .resizeDimen(R.dimen.picture_width, R.dimen.picture_height)
                        .into(holder.pictureImageView);
            } else {
                holder.pictureImageView.setImageResource(R.drawable.ui_appbar_light);
            }

            holder.cityTextView.setText(item.getCity().name);
        } else if (itemList.get(position) instanceof TourRes) {
            TourRes item = (TourRes) itemList.get(position);
            if (item.getImages() != null && item.getImages().size() > 0) {
                CommonRepository.getInstance().getPicasso()
                        .load(item.getImages().get(0).src)
                        .resizeDimen(R.dimen.picture_width, R.dimen.picture_height)
                        .into(holder.pictureImageView);
            } else {
                holder.pictureImageView.setImageResource(R.drawable.ui_appbar_light);
            }
            holder.cityTextView.setText(item.getDots().get(0).city.name);
        }

        if (mOnItemClickListener != null)
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(itemList.get(holder.getAdapterPosition()), holder.pictureImageView);
                }
            });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView titleTextView;
        ImageView pictureImageView;
        TextView cityTextView;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            titleTextView = view.findViewById(R.id.name_tv);
            pictureImageView = view.findViewById(R.id.picture_iv);
            cityTextView = view.findViewById(R.id.city_tv);
        }
    }
}
