package com.santra.sanchita.iforgot.ui.preview;

import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.MvpPresenter;

/**
 * Created by sanchita on 21/3/18.
 */

public interface PreviewMvpPresenter<V extends PreviewMvpView> extends MvpPresenter<V> {
    void addToDb(SafeItem safeItem);
}
