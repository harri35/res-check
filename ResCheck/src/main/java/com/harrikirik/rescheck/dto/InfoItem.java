package com.harrikirik.rescheck.dto;

import android.text.TextUtils;

import androidx.annotation.NonNull;

/**
 * Bae InfoItem class
 * Harri Kirik, harri35@gmail.com
 */
public class InfoItem extends BaseInfoObject implements CategorisedInfoItem {
    private static final int VERSION_CODE_UNKNOWN = -1;

    private static final long serialVersionUID = 6766729117231253812L;

    private String key;
    private String value;
    private InfoCategory category;
    private String description;
    private int versionCode;

    public InfoItem(final String key, final String value, final InfoCategory category) {
        this.key = key;
        this.value = value;
        this.category = category;
        this.versionCode = VERSION_CODE_UNKNOWN;
        this.description = null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public InfoCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(InfoCategory category) {
        this.category = category;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unused")
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    @SuppressWarnings("unused")
    public int getVersionCode() {
        return versionCode;
    }

    @SuppressWarnings("unused")
    public boolean hasDetails() {
        return versionCode > 0 && !TextUtils.isEmpty(description);
    }

    @Override
    public String toPrintableString() {
        return getKey() + ": " + getValue();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " with key: " + getKey() + ", value: " + getValue();
    }
}
