package com.santra.sanchita.iforgot.ui.preview;

import com.santra.sanchita.iforgot.R;
import com.santra.sanchita.iforgot.data.DataManager;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.BasePresenter;
import com.santra.sanchita.iforgot.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 21/3/18.
 */

public class PreviewPresenter<V extends PreviewMvpView> extends BasePresenter<V> implements PreviewMvpPresenter<V> {

    @Inject
    public PreviewPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void addToDb(SafeItem safeItem) {
        getCompositeDisposable().add(getDataManager()
                .insertSafeItem(safeItem)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(rowId -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().nextActivity(rowId);
                }, throwable -> {
                    if(!isViewAttached()) {
                        return;
                    }
                    getMvpView().onError(R.string.default_error);
                    getMvpView().nextActivity(-1L);
                }));
    }
}
