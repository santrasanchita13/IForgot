package com.santra.sanchita.iforgot.ui.view_image;

import com.santra.sanchita.iforgot.data.DataManager;
import com.santra.sanchita.iforgot.ui.base.BasePresenter;
import com.santra.sanchita.iforgot.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 25/3/18.
 */

public class ViewImagePresenter<V extends ViewImageMvpView> extends BasePresenter<V> implements ViewImageMvpPresenter<V> {

    @Inject
    public ViewImagePresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
