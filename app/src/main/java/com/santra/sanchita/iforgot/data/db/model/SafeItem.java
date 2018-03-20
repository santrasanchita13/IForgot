package com.santra.sanchita.iforgot.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sanchita on 9/12/17.
 */

@Entity(nameInDb = "safe_item")
public class SafeItem {

    @Expose
    @SerializedName("id")
    @Id
    private Long id;

    @Expose
    @SerializedName("safe_item_name")
    @Property(nameInDb = "safe_item_name")
    private String safeItemName;

    @Expose
    @SerializedName("image_path")
    @Property(nameInDb = "image_path")
    private String imagePath;

    @Expose
    @SerializedName("description")
    @Property(nameInDb = "description")
    private String description;

    @Expose
    @SerializedName("saved_date")
    @Property(nameInDb = "saved_date")
    private String savedDate;

    @Expose
    @SerializedName("is_found")
    @Property(nameInDb = "is_found")
    private boolean isFound;

    @Generated(hash = 1492873418)
    public SafeItem(Long id, String safeItemName, String imagePath,
            String description, String savedDate, boolean isFound) {
        this.id = id;
        this.safeItemName = safeItemName;
        this.imagePath = imagePath;
        this.description = description;
        this.savedDate = savedDate;
        this.isFound = isFound;
    }

    @Generated(hash = 753745967)
    public SafeItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSafeItemName() {
        return this.safeItemName;
    }

    public void setSafeItemName(String safeItemName) {
        this.safeItemName = safeItemName;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSavedDate() {
        return this.savedDate;
    }

    public void setSavedDate(String savedDate) {
        this.savedDate = savedDate;
    }

    public boolean getIsFound() {
        return this.isFound;
    }

    public void setIsFound(boolean isFound) {
        this.isFound = isFound;
    }
}
