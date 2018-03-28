package com.santra.sanchita.iforgot.ui.preview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.BaseActivity;
import com.santra.sanchita.iforgot.utils.CommonUtils;
import com.santra.sanchita.iforgot.utils.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sanchita on 21/3/18.
 */

public class PreviewActivity extends BaseActivity implements PreviewMvpView {

    @Inject
    PreviewMvpPresenter<PreviewMvpView> presenter;

    @BindView(R.id.previewNameEditText)
    EditText previewNameEditText;

    @BindView(R.id.previewCommentEditText)
    EditText previewCommentEditText;

    @BindView(R.id.buttonPreviewSave)
    Button buttonPreviewSave;

    @BindView(R.id.previewImageView)
    ImageView previewImageView;

    @BindView(R.id.framePreviewAd)
    AdView framePreviewAd;

    File imgFile;

    long insertedRow = -1;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PreviewActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        MobileAds.initialize(this, getString(R.string.ad_mob_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        framePreviewAd.loadAd(adRequest);

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

        if(imgFile != null && insertedRow == -1) {
            imgFile.delete();
        }

        super.onDestroy();
    }

    @OnClick(R.id.buttonPreviewSave)
    public void savePic() {
        savePicAndReturn();
    }

    @Override
    public void savePicAndReturn() {
        Long fileId = getIntent().getLongExtra(Constants.FILE_ID, -1);

        if(fileId != -1) {

            if(!previewNameEditText.getText().toString().isEmpty()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                Date netDate = (new Date(fileId));
                String date =  sdf.format(netDate);

                SafeItem safeItem = new SafeItem(fileId, previewNameEditText.getText().toString(),
                        imgFile.getAbsolutePath(),
                        previewCommentEditText.getText().toString(),
                        date,
                        false);
                presenter.addToDb(safeItem);
            }
            else {
                previewNameEditText.setError("You must name the object in the image");
            }
        }
    }

    @Override
    public void nextActivity(long rowId) {
        if(rowId != -1) {
            insertedRow = rowId;
            onBackPressed();
        }
    }

    @Override
    protected void setUp() {
        String filePath = getIntent().getStringExtra(Constants.FILE_PATH);

        if(filePath != null) {

            imgFile = new File(filePath);

            if (imgFile.exists()) {

                CommonUtils.decodeFile(imgFile, PreviewActivity.this, previewImageView);

                /*Bitmap myBitmap = CommonUtils.decodeFile(imgFile.getAbsolutePath());

                previewImageView.setImageBitmap(myBitmap);*/

            }
        }

        previewCommentEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (event != null) {
                // if shift key is down, then we want to insert the '\n' char in the TextView;
                // otherwise, the default action is to send the message.
                if (!event.isShiftPressed()) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        savePicAndReturn();
                        return true;
                    }
                }
            }

            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                savePicAndReturn();
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if(imgFile != null && insertedRow == -1) {
            imgFile.delete();
        }
        super.onBackPressed();
    }
}
