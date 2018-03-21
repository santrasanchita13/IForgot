package com.santra.sanchita.iforgot.ui.preview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

    @OnClick(R.id.buttonPreviewSave)
    public void savePic() {

        Long fileId = getIntent().getLongExtra(Constants.FILE_ID, -1);

        if(fileId != -1) {

            if(!previewNameEditText.getText().toString().isEmpty()) {
                SafeItem safeItem = new SafeItem(fileId, previewNameEditText.getText().toString(),
                        imgFile.getAbsolutePath(),
                        previewCommentEditText.getText().toString(),
                        fileId + "",
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

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                previewImageView.setImageBitmap(myBitmap);

            }
        }
    }

    @Override
    public void onBackPressed() {
        if(imgFile != null && insertedRow == -1) {
            imgFile.delete();
        }
        super.onBackPressed();
    }
}
