package com.santra.sanchita.iforgot.ui.main;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.DataManager;
import com.santra.sanchita.iforgot.ui.base.BasePresenter;
import com.santra.sanchita.iforgot.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 11/12/17.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void getSavedImage() {
        getCompositeDisposable().add(getDataManager()
                .getLastSafeItems(1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(lastRows -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    if(lastRows != null && lastRows.size() > 0 && lastRows.get(0) != null) {
                        getMvpView().galleryPreview(lastRows.get(0));
                    }
                    else {
                        getMvpView().galleryPreview(null);
                    }
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                    getMvpView().galleryPreview(null);
                }));
    }
}
