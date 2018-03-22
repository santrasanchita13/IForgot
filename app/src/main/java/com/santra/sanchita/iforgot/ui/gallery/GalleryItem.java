package com.santra.sanchita.iforgot.ui.gallery;

import com.santra.sanchita.iforgot.data.db.model.SafeItem;

import java.util.List;

/**
 * Created by sanchita on 23/3/18.
 */

public class GalleryItem {
    private String date;
    private List<SafeItem> safeItems;

    public GalleryItem(String date, List<SafeItem> safeItems) {
        this.date = date;
        this.safeItems = safeItems;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SafeItem> getSafeItems() {
        return safeItems;
    }

    public void setSafeItems(List<SafeItem> safeItems) {
        this.safeItems = safeItems;
    }
}
