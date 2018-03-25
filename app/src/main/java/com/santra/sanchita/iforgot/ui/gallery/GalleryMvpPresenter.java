package com.santra.sanchita.iforgot.ui.gallery;

import com.santra.sanchita.iforgot.ui.base.MvpPresenter;

/**
 * Created by sanchita on 22/3/18.
 */

public interface GalleryMvpPresenter<V extends GalleryMvpView> extends MvpPresenter<V> {
    void getAllDates();

    void getAllFoundItems();

    void getImagesByDate(String date);

    void getFoundImagesByDate(String date);
}
