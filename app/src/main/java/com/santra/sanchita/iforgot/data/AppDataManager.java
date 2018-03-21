package com.santra.sanchita.iforgot.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.santra.sanchita.iforgot.data.db.DbHelper;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.data.network.ApiHelper;
import com.santra.sanchita.iforgot.data.prefs.PreferenceHelper;
import com.santra.sanchita.iforgot.di.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by sanchita on 21/11/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private final Context context;
    private final DbHelper dbHelper;
    private final PreferenceHelper preferenceHelper;
    private final ApiHelper apiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, DbHelper dbHelper, PreferenceHelper preferenceHelper, ApiHelper apiHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.preferenceHelper = preferenceHelper;
        this.apiHelper = apiHelper;
    }

    @Override
    public String getAuthCode() {
        return preferenceHelper.getAuthCode();
    }

    @Override
    public void setAuthCode(String authCode) {
        preferenceHelper.setAuthCode(authCode);
    }

    @Override
    public Retrofit getRetrofit() {
        return apiHelper.getRetrofit();
    }

    @Override
    public Observable<Long> insertSafeItem(SafeItem designItem) {
        return dbHelper.insertSafeItem(designItem);
    }

    @Override
    public Observable<Boolean> insertSafeItems(List<SafeItem> designItems) {
        return dbHelper.insertSafeItems(designItems);
    }

    @Override
    public Observable<List<SafeItem>> getAllSafeItems() {
        return dbHelper.getAllSafeItems();
    }

    @Override
    public Observable<Boolean> isSafeItemEmpty() {
        return dbHelper.isSafeItemEmpty();
    }

    @Override
    public Observable<SafeItem> getSafeItemById(Long id) {
        return dbHelper.getSafeItemById(id);
    }

    @Override
    public Observable<Long> getSafeItemsCount() {
        return dbHelper.getSafeItemsCount();
    }

    @Override
    public Observable<List<SafeItem>> getLastSafeItems(Integer noOfRows) {
        return dbHelper.getLastSafeItems(noOfRows);
    }
}
