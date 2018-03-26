package com.santra.sanchita.iforgot.ui.view_image;

import com.santra.sanchita.iforgot.ui.base.MvpPresenter;

/**
 * Created by sanchita on 25/3/18.
 */

public interface ViewImageMvpPresenter<V extends ViewImageMvpView> extends MvpPresenter<V> {
    void getImage(Long id);
    void markAsFound(Long id);
}
