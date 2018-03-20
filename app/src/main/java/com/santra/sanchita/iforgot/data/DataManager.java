package com.santra.sanchita.iforgot.data;

import com.santra.sanchita.iforgot.data.db.DbHelper;
import com.santra.sanchita.iforgot.data.network.ApiHelper;
import com.santra.sanchita.iforgot.data.prefs.PreferenceHelper;

import io.reactivex.Observable;

/**
 * Created by sanchita on 20/11/17.
 */

public interface DataManager extends DbHelper, ApiHelper, PreferenceHelper {

    Observable<Boolean> seedSafeItems();
}
