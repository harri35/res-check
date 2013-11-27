package com.harrikirik.rescheck.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.dto.*;

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
        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_general_info));
        addItem(items, activity.getString(R.string.text_model), android.os.Build.MODEL, cat);
        addItem(items, activity.getString(R.string.text_device), android.os.Build.DEVICE, cat);
        addItem(items, activity.getString(R.string.text_manufacturer), android.os.Build.MANUFACTURER, cat);
        addItem(items, activity.getString(R.string.text_product), android.os.Build.PRODUCT, cat);
        return items;
    }

    private static ArrayList<BaseInfoObject> getDisplayInfo(final Activity activity) {
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();

        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_display));
        addItem(items, activity.getString(R.string.text_display_id), String.valueOf(display.getDisplayId()), cat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            addItem(items, activity.getString(R.string.text_display_name), display.getName(), cat);
        } else {
            addItem(items, activity.getString(R.string.text_display_name), activity.getString(R.string.text_unknown), cat);
        }
        addItem(items, activity.getString(R.string.text_display_with), activity.getString(R.string.text_x_pixels, String.valueOf(metrics.widthPixels)), cat);
        addItem(items, activity.getString(R.string.text_display_height), activity.getString(R.string.text_x_pixels, String.valueOf(metrics.heightPixels)), cat);

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
        addItem(items, activity.getString(R.string.text_display_orientation), orientation, cat);

        return items;
    }

    public static ArrayList<BaseInfoObject> getScreenInfo(final Activity activity) {
        final Configuration conf = activity.getResources().getConfiguration();

        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_screen));
        addItem(items, activity.getString(R.string.text_screen_with), activity.getString(R.string.text_x_dpi, Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ? String.valueOf(conf.screenWidthDp) : activity.getString(R.string.text_unknown)), cat);
        addItem(items, activity.getString(R.string.text_screen_height), activity.getString(R.string.text_x_dpi, Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ? String.valueOf(conf.screenHeightDp) : activity.getString(R.string.text_unknown)), cat);
        addItem(items, activity.getString(R.string.text_screen_smallest_width_dp), Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ?  activity.getString(R.string.text_x_dpi, String.valueOf(conf.smallestScreenWidthDp)) : activity.getString(R.string.text_unknown), cat);

        items.add(new InfoImageItem(activity.getString(R.string.text_screen_density), R.drawable.icon_dpi, cat));
        addItem(items, activity.getString(R.string.text_screen_density_value_dpi), activity.getString(R.string.text_x_dpi, String.valueOf(metrics.densityDpi)), cat);
        addItem(items, activity.getString(R.string.text_screen_density_value), String.valueOf(metrics.density), cat);
        addItem(items, activity.getString(R.string.text_screen_scaled_density), String.valueOf(metrics.scaledDensity), cat);
        addItem(items, activity.getString(R.string.text_font_scale), String.valueOf(conf.fontScale), cat);

        final int screenSize = (conf.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        String sizeValue = null;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            sizeValue = activity.getString(R.string.text_screen_normal_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            sizeValue = activity.getString(R.string.text_screen_small_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            sizeValue = activity.getString(R.string.text_screen_large_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            sizeValue = activity.getString(R.string.text_screen_xlarge_caps);
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
            sizeValue = activity.getString(R.string.text_screen_unknown_caps);
        }
        addItem(items, activity.getString(R.string.screen_size), sizeValue, cat);

        final int screenLength = (conf.screenLayout & Configuration.SCREENLAYOUT_LONG_MASK);

        String lengthValue = null;
        if (screenLength == Configuration.SCREENLAYOUT_LONG_YES) {
            lengthValue = activity.getString(R.string.screen_length_long_caps);
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_NO) {
            lengthValue = activity.getString(R.string.screen_length_notlong_caps);
        } else if (screenLength == Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
            lengthValue = activity.getString(R.string.screen_length_unknown_caps);
        }
        addItem(items, activity.getString(R.string.text_screen_length), lengthValue, cat);


        String touchScreenValue = activity.getString(R.string.touchscreen_unknown);
        if (conf.touchscreen == Configuration.TOUCHSCREEN_FINGER) {
            touchScreenValue = activity.getString(R.string.touchscreen_finger);
        } else if (conf.touchscreen == Configuration.TOUCHSCREEN_NOTOUCH) {
            touchScreenValue = activity.getString(R.string.touchscreen_no_touch);
        }
        addItem(items, activity.getString(R.string.text_screen_touch_type), touchScreenValue, cat);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String layoutDirectionValue = null;
            if (conf.getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                layoutDirectionValue = activity.getString(R.string.screen_layout_direction_left_to_right);
            } else if (conf.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                layoutDirectionValue = activity.getString(R.string.screen_layout_direction_right_to_left);
            }
            addItem(items, activity.getString(R.string.text_screen_layout_direction), layoutDirectionValue, cat);
        } else {
            addItem(items, activity.getString(R.string.text_screen_layout_direction), activity.getString(R.string.text_unknown), cat);
        }

        return items;
    }

    public static ArrayList<BaseInfoObject> getMemoryInfo(final Activity activity) {
        final ArrayList<BaseInfoObject> items = new ArrayList<BaseInfoObject>();
        final InfoCategory cat = new InfoCategory(activity.getString(R.string.text_device_memory));
        addItem(items, activity.getString(R.string.text_device_memory_class_mb), activity.getString(R.string.text_x_mb, String.valueOf(getMemoryClass(activity) / 1024d / 1024d)), cat);
        addItem(items, activity.getString(R.string.text_device_max_memory_mb), activity.getString(R.string.text_x_mb, String.valueOf((getAllowedMemory() / 1024d / 1024d))), cat);
        addItem(items, activity.getString(R.string.text_low_ram_device), activity.getString(isLowRamDevice(activity)), cat);
        return items;
    }

    public static ArrayList<BaseInfoObject> createCategorizedInfo(final ArrayList<BaseInfoObject> items) {
        final ArrayList<BaseInfoObject> catItems = new ArrayList<BaseInfoObject>();
        if (items == null || items.size() == 0) {
            return catItems;
        }

        // Assume info is grouped by category already
        InfoCategory cat = null;
        for (BaseInfoObject o : items) {
            if (o instanceof CategorisedInfoItem && !((CategorisedInfoItem) o).getCategory().equals(cat)) {
                cat = new InfoCategory(((CategorisedInfoItem) o).getCategory().getName());
                catItems.add(cat);
            }
            catItems.add(o);
        }

        return catItems;
    }

    public static String getFullInfoString(final Activity activity) {
        final StringBuilder builder = new StringBuilder();
        final ArrayList<BaseInfoObject> items = createCategorizedInfo(getFullInfo(activity));
        for (BaseInfoObject item : items) {
            if (item instanceof InfoImageItem) {
                // Skip
                continue;
            }

            if (item instanceof InfoCategory && builder.length() != 0) {
                builder.append("\n\n");
            } else if (builder.length() != 0) {
                builder.append("\n");
            }
            builder.append(item.toPrintableString());
        }

        return builder.toString();
    }

    private static void addItem(ArrayList<BaseInfoObject> items, final String key, final String value, final InfoCategory cat) {
        items.add(new InfoItem(key, value, cat));
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
