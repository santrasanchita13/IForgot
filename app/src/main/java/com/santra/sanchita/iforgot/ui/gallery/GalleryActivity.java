package com.santra.sanchita.iforgot.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 22/3/18.
 */

public class GalleryActivity extends BaseActivity implements GalleryMvpView {

    @Inject
    GalleryMvpPresenter<GalleryMvpView> presenter;

    GalleryAdapter galleryAdapter;

    @BindView(R.id.coordinator_layout_gallery)
    ViewGroup container;

    @BindView(R.id.galleryRecyclerView)
    RecyclerView galleryRecyclerView;

    @BindView(R.id.tabFoundText)
    TextView tabFoundText;

    @BindView(R.id.tabSafeText)
    TextView tabSafeText;

    List<GalleryItem> galleryItemList;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        if(savedInstanceState != null) {
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideActionBar();

        setUp();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();

        super.onDestroy();
    }

    @Override
    protected void setUp() {

        galleryItemList = new ArrayList<>();

        presenter.getAllDates();
    }

    @Override
    public void allDates(List<String> dates) {
        if(dates != null && dates.size() > 0) {

            for (String date : dates) {
                if (date != null) {
                    presenter.getImagesByDate(date);
                }
            }
        }
    }

    @Override
    public void allFound(List<String> dates) {
        if(dates != null && dates.size() > 0) {

            for (String date : dates) {
                if (date != null) {
                    presenter.getFoundImagesByDate(date);
                }
            }
        }
    }

    @Override
    public void imagesByDate(String date, List<SafeItem> safeItems) {
        if(safeItems != null && safeItems.size() > 0) {

            galleryItemList.add(new GalleryItem(date, safeItems));

            if(galleryAdapter == null) {
                galleryAdapter = new GalleryAdapter(GalleryActivity.this, galleryItemList, container);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(GalleryActivity.this);
                galleryRecyclerView.setLayoutManager(mLayoutManager);
                galleryRecyclerView.setAdapter(galleryAdapter);
                galleryAdapter.notifyDataSetChanged();
            }
            else {
                galleryAdapter.notifyDataSetChanged();
            }
        }
        else {
            galleryAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tabSafeText)
    void tabSafeClick() {
        tabSafeText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_left_pressed));
        tabFoundText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_right));

        galleryItemList.clear();

        presenter.getAllDates();
    }

    @OnClick(R.id.tabFoundText)
    void tabFoundClick() {
        tabSafeText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_left));
        tabFoundText.setBackground(ContextCompat.getDrawable(GalleryActivity.this, R.drawable.round_rectangle_right_pressed));

        galleryItemList.clear();

        presenter.getAllFoundItems();
    }
}
