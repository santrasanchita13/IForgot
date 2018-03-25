package com.santra.sanchita.iforgot.ui.gallery;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.DataManager;
import com.santra.sanchita.iforgot.ui.base.BasePresenter;
import com.santra.sanchita.iforgot.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 22/3/18.
 */

public class GalleryPresenter<V extends GalleryMvpView> extends BasePresenter<V> implements GalleryMvpPresenter<V> {

    @Inject
    public GalleryPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void getAllDates() {
        getCompositeDisposable().add(getDataManager()
                .getAllDates()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(allDates -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().allDates(allDates);
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                    getMvpView().allDates(null);
                }));
    }

    @Override
    public void getAllFoundItems() {
        getCompositeDisposable().add(getDataManager()
                .getAllDates()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(allDates -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().allFound(allDates);
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                    getMvpView().allFound(null);
                }));
    }

    @Override
    public void getImagesByDate(String date) {
        getCompositeDisposable().add(getDataManager()
                .getSafeItemsByDate(date)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(images -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().imagesByDate(date, images);
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                    getMvpView().imagesByDate(date, null);
                }));
    }

    @Override
    public void getFoundImagesByDate(String date) {
        getCompositeDisposable().add(getDataManager()
                .getFoundItemsByDate(date)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(images -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().imagesByDate(date, images);
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                    getMvpView().imagesByDate(date, null);
                }));
    }
}
