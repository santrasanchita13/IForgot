package com.santra.sanchita.iforgot.ui.gallery;

import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.MvpView;

import java.util.List;

/**
 * Created by sanchita on 22/3/18.
 */

public interface GalleryMvpView extends MvpView {
    void allDates(List<String> dates);

    void imagesByDate(List<SafeItem> safeItems);
}
