package com.santra.sanchita.iforgot.ui.main;

import com.santra.sanchita.iforgot.ui.base.MvpPresenter;

/**
 * Created by sanchita on 11/12/17.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void getSavedImage();
}
