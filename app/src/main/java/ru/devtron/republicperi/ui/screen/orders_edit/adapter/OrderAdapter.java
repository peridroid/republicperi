package ru.devtron.republicperi.ui.screen.orders_edit.adapter;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.utils.OrderStatus;

/**
 * Created by Dalalai on 16-08-2017.
 */


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private ArrayList<ItemParams> itemParams;
    private int itemCount;



    public OrderAdapter(ArrayList<ItemParams> params){
        this.itemParams = params;
        itemCount = params.size();
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_order, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.MyViewHolder holder, int position) {
        ItemParams item = itemParams.get(position);
        holder.titleTextView.setText(item.title);
        holder.statusTextView.setText(item.statusText);
        holder.statusTextView.setTextColor(item.footerColor);
        holder.footer.setBackgroundColor(item.footerColor);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    /*
    Class for storing order CardView paramaters like status, period etd.
     */
    public static class ItemParams{
        private Context context;

        private String title;
        private String statusText;
        private OrderStatus status;
        int footerColor;
        public ItemParams(String title, OrderStatus status, Context context, int footerColor) {
            this.title = title;
            this.status = status;
            this.context = context;
            setStatusText();

            this.footerColor = footerColor;
        }

        private void setStatusText(){
            statusText = "";
            switch (status){
                case ACTIVE:
                    statusText =  context.getResources().getString(R.string.active_tour_status);
                    break;
                case COMPLETED:
                    statusText =  context.getResources().getString(R.string.completed_tour_status);
                    break;
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView statusTextView;
        private TextView periodTextView;
        private FrameLayout footer;

        public MyViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.item_title);
            statusTextView = view.findViewById(R.id.status_text);
            periodTextView = view.findViewById(R.id.period_text);
            footer = view.findViewById(R.id.footer_layout);
        }
    }

}
