package com.santra.sanchita.iforgot.ui.view_image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by sanchita on 25/3/18.
 */

public class ViewImageActivity extends BaseActivity implements ViewImageMvpView {

    @Inject
    ViewImageMvpPresenter<ViewImageMvpView> presenter;

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

    }
}
