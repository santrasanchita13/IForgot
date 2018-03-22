package com.santra.sanchita.iforgot.ui.gallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santra.sanchita.iforgot.R;

import java.util.List;

/**
 * Created by sanchita on 23/3/18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<GalleryItem> galleryItemList;
    private Context context;
    private ViewGroup container;

    private GalleryViewHolder galleryViewHolder;

    public GalleryAdapter(Context context, List<GalleryItem> galleryItemList, ViewGroup container) {
        this.galleryItemList = galleryItemList;
        this.context = context;
        this.container = container;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    public class GalleryViewHolder extends ViewHolder {

        TextView dateText;
        RecyclerView galleryItemRecyclerView;
        GalleryItemAdapter galleryItemAdapter;

        public GalleryViewHolder(View view) {
            super(view);
            dateText = view.findViewById(R.id.dateText);
            galleryItemRecyclerView = view.findViewById(R.id.galleryItemRecyclerView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_gallery, parent, false);
        return new GalleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        galleryViewHolder = (GalleryViewHolder) holder;

        GalleryItem galleryItem = galleryItemList.get(position);

        galleryViewHolder.dateText.setText(galleryItem.getDate());

        if(galleryViewHolder.galleryItemAdapter == null) {
            galleryViewHolder.galleryItemAdapter = new GalleryItemAdapter(context, galleryItem.getSafeItems(), container);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
            galleryViewHolder.galleryItemRecyclerView.setLayoutManager(mLayoutManager);
            galleryViewHolder.galleryItemRecyclerView.setAdapter(galleryViewHolder.galleryItemAdapter);
            galleryViewHolder.galleryItemAdapter.notifyDataSetChanged();
        }
        else {
            galleryViewHolder.galleryItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return galleryItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}
