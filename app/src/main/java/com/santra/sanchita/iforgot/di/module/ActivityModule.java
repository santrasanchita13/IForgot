package com.santra.sanchita.iforgot.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.santra.sanchita.iforgot.di.ActivityContext;
import com.santra.sanchita.iforgot.di.PerActivity;
import com.santra.sanchita.iforgot.ui.main.MainMvpPresenter;
import com.santra.sanchita.iforgot.ui.main.MainMvpView;
import com.santra.sanchita.iforgot.ui.main.MainPresenter;
import com.santra.sanchita.iforgot.utils.rx.AppSchedulerProvider;
import com.santra.sanchita.iforgot.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 6/12/17.
 */

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }
}
