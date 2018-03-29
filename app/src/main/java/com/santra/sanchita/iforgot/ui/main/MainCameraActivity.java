package com.santra.sanchita.iforgot.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.TextureView;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.BaseActivity;
import com.santra.sanchita.iforgot.ui.gallery.GalleryActivity;
import com.santra.sanchita.iforgot.utils.CommonUtils;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 29/3/18.
 */

public class MainCameraActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @BindView(R.id.buttonTakePic)
    Button takePictureButton;

    @BindView(R.id.galleryImage)
    ImageView galleryImage;

    @BindView(R.id.frameAd)
    AdView frameAd;

    CameraFragment cameraFragment;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        MobileAds.initialize(this, getString(R.string.ad_mob_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        frameAd.loadAd(adRequest);

        if (null == savedInstanceState) {
            cameraFragment = CameraFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, cameraFragment)
                    .commit();
        }

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
        takePictureButton.setOnClickListener(v -> {
            if(cameraFragment != null) {
                cameraFragment.takePicture();
            }
        });

        presenter.getSavedImage();
    }

    @OnClick(R.id.galleryImage)
    void clickGallery() {
        startActivity(GalleryActivity.getStartIntent(MainCameraActivity.this));
    }

    @Override
    public void galleryPreview(SafeItem safeItem) {
        if(safeItem != null) {
            if(safeItem.getImagePath() != null) {
                String filePath = safeItem.getImagePath();

                if(filePath != null) {

                    File imgFile = new File(filePath);

                    if (imgFile.exists()) {

                        CommonUtils.decodeFile(imgFile, MainCameraActivity.this, galleryImage);

                        /*Bitmap myBitmap = CommonUtils.decodeFile(imgFile.getAbsolutePath());

                        galleryImage.setImageBitmap(myBitmap);*/

                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
