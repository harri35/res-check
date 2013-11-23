package com.harrikirik.rescheck.dto;

/**
 * Placeholder for list title
 * Harri Kirik, harri35@gmail.com
 */
public class InfoHeader extends BaseInfoObject {
    private static final long serialVersionUID = -7943128086149637556L;

    private String title;

    public InfoHeader(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toPrintableString() {
        return getTitle();
    }

    @Override
    public String toString() {
        return super.toString() + " with title: " + getTitle();
    }
}
