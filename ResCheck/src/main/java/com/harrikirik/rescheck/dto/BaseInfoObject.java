package com.harrikirik.rescheck.dto;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Base object
 * Harri Kirik, harri35@gmail.com
 */
public abstract class BaseInfoObject implements Serializable {
    private static final long serialVersionUID = -5538484609028649299L;

    abstract public String toPrintableString();

    @NonNull
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
