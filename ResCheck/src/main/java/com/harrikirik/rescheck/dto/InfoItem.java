package com.harrikirik.rescheck.dto;

/**
 * Bae InfoItem class
 * Harri Kirik, harri35@gmail.com
 */
public class InfoItem extends BaseInfoObject implements CategorisedInfoItem {
    private static final long serialVersionUID = 6766729117231253812L;

    private String key;
    private String value;
    private InfoCategory category;
    private String description;

    public InfoItem(final String key, final String value, final InfoCategory category) {
        this.key = key;
        this.value = value;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toPrintableString() {
        return getKey() + ": " + getValue();
    }

    @Override
    public String toString() {
        return super.toString() + " with key: " + getKey() + ", value: " + getValue();
    }
}
