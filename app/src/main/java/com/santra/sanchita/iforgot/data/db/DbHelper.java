package com.santra.sanchita.iforgot.data.db;

import com.santra.sanchita.iforgot.data.db.model.SafeItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by sanchita on 20/11/17.
 */

public interface DbHelper {

    Observable<Long> insertSafeItem(final SafeItem safeItem);

    Observable<Boolean> insertSafeItems(final List<SafeItem> safeItems);

    Observable<List<SafeItem>> getAllSafeItems();

    Observable<Boolean> isSafeItemEmpty();

    Observable<SafeItem> getSafeItemById(Long id);

    Observable<List<SafeItem>> getLastSafeItems(Integer noOfRows, Integer offset);

    Observable<List<SafeItem>> getSafeItemsByDate(String date);

    Observable<List<SafeItem>> getFoundItemsByDate(String date);

    Observable<List<SafeItem>> getSafeItemsByDateAndSearch(String date, String search);

    Observable<List<SafeItem>> getFoundItemsByDateAndSearch(String date, String search);

    Observable<List<String>> getAllDates();

    Observable<Long> getSafeItemsCount();

}
