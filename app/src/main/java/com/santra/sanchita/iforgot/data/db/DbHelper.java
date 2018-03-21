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

    Observable<Long> getSafeItemsCount();

}