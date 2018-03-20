package com.santra.sanchita.iforgot.di.module;

import android.app.Application;
import android.content.Context;

import com.santra.sanchita.iforgot.data.AppDataManager;
import com.santra.sanchita.iforgot.data.DataManager;
import com.santra.sanchita.iforgot.data.db.AppDbHelper;
import com.santra.sanchita.iforgot.data.db.DbHelper;
import com.santra.sanchita.iforgot.data.network.ApiHelper;
import com.santra.sanchita.iforgot.data.network.AppApiHelper;
import com.santra.sanchita.iforgot.data.prefs.AppPreferenceHelper;
import com.santra.sanchita.iforgot.data.prefs.PreferenceHelper;
import com.santra.sanchita.iforgot.di.ApplicationContext;
import com.santra.sanchita.iforgot.di.DatabaseInfo;
import com.santra.sanchita.iforgot.di.PreferenceInfo;
import com.santra.sanchita.iforgot.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sanchita on 21/11/17.
 */

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application app) {
        this.application = app;
    }

    @Provides
    @ApplicationContext
    public Context provideContext() {
        return application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return Constants.SHARED_PREF;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return Constants.DB_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(AppPreferenceHelper appPreferenceHelper) {
        return appPreferenceHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }
}
