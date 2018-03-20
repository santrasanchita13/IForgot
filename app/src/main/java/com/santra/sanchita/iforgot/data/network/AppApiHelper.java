package com.santra.sanchita.iforgot.data.network;

import android.content.Context;

import com.santra.sanchita.iforgot.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * Created by sanchita on 21/11/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    Context context;

    @Inject
    public AppApiHelper(@ApplicationContext Context mContext) {
        context = mContext;
    }

    @Override
    public Retrofit getRetrofit() {
        return RetrofitInstance.getInstance(context);
    }
}
