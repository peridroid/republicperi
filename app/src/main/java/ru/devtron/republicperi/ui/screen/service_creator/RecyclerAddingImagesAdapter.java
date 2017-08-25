package ru.devtron.republicperi.ui.screen.service_creator;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.network.response.Image;
import ru.devtron.republicperi.ui.views.adapter.ItemTouchHelperAdapter;
import ru.devtron.republicperi.ui.views.adapter.ItemTouchHelperViewHolder;
import ru.devtron.republicperi.ui.views.adapter.OnRemoveItemListener;

class RecyclerAddingImagesAdapter extends
        RecyclerView.Adapter<RecyclerAddingImagesAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter, OnRemoveItemListener {

    @Override
    public void onRemoveItem(int position) {
        onItemDismiss(position);
    }

    interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
        void onItemRemoved(int size, Image item);
    }

    private final List<Image> mItems = new ArrayList<>();

    private final OnDragStartListener mDragStartListener;

    public RecyclerAddingImagesAdapter(OnDragStartListener dragStartListener) {
        mDragStartListener = dragStartListener;
    }

    public List<Image> getItems() {
        return mItems;
    }

    public void addNewItem(Image file) {
        if (mItems.size() < 6) {
            mItems.add(file);
            notifyItemInserted(mItems.size() - 1);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_image, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view, this);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.setUploadedImage(mItems.get(position));
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onDragStarted(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        if (position != 0) {
            mDragStartListener.onItemRemoved(position, mItems.get(position));
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Image prev = mItems.remove(fromPosition);
        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        //Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        @BindView(R.id.handle)
        ImageView handleView;
        @BindView(R.id.imageBg)
        ImageView uploadedImage;
        OnRemoveItemListener listener;

        ItemViewHolder(View itemView, OnRemoveItemListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        @OnClick(R.id.removePhoto)
        void removePhoto() {
            listener.onRemoveItem(getAdapterPosition());
        }

        @Override
        public void onItemSelected() {
            handleView.setColorFilter(ContextCompat.getColor(handleView.getContext(), R.color.colorAccent));
        }

        @Override
        public void onItemClear() {
            handleView.setColorFilter(0);
        }

        private void setUploadedImage(Image image) {
            if (!TextUtils.isEmpty(image.src)) {
                CommonRepository.getInstance().getPicasso()
                        .load(image.src)
                        .fit()
                        .into(uploadedImage);
            } else {
                CommonRepository.getInstance().getPicasso()
                        .load(image.getImageUri())
                        .fit()
                        .into(uploadedImage);
            }
        }
    }
}