package com.harrikirik.rescheck.dto;

/**
 * Bae InfoItem class
 * Harri Kirik, harri35@gmail.com
 */
public class InfoItem extends BaseInfoObject {
    private static final long serialVersionUID = 6766729117231253812L;

    private String key;
    private String value;

    public InfoItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
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
