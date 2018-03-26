package com.santra.sanchita.iforgot.data.db;

import android.database.Cursor;

import com.santra.sanchita.iforgot.data.db.model.DaoMaster;
import com.santra.sanchita.iforgot.data.db.model.DaoSession;
import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.data.db.model.SafeItemDao;

import java.util.ArrayList;
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
    public Observable<Boolean> markItemFoundById(Long id) {
        return Observable.fromCallable(() -> {
            SafeItem safeItem = daoSession.getSafeItemDao().load(id);
            safeItem.setIsFound(true);
            daoSession.getSafeItemDao().update(safeItem);
            return true;
        });
    }

    @Override
    public Observable<List<SafeItem>> getLastSafeItems(Integer noOfRows, Integer offset) {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao().queryBuilder()
                .orderDesc(SafeItemDao.Properties.Id).limit(noOfRows).offset(offset).list());
    }

    @Override
    public Observable<List<SafeItem>> getSafeItemsByDate(String date) {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao()
                .queryBuilder().where(SafeItemDao.Properties.SavedDate.eq(date),
                        SafeItemDao.Properties.IsFound.eq(false)).orderDesc(SafeItemDao.Properties.Id).list());
    }

    @Override
    public Observable<List<SafeItem>> getFoundItemsByDate(String date) {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao()
                .queryBuilder().where(SafeItemDao.Properties.SavedDate.eq(date),
                        SafeItemDao.Properties.IsFound.eq(true)).orderDesc(SafeItemDao.Properties.Id).list());
    }

    @Override
    public Observable<List<SafeItem>> getSafeItemsByDateAndSearch(String date, String search) {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao()
                .queryBuilder().where(SafeItemDao.Properties.SavedDate.eq(date),
                        SafeItemDao.Properties.IsFound.eq(false),
                        daoSession.getSafeItemDao()
                                .queryBuilder().or(SafeItemDao.Properties.SafeItemName.like("%" + search + "%"),
                                SafeItemDao.Properties.Description.like("%" + search + "%")))
                .orderDesc(SafeItemDao.Properties.Id).list());
    }

    @Override
    public Observable<List<SafeItem>> getFoundItemsByDateAndSearch(String date, String search) {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao()
                .queryBuilder().where(SafeItemDao.Properties.SavedDate.eq(date),
                        SafeItemDao.Properties.IsFound.eq(true),
                        daoSession.getSafeItemDao()
                                .queryBuilder().or(SafeItemDao.Properties.SafeItemName.like("%" + search + "%"),
                                SafeItemDao.Properties.Description.like("%" + search + "%")))
                .orderDesc(SafeItemDao.Properties.Id).list());
    }

    @Override
    public Observable<List<String>> getAllDates() {
        return Observable.fromCallable(() -> {
            List<String> allDates = new ArrayList<>();
            Cursor mCursor = daoSession.getDatabase()
                    .rawQuery("SELECT DISTINCT " + SafeItemDao.Properties.SavedDate.columnName + " FROM " + SafeItemDao.TABLENAME, new String[0]);
            for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
                // The Cursor is now set to the right position
                allDates.add(mCursor.getString(0));
            }
            return allDates;
        });
    }

    @Override
    public Observable<Long> getSafeItemsCount() {
        return Observable.fromCallable(() -> daoSession.getSafeItemDao().count());
    }
}
