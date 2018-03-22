package com.santra.sanchita.iforgot.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by sanchita on 22/3/18.
 */

public class GalleryActivity extends BaseActivity implements GalleryMvpView {

    @Inject
    GalleryMvpPresenter<GalleryMvpView> presenter;

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
        presenter.getAllDates();
    }

    @Override
    public void allDates(List<String> dates) {
        if(dates != null) {
            for (String date : dates) {
                if (date != null) {
                    presenter.getImagesByDate(date);
                }
            }
        }
    }

    @Override
    public void imagesByDate(List<SafeItem> safeItems) {
        if(safeItems != null) {
            showMessage(safeItems.get(0).getSafeItemName());
        }
    }
}
