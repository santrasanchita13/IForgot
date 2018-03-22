package com.santra.sanchita.iforgot.ui.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;

import java.util.List;

/**
 * Created by sanchita on 22/3/18.
 */

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.ViewHolder> {

    private List<SafeItem> listSafeItems;
    private Context context;
    private ViewGroup container;

    private ImageViewHolder imageViewHolder;

    public GalleryItemAdapter(Context context, List<SafeItem> listSafeItems, ViewGroup container) {
        this.listSafeItems = listSafeItems;
        this.context = context;
        this.container = container;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    public class ImageViewHolder extends ViewHolder {

        ImageView imageBgItemProfileIntroduction;

        public ImageViewHolder(View view) {
            super(view);
            imageBgItemProfileIntroduction = view.findViewById(R.id.imageGalleryItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_gallery_item, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        imageViewHolder = (ImageViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return listSafeItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}