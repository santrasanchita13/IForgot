package com.santra.sanchita.iforgot.ui.main;

import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.MvpView;

/**
 * Created by sanchita on 11/12/17.
 */

public interface MainMvpView extends MvpView {
    void galleryPreview(SafeItem safeItem);
}
