package com.harrikirik.rescheck.util;

import com.harrikirik.rescheck.BuildConfig;

/**
 * A little more convenient logging class
 * Harri Kirik, harri35@gmail.com
 */
public class Log {
    private String tag;

    private Log(final String tag) {
        this.tag = tag;
    }

    public static Log getInstance(final Object object) {
        return getInstance(object.getClass());
    }

    public static Log getInstance(final Class clazz) {
        return getInstance(clazz.getSimpleName());
    }

    public static Log getInstance(final String tag) {
        return new Log(tag);
    }

    public void d(final String msg) {
        d(msg, null);
    }

    public void d(final String msg, final Throwable e) {
        if (!BuildConfig.DEBUG) {
            // No not log this level for release
            return;
        }
        if (e == null) {
            android.util.Log.d(tag, msg);
        } else {
            android.util.Log.d(tag, msg, e);
        }
    }

    public void e(final String msg) {
        d(msg, null);
    }

    public void e(final String msg, final Throwable e) {
        if (e == null) {
            android.util.Log.e(tag, msg);
        } else {
            android.util.Log.e(tag, msg, e);
        }
    }

}
