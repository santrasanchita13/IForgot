package com.santra.sanchita.iforgot.ui.view_image;

import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.MvpView;

/**
 * Created by sanchita on 25/3/18.
 */

public interface ViewImageMvpView extends MvpView {
    void imageFetched(SafeItem safeItem);
    void imageMarkedAsFound();
}
