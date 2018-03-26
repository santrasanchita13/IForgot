package com.santra.sanchita.iforgot.ui.view_image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.BaseActivity;
import com.santra.sanchita.iforgot.utils.Constants;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 25/3/18.
 */

public class ViewImageActivity extends BaseActivity implements ViewImageMvpView {

    @Inject
    ViewImageMvpPresenter<ViewImageMvpView> presenter;

    @BindView(R.id.zoomageView)
    ZoomageView zoomageView;

    @BindView(R.id.textMarkAsFound)
    TextView textMarkAsFound;

    @BindView(R.id.buttonMarkAsFound)
    Button buttonMarkAsFound;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ViewImageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

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

        Long id = getIntent().getLongExtra(Constants.FILE_ID, -1L);

        if(id != -1L) {
            presenter.getImage(id);
        }
    }

    @Override
    public void imageFetched(SafeItem safeItem) {
        if(safeItem != null) {
            if(safeItem.getImagePath() != null) {
                String filePath = safeItem.getImagePath();

                if(filePath != null) {

                    File imgFile = new File(filePath);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        zoomageView.setImageBitmap(myBitmap);

                        if(safeItem.getIsFound()) {
                            textMarkAsFound.setVisibility(View.GONE);
                            buttonMarkAsFound.setVisibility(View.GONE);
                        }
                        else {
                            textMarkAsFound.setVisibility(View.VISIBLE);
                            buttonMarkAsFound.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

    @OnClick({R.id.textMarkAsFound, R.id.buttonMarkAsFound})
    void onMarkAsFoundClick() {
        Long id = getIntent().getLongExtra(Constants.FILE_ID, -1L);

        if(id != -1L) {
            presenter.markAsFound(id);
        }
    }

    @Override
    public void imageMarkedAsFound() {
        textMarkAsFound.setVisibility(View.GONE);
        buttonMarkAsFound.setVisibility(View.GONE);
    }
}
