package com.harrikirik.rescheck.util;

import com.harrikirik.rescheck.BuildConfig;

/**
 * A little more convenient logging class
 * Harri Kirik, harri35@gmail.com
 */
@SuppressWarnings({"WeakerAccess", "unused"})
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
            //Checkstyle off: androidUtilLogs
            android.util.Log.d(tag, msg);
            //Checkstyle on: androidUtilLogs
        } else {
            //Checkstyle off: androidUtilLogs
            android.util.Log.d(tag, msg, e);
            //Checkstyle on: androidUtilLogs
        }
    }

    public void e(final String msg) {
        d(msg, null);
    }

    public void e(final String msg, final Throwable e) {
        if (e == null) {
            //Checkstyle off: androidUtilLogs
            android.util.Log.e(tag, msg);
            //Checkstyle on: androidUtilLogs
        } else {
            //Checkstyle off: androidUtilLogs
            android.util.Log.e(tag, msg, e);
            //Checkstyle on: androidUtilLogs
        }
    }

}
