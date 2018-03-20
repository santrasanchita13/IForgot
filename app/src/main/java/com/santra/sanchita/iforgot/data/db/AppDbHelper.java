package com.santra.sanchita.iforgot.data.db;

import com.santra.sanchita.iforgot.data.db.model.DaoMaster;
import com.santra.sanchita.iforgot.data.db.model.DaoSession;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by sanchita on 21/11/17.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession daoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        daoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertSafeItem(final SafeItem safeItem) {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao().insert(safeItem));
    }

    @Override
    public Observable<Boolean> insertSafeItems(final List<SafeItem> safeItems) {
        return Observable.fromCallable(() -> {
            daoSession.getSafeItemDao().insertInTx(safeItems);
            return true;
        });
    }

    @Override
    public Observable<List<SafeItem>> getAllSafeItems() {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao().loadAll());
    }

    @Override
    public Observable<Boolean> isSafeItemEmpty() {
        return Observable.fromCallable(() -> !(daoSession.getSafeItemDao().count() > 0));
    }

    @Override
    public Observable<SafeItem> getSafeItemById(Long id) {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao().load(id));
    }

    @Override
    public Observable<Long> getSafeItemsCount() {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao().count());
    }
}
