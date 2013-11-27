package com.harrikirik.rescheck.dto;

import android.text.TextUtils;

/**
 * Category of an InfoItem
 * Harri Kirik, harri35@gmail.com
 */
public class InfoCategory extends BaseInfoObject {
    private static final long serialVersionUID = -9173790106163356722L;
    private String name;

    public InfoCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toPrintableString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof InfoCategory) {
            return TextUtils.equals(getName(), ((InfoCategory) o).getName());
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString() + " with name: " + getName();
    }
}
