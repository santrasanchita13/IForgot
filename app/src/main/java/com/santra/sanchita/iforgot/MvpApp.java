package com.santra.sanchita.iforgot;

import android.support.multidex.MultiDexApplication;

import com.santra.sanchita.iforgot.data.DataManager;
import com.santra.sanchita.iforgot.di.component.ApplicationComponent;
import com.santra.sanchita.iforgot.di.component.DaggerApplicationComponent;
import com.santra.sanchita.iforgot.di.module.ApplicationModule;
import com.santra.sanchita.iforgot.utils.AppLogger;

import javax.inject.Inject;

/**
 * Created by sanchita on 20/11/17.
 */

public class MvpApp extends MultiDexApplication {

    @Inject
    DataManager dataManager;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        applicationComponent.inject(this);

        AppLogger.init();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent component) {
        applicationComponent = component;
    }
}
