package com.harrikirik.rescheck.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
import com.harrikirik.rescheck.R;

public class Util {

    private static Log log = Log.getInstance(Util.class);

    /**
     * Return the available memory size in bytes. <br/>
     * PS: This is usually the same as {@link #getMemoryClass(android.content.Context)} but may be also bigger is some cases, for example for rooted devices with custom builds or when android:largeHeap="true" is set in the Manifest<br/>
     * PPS: Going over this will crash the app.
     */
    public static long getAllowedMemory() {
        final long defaultValue = 16l * 1024l * 1024l;

        final Runtime runtime = Runtime.getRuntime();
        if (runtime != null) {
            final Long value = runtime.maxMemory();
            if (value == Long.MAX_VALUE) {
                // No value available
                return defaultValue;
            } else {
                return value;
            }

        }

        return defaultValue;
    }

    /**
     * Gives the device memory class, eg the max memory size in bytes that the app should use.<br/>
     * PS: Should be equal or smaller than {@link #getAllowedMemory()}<br/>
     * PPS: Going over this may or may not crash the app depending on the actual #getAllowedMemory() value. But it is strongly advised to stay under this limit.
     */
    public static long getMemoryClass(final Context context) {
        final long defaultValue = 16l * 1024l * 1024l;

        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        if (activityManager != null) {
            long memoryClass = activityManager.getMemoryClass() * 1024l * 1024l;
            return memoryClass;
        }

        return defaultValue;
    }

    public static void copyToClipboard(final Context context, final String content, final boolean showToast) {
        boolean success = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Newer versions
            success = copyToClipboardWithContentClipboardManager(context, content);
        } else {
            // Earlier versions
            success = copyToClipboardWithTextClipboardManager(context, content);
        }

        if (showToast && success) {
            Toast.makeText(context, context.getString(R.string.text_copied_to_clipboard), Toast.LENGTH_SHORT).show();
        } else if (showToast) {
            Toast.makeText(context, context.getString(R.string.err_unexpected_error), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("Depricated")
    private static boolean copyToClipboardWithTextClipboardManager(final Context context, final String content) {
        try {
            final android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(content);
            return true;
        } catch (Exception e) {
            log.e("copyToClipboardWithTextClipboardManager", e);
        }
        return false;
    }

    /**
     * PS: API level 11 and over
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static boolean copyToClipboardWithContentClipboardManager(final Context context, final String content) {
        Log.getInstance(Util.class).d("copyToClipboardWithContentClipboardManager - content: " + content);
        try {
            final android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            final ClipData clip = ClipData.newPlainText(context.getText(R.string.app_name), content);
            clipboard.setPrimaryClip(clip);
            return true;
        } catch (Exception e) {
            Log.getInstance(Util.class).e("copyToClipboardWithContentClipboardManager", e);
        }
        return false;
    }

    public static void shareText(final Context context, final String chooserTitle, final String text) {
        Log.getInstance(Util.class).d("shareText: " + text);

        try {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(intent, chooserTitle));
        } catch (Exception e) {
            Log.getInstance(Util.class).e("shareText", e);
        }
    }
}
