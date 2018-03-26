package com.santra.sanchita.iforgot.ui.view_image;

import com.santra.sanchita.iforgot.R;
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

    @Override
    public void getImage(Long id) {
        getCompositeDisposable().add(getDataManager()
                .getSafeItemById(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(image -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().imageFetched(image);
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                    getMvpView().imageFetched(null);
                }));
    }

    @Override
    public void markAsFound(Long id) {
        getCompositeDisposable().add(getDataManager()
                .markItemFoundById(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(marked -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    if(marked) {
                        getMvpView().imageMarkedAsFound();
                    }
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                }));
    }
}
