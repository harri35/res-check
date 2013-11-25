package com.harrikirik.rescheck.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.BaseInfoObject;
import com.harrikirik.rescheck.dto.InfoHeader;
import com.harrikirik.rescheck.dto.InfoImageItem;
import com.harrikirik.rescheck.dto.InfoItem;

import java.util.ArrayList;

/**
 * Util class to get the actual device info
 * Harri Kirik, harri35@gmail.com
 */
public class InfoUtil {
    private static final int LOW_RAM_DEVICE_UNKNOWN = R.string.text_low_ram_device_unknown;
    private static final int LOW_RAM_DEVICE_YES = R.string.text_low_ram_device_yes;
    private static final int LOW_RAM_DEVICE_NO = R.string.text_low_ram_device_no;

    private static Log log = Log.getInstance(InfoUtil.class);

    public static ArrayList<BaseInfoObject> getFullInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();

        try {
            items.addAll(getGeneralDeviceInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getGeneralDeviceInfo", e);
        }

        try {
            items.addAll(getDisplayInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getDisplayInfo", e);
        }

        try {
            items.addAll(getScreenInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getScreenInfo", e);
        }

        try {
            items.addAll(getMemoryInfo(activity));
        } catch (Exception e) {
            log.e("getFullInfo -> getMemoryInfo", e);
        }

        return items;
    }

    private static ArrayList<BaseInfoObject> getGeneralDeviceInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        addHeader(items, activity.getString(R.string.text_device_general_info));
        addItem(items, activity.getString(R.string.text_model), android.os.Build.MODEL);
        addItem(items, activity.getString(R.string.text_device), android.os.Build.DEVICE);
        addItem(items, activity.getString(R.string.text_manufacturer), android.os.Build.MANUFACTURER);
        addItem(items, activity.getString(R.string.text_product), android.os.Build.PRODUCT);
        return items;
    }

    private static ArrayList<BaseInfoObject> getDisplayInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        final Display display = activity.getWindowManager().getDefaultDisplay();

        addHeader(items, activity.getString(R.string.text_device_display));
        addItem(items, activity.getString(R.string.text_display_id), String.valueOf(display.getDisplayId()));
        addItem(items, activity.getString(R.string.text_display_with), String.valueOf(display.getWidth()));
        addItem(items, activity.getString(R.string.text_display_height), String.valueOf(display.getHeight()));


        String orientation = activity.getString(R.string.text_orientation_unknown);
        switch (activity.getResources().getConfiguration().orientation) {
            case android.content.res.Configuration.ORIENTATION_LANDSCAPE:
                orientation = activity.getString(R.string.text_orientation_landscape);
                break;
            case android.content.res.Configuration.ORIENTATION_PORTRAIT:
                orientation = activity.getString(R.string.text_orientation_portrait);
                break;
            case android.content.res.Configuration.ORIENTATION_SQUARE:
                orientation = activity.getString(R.string.text_orientation_square);
                break;
        }
        addItem(items, activity.getString(R.string.text_display_orientation), orientation);

        return items;
    }

    public static ArrayList<BaseInfoObject> getScreenInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        addHeader(items, activity.getString(R.string.text_device_screen));
        items.add(new InfoImageItem(activity.getString(R.string.text_screen_density), R.drawable.icon_dpi));
        addItem(items, activity.getString(R.string.text_screen_density_value_dpi), String.valueOf(metrics.densityDpi));
        addItem(items, activity.getString(R.string.text_screen_density_value), String.valueOf(metrics.density));
        addItem(items, activity.getString(R.string.text_screen_scaled_density), String.valueOf(metrics.scaledDensity));

        final int screenSize = (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        String sizeValue = null;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            sizeValue = activity.getString(R.string.text_screen_normal_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            sizeValue = activity.getString(R.string.text_screen_small_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            sizeValue = activity.getString(R.string.text_screen_large_caps);
        } else if (screenSize == 4) {
            sizeValue = activity.getString(R.string.text_screen_xlarge_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
            sizeValue = activity.getString(R.string.text_screen_unknown_caps);
        }
        addItem(items, activity.getString(R.string.screen_size), sizeValue);

        final int screenLength = (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_LONG_MASK);

        String lengthValue = null;
        if (screenLength == Configuration.SCREENLAYOUT_LONG_YES) {
            lengthValue = activity.getString(R.string.screen_length_long_caps);
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_NO) {
            lengthValue = activity.getString(R.string.screen_length_notlong_caps);
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
            lengthValue = activity.getString(R.string.screen_length_unknown_caps);
        }
        addItem(items, activity.getString(R.string.text_screen_length), lengthValue);

        return items;
    }

    public static ArrayList<BaseInfoObject> getMemoryInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        addHeader(items, activity.getString(R.string.text_device_memory));
        addItem(items, activity.getString(R.string.text_device_memory_class_mb), activity.getString(R.string.text_x_mb, String.valueOf(getMemoryClass(activity) / 1024d / 1024d)));
        addItem(items, activity.getString(R.string.text_device_max_memory_mb), activity.getString(R.string.text_x_mb, String.valueOf((getAllowedMemory() / 1024d / 1024d))));
        addItem(items, activity.getString(R.string.text_low_ram_device), activity.getString(isLowRamDevice(activity)));
        return items;
    }

    public static String getFullInfoString(final Activity activity) {
        final StringBuilder builder = new StringBuilder();
        final ArrayList<BaseInfoObject> items = getFullInfo(activity);
        for (BaseInfoObject item : items) {
            if (item instanceof InfoImageItem) {
                // Skip
                continue;
            }

            if (item instanceof InfoHeader && builder.length() != 0) {
                builder.append("\n\n");
            } else if (builder.length() != 0) {
                builder.append("\n");
            }
            builder.append(item.toPrintableString());
        }

        return builder.toString();
    }

    private static void addHeader(ArrayList<BaseInfoObject> items, final String text) {
        items.add(new InfoHeader(text));
    }

    private static void addItem(ArrayList<BaseInfoObject> items, final String key, final String value) {
        items.add(new InfoItem(key, value));
    }

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static int isLowRamDevice(final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return LOW_RAM_DEVICE_UNKNOWN;
        }
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return LOW_RAM_DEVICE_UNKNOWN;
        }
        return activityManager.isLowRamDevice() ? LOW_RAM_DEVICE_YES : LOW_RAM_DEVICE_NO;
    }
}
