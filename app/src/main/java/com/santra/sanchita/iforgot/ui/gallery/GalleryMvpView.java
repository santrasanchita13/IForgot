package com.santra.sanchita.iforgot.ui.gallery;

import android.view.MenuItem;

import com.santra.sanchita.iforgot.data.db.model.SafeItem;
import com.santra.sanchita.iforgot.ui.base.MvpView;

import java.util.List;

/**
 * Created by sanchita on 22/3/18.
 */

public interface GalleryMvpView extends MvpView {
    void allDates(List<String> dates);

    void allFound(List<String> dates);

    void imagesByDate(String date, List<SafeItem> safeItems);

    void selectDateRange(MenuItem menuItem);
}
