package com.harrikirik.rescheck.dto;

import androidx.annotation.NonNull;

/**
 * Info item containing an image
 * Harri Kirik, harri35@gmail.com
 */
public class InfoImageItem extends InfoItem {
    private static final long serialVersionUID = 9074927018674588416L;
    private int drawableId;

    public InfoImageItem(final String key, final int drawableId, final InfoCategory category) {
        super(key, null, category);
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    @Override
    public String toPrintableString() {
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " with key: " + getKey() + ", drawableId: " + getDrawableId();
    }
}
