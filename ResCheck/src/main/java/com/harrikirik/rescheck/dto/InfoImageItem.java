package com.harrikirik.rescheck.dto;

/**
 * Info item containing an image
 * Harri Kirik, harri35@gmail.com
 */
public class InfoImageItem extends BaseInfoObject{
    private static final long serialVersionUID = 9074927018674588416L;
    private String key;
    private int drawableId;

    public InfoImageItem(final String key, final int drawableId) {
        this.key = key;
        this.drawableId = drawableId;
    }

    public String getKey() {
        return key;
    }

    public int getDrawableId() {
        return drawableId;
    }

    @Override
    public String toPrintableString() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " with key: " + getKey() + ", drawableId: " + getDrawableId();
    }
}
